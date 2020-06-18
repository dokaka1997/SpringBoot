package com.viettel.arpu.constant;

import com.viettel.arpu.model.response.ErrorResponse;

public interface ErrorConstant {
    ErrorResponse CODE_NOT_FOUND = new ErrorResponse("ARPU406", "msg.code_not_found");
    ErrorResponse CUSTOMER_VERSION_INVALID = new ErrorResponse("ARPU301", "msg.customer_version_invalid");
    ErrorResponse CUSTOMER_NOT_FOUND = new ErrorResponse("ARPU404", "error.msg.whitelist.id_not_found");
    ErrorResponse CUSTOMER_HAS_BEEN_LOCK = new ErrorResponse("ARPU407", "msg.customer_has_been_lock");
    ErrorResponse CUSTOMER_HAS_BEEN_UNLOCK = new ErrorResponse("ARPU408", "msg.customer_has_been_unlock");

    ErrorResponse LOAN_HAS_BEEN_APPROVED = new ErrorResponse("ARPU400", "msg.loan_has_been_approved");
    ErrorResponse CODE_INPUT_INVALID = new ErrorResponse("ARPU400", "msg.code_input_invalid");
    ErrorResponse LOAN_STATUS_INVALID = new ErrorResponse("ARPU400", "msg.loan_status_invalid");
    ErrorResponse ID_INVALID = new ErrorResponse("ARPU400", "msg.id_invalid");
    ErrorResponse REASON = new ErrorResponse("ARPU400", "msg.reason_missing");
}
