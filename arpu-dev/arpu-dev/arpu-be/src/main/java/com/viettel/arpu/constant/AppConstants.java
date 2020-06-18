package com.viettel.arpu.constant;

/**
 * @author trungnb3
 * @Date :6/3/2020, Wed
 */
public interface AppConstants {
    //System code
    MessageCode OK = new MessageCode("00", "00");
    MessageCode NOT_FOUND = new MessageCode("ARPU404", "msg.not_found");
    MessageCode SUCCESS = new MessageCode("ARPU200", "msg.success");
    MessageCode NO_CONTENT = new MessageCode("ARPU204", "msg.success");
    MessageCode INTERNAL_SERVER_ERROR = new MessageCode("ARPU500", "error.msg.internal_server");
    MessageCode BAD_REQUEST = new MessageCode("ARPU400", "msg.bad_request");
    MessageCode CONNECTION_TIMEOUT = new MessageCode("ARPU504", "msg.connection_timeout");

    //response for customer
    MessageCode MSG_30 = new MessageCode("ARPU030", "msg_30");
    MessageCode MSG_32 = new MessageCode("ARPU032", "msg_32");
    MessageCode MSG_23 = new MessageCode("ARPU023", "msg_23");
    //response for loan
    MessageCode LOAN_NOT_FOUND = new MessageCode("ARPU405", "msg.loan_not_found");

    //response for file
    MessageCode FILE_NOT_FOUND = new MessageCode("ARPU407", "msg.file_not_found");

    MessageCode FILE_INVALID = new MessageCode("ARPU400", "msg.file_invalid");

    //validator
    MessageCode TYPE_MISMATCH = new MessageCode("error.msg.request.param.type.mismatch");
    MessageCode MISSING_SERVLET_REQUEST_PARAMETER = new MessageCode("error.msg.request.param.require");

}
