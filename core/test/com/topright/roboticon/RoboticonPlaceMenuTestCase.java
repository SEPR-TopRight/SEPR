package com.topright.roboticon;

import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.Before;
import org.junit.Test;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import mockit.Expectations;
import mockit.Mocked;

/**
 * Test case for the RoboticonPlaceMenu class
 * @author jcn509
 *
 */
public class RoboticonPlaceMenuTestCase extends GuiTest { 
	private RoboticonPlaceMenu roboticonPlaceMenu;
	@Mocked private PlotManager plotManager;
	@Mocked private Player humanPlayer;
	@Mocked private Player AIPlayer;
	@Mocked private PlayerInventory humanPlayerInventory;
	@Mocked private PlayerInventory AIPlayerInventory;
	@Mocked private Plot[][] plots;
	@Mocked private Plot plot1;
	@Mocked private Plot plot2;
	
	@Before
	public void setup(){
		new RoboticonPlaceMenu(0, 0, 0, 0, null, null);
		
	}
	
	@Test
	public void t(){
		assertTrue(true);
	}

}
