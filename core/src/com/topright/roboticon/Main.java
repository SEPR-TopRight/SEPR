package com.topright.roboticon;

import java.util.EnumMap;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.lang.Math;

/**
 * Sets up the various stages of the game and the GUI.
 * An executable version of the game can be found here: https://sepr-topright.github.io/SEPR/documentation/assessment2/TRCGame.zip
 * @author jcn509
 */
public class Main extends ApplicationAdapter implements Telegraph{
	private SpriteBatch batch;
	private Stage stage;
	
	private MenuBar menu;	
	private PopUpWindow marketWindow;
	private PlotManager plotManager;

	private Player humanPlayer;
	private AIPlayer AIPlayer;
	private Player currentPlayer;
	private Player firstPlayer;
	
	/**
	 * Called when the game is started. Creates and initialises all the objects that are needed and starts the game.
	 */
	@Override
	public void create(){
		batch = new SpriteBatch();
		stage = new Stage();
		menu = new MenuBar();
       	Gdx.input.setInputProcessor(stage);
       	
       	initialisePlayers();
       	initiliasePlotManager();
       	Market.getInstance().setInventory(new MarketInventory(0,16,12));
        
 		//this class handles all game events
        for (GameEvents event : GameEvents.values()) {
        	MessageManager.getInstance().addListener(this, event.ordinal());
        }
     
        stage.addActor(createMainGUITable());
        startGame();
	}
	
	
	/**
	 * Creates and initialises the player objects and chooses (randomly) which player is to go first
	 */
	private void initialisePlayers(){
		EnumMap<RoboticonCustomisation,Integer> HumanRoboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
        EnumMap<RoboticonCustomisation,Integer> AIRoboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
        humanPlayer = new Player(new PlayerInventory(0, 0, HumanRoboticonQuantities, 30));
        AIPlayer = new AIPlayer(new PlayerInventory(0, 0, AIRoboticonQuantities, 30));
        
        // Randomly select which player is to go first
        firstPlayer = (Math.random() < 0.5) ? humanPlayer : AIPlayer;
        currentPlayer = firstPlayer;
	}
	
	/**
	 * Creates the table that contains all the other GUI elements (and adds those elements to the table)
	 * @return
	 */
	private Table createMainGUITable(){
		Table mainGuiContainer = new Table();
        mainGuiContainer.setFillParent(true);
        mainGuiContainer.add(menu).expandX().fillX();
        mainGuiContainer.row();
        mainGuiContainer.add(plotManager).fill().expand();
        return mainGuiContainer;
	}

	/**
	 * Sets up the PlotManager object that contains all of the plots used in the game
	 */
	private void initiliasePlotManager(){
		Plot[][] plots = CreatePlots.createPlots(4,5);
		plotManager = new PlotManager("backgrounds/map.png",plots,humanPlayer,AIPlayer);
		  
		// Don't want anything to happen when a plot is clicked initially
	    plotManager.setPlotClickMode(PlotClickMode.NOACTION);
	}
	
	/**
	 * Overwritten LibGDX method that is called to render the game
	 */
	@Override
	public void render() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(); 
		
