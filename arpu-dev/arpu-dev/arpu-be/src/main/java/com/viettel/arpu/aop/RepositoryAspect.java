package com.viettel.arpu.aop;

import com.viettel.arpu.exception.MbResponse;
import com.viettel.arpu.exception.MbResponseException;
import com.viettel.arpu.model.entity.LogApi;
import com.viettel.arpu.model.response.mb.MbBaseResponse;
import com.viettel.arpu.repository.LogApiRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Aspect for all repositories
 */
@Aspect
@Configuration
@Slf4j
public class RepositoryAspect {
    @Value("${application.repository.query-limit-warning-ms}")
    private int executionLimitMs;

    @Autowired
    LogApiRepository logApiRepository;

    /**
     * logExecutionTime Logger all sql query slow
     *
     * @param joinPoint
     * @return Object
     * @throws Throwable
     * @author xuatdd
     * @version 1.0
     * @since 2020/03/01
     */
    @Around("execution(* com.viettel.arpu.repository.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        String message = joinPoint.getSignature() + " exec in " + executionTime + " ms";
        if (executionTime >= executionLimitMs) {
            log.warn(message + " : SLOW QUERY");
        }
        return proceed;
    }

    @Around("execution(* com.viettel.arpu.service.mb.*.*(..))")
    public Object logAroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        LogApi logApi = getLogApiBeforeController(joinPoint);
        String res = null;
        MbBaseResponse mbBaseResponse = null;

        try {
            mbBaseResponse = (MbBaseResponse)joinPoint.proceed(args);
            logApi.setResponseBodyInfo(mbBaseResponse.toString());
            MbResponse.from(mbBaseResponse.getErrorCode(), mbBaseResponse.getErrorDesc()).check();
        } catch (MbResponseException e) {
            log.error("Mb exception", e);
            logApi.setResponseBodyInfo(e.toString());
            throw e;
        } catch (ResourceAccessException e) {
            log.error("time out exception", e);
            logApi.setResponseBodyInfo(StringUtils.EMPTY);
            throw e;
        } catch (Exception e) {
            log.error("server exception", e);
            logApi.setResponseBodyInfo(StringUtils.EMPTY);
            throw e;
        } finally {
            logApiRepository.save(logApi);
        }

        return mbBaseResponse;
    }

    protected LogApi getLogApiBeforeController(ProceedingJoinPoint joinPoint) {
        if (joinPoint == null) return new LogApi();
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder
                        .currentRequestAttributes())
                        .getRequest();

        LogApi logApi = new LogApi();

        List<Object> requestBodies = new ArrayList<>();
        Arrays.stream(joinPoint.getArgs()).forEach(e -> requestBodies.add(e));
        logApi.setRequestBodyInfo(requestBodies.toString());

        String requestInfo = getRequestInfoString();
        logApi.setRequestHeaderInfo(requestInfo);

        String requestId = request.getHeader("requestId");
        logApi.setRequestId(requestId);
        return logApi;
    }

    public static String getRequestInfoString() {
        try {
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder
                            .currentRequestAttributes())
                            .getRequest();

            return new StringBuilder()
                    .append(request.getMethod())
                    .append(StringUtils.SPACE)
                    .append(request.getRequestURI())
                    .toString();

        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

}
