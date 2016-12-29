package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class CustomiseRoboticonsMarket extends PopUpWindow {

	Player player;
	TextButton energyCustomisationButton;

	public CustomiseRoboticonsMarket(Player player){
		super("Market: Customise roboticons");
		
		this.player = player;

		Label energyCustomisationCostLabel = new Label("Energy customisation cost: "+
			Integer.toString(Market.getInstance().getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)),
			new Skin(Gdx.files.internal("uiskin.json")));

		Label oreCustomisationCostLabel = new Label("Ore customisation cost: "+
			Integer.toString(Market.getInstance().getCostRoboticonCustomisation(RoboticonCustomisation.ORE)),
			new Skin(Gdx.files.internal("uiskin.json")));
		
		energyCustomisationButton = new TextButton("Customise a roboticon for energy", new Skin(Gdx.files.internal("uiskin.json")));
		energyCustomisationButton.addListener(new ClickListener(){
							@Override
							public void clicked(InputEvent event, float x, float y)
							{
								attemptCustomisation(RoboticonCustomisation.ENERGY);
							}
						});

		TextButton oreCustomisationButton = new TextButton("Customise a roboticon for ore", new Skin(Gdx.files.internal("uiskin.json")));
		oreCustomisationButton.addListener(new ClickListener(){
							@Override
							public void clicked(InputEvent event, float x, float y)
							{
								attemptCustomisation(RoboticonCustomisation.ORE);
							}
						});

		add(energyCustomisationCostLabel).left();
		row();
		add(oreCustomisationCostLabel).left().padBottom(20);
		row();
		
		add(energyCustomisationButton).left().fillX();
		row();
		add(oreCustomisationButton).left().fillX();

		setSize(getPrefWidth(),getPrefHeight());
		moveToMiddleOfScreen();
	}
	
	private void attemptCustomisation(RoboticonCustomisation customisation){
		if(player.attemptToCustomiseRoboticon(customisation)){
			MessageManager.getInstance().dispatchMessage(GameEvents.PLAYERPURCHASE.ordinal());
		}
		else if(player.inventory.getMoneyQuantity()<Market.getInstance().getCostRoboticonCustomisation(customisation)){ // If the purchase was unsuccessful
			getParent().addActor(new MessagePopUp("Not enough money","You don't have enough money!"));
		}
		else if(player.inventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED) < 1){
			getParent().addActor(new MessagePopUp("No uncustomised roboticons","You have no roboticons that you can customise!"));
		}
	}
}
