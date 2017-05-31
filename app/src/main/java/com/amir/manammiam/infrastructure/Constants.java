package com.amir.manammiam.infrastructure;

public class Constants {
    public static final int MINIMUM_PRICE = 500;
    public static final int MAXIMUM_PRICE = Integer.MAX_VALUE;
    public static final Integer MAXIMUM_CAPACITY = 10;
    public static final Integer MINIMUM_CAPACITY = 1;
    public static final long ANIMATION_DURATION = 350;
    public static final int FAB_SHOW_HIDE_INTENSITY = 15;

    public static final String TELEGRAM_LINK = "https://telegram.me/";

    public static final String TELEGRAM_PACKAGE = "org.telegram.messenger";
    public static final String MOBOGRAM_PACKAGE = "com.hanista.mobogram";
    public static final String MOBOGRAM_TWO = "com.hanista.mobogram.two";
    public static final String TELEGRAPH_PACKAGE = "ir.ilmili.telegraph";
    public static final String TELEGRAM_PLUS_PACKAGE = "org.telegram.plus";
    public static final String PERSIAN_TELEGRAM = "ir.persianfox.messenger";
    public static final String ORANGE_TELEGRAM = "org.telegram.comorangetelegram";
    public static final String PERSIAN_VOICE_TELEGRAM = "ir.rrgc.telegram";
    public static final String MY_TELEGRAM = "ir.alimodaresi.mytelegram";
    public static final String ANIWAYS = "com.aniways.anigram.messenger";
    public static final String LAGATGRAM = "org.ilwt.lagatgram";

    public static final String[] TELEGRAM_PACKAGES = {TELEGRAM_PACKAGE
            , MOBOGRAM_PACKAGE
            , MOBOGRAM_TWO
            , TELEGRAPH_PACKAGE
            , TELEGRAM_PLUS_PACKAGE
            , PERSIAN_TELEGRAM
            , ORANGE_TELEGRAM
            , PERSIAN_VOICE_TELEGRAM
            , MY_TELEGRAM
            , ANIWAYS
            , LAGATGRAM};

//    public static final String BASE_URL = "http://stickergramapp.com/manammiam/";
    public static final String BASE_URL = "http://10.0.2.2/manammyam/";
    public static final String CONCAT_URL = "getData.php";

    public static final String ACTION_TYPE = "action_type";

    public static final Integer LOGIN_USER = 0;
    public static final Integer GET_VERIFIED_CARS = 1;
    public static final Integer GET_LOCATIONS = 2;
    public static final Integer GET_LOCATIONS_EXCEPT_ONE = 3; //probably won't be used
    public static final Integer ADD_SERVER_WITH_NO_NOTIFICATION = 4;
    public static final Integer GET_PERSON_S_CAR = 5;
    public static final Integer ADD_A_CAR = 6;
    public static final Integer ENROLL_USER = 7;
    public static final Integer ADD_LOCATION = 8;
    public static final Integer ADD_PASSENGER = 9;
    public static final Integer GET_UNREAD_POSTS_OF_A_USER = 10; //commented out on the server side (need to take care of the new TEXT table)
    public static final Integer GET_SERVERS = 11;
    public static final Integer GET_AVAILABE_PASSENGERS = 12;
    public static final Integer ADD_REPORT = 13;
    public static final Integer GET_POSTS = 14;
    public static final Integer UPDATE_RATE = 15; //add a fucking rate table you lazy bitch
    public static final Integer LOGIN_WITH_TOKEN = 16;
    public static final Integer GET_TRIPS = 17;
    public static final Integer IS_IN_THE_FUTURE = 18;
    public static final Integer GET_SERVER_SPECIFIC = 19; //defined source and destination
    public static final Integer SEARCH_LOCATIONS = 20;
    public static final Integer CANCEL_PASSENGER = 21;
    public static final Integer CANCEL_SERVER = 22;
    public static final Integer RESERVE_SERVER = 23;
    public static final Integer ADD_SERVER_WITH_NOTIFICATION_TO_A_PASSENGER = 24;
    public static final Integer LOGOUT = 25;
    public static final int SERVICES_FRAGMENT_PAGE_NUMBER = 1;
    public static final int INBOX_FRAGMENT_PAGE_NUMBER = 2;
    public static final int PROFILE_FRAGMENT_PAGE_NUMBER = 3;
    public static final int TRIPS_FRAGMENT_PAGE_NUMBER = 0;


    private Constants() {
    }
}
