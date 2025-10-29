package com.trello.api.config;


public class Config {
    
    // Trello API Base URL
    public static final String BASE_URL = "https://api.trello.com/1";
    
    // API Version
    public static final String API_VERSION = "1";
    
    // Endpoints
    public static final String BOARDS_ENDPOINT = "/boards";
    public static final String CARDS_ENDPOINT = "/cards";
    public static final String LISTS_ENDPOINT = "/lists";
    
    // Query Parameters
    public static final String KEY_PARAM = "key";
    public static final String TOKEN_PARAM = "token";
    public static final String NAME_PARAM = "name";
    public static final String DESC_PARAM = "desc";
    public static final String ID_LIST_PARAM = "idList";
    public static final String ID_BOARD_PARAM = "idBoard";
    
    // Default Board Settings
    public static final String DEFAULT_BOARD_NAME = "Test Board";
    public static final String DEFAULT_BOARD_DESC = "Board created for API testing";
    
    // Default Card Settings
    public static final String DEFAULT_CARD_NAME_1 = "Test Card 1";
    public static final String DEFAULT_CARD_NAME_2 = "Test Card 2";
    public static final String DEFAULT_CARD_DESC = "Card created for API testing";
    public static final String UPDATED_CARD_DESC = "Updated card description";
    
    // Response Status Codes
    public static final int OK_STATUS = 200;
    public static final int CREATED_STATUS = 201;
    public static final int NO_CONTENT_STATUS = 204;
    public static final int BAD_REQUEST_STATUS = 400;
    public static final int UNAUTHORIZED_STATUS = 401;
    public static final int NOT_FOUND_STATUS = 404;
}
