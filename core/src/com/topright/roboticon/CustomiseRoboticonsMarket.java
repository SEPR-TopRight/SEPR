package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * The window that players can use to customise their roboticons
 * @author josh
 */
public class CustomiseRoboticonsMarket extends PopUpWindow {

	Player currentPlayer; // The current player who is looking to customise some of their roboticons
	private TextButton energyCustomisationButton;
	private TextButton oreCustomisationButton;

	/**
	 * Constructor
	 * @param player The current player (who is looking to customise some of their roboticons)
	 */
	public CustomiseRoboticonsMarket(Player player){
		super("Market: Customise roboticons"); // The argument in the superclass constructor is the window title
		
		this.currentPlayer = player;

		createAndAddCostLabels();
		row();
		createAndAddCustomisationButtons();

		setSize(getPrefWidth(),getPrefHeight()); // Want the window to be exactly as large as it has be to fit in all of the widgets (no smaller/larger)
		moveToMiddleOfScreen();
	}
	
	/**
	 * Creates the energy and ore customisation buttons, calls the SetCustomisationButtonClickBehaviour method
	 * to ensure that they are set up to carry out the correct actions when clicked and then adds them to the window.
	 */
	private void createAndAddCustomisationButtons(){
		energyCustomisationButton = new TextButton("Customise a roboticon for energy", new Skin(Gdx.files.internal("uiskin.json")));
		oreCustomisationButton = new TextButton("Customise a roboticon for ore", new Skin(Gdx.files.internal("uiskin.json")));
		setCustomisationButtonClickBehaviour();
		
		add(energyCustomisationButton).left().fillX();
		row();
		add(oreCustomisationButton).left().fillX();
	}
	
	/**
	 * Creates the labels that display the cost of the energy and ore customisations and adds them to the window
	 */
	private void createAndAddCostLabels(){
		Label energyCustomisationCostLabel = new Label("Energy customisation cost: "+
				Integer.toString(Market.getInstance().getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)),
				new Skin(Gdx.files.internal("uiskin.json")));

		Label oreCustomisationCostLabel = new Label("Ore customisation cost: "+
				Integer.toString(Market.getInstance().getCostRoboticonCustomisation(RoboticonCustomisation.ORE)),
				new Skin(Gdx.files.internal("uiskin.json")));
		
		// Add the widgets to the window in the desired locations
		add(energyCustomisationCostLabel).left();
		row();
		add(oreCustomisationCostLabel).left().padBottom(20);
	}
	
	/**
	 * Sets up the energy and ore customisation buttons to call the attemptCustomisation method when clicked
	 * @param energyCustomisationButton
	 * @param oreCustomisationButton
	 */
	private void setCustomisationButtonClickBehaviour(){
		energyCustomisationButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				// Called whenever the energy customisation button is clicked
				attemptCustomisation(RoboticonCustomisation.ENERGY); 
			}
		});
		
		oreCustomisationButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				// Called whenever the ore customisation button is clicked
				attemptCustomisation(RoboticonCustomisation.ORE); 
			}
		});
	}
	
	/**
	 * Attempt to customise one of the players roboticons with the given customisation
	 * <p>
	 * If unable to customise a roboticon due to the player not having enough money or not having any uncustomised roboticons then the appropriate pop up message will be created to inform the user.
	 * </p>
	 * @param customisation The customisation that is to be applied to one of the player's roboticons
	 */
	private void attemptCustomisation(RoboticonCustomisation customisation){
		// The MessagePopUps are added to the parent of this window as they would otherwise be contained within the window itself
		
		if(currentPlayer.attemptToCustomiseRoboticon(customisation)){ // If it is possible to customise one of the players roboticons
			// Other parts of the game must respond (e.g. the menu bar must updated as the contents of the player's inventory has changed)
			MessageManager.getInstance().dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal()); 
		}
		else if(currentPlayer.getMoneyQuantity()<Market.getInstance().getCostRoboticonCustomisation(customisation)){ // If the player does not have enough money
			getStage().addActor(new MessagePopUp("Not enough money","You don't have enough money!"));
		}
		else if(currentPlayer.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED) < 1){ // If the player does not have any uncustomised roboticons
			getStage().addActor(new MessagePopUp("No uncustomised roboticons","You have no roboticons that you can customise!"));
		}
	}
	
	/**
	 * Returns the TextButton object that is clicked by the player when they want to customise a roboticon for energy production
	 * @return The TextButton object that is clicked by the player when they want to customise a roboticon for energy production
	 */
	public TextButton getEnergyCustomisationButton(){
		return energyCustomisationButton;
	}
	
	/**
	 * Returns the TextButton object that is clicked by the player when they want to customise a roboticon for ore production
	 * @return The TextButton object that is clicked by the player when they want to customise a roboticon for ore production
	 */
	public TextButton getOreCustomisationButton(){
		return oreCustomisationButton;
	}
}
