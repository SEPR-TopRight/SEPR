package com.topright.roboticon;

import static org.junit.Assert.*;

import java.util.EnumMap;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

/**
 * Test case for the MessagePopUp class
 * We are unable to test whether it opens in the middle of the screen due to limitations with the headless
 * backend for LibGDX that we are using to perform these tests. This has been tested manually.
 * @author jcn509
 *
 */
public class MessagePopUpTestcase extends GuiTest { 
	private MessagePopUp messagePopUp;
	private String messageForTest;
	private String titleForTest;
	
	/**
	 * Runs before every test. Creates the required messagePopUp object
	 */
	@Before
	public void setup(){
		titleForTest = "title for testing";
		messageForTest = "message for testing";
		messagePopUp = new MessagePopUp(titleForTest,messageForTest);
		
		stage.addActor(messagePopUp);
	}
	
	/**
	 * Ensures that the title is displayed in the window title
	 */
	@Test
	public void testTitleDisplayed(){
		// The title is set in the setup() method
		assertEquals(titleForTest,messagePopUp.getTitleLabel().getText().toString());
	}
	
	/**
	 * Ensures that the message is displayed in a label in the window
	 */
	@Test
	public void testMessageDisplayed(){
		Array<Actor> windowChildren = messagePopUp.getChildren();
	    for(int windowChild=0;windowChild<windowChildren.size;windowChild++)
	    { // Look at every widget in the game over window
	    	
	    	// Every child of the window should be a table (one for buttons, one for labels etc)
	    	if(windowChildren.get(windowChild) instanceof Table){
	    		
	    		// Examine every element in this table
	    		Array<Actor> children2 = ((Table)windowChildren.get(windowChild)).getChildren();
	    	    for(int child2=0;child2<children2.size;child2++)
	    	    { 
	    	    	// If the current widget is a label
	    	    	if(children2.get(child2) instanceof Label){ 
	    	            String labelText = ((Label)children2.get(child2)).getText().toString();
	    	          
	    	            // If this label contains the message text
	    	            if(labelText.contains(messageForTest)){
	    	            	return; // The message is displayed and we have passed this test	    	            	
	    	            }
	    	        }
	    	    	
	    	    }
	    	    
	        }
	    	
	    }
		// If we reach this point without finding a Label that contains the message, 
		// then it is not displayed and this test fails
		fail(); 
	}
	
	/**
	 * Ensures that when the close button is clicked the window is destroyed
	 */
	@Test
	public void testClickCloseButton(){
		clickActor(messagePopUp.getCloseButton());
		
		// If the window is removed from the stage (i.e. it is 'closed')
		// It will not have an associated stage.
		assertEquals(messagePopUp.getStage(),null);
	}
	
}
