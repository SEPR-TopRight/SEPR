package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A window containing a menu that players can use to customise their roboticons
 * @author jcn509
 */
public class RoboticonPlaceMenu extends PopUpWindow{
	private PlotManager plotManager;
	private int plotRow;
	private int plotColumn;
	private Player player;
	
	/**
	 * Constructor
	 * @param menuX The X coordinate of the location where the menu should be placed
	 * @param menuY The Y coordinate of the location where the menu should be placed
	 * @param plotColumn The column of the plot that has been clicked (the plot where a roboticon may now be placed)
	 * @param plotRow The row of the plot that has been clicked (the plot where a roboticon may now be placed)
	 * @param player The current player who may want to place a roboticon
	 * @param plotManager The plot manager object that contains the plot where a roboticon may be placed
	 */
	public RoboticonPlaceMenu(float menuX, float menuY, int plotColumn, int plotRow, Player player ,PlotManager plotManager){
	
		super("Place roboticon");
		
		this.plotManager = plotManager;	
		this.plotRow = plotRow;
		this.plotColumn = plotColumn;
		this.player = player;
		
		createMenu();
		
		// Make the window exactly as large as it needs to be to fit its contents
		setSize(getPrefWidth(),getPrefHeight());
		
		// Position the window
		setX(menuX);
		setY(menuY - getHeight()); // We want the top of the menu to be just below the cursor
	}
	
	/**
	 * Chooses which portions of the menu to create based on whether the plot has a roboticon on it already and how
	 * many customised roboticons the player has
	 */
	private void createMenu(){
		Plot plot = plotManager.getPlots()[plotRow][plotColumn];	

		if(plot.hasRoboticon()){
			alreadyHasRoboticonMenu();
		}
		// If the player has no customised roboticons
		else if(player.inventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY) ==0 && player.inventory.getRoboticonQuantity(RoboticonCustomisation.ORE) ==0){
			noRoboticonsMenu();
		}
		else{ // If the plot does not have a roboticon on it and the player has at least 1 customised roboticon
			placementAllowedMenu();
		}
		
		addCloseButton();
	}
	
	/**
	 * Adds a close button to the window 
	 */
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
	}
	
	/**
	 * Creates the menu that allows players to place a roboticon
	 */
	private void placementAllowedMenu(){
		
		TextButton oreRoboticonButton = new TextButton("Ore roboticon ("+player.inventory.getRoboticonQuantity(RoboticonCustomisation.ORE)+")", new Skin(Gdx.files.internal("uiskin.json")));
		TextButton energyRoboticonButton = new TextButton("Energy roboticon ("+player.inventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY)+")", new Skin(Gdx.files.internal("uiskin.json")));
		
		setRoboticonButtonBehaviour(oreRoboticonButton, energyRoboticonButton);
		
		// Add the appropriate buttons to the window
		// If the player has at least one ore roboticon
		if(player.inventory.getRoboticonQuantity(RoboticonCustomisation.ORE)>0){
			row();
			add(oreRoboticonButton).expand().fill().left();
		}
		// If the player has at least one energy roboticon
		if(player.inventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY)>0){
			row();
			add(energyRoboticonButton).expand().fill().left();
		}
		
	}
	
	/**
	 * Sets the ore and energy roboticon buttons to call the placeOreRoboticon and placeEnergyRoboticon methods respectively
	 * @param oreRoboticonButton The button that is clicked to place an ore roboticon
	 * @param energyRoboticonButton The button that is clicked to place an energy roboticon
	 */
	private void setRoboticonButtonBehaviour(TextButton oreRoboticonButton, TextButton energyRoboticonButton){
		oreRoboticonButton.addListener(new ClickListener() {
    		@Override
    			public void clicked(InputEvent event, float x, float y)
    			{
    				plotManager.placeOreRoboticon(plotColumn,plotRow); // Called when oreRoboticonButton is clicked
    			}
		} );
		
		energyRoboticonButton.addListener(new ClickListener() {
    		@Override
    			public void clicked(InputEvent event, float x, float y)
    			{
    				plotManager.placeEnergyRoboticon(plotColumn,plotRow); // Called when energyRoboticonButton is clicked
    			}
		} );
	}
	
	/**
	 * Creates the menu that should be displayed when the chosen plot already has a roboticon on it
	 */
	private void alreadyHasRoboticonMenu(){
		add(new Label("This plot already has a roboticon placed on it!", new Skin(Gdx.files.internal("uiskin.json")))).fill().expand();
	}
	
	/**
	 * Creates the menu that should be displayed when the player has no customised roboticons
	 */
	private void noRoboticonsMenu(){
		add(new Label("You don't have any customised roboticons!", new Skin(Gdx.files.internal("uiskin.json")))).fill().expand();
	}
}
