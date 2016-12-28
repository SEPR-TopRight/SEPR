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
			placementAllowed(player,plot,plotX,plotY);
		
		addCloseButton();
		setSize(getPrefWidth(),getPrefHeight());
		setX(menuX);
		setY(menuY);
	
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
	
	private void placementAllowed(Player player,Plot plot, int plotX, int plotY){
		Label bestAt = new Label("Suited for: "+plot.getBest(), new Skin(Gdx.files.internal("uiskin.json")));
		
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
			
		add(bestAt).expand().fill().left();
		
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
