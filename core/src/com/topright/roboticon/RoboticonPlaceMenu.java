package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * @author jcn509
 * The menu that is displayed when you click on a plot during the roboticon placing stage
 */
public class RoboticonPlaceMenu extends PopUpWindow{
	PlotManager plotManager;
	public RoboticonPlaceMenu(float menuX, float menuY, int plotX, int plotY, Player player ,PlotManager plotManager){
	
		super("Place roboticon");
		
		this.plotManager = plotManager;	
		Plot plot = plotManager.getPlots()[plotY][plotX];	

		if(plot.hasRoboticon())
			alreadyHasRoboticon();
		else if(player.inventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY) ==0 && player.inventory.getRoboticonQuantity(RoboticonCustomisation.ORE) ==0)
			noRoboticons();
		else
			placementAllowed(player,plotX,plotY);
		
		addCloseButton();
		setSize(getPrefWidth(),getPrefHeight());
		setX(menuX);
		setY(menuY - getHeight()); // We want the menu to open just below the cursor, not above it
	
	}
	
	private void addCloseButton(){
		TextButton closeButton = new TextButton("close",new Skin(Gdx.files.internal("uiskin.json")));
		closeButton.addListener(new ClickListener() {
	        		@Override
	        			public void clicked(InputEvent event, float x, float y)
	        			{
	        				remove();
	        			}
	    		} );
		
		row();
		add(closeButton);
		
		// Make sure that this window is exactly the right size!
	}
	
	private void placementAllowed(Player player, int plotX, int plotY){
		
		TextButton oreRoboticonButton = new TextButton("Ore roboticon ("+player.inventory.getRoboticonQuantity(RoboticonCustomisation.ORE)+")", new Skin(Gdx.files.internal("uiskin.json")));
		oreRoboticonButton.addListener(new ClickListener() {
	        		@Override
	        			public void clicked(InputEvent event, float x, float y)
	        			{
	        				plotManager.placeOreRoboticon(plotX,plotY);
	        			}
	    		} );
	
		TextButton energyRoboticonButton = new TextButton("Energy roboticon ("+player.inventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY)+")", new Skin(Gdx.files.internal("uiskin.json")));
		energyRoboticonButton.addListener(new ClickListener() {
	        		@Override
	        			public void clicked(InputEvent event, float x, float y)
	        			{
	        				plotManager.placeEnergyRoboticon(plotX,plotY);
	        			}
	    		} );
		
		if(player.inventory.getRoboticonQuantity(RoboticonCustomisation.ORE)>0){
			row();
			add(oreRoboticonButton).expand().fill().left();
		}
		if(player.inventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY)>0){
			row();
			add(energyRoboticonButton).expand().fill().left();
		}
		
	}
	
	
	private void alreadyHasRoboticon(){
		add(new Label("This plot already has a roboticon placed on it!", new Skin(Gdx.files.internal("uiskin.json")))).fill().expand();
	}
	private void noRoboticons(){
		add(new Label("You don't have any customised roboticons!", new Skin(Gdx.files.internal("uiskin.json")))).fill().expand();
	}
}
