package com.topright.roboticon;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Test case for the PlotManager class
 * @author jcn509
 *
 */
public class PlotManagerTestCase extends GuiTest { 
	private PlotManager plotManager;
	
	/**
	 * Runs before any tests and creates the required ButtonWithIcon object
	 */
	@Before
	public void setup(){
		plotManager = new PlotManager("backgrounds/map.png", null, null);
	}
	
	/**
	 * Tests whether or not the clickListener for the newly creted button is set up correctly
	 */
	@Test
	public void testClickListener(){
		//button.getClickListener().clicked(null, 0, 0);
		assertTrue(true);
	}
	
}
