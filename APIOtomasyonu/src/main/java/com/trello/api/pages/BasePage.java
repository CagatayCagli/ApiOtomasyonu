package com.trello.api.pages;

import com.trello.api.config.ApiCredentials;
import com.trello.api.config.Config;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Base Page Object class for Trello API
 * Contains common functionality for all API operations
 */
public abstract class BasePage {
    
    protected RequestSpecification requestSpec;
    
    /**
     * Constructor initializes the base request specification
     */
    public BasePage() {
        initializeRequestSpec();
    }
    
    /**
     * Initializes the base request specification with common parameters
     */
    private void initializeRequestSpec() {
        requestSpec = RestAssured.given()
                .baseUri(Config.BASE_URL)
                .queryParam(Config.KEY_PARAM, ApiCredentials.getApiKey())
                .queryParam(Config.TOKEN_PARAM, ApiCredentials.getApiToken());
    }
    
    /**
     * Validates if API credentials are available
     * @throws IllegalStateException if credentials are not loaded
     */
    protected void validateCredentials() {
        if (!ApiCredentials.areCredentialsLoaded()) {
            throw new IllegalStateException("API credentials are not loaded. Please check your api.properties file.");
        }
    }
    
    /**
     * Logs the response for debugging purposes
     * @param response The response to log
     */
    protected void logResponse(Response response) {
        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
    }
    
    /**
     * Validates the response status code
     * @param response The response to validate
     * @param expectedStatus Expected status code
     * @throws AssertionError if status codes don't match
     */
    protected void validateStatusCode(Response response, int expectedStatus) {
        if (response.getStatusCode() != expectedStatus) {
            throw new AssertionError("Expected status code " + expectedStatus + 
                    " but got " + response.getStatusCode() + 
                    ". Response: " + response.getBody().asString());
        }
    }
}
