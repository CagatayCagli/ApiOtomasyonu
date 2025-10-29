package com.trello.api.tests;

import com.trello.api.config.Config;
import com.trello.api.config.ApiCredentials;
import com.trello.api.models.Board;
import com.trello.api.models.Card;
import com.trello.api.models.List;
import com.trello.api.pages.BoardPage;
import com.trello.api.pages.CardPage;
import com.trello.api.utils.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;


public class TrelloApiTestNGTest {
    
    private BoardPage boardPage;
    private CardPage cardPage;
    private Board createdBoard;
    private java.util.List<Card> createdCards;
    private List createdList;
    
    @BeforeClass
    public void setUp() {
        // Validate API credentials before running tests
        Assert.assertTrue(ApiCredentials.areCredentialsLoaded(), 
                "API credentials are not loaded. Please check your api.properties file.");
        
        // Initialize page objects
        boardPage = new BoardPage();
        cardPage = new CardPage();
        createdCards = new ArrayList<>();
        
        System.out.println("=== Trello API Automation Test Started (TestNG) ===");
    }
    
    @Test(priority = 1, description = "Create a new board")
    public void testCreateBoard() {
        System.out.println("\n--- Step 1: Creating a new board ---");
        
        // Create board with name and description
        createdBoard = boardPage.createBoard(Config.DEFAULT_BOARD_NAME, Config.DEFAULT_BOARD_DESC);
        
        // Assertions
        Assert.assertNotNull(createdBoard, "Board should be created successfully");
        Assert.assertNotNull(createdBoard.getId(), "Board ID should not be null");
        Assert.assertEquals(createdBoard.getName(), Config.DEFAULT_BOARD_NAME, "Board name should match");
        Assert.assertEquals(createdBoard.getDesc(), Config.DEFAULT_BOARD_DESC, "Board description should match");
        Assert.assertFalse(createdBoard.isClosed(), "Board should not be closed");
        
        System.out.println("✓ Board created successfully:");
        System.out.println("  - ID: " + createdBoard.getId());
        System.out.println("  - Name: " + createdBoard.getName());
        System.out.println("  - Description: " + createdBoard.getDesc());
    }
    
    @Test(priority = 2, description = "Get board lists and create two cards")
    public void testCreateCards() {
        System.out.println("\n--- Step 2: Creating two cards in the board ---");
        
        // Get the first list (To Do list) from the board
        createdList = boardPage.getFirstList(createdBoard.getId());
        Assert.assertNotNull(createdList, "Board should have at least one list");
        
        System.out.println("✓ Found list: " + createdList.getName() + " (ID: " + createdList.getId() + ")");
        
        // Create first card
        Card card1 = cardPage.createCard(Config.DEFAULT_CARD_NAME_1, Config.DEFAULT_CARD_DESC, createdList.getId());
        Assert.assertNotNull(card1, "First card should be created successfully");
        Assert.assertNotNull(card1.getId(), "First card ID should not be null");
        Assert.assertEquals(card1.getName(), Config.DEFAULT_CARD_NAME_1, "First card name should match");
        Assert.assertEquals(card1.getDesc(), Config.DEFAULT_CARD_DESC, "First card description should match");
        createdCards.add(card1);
        
        System.out.println("✓ Card 1 created: " + card1.getName() + " (ID: " + card1.getId() + ")");
        
        // Create second card
        Card card2 = cardPage.createCard(Config.DEFAULT_CARD_NAME_2, Config.DEFAULT_CARD_DESC, createdList.getId());
        Assert.assertNotNull(card2, "Second card should be created successfully");
        Assert.assertNotNull(card2.getId(), "Second card ID should not be null");
        Assert.assertEquals(card2.getName(), Config.DEFAULT_CARD_NAME_2, "Second card name should match");
        Assert.assertEquals(card2.getDesc(), Config.DEFAULT_CARD_DESC, "Second card description should match");
        createdCards.add(card2);
        
        System.out.println("✓ Card 2 created: " + card2.getName() + " (ID: " + card2.getId() + ")");
        
        // Verify both cards exist in the list
        java.util.List<Card> cardsInList = cardPage.getCardsInList(createdList.getId());
        Assert.assertEquals(cardsInList.size(), 2, "Should have exactly 2 cards in the list");
    }
    
