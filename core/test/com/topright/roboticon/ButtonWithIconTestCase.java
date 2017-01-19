package com.topright.roboticon;

import static org.junit.Assert.*;



import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Test case for the ButtonWithIcon class
 * @author jcn509
 *
 */
public class ButtonWithIconTestCase extends GuiTest {
	private boolean clickValue; // Used to test whether or not the clickListener is set up correctly
	ButtonWithIcon button;
	
	public void setClickValue(){
		clickValue=true;
	}
	
	/**
	 * Runs before any tests and creates the required ButtonWithIcon object
	 */
	@Before
	public void setup(){
		clickValue=false;
		button = new ButtonWithIcon("", "plot_overlays/unacquired.pack", "unacquired", "unacquired",
				new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y)
					{
						setClickValue(); // Called whenever this button is clicked
					}
				});
	}
	
	/**
	 * Tests whether or not the clickListener for the newly created button is set up correctly
	 */
	@Test
	public void testClickListener(){
		clickActor(button);
		assertTrue(clickValue);
	}
	
	
	
}
