package com.viettel.arpu.service.mb;

import com.viettel.arpu.constant.ParamsHeader;
import com.viettel.arpu.model.response.mb.MbBaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author VuHQ
 * @Since 6/5/2020
 */
public abstract class AbstractMbService {
    @Autowired
    RetryTemplate retryTemplate;

    @Autowired
    RestTemplate restTemplate;
    protected <T extends MbBaseResponse> T sendToMbAPI(Object form, HttpMethod httpMethod, String url, Class<T> mbClass, ParamsHeader headersValue) {

        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder
                        .currentRequestAttributes())
                        .getRequest();

        RetryCallback<T, RestClientException> result = retryContext -> restTemplate.exchange(url
                , httpMethod, new HttpEntity<>(form, headersValue.setRequestId(request.getHeader("requestId")).build())
                , mbClass).getBody();
        return retryTemplate.execute(result);
    }
}
