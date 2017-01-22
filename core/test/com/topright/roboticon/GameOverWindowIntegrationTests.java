package com.topright.roboticon;

import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

/**
 * Integration tests for the {@link GameOverWindow} and {@link Player} classes
 * @author jcn509
 *
 */
public class GameOverWindowIntegrationTests extends GuiTest { 
	private GameOverWindow gameOverWindow;
	private String humanScoreString;
	private String AIScoreString;
	private String winnerTextString;
	Player AIPlayer;
	Player humanPlayer;
	PlayerInventory AIPlayerInventory;
	PlayerInventory humanPlayerInventory;
	
	/**
	 * Runs before every test. Creates the required (mocked) Player and PlayerInventory objects
	 */
	@Before
	public void setup(){
		EnumMap<RoboticonCustomisation,Integer> humanRoboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		humanPlayerInventory = new PlayerInventory(0,0,humanRoboticonQuantities,20);
		
		EnumMap<RoboticonCustomisation,Integer> AIRoboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		AIPlayerInventory = new PlayerInventory(0,0,AIRoboticonQuantities,20);
		
		humanPlayer = new Player(humanPlayerInventory);
		AIPlayer = new Player(AIPlayerInventory);
	}
	
	/**
	 * Extract the strings stored in the labels that display:
	 *   Who has won the game (the AI or the human player)
	 *   The AI's final score
	 *   The human's final score
	 */
	private void getLabelData(){
		Array<Actor> children = gameOverWindow.getChildren();
	    for(int child=0;child<children.size;child++)
	    { // Look at every widget in the game over window
	    	if(children.get(child) instanceof Label){ // If the current widget is a label
	            String labelText = ((Label)children.get(child)).getText().toString().toLowerCase();
	            if(labelText.contains("won") || labelText.contains("draw")){
	            	winnerTextString = labelText;
	            }
	            else if(labelText.contains("your") && labelText.contains("score")){
	            	humanScoreString = labelText;
	            }
	            else if(labelText.contains("ai") && labelText.contains("score")){
	            	AIScoreString = labelText;
	            }
	        }
	    }
	}
	
	/**
	 * Ensures that the human player's score is displayed correctly (for a score of 0)
	 */
	@Test
	public void testHumanScoreZero(){
		
		// No money = 0 score
		humanPlayerInventory.decreaseMoneyQuantity(humanPlayerInventory.getMoneyQuantity());
		gameOverWindow = new GameOverWindow(humanPlayer,AIPlayer);
		getLabelData();
		String score = humanScoreString.replaceAll("[^0-9]",""); // Leaves on the numbers
		assertEquals(0,Integer.parseInt(score));
		
		
	}
	
	/**
	 * Ensures that the human player's score is displayed correctly (for a score of 15)
	 */
	@Test
	public void testHumanScoreFifteen(){
		// No money = 0 score
		humanPlayerInventory.decreaseMoneyQuantity(humanPlayerInventory.getMoneyQuantity());
		humanPlayerInventory.increaseMoneyQuantity(15); // 15 money = 15 score
		gameOverWindow = new GameOverWindow(humanPlayer,AIPlayer);
		getLabelData();
		String score = humanScoreString.replaceAll("[^0-9]",""); // Leaves on the numbers
		assertEquals(15,Integer.parseInt(score));
	}
	
	/**
	 * Ensures that the AI player's score is displayed correctly (for a score of 0)
	 */
	@Test
	public void testAIScoreZero(){
		// No money = 0 score
		AIPlayerInventory.decreaseMoneyQuantity(AIPlayerInventory.getMoneyQuantity());
		gameOverWindow = new GameOverWindow(humanPlayer,AIPlayer);
		gameOverWindow = new GameOverWindow(humanPlayer,AIPlayer);
		getLabelData();
		String score = AIScoreString.replaceAll("[^0-9]",""); // Leaves on the numbers
		assertEquals(0,Integer.parseInt(score));
	}
	
	/**
	 * Ensures that the AI player's score is displayed correctly (for a score of 15)
	 */
	@Test
	public void testAIScoreFifteen(){
		// No money = 0 score
		AIPlayerInventory.decreaseMoneyQuantity(AIPlayerInventory.getMoneyQuantity());
		AIPlayerInventory.increaseMoneyQuantity(15); // 15 money = 15 score
		gameOverWindow = new GameOverWindow(humanPlayer,AIPlayer);
		getLabelData();
		String score = AIScoreString.replaceAll("[^0-9]",""); // Leaves on the numbers
		assertEquals(15,Integer.parseInt(score));
	}
	
	/**
	 * Ensures that if both players end up with a score of 1, a string stating that it was a draw is displayed
	 */
	@Test
	public void winnerTextDrawBothOne(){
		// No money = 0 score
		AIPlayerInventory.decreaseMoneyQuantity(AIPlayerInventory.getMoneyQuantity());
		AIPlayerInventory.increaseMoneyQuantity(1); // 1 money = 1 score
				
		// No money = 0 score
		humanPlayerInventory.decreaseMoneyQuantity(humanPlayerInventory.getMoneyQuantity());
		humanPlayerInventory.increaseMoneyQuantity(1); // 1 money = 1 score
		gameOverWindow = new GameOverWindow(humanPlayer,AIPlayer);
		getLabelData();
		assertTrue(winnerTextString.contains("draw") && !winnerTextString.contains("won"));
	}
	
