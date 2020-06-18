package com.viettel.arpu.constant;

import com.viettel.arpu.constant.enums.ServiceChanel;

/**
 * @Author VuHQ
 * @Since 6/8/2020
 */
public interface HeaderConstants {
    ParamsHeader POST_CHECK_LOAN = ParamsHeader.paramsHeader()
                                        .serviceChanel(ServiceChanel.COUNTER.getCode())
                                        .serviceCode("052001").serviceIndicator("").systemTrace("post check loan");

    ParamsHeader POST_CREATE_LOAN = ParamsHeader.paramsHeader()
                                        .serviceChanel(ServiceChanel.COUNTER.getCode())
                                        .serviceCode("052002").serviceIndicator("").systemTrace("post create loan");

    ParamsHeader PUT_CREATE_LOAN = ParamsHeader.paramsHeader()
                                        .serviceChanel(ServiceChanel.COUNTER.getCode())
                                        .serviceCode("052003").serviceIndicator("").systemTrace("put create loan");

    ParamsHeader GET_CREATE_LOAN = ParamsHeader.paramsHeader()
                                        .serviceChanel(ServiceChanel.COUNTER.getCode())
                                        .serviceCode("052004").serviceIndicator("").systemTrace("get create loan");

    ParamsHeader POST_INCREASE_LIMIT = ParamsHeader.paramsHeader()
                                        .serviceChanel(ServiceChanel.COUNTER.getCode())
                                        .serviceCode("052005").serviceIndicator("").systemTrace("post increase limit");

    ParamsHeader PUT_INCREASE_LIMIT = ParamsHeader.paramsHeader()
                                        .serviceChanel(ServiceChanel.COUNTER.getCode())
                                        .serviceCode("052006").serviceIndicator("").systemTrace("put increase limit");

    ParamsHeader POST_REDUCE_LIMIT = ParamsHeader.paramsHeader()
                                        .serviceChanel(ServiceChanel.COUNTER.getCode())
                                        .serviceCode("052007").serviceIndicator("").systemTrace("post reduce limit");

    ParamsHeader PUT_REDUCE_LIMIT = ParamsHeader.paramsHeader()
                                        .serviceChanel(ServiceChanel.COUNTER.getCode())
                                        .serviceCode("052008").serviceIndicator("").systemTrace("put reduce limit");

    ParamsHeader POST_PAY_LOAN = ParamsHeader.paramsHeader()
                                        .serviceChanel(ServiceChanel.COUNTER.getCode())
                                        .serviceCode("052009").serviceIndicator("").systemTrace("post pay loan");

    ParamsHeader PUT_PAY_LOAN = ParamsHeader.paramsHeader()
                                        .serviceChanel(ServiceChanel.COUNTER.getCode())
                                        .serviceCode("052010").serviceIndicator("").systemTrace("put pay loan");

    ParamsHeader POST_FINAL_LOAN = ParamsHeader.paramsHeader()
                                        .serviceChanel(ServiceChanel.COUNTER.getCode())
                                        .serviceCode("052011").serviceIndicator("").systemTrace("post final loan");

    ParamsHeader PUT_FINAL_LOAN = ParamsHeader.paramsHeader()
                                        .serviceChanel(ServiceChanel.COUNTER.getCode())
                                        .serviceCode("052012").serviceIndicator("").systemTrace("put final loan");

    ParamsHeader POST_HISTORY_LOAN = ParamsHeader.paramsHeader()
                                        .serviceChanel(ServiceChanel.COUNTER.getCode())
                                        .serviceCode("052013").serviceIndicator("").systemTrace("post history loan");

    ParamsHeader GET_DETAIL_LOAN = ParamsHeader.paramsHeader()
                                        .serviceChanel(ServiceChanel.COUNTER.getCode())
                                        .serviceCode("052014").serviceIndicator("").systemTrace("get loan detail");

    ParamsHeader POST_KYC = ParamsHeader.paramsHeader()
            .serviceChanel(ServiceChanel.MOBILE.getCode())
            .serviceCode("031101").serviceIndicator("").systemTrace("post kyc");
}
