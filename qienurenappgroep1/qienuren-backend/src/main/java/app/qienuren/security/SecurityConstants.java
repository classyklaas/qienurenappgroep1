package app.qienuren.security;

import app.qienuren.QienurenApplicationContext;


public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/users";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) QienurenApplicationContext.getBean("AppProperties");  //method to acces components that were created by springframework
        return appProperties.getTokenSecret();
    }
}