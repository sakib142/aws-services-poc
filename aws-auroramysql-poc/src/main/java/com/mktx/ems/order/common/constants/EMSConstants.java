package com.mktx.ems.order.common.constants;

/**
 * Util class to store EMS application constants
 * 
 * @author rguduri
 *
 */
public class EMSConstants {

    private EMSConstants() {
        throw new IllegalStateException("EMS Constant class");
    }

    /**
     * EndPoints & Routes
     */
    public static final String FIX_MESSAGE_ROUTE_ENDPOINT = "direct:IncomingFixMessage";
    public static final String FIX_MESSAGE_ROUTE = "IncomingFixMessageRoute";
    public static final String FIX_LOGIN_MESSAGE_ENDPOINT = "direct:LoginFixMessage";
    public static final String INCOMING_LOGON_MESSAGE_ROUTE = "FixIncomingLogonMessage";
    public static final String FIX_LOGOFF_MESSAGE_ENDPOINT = "direct:LogoffFixMessage";
    public static final String INCOMING_LOGOFF_MESSAGE_ROUTE = "FixIncomingLogoffMessage";
    public static final String ORDER_ROUTE = "NewOrderRoute";
    public static final String ORDER_QUEUE = "activemq:queue:ems.order";
    public static final String BROADCAST_ORDER_TOPIC = "activemq:topic:broadcast.order";
    public static final String BROADCAST_ORDER_ROUTE = "BroadcastOrderRoute";
    public static final String FIX_RESPONSE_ENDPOINT = "direct:OutgoingFixMessage";
    public static final String FIX_RESPONSE_ROUTE = "FixResponseRoute";

    /**
     * Bean method name to invoke of Route definition
     */
    public static final String VALIDATE_LOGON = "validateLogon";
    public static final String SEND_FIX_MESSAGE = "sendFixMessage";
    public static final String INJECT_FIX_CONNECTION_ID = "injectFixConnectionID";
    public static final String MARKETDEPTH_JSONDATAFORMAT = "marketDepthJacksonDataFormat";
    public static final String ORDERDTO_JSONDATAFORMAT = "orderDTOJacksonDataFormat";
    public static final String RESOLVE_SESSIONID_HEADER = "resolveSessionIDHeader";

    /**
     * Constant using in transaction of the camel routes
     */
    public static final String PROPAGATION_REQUIRED = "PROPAGATION_REQUIRED";

    /**
     * FIX Constants
     */
    public static final String SESSION_ID_KEY = "SessionID";
    public static final String FIX_SESSION_NOT_LOGGED_ON_HEADER = "FixSessionNotLoggedOn";

    /**
     * Constant for COMMA symbol
     */
    public static final String COMMA = ",";

    /**
     * Fix Execution report related constants
     */
    public static final String ORDER_SYMBOL = "FIXED";

    /**
     * Enabled/Disabled Char constants
     */
    public static final Character TRUE_CHAR = 'Y';
    public static final Character FALSE_CHAR = 'N';

    /**
     * Miscellaneous constants
     */
    public static final String LAST_CHANGE_USER_NEW = "OMS-New";
    public static final String LAST_CHANGE_USER_CANCEL = "OMS-Cancel";
    public static final String GET_METHOD_PREFIX = "get";
    public static final String REMOVED = "?";
}