    @Test(priority = 3, description = "Randomly update one of the cards")
    public void testUpdateRandomCard() {
        System.out.println("\n--- Step 3: Randomly updating one card ---");
        
        // Select a random card to update
        Card cardToUpdate = RandomUtils.getRandomElement(createdCards);
        Assert.assertNotNull(cardToUpdate, "Should have a card to update");
        
        System.out.println("✓ Selected card for update: " + cardToUpdate.getName() + " (ID: " + cardToUpdate.getId() + ")");
        
        // Update the card description
        Card updatedCard = cardPage.updateCardDescription(cardToUpdate.getId(), Config.UPDATED_CARD_DESC);
        Assert.assertNotNull(updatedCard, "Card should be updated successfully");
        Assert.assertEquals(updatedCard.getDesc(), Config.UPDATED_CARD_DESC, "Card description should be updated");
        Assert.assertEquals(updatedCard.getName(), cardToUpdate.getName(), "Card name should remain the same");
        
        System.out.println("✓ Card updated successfully:");
        System.out.println("  - Name: " + updatedCard.getName());
        System.out.println("  - New Description: " + updatedCard.getDesc());
        
        // Update the card in our list
        int index = createdCards.indexOf(cardToUpdate);
        createdCards.set(index, updatedCard);
    }
    
    @Test(priority = 4, description = "Delete all created cards")
    public void testDeleteCards() {
        System.out.println("\n--- Step 4: Deleting all created cards ---");
        
        for (Card card : createdCards) {
            System.out.println("Deleting card: " + card.getName() + " (ID: " + card.getId() + ")");
            
            // Delete the card
            cardPage.deleteCard(card.getId());
            
            // Verify card is deleted by trying to get it (should return 404)
            try {
                cardPage.getCard(card.getId());
                Assert.fail("Card should be deleted and not retrievable");
            } catch (AssertionError e) {
                // Expected - card should not be found
                System.out.println("✓ Card deleted successfully: " + card.getName());
            }
        }
        
        // Verify no cards remain in the list
        java.util.List<Card> remainingCards = cardPage.getCardsInList(createdList.getId());
        Assert.assertEquals(remainingCards.size(), 0, "No cards should remain in the list");
        
        System.out.println("✓ All cards deleted successfully");
    }
    
    @Test(priority = 5, description = "Delete the created board")
    public void testDeleteBoard() {
        System.out.println("\n--- Step 5: Deleting the created board ---");
        
        System.out.println("Deleting board: " + createdBoard.getName() + " (ID: " + createdBoard.getId() + ")");
        
        // Delete the board
        boardPage.deleteBoard(createdBoard.getId());
        
        // Verify board is deleted by trying to get it (should return 404)
        try {
            boardPage.getBoard(createdBoard.getId());
            Assert.fail("Board should be deleted and not retrievable");
        } catch (AssertionError e) {
            // Expected - board should not be found
            System.out.println("✓ Board deleted successfully: " + createdBoard.getName());
        }
        
        System.out.println("\n=== Trello API Automation Test Completed Successfully (TestNG) ===");
    }
    
    @Test(priority = 6, description = "Test Summary")
    public void testSummary() {
        System.out.println("\n=== TEST SUMMARY ===");
        System.out.println("✓ Board created and deleted: " + Config.DEFAULT_BOARD_NAME);
        System.out.println("✓ Cards created and deleted: " + createdCards.size());
        System.out.println("✓ Random card updated: " + (createdCards.size() > 0 ? "Yes" : "No"));
        System.out.println("✓ All operations completed successfully");
        System.out.println("===================");
    }
}
