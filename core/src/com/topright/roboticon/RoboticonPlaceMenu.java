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
	public RoboticonPlaceMenu(float x, float y,Player player,Plot plot){
		
		super("Place roboticon");
		setX(x);
		setY(y);
		
		if(plot.hasRoboticon())
			alreadyHasRoboticon();
		else if(player.inventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY) ==0 && player.inventory.getRoboticonQuantity(RoboticonCustomisation.ORE) ==0)
			noRoboticons();
		else
			placementAllowed(player,plot);
		
		addCloseButton();
		setSize(getPrefWidth(),getPrefHeight());
	
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
		row();
		add(closeButton);
		
		// Make sure that this window is exactly the right size!
	}
	
	private void placementAllowed(Player player, Plot plot){
		Label bestAt = new Label("Suited for: "+plot.getBest(), new Skin(Gdx.files.internal("uiskin.json")));
		
		TextButton oreRoboticonButton = new TextButton("Ore roboticon ("+player.inventory.getRoboticonQuantity(RoboticonCustomisation.ORE)+")", new Skin(Gdx.files.internal("uiskin.json")));
		oreRoboticonButton.addListener(new ClickListener() {
	        		@Override
	        			public void clicked(InputEvent event, float x, float y)
	        			{
	        				if(player.attemptToPlaceRoboticon(plot,RoboticonCustomisation.ORE)){
	        					MessageManager.getInstance().dispatchMessage(GameEvents.ROBOTICONPLACED.ordinal()); // Trigger main to update the menubar
	        					remove();
	        				}
	        			}
	    		} );
	
		TextButton energyRoboticonButton = new TextButton("Energy roboticon ("+player.inventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY)+")", new Skin(Gdx.files.internal("uiskin.json")));
		energyRoboticonButton.addListener(new ClickListener() {
	        		@Override
	        			public void clicked(InputEvent event, float x, float y)
	        			{
	        				if(player.attemptToPlaceRoboticon(plot,RoboticonCustomisation.ENERGY)){
	        					MessageManager.getInstance().dispatchMessage(GameEvents.ROBOTICONPLACED.ordinal()); // Trigger main to update the menubar
	        					remove();
	        				}
	        			}
	    		} );
			
		add(bestAt).expand().fill().left();
		
		if(player.inventory.getRoboticonQuantity(RoboticonCustomisation.ORE)>0){
			row();
			add(oreRoboticonButton).expand().fill().left();
		}
		if(player.inventory.getRoboticonQuantity(RoboticonCustomisation.ORE)>0){
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
