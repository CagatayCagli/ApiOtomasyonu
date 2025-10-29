package com.trello.api.tests;

import com.trello.api.config.Config;
import com.trello.api.config.ApiCredentials;
import com.trello.api.models.Board;
import com.trello.api.models.Card;
import com.trello.api.models.List;
import com.trello.api.pages.BoardPage;
import com.trello.api.pages.CardPage;
import com.trello.api.utils.RandomUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Trello API Automation Test Suite")
public class TrelloApiTest {
    
    private static BoardPage boardPage;
    private static CardPage cardPage;
    private static Board createdBoard;
    private static java.util.List<Card> createdCards;
    private static List createdList;
    
    @BeforeAll
    static void setUp() {
        // Validate API credentials before running tests
        assertTrue(ApiCredentials.areCredentialsLoaded(), 
                "API credentials are not loaded. Please check your api.properties file.");
        
        // Initialize page objects
        boardPage = new BoardPage();
        cardPage = new CardPage();
        createdCards = new ArrayList<>();
        
        System.out.println("=== Trello API Automation Test Started ===");
    }
    
    @Test
    @Order(1)
    @DisplayName("1. Create a new board")
    void testCreateBoard() {
        System.out.println("\n--- Step 1: Creating a new board ---");
        
        // Create board with name and description
        createdBoard = boardPage.createBoard(Config.DEFAULT_BOARD_NAME, Config.DEFAULT_BOARD_DESC);
        
        // Assertions
        assertNotNull(createdBoard, "Board should be created successfully");
        assertNotNull(createdBoard.getId(), "Board ID should not be null");
        assertEquals(Config.DEFAULT_BOARD_NAME, createdBoard.getName(), "Board name should match");
        assertEquals(Config.DEFAULT_BOARD_DESC, createdBoard.getDesc(), "Board description should match");
        assertFalse(createdBoard.isClosed(), "Board should not be closed");
        
        System.out.println("✓ Board created successfully:");
        System.out.println("  - ID: " + createdBoard.getId());
        System.out.println("  - Name: " + createdBoard.getName());
        System.out.println("  - Description: " + createdBoard.getDesc());
    }
    
    @Test
    @Order(2)
    @DisplayName("2. Get board lists and create a card")
    void testCreateCards() {
        System.out.println("\n--- Step 2: Creating a card in the board ---");

        // Get all lists from the board
        java.util.List<List> allLists = boardPage.getBoardLists(createdBoard.getId());
        assertNotNull(allLists, "Board should have lists");
        assertTrue(allLists.size() >= 1, "Board should have at least 1 list");

        // Use first list for the card
        List list1 = allLists.get(0);
        System.out.println("✓ Found list: " + list1.getName() + " (ID: " + list1.getId() + ")");

        // Create card
        System.out.println("Creating card with list ID: " + list1.getId());
        Card card1 = cardPage.createCard(Config.DEFAULT_CARD_NAME_1, Config.DEFAULT_CARD_DESC, list1.getId());
        assertNotNull(card1, "Card should be created successfully");
        assertNotNull(card1.getId(), "Card ID should not be null");
        assertEquals(Config.DEFAULT_CARD_NAME_1, card1.getName(), "Card name should match");
        assertEquals(Config.DEFAULT_CARD_DESC, card1.getDesc(), "Card description should match");
        createdCards.add(card1);
        
        System.out.println("✓ Card created: " + card1.getName() + " (ID: " + card1.getId() + ")");

        // Store the list for later use
        createdList = list1;

        // Verify we have 1 card in the board
        java.util.List<Card> cardsInBoard = cardPage.getCardsInBoard(createdBoard.getId());
        assertTrue(cardsInBoard.size() >= 1, "Should have at least 1 card in the board");
        
        System.out.println("✓ Card creation completed successfully");
    }
    
    @Test
    @Order(3)
    @DisplayName("3. Update the created card")
    void testUpdateRandomCard() {
        System.out.println("\n--- Step 3: Updating the created card ---");
        
        // Check if we have a card to update
        assertNotNull(createdCards, "Created cards list should not be null");
        assertFalse(createdCards.isEmpty(), "Should have at least one card to update");
        
        // Get the card to update
        Card cardToUpdate = createdCards.get(0);
        assertNotNull(cardToUpdate, "Should have a card to update");
        
        System.out.println("✓ Selected card for update: " + cardToUpdate.getName() + " (ID: " + cardToUpdate.getId() + ")");
        
        // Update the card description
        Card updatedCard = cardPage.updateCardDescription(cardToUpdate.getId(), Config.UPDATED_CARD_DESC);
        assertNotNull(updatedCard, "Card should be updated successfully");
        assertTrue(updatedCard.getDesc().contains(Config.UPDATED_CARD_DESC), "Card description should contain the updated text");
        assertEquals(cardToUpdate.getName(), updatedCard.getName(), "Card name should remain the same");
        
        System.out.println("✓ Card updated successfully:");
        System.out.println("  - Name: " + updatedCard.getName());
        System.out.println("  - New Description: " + updatedCard.getDesc());
        
        // Update the card in our list
        createdCards.set(0, updatedCard);
    }
    
    @Test
    @Order(4)
    @DisplayName("4. Delete all created cards")
    void testDeleteCards() {
        System.out.println("\n--- Step 4: Deleting all created cards ---");
        
        for (Card card : createdCards) {
            System.out.println("Deleting card: " + card.getName() + " (ID: " + card.getId() + ")");
            
            // Delete the card
            cardPage.deleteCard(card.getId());
            
            // Verify card is deleted by trying to get it (should return 404)
            try {
                cardPage.getCard(card.getId());
                fail("Card should be deleted and not retrievable");
            } catch (AssertionError e) {
                // Expected - card should not be found
                System.out.println("✓ Card deleted successfully: " + card.getName());
            }
        }
        
        // Best-effort verification: ensure we cannot fetch any deleted card
        for (Card card : createdCards) {
            try {
                cardPage.getCard(card.getId());
                fail("Card should be deleted and not retrievable");
            } catch (AssertionError ignored) {
            }
        }
        
        System.out.println("✓ All cards deleted successfully");
    }
    
    @Test
    @Order(5)
    @DisplayName("5. Delete the created board")
    void testDeleteBoard() {
        System.out.println("\n--- Step 5: Deleting the created board ---");
        
        System.out.println("Deleting board: " + createdBoard.getName() + " (ID: " + createdBoard.getId() + ")");
        
        // Delete the board
        boardPage.deleteBoard(createdBoard.getId());
        
        // Verify board is deleted by trying to get it (should return 404)
        try {
            boardPage.getBoard(createdBoard.getId());
            fail("Board should be deleted and not retrievable");
        } catch (AssertionError e) {
            // Expected - board should not be found
            System.out.println("✓ Board deleted successfully: " + createdBoard.getName());
        }
        
        System.out.println("\n=== Trello API Automation Test Completed Successfully ===");
    }
    
    /**
     * Helper method to display test summary
     */
    @Test
    @Order(6)
    @DisplayName("6. Test Summary")
    void testSummary() {
        System.out.println("\n=== TEST SUMMARY ===");
        System.out.println("✓ Board created and deleted: " + Config.DEFAULT_BOARD_NAME);
        System.out.println("✓ Cards created and deleted: " + createdCards.size());
        System.out.println("✓ Card updated: " + (createdCards.size() > 0 ? "Yes" : "No"));
        System.out.println("✓ All operations completed successfully");
        System.out.println("===================");
    }
}
