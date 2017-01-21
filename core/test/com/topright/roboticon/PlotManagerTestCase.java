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
 * Test case for the PlotManger class
 * @author jcn509
 */
public class PlotManagerTestCase extends GuiTest { 
	private PlotManager plotManager;
	@Mocked Player humanPlayer;
	@Mocked Player AIPlayer;
	@Mocked PlayerInventory humanPlayerInventory;
	@Mocked PlayerInventory AIPlayerInventory;
	@Mocked MessageDispatcher messageDispatcher;
	
	@Before
	public void setup(){
		messageDispatcher = MessageManager.getInstance();
		
		EnumMap<RoboticonCustomisation,Integer> humanRoboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		humanPlayerInventory = new PlayerInventory(0,1,humanRoboticonQuantities, 2);
		humanPlayer = new Player(humanPlayerInventory);
		
		EnumMap<RoboticonCustomisation,Integer> AIRoboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		AIPlayerInventory = new PlayerInventory(0,1,AIRoboticonQuantities, 2);
		AIPlayer = new Player(AIPlayerInventory);
		
		plotManager = new PlotManager("assets/backgrounds/map.png", humanPlayer, AIPlayer);
	}
}
