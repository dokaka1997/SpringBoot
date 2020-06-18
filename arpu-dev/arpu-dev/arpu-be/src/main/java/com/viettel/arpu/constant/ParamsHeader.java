package com.viettel.arpu.constant;

import org.springframework.http.HttpHeaders;

/**
 * @Author VuHQ
 * @Since 6/8/2020
 */
public class ParamsHeader {
    private final HttpHeaders mapHeader = new HttpHeaders();

    public static ParamsHeader paramsHeader(){
        return new ParamsHeader();
    }

    public ParamsHeader serviceChanel(String serviceChanel){
        this.mapHeader.add("serviceChanel", serviceChanel);
        return this;
    }

    public ParamsHeader serviceCode(String serviceCode){
        this.mapHeader.add("serviceCode", serviceCode);
        return this;
    }

    public ParamsHeader serviceIndicator(String serviceIndicator){
        this.mapHeader.add("serviceIndicator", serviceIndicator);
        return this;
    }

    public ParamsHeader systemTrace(String systemTrace){
        this.mapHeader.add("systemTrace", systemTrace);
        return this;
    }

    public ParamsHeader setRequestId(String requestId) {
        this.mapHeader.add("requestId", requestId);
        return this;
    }

    public HttpHeaders build(){
        return this.mapHeader;
    }

}
