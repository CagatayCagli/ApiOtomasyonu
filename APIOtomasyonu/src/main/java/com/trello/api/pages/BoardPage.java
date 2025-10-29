package com.trello.api.pages;

import com.trello.api.config.Config;
import com.trello.api.models.Board;
import com.trello.api.models.List;
import io.restassured.response.Response;

import java.util.Arrays;

/**
 * Page Object class for Trello Board API operations
 * Implements Board-related API calls using Page Object Pattern
 */
public class BoardPage extends BasePage {
    
    /**
     * Creates a new board
     * @param boardName Name of the board to create
     * @return Board object with created board details
     */
    public Board createBoard(String boardName) {
        validateCredentials();
        
        Response response = requestSpec
                .queryParam(Config.NAME_PARAM, boardName)
                .when()
                .post(Config.BOARDS_ENDPOINT);
        
        logResponse(response);
        // Trello API returns 200 for board creation, not 201
        validateStatusCode(response, Config.OK_STATUS);
        
        return response.as(Board.class);
    }
    
    /**
     * Creates a new board with description
     * @param boardName Name of the board to create
     * @param description Description of the board
     * @return Board object with created board details
     */
    public Board createBoard(String boardName, String description) {
        validateCredentials();
        
        Response response = requestSpec
                .queryParam(Config.NAME_PARAM, boardName)
                .queryParam(Config.DESC_PARAM, description)
                .when()
                .post(Config.BOARDS_ENDPOINT);
        
        logResponse(response);
        // Trello API returns 200 for board creation, not 201
        validateStatusCode(response, Config.OK_STATUS);
        
        return response.as(Board.class);
    }
    
    /**
     * Gets board details by ID
     * @param boardId ID of the board to retrieve
     * @return Board object with board details
     */
    public Board getBoard(String boardId) {
        validateCredentials();
        
        Response response = requestSpec
                .when()
                .get(Config.BOARDS_ENDPOINT + "/" + boardId);
        
        logResponse(response);
        validateStatusCode(response, Config.OK_STATUS);
        
        return response.as(Board.class);
    }
    
    /**
     * Updates a board
     * @param boardId ID of the board to update
     * @param boardName New name for the board
     * @param description New description for the board
     * @return Board object with updated board details
     */
    public Board updateBoard(String boardId, String boardName, String description) {
        validateCredentials();
        
        Response response = requestSpec
                .queryParam(Config.NAME_PARAM, boardName)
                .queryParam(Config.DESC_PARAM, description)
                .when()
                .put(Config.BOARDS_ENDPOINT + "/" + boardId);
        
        logResponse(response);
        validateStatusCode(response, Config.OK_STATUS);
        
        return response.as(Board.class);
    }
    
    /**
     * Deletes a board
     * @param boardId ID of the board to delete
     * @return Response object
     */
    public Response deleteBoard(String boardId) {
        validateCredentials();
        
        Response response = requestSpec
                .when()
                .delete(Config.BOARDS_ENDPOINT + "/" + boardId);
        
        logResponse(response);
        validateStatusCode(response, Config.OK_STATUS);
        
        return response;
    }
    
    /**
     * Gets all lists in a board
     * @param boardId ID of the board
     * @return List of List objects
     */
    public java.util.List<List> getBoardLists(String boardId) {
        validateCredentials();
        int attempts = 0;
        while (true) {
            attempts++;
            Response response = requestSpec
                    .when()
                    .get(Config.BOARDS_ENDPOINT + "/" + boardId + Config.LISTS_ENDPOINT);

            logResponse(response);
            validateStatusCode(response, Config.OK_STATUS);

            java.util.List<List> lists = Arrays.asList(response.as(List[].class));
            if (!lists.isEmpty() || attempts >= 3) {
                return lists;
            }
            try {
                Thread.sleep(500L * attempts);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return lists;
            }
        }
    }
    
    /**
     * Gets the first list (To Do list) from a board
     * @param boardId ID of the board
     * @return First List object or null if no lists found
     */
    public List getFirstList(String boardId) {
        java.util.List<List> lists = getBoardLists(boardId);
        return lists.isEmpty() ? null : lists.get(0);
    }
}