		batch.begin(); //everything between .begin() and .end() is drawn
		stage.draw(); // used to draw UI elements like Buttons & windows
		batch.end();
	}
	
	/**
	 * Overwritten LibGDX method that is called if the window is resized
	 */
	@Override
	public void resize (int width, int height) {
	    stage.getViewport().update(width, height, true);
	}
	
	/**
	 * Called to start the game (at the plot acquisition stage)
	 */
	private void startGame() {
		// Display the human players inventory data on the screen
		menu.setPlayerInventoryData(humanPlayer);
		plotAcquisitionStage();
	}
	
	/**
	 * Sets the game up for the plot acquisition stage. Makes AI Players choose a plot. Allows the human player choose to a plot.
	 * <p>
	 * If a human player is acquiring a plot then the PlotManager is updated
	 * </p>
	 */
	private void plotAcquisitionStage(){	
		plotManager.setCurrentPlayer(currentPlayer);
		if(currentPlayer == humanPlayer){
			menu.setMenuText("Choose a plot to acquire.");
        	plotManager.setPlotClickMode(PlotClickMode.ACQUIRE);
		}
		else{
			AIPlayer.choosePlot(plotManager);
		}

		// When done, want to call buying Roboticons phase method
	}
	
	/**
	 * Sets the game up for the roboticon purchasing stage.
	 * <p>
	 * Tells AI player to purchase roboticons if they wish. Allows human players to purchase roboticons via a market window.
	 * </p>
	 */
	private void buyingRoboticonsStage(){
		if(currentPlayer == humanPlayer){
			
			// Nothing should happen when a player clicks on a plot
			plotManager.setPlotClickMode(PlotClickMode.NOACTION);
			marketWindow = new BuyRoboticonsMarket(currentPlayer);
			
			// When the player has finished buying roboticons they can click this button to advance to the next stage
			menu.setAndShowNextStageButton("Customise roboticons",GameEvents.FINISHEDBUYINGROBOTICONS.ordinal());
		
        	stage.addActor(marketWindow);        
        	menu.setMenuText("Purchase roboticons if you wish. Click the 'Customise roboticons' button when you're done.");
        	
        	// The player has a limited amount of time in which to purchase and customise roboticons
        	// If the timer runs out we may need to skip the customising stage entirely
        	menu.setTimer(GameEvents.FINISHEDCUSTOMISINGROBOTICONS.ordinal(), 30); 
		}
		else{
			// There is no timer for AI player (as it makes decisions very quickly and it would complicate the AI)
			AIPlayer.buyRoboticons(plotManager);
			customisingRoboticonsStage();
		}
	}
	
	/**
	 * Sets the game up for the roboticon customising stage.
	 * <p>
	 * Tells AI player to customise roboticons if they wish. Allows human players to customise roboticons via a market window.
	 * </p>
	 */
	private void customisingRoboticonsStage(){
		if(currentPlayer == humanPlayer){
			
			marketWindow.remove(); // get rid of the old window (from the roboticon buying phase)
			marketWindow = new CustomiseRoboticonsMarket(currentPlayer);
			
			// Users may click the next stage button once they have finished customising their roboticons
			menu.setAndShowNextStageButton("Place roboticons",GameEvents.FINISHEDCUSTOMISINGROBOTICONS.ordinal());
		
       		stage.addActor(marketWindow);        
        	menu.setMenuText("Customise as many roboticons as you want. Click the 'Place roboticons' button when you're done.");
		}
		else{
			AIPlayer.customiseRoboticons(plotManager);
			roboticonPlacingStage();
		}
        // Note that the timer is still set from the buying roboticons stage (if human player)
	}
	
	/**
	 * Sets the game up for the roboticon placing stage.
	 * <p>
	 * Tells AI player to place roboticons if they wish. Allows human players to place roboticons.
	 * </p>
	 */
	private void roboticonPlacingStage(){
		if(currentPlayer == humanPlayer){
			menu.clearTimer();
			marketWindow.remove();
			marketWindow = null; // No longer need it
			plotManager.setPlotClickMode(PlotClickMode.PLACEROBOTICON);
			menu.setMenuText("Place roboticons if you want. Click the 'Finished my turn' button once you're done.");
			menu.setAndShowNextStageButton("Finished my turn",GameEvents.FINISHEDPLACINGROBOTICONS.ordinal());
		}
		else{
			AIPlayer.placeRoboticons(plotManager);
			nextPlayersTurn();
		}
	}
	
	/**
	 * Sets up the game for the stage where all players can buy and sell from the market.
	 * <p>
	 * This stage can only be ended by the human player
	 * </p>
	 */
	private void allPlayersMarketStage(){
		
		// Tell the AI to buy and sell whatever resources it wants to
		AIPlayer.buyAndSellResources();	

		marketWindow = new ResourceMarket(humanPlayer);
		
		// The human player may click the next stage button when they are done with the market
		menu.setAndShowNextStageButton("Finished with the market",GameEvents.FINISHEDWITHTHEMARKET.ordinal());
		
        stage.addActor(marketWindow);        
        menu.setMenuText("Market: buy and sell resources if you want. Click the 'Finished with the market' button when you are done.");
	}
	
	/**
	 * Tells all plots to produce resources for their owners (where applicable) 
	 * <p> 
	 * Also updates the players inventory data visible in the menu.
	 * </p>
	 */
	private void produceResources(){
		plotManager.produceResources();
		menu.setPlayerInventoryData(humanPlayer);
		allPlayersMarketStage();// Go straight to this stage
	}
	
	/**
	 * Starts the next round of the game (the first player can then acquire a plot again)
	 * <p>
	 * To be called once both players have completed their turns in a given round (if the game is not yet over)
	 * </p>
	 */
	private void nextRound(){
		marketWindow.remove();
		menu.hideNextStageButton(); // Shouldn't be visible during the plot acquisition stage
		currentPlayer = (currentPlayer == humanPlayer)? AIPlayer : humanPlayer; // swap players
		plotAcquisitionStage();
	}
	
	/**
	 * Sets up the game for the next players turn in a given round
	 * <p>
	 * To be called when a player has finished their turn for a given round, but another player has yet to have theirs
	 * </p>
	 */
	private void nextPlayersTurn(){
		if(currentPlayer == humanPlayer){
			menu.hideNextStageButton();
		}
		
		if(currentPlayer == firstPlayer){
			currentPlayer = (currentPlayer == humanPlayer)? AIPlayer : humanPlayer; // swap players
			plotManager.setCurrentPlayer(currentPlayer);
			plotAcquisitionStage();
		}
		else{
			produceResources();// followed immediately by the shared market phase
		}
	}
	
	/**
	 * Calculates each players final score and creates a window to tell the user who the winner is
	 * <p>
	 * To be called once all plots have been acquired and each player has finished their turn
	 * </p>
	 */
	private void gameOver(){
		// calculate scores, dialog box or something declaring the winner
		menu.hideNextStageButton();
		marketWindow.remove();
		marketWindow = null;
		menu.setMenuText("Game over!");
		stage.addActor(new GameOverWindow(humanPlayer,AIPlayer));
	}

	/**
	 * Other classes may dispatch messages (to inform this class whenever a certain event occurs). They are dealt with here
	 */
	@Override
	public boolean handleMessage(Telegram msg) {
		
		GameEvents event = GameEvents.values()[msg.message]; //convert from the integer to code to an enum for readability
		switch(event){
		case PLOTAQUIRED:// When a plot has been acquired we want to move to the buying stage
			buyingRoboticonsStage();
			break;
		case FINISHEDBUYINGROBOTICONS:
			customisingRoboticonsStage();
			break;
		case FINISHEDCUSTOMISINGROBOTICONS:
			roboticonPlacingStage();
			break;
		case FINISHEDPLACINGROBOTICONS:
			plotManager.removeRoboticonPlaceMenu();
			plotManager.setPlotClickMode(PlotClickMode.NOACTION);
			nextPlayersTurn(); // if all players have had their turn, causes production and then market access
			break;
		case FINISHEDWITHTHEMARKET:
			// The game ends when all plots have been acquired and all players finished their turn in the current round
			// (Both players will have finished their turn once they are both finished using the market in the stage where they both have access to the market)
			if(plotManager.allPlotsAquired()){
				gameOver();
			}
			else{
				nextRound();
			}
			break;
		case PLAYERINVENTORYUPDATE:
			// Whenever something happens that means that the players inventory data that is displayed on the screen should be updated
			menu.setPlayerInventoryData(humanPlayer);
			break;
		default:
			throw new IllegalArgumentException("Unable to respond to message type "+event);
		}
	
		return true;
	}
}