	/**
	 * Ensures that if both players end up with a score of 0, a string stating that it was a draw is displayed
	 */
	@Test
	public void winnerTextDrawBothZero(){
		// No money = 0 score
		AIPlayerInventory.decreaseMoneyQuantity(AIPlayerInventory.getMoneyQuantity());
				
		// No money = 0 score
		humanPlayerInventory.decreaseMoneyQuantity(humanPlayerInventory.getMoneyQuantity());
		gameOverWindow = new GameOverWindow(humanPlayer,AIPlayer);
		getLabelData();
		assertTrue(winnerTextString.contains("draw") && !winnerTextString.contains("won"));
	}
	
	/**
	 * Ensures that if both players end up with a score of 19, a string stating that it was a draw is displayed
	 */
	@Test
	public void winnerTextDrawBothNineteen(){
		// No money = 0 score
		AIPlayerInventory.decreaseMoneyQuantity(AIPlayerInventory.getMoneyQuantity());
		AIPlayerInventory.increaseMoneyQuantity(9); // 9 money = 9 score
						
		// No money = 0 score
		humanPlayerInventory.decreaseMoneyQuantity(humanPlayerInventory.getMoneyQuantity());
		humanPlayerInventory.increaseMoneyQuantity(9); // 9 money = 9 score
		gameOverWindow = new GameOverWindow(humanPlayer,AIPlayer);
		getLabelData();
		assertTrue(winnerTextString.contains("draw") && !winnerTextString.contains("won"));
	}
	
	/**
	 * Ensures that if the human player wins by 1 point a string stating that they won is displayed
	 */
	@Test
	public void winnerTextHumanWonByOnePoint(){
		// No money = 0 score
		AIPlayerInventory.decreaseMoneyQuantity(AIPlayerInventory.getMoneyQuantity());
		AIPlayerInventory.increaseMoneyQuantity(9); // 9 money = 9 score
								
		// No money = 0 score
		humanPlayerInventory.decreaseMoneyQuantity(humanPlayerInventory.getMoneyQuantity());
		humanPlayerInventory.increaseMoneyQuantity(10); // 10 money = 10 score
		gameOverWindow = new GameOverWindow(humanPlayer,AIPlayer);
		getLabelData();
		assertTrue(winnerTextString.contains("you") && winnerTextString.contains("won") && !winnerTextString.contains("ai"));
	}
	
	/**
	 * Ensures that if the human player wins by 5 points a string stating that they won is displayed
	 */
	@Test
	public void winnerTextHumanWonByFivePoints(){
		// No money = 0 score
		AIPlayerInventory.decreaseMoneyQuantity(AIPlayerInventory.getMoneyQuantity());
		AIPlayerInventory.increaseMoneyQuantity(9); // 9 money = 9 score
								
		// No money = 0 score
		humanPlayerInventory.decreaseMoneyQuantity(humanPlayerInventory.getMoneyQuantity());
		humanPlayerInventory.increaseMoneyQuantity(14); // 14 money = 14 score
		gameOverWindow = new GameOverWindow(humanPlayer,AIPlayer);
		getLabelData();
		assertTrue(winnerTextString.contains("you") && winnerTextString.contains("won") && !winnerTextString.contains("ai"));
	}
	
	/**
	 * Ensures that if the AI player wins by 1 point a string stating that they won is displayed
	 */
	@Test
	public void winnerTextAIWonByOnePoint(){
		// No money = 0 score
		AIPlayerInventory.decreaseMoneyQuantity(AIPlayerInventory.getMoneyQuantity());
		AIPlayerInventory.increaseMoneyQuantity(9); // 9 money = 9 score
								
		// No money = 0 score
		humanPlayerInventory.decreaseMoneyQuantity(humanPlayerInventory.getMoneyQuantity());
		humanPlayerInventory.increaseMoneyQuantity(8); // 8 money = 8 score
		gameOverWindow = new GameOverWindow(humanPlayer,AIPlayer);
		getLabelData();
		assertTrue(winnerTextString.contains("ai") && winnerTextString.contains("won") && !winnerTextString.contains("you"));
	}
	
	/**
	 * Ensures that if the AI player wins by 3 points a string stating that they won is displayed
	 */
	@Test
	public void winnerTextAIWonByThreePoints(){
		// No money = 0 score
		AIPlayerInventory.decreaseMoneyQuantity(AIPlayerInventory.getMoneyQuantity());
		AIPlayerInventory.increaseMoneyQuantity(9); // 9 money = 9 score
								
		// No money = 0 score
		humanPlayerInventory.decreaseMoneyQuantity(humanPlayerInventory.getMoneyQuantity());
		humanPlayerInventory.increaseMoneyQuantity(4); // 4 money = 4 score
		gameOverWindow = new GameOverWindow(humanPlayer,AIPlayer);
		getLabelData();
		assertTrue(winnerTextString.contains("ai") && winnerTextString.contains("won") && !winnerTextString.contains("you"));
	}
	
	
}
