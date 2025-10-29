package com.trello.api.pages;

import com.trello.api.config.Config;
import com.trello.api.models.Card;
import io.restassured.response.Response;

import java.util.Arrays;

/**
 * Page Object class for Trello Card API operations
 * Implements Card-related API calls using Page Object Pattern
 */
public class CardPage extends BasePage {
    
    /**
     * Creates a new card
     * @param cardName Name of the card to create
     * @param listId ID of the list to create the card in
     * @return Card object with created card details
     */
    public Card createCard(String cardName, String listId) {
        validateCredentials();
        return createCardWithRetry(cardName, null, listId);
    }
    
    /**
     * Creates a new card with description
     * @param cardName Name of the card to create
     * @param description Description of the card
     * @param listId ID of the list to create the card in
     * @return Card object with created card details
     */
    public Card createCard(String cardName, String description, String listId) {
        validateCredentials();
        return createCardWithRetry(cardName, description, listId);
    }

    private Card createCardWithRetry(String cardName, String description, String listId) {
        int attempts = 0;
        while (true) {
            attempts++;
            io.restassured.specification.RequestSpecification spec = requestSpec
                    .queryParam(Config.NAME_PARAM, cardName)
                    .queryParam(Config.ID_LIST_PARAM, listId);

            if (description != null) {
                spec = spec.queryParam(Config.DESC_PARAM, description);
            }

            Response response = spec.when().post(Config.CARDS_ENDPOINT);

            logResponse(response);

            if (response.getStatusCode() == Config.OK_STATUS) {
                return response.as(Card.class);
            }

            String body = response.getBody() == null ? "" : response.getBody().asString();
            boolean retryable = response.getStatusCode() == 400 && (body.contains("invalid value for idList") || body.contains("invalid idList") || body.contains("invalid id") || body.toLowerCase().contains("error parsing body"));
            if (!retryable || attempts >= 3) {
                validateStatusCode(response, Config.OK_STATUS);
                return response.as(Card.class);
            }

            // On first retry, try without description to avoid parsing issues
            if (attempts == 1 && description != null) {
                description = null;
            }

            try {
                Thread.sleep(800L * attempts);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * Gets card details by ID
     * @param cardId ID of the card to retrieve
     * @return Card object with card details
     */
    public Card getCard(String cardId) {
        validateCredentials();
        
        Response response = requestSpec
                .when()
                .get(Config.CARDS_ENDPOINT + "/" + cardId);
        
        logResponse(response);
        validateStatusCode(response, Config.OK_STATUS);
        
        return response.as(Card.class);
    }
    
    /**
     * Updates a card
     * @param cardId ID of the card to update
     * @param cardName New name for the card
     * @param description New description for the card
     * @return Card object with updated card details
     */
    public Card updateCard(String cardId, String cardName, String description) {
        validateCredentials();
        
        Response response = requestSpec
                .queryParam(Config.NAME_PARAM, cardName)
                .queryParam(Config.DESC_PARAM, description)
                .when()
                .put(Config.CARDS_ENDPOINT + "/" + cardId);
        
        logResponse(response);
        validateStatusCode(response, Config.OK_STATUS);
        
        return response.as(Card.class);
    }
    
    /**
     * Updates only the description of a card
     * @param cardId ID of the card to update
     * @param description New description for the card
     * @return Card object with updated card details
     */
    public Card updateCardDescription(String cardId, String description) {
        validateCredentials();
        int attempts = 0;
        while (true) {
            attempts++;
            Response response = requestSpec
                    .queryParam(Config.DESC_PARAM, description)
                    .when()
                    .put(Config.CARDS_ENDPOINT + "/" + cardId);

            logResponse(response);
            if (response.getStatusCode() == Config.OK_STATUS) {
                return response.as(Card.class);
            }
            String body = response.getBody() == null ? "" : response.getBody().asString();
            boolean retryable = response.getStatusCode() == 400 && body.contains("invalid id");
            if (!retryable || attempts >= 3) {
                validateStatusCode(response, Config.OK_STATUS);
                return response.as(Card.class);
            }
            try {
                Thread.sleep(800L * attempts);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * Deletes a card
     * @param cardId ID of the card to delete
     * @return Response object
     */
    public Response deleteCard(String cardId) {
        validateCredentials();
        
        Response response = requestSpec
                .when()
                .delete(Config.CARDS_ENDPOINT + "/" + cardId);
        
        logResponse(response);
        validateStatusCode(response, Config.OK_STATUS);
        
        return response;
    }
    
    /**
     * Gets all cards in a list
     * @param listId ID of the list
     * @return List of Card objects
     */
    public java.util.List<Card> getCardsInList(String listId) {
        validateCredentials();
        
        Response response = requestSpec
                .when()
                .get(Config.LISTS_ENDPOINT + "/" + listId + Config.CARDS_ENDPOINT);
        
        logResponse(response);
        validateStatusCode(response, Config.OK_STATUS);
        
        return Arrays.asList(response.as(Card[].class));
    }
    
    /**
     * Gets all cards in a board
     * @param boardId ID of the board
     * @return List of Card objects
     */
    public java.util.List<Card> getCardsInBoard(String boardId) {
        validateCredentials();
        
        Response response = requestSpec
                .when()
                .get(Config.BOARDS_ENDPOINT + "/" + boardId + Config.CARDS_ENDPOINT);
        
        logResponse(response);
        validateStatusCode(response, Config.OK_STATUS);
        
        return Arrays.asList(response.as(Card[].class));
    }

    /**
     * Copies an existing card
     * @param sourceCardId The ID of the card to copy
     * @param targetListId The list to place the copied card in
     * @param newName Optional new name
     * @return The new copied Card
     */
    public Card copyCard(String sourceCardId, String targetListId, String newName) {
        validateCredentials();
        io.restassured.specification.RequestSpecification spec = requestSpec
                .queryParam("idCardSource", sourceCardId)
                .queryParam(Config.ID_LIST_PARAM, targetListId)
                .queryParam("keepFromSource", "all");
        if (newName != null && !newName.isEmpty()) {
            spec = spec.queryParam(Config.NAME_PARAM, newName);
        }
        Response response = spec.when().post(Config.CARDS_ENDPOINT);
        logResponse(response);
        validateStatusCode(response, Config.OK_STATUS);
        return response.as(Card.class);
    }
}
