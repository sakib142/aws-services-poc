package com.mktx.ems.order.common.constants;

public class Headers {

    private Headers() {
        throw new IllegalStateException("Headers Utility class");
    }

    public static final String EMS_ERROR = "EMS_ERROR";
    public static final String EMS_EVENT_PROTOCOL = "EMS_EVENT_PROTOCOL";
    public static final String EMS_EVENT_URL = "EMS_EVENT_URL";
    public static final String EMS_EVENT_TYPE = "EMS_EVENT_TYPE";
    public static final String EMS_SESSION_ID = "EMS_SESSION_ID";
    public static final String EMS_REQUEST_ID = "EMS_REQUEST_ID";
    public static final String EMS_USER_ID = "EMS_USER_ID";

    public static final String EMS_FIRM_ID = "EMS_FIRM_ID";
    public static final String EMS_INST_ID = "EMS_INST_ID";

    public static final String JMSXGroupID = "JMSXGroupID";

    public static final String SESSION_ID_KEY = "SessionID";
    public static final String FIX_CONNECTION_KEY = "EMSFixConnection";

    public static final String WS_CLIENT_ID = "ClientID";

}
