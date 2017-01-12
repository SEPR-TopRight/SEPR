package com.topright.roboticon;

import java.util.EnumMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.lang.Math;

/**
 * Implements the actual game
 * @author jcn509
 */
public class Main extends ApplicationAdapter implements Telegraph{
	private SpriteBatch batch;
	private Texture img;
	private Stage stage;
	private MenuBar menu;
	
	private PopUpWindow marketWindow;    
	
	private Player humanPlayer;
	private AIPlayer AIPlayer;
	private Player currentPlayer;
	private Player firstPlayer;
	private PlotManager plotManager;
	
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
		  plotManager = new PlotManager("backgrounds/map.png",humanPlayer,AIPlayer);
		  
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
		menu.updatePlayerInventoryData(humanPlayer);
		
		plotManager.setCurrentPlayer(currentPlayer);
		plotAcquisitionStage();
	}
	
	/**
	 * Handles plot acquisition. Makes AI Players choose a plot. Allows the human player choose to a plot.
	 * <p>
	 * If a human player is acquiring a plot then the PlotManager is updated
	 * </p>
	 */
	private void plotAcquisitionStage(){	
		if(currentPlayer == humanPlayer){
			menu.setMenuText("Choose a plot to aquire");
			plotManager.setCurrentPlayer(currentPlayer);
        	plotManager.setPlotClickMode(PlotClickMode.AQUIRE);
		}
		else{
			AIPlayer.choosePlot(plotManager);
		}

		// When done, want to call buying Roboticons phase method
	}
	
	/**
	 * Handles the roboticon placing stage of the game. 
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
			menu.setMenuText("Place roboticons if you want. Click next once you're done");
			menu.setAndShowNextStageButton("finished my turn",GameEvents.FINISHEDPLACINGROBOTICONS.ordinal());
		}
		else{
			AIPlayer.placeRoboticons(plotManager);
			nextPlayersTurn();
		}
	}
	
	/**
	 * 
	 */
	private void buyingRoboticonsStage(){
		if(currentPlayer == humanPlayer){
			plotManager.setPlotClickMode(PlotClickMode.NOACTION);
			marketWindow = new BuyRoboticonsMarket(currentPlayer);
			menu.setAndShowNextStageButton("customising roboticons",GameEvents.FINISHEDBUYINGROBOTICONS.ordinal());
		
        	stage.addActor(marketWindow);        
        	menu.setMenuText("Purchase roboticons. Click the next button when you're done.");
        	menu.setTimer(GameEvents.FINISHEDCUSTOMISINGROBOTICONS.ordinal(), 30); // If the timer runs out we may need to skip the customising stage entirely
		}
		else{
			AIPlayer.buyRoboticons(plotManager);
			customisingRoboticonsStage();
		}
	}
	
	private void customisingRoboticonsStage(){
		if(currentPlayer == humanPlayer){
			plotManager.setPlotClickMode(PlotClickMode.NOACTION);
			marketWindow.remove(); // get rid of the old window
			marketWindow = new CustomiseRoboticonsMarket(currentPlayer);
			menu.setAndShowNextStageButton("place roboticos",GameEvents.FINISHEDCUSTOMISINGROBOTICONS.ordinal());
		
       		stage.addActor(marketWindow);        
        	menu.setMenuText("Customise roboticons. Click the next button when you're done.");
		}
		else{
			AIPlayer.customiseRoboticons(plotManager);
			roboticonPlacingStage();
		}
        // Note that the timer is still set from the buying roboticons stage (if human player)
	}
	
	private void allPlayersMarketStage(){
		AIPlayer.buyAndSellResources();// currently only does it once..		

		marketWindow = new ResourceMarket(humanPlayer);
		menu.setAndShowNextStageButton("Finished with the market",GameEvents.FINISHEDWITHTHEMARKET.ordinal());
		
        stage.addActor(marketWindow);        
        menu.setMenuText("Market. Click the finished button when you are done.");
	}
	
	private void produceResources(){
		plotManager.produceResources();
		menu.updatePlayerInventoryData(humanPlayer);
		allPlayersMarketStage();// must happen straight after
	}
	
	private void nextRound(){
		marketWindow.remove();
		menu.hideNextStageButton();
		currentPlayer = (currentPlayer == humanPlayer)? AIPlayer : humanPlayer; // swap players
		menu.updatePlayerInventoryData(humanPlayer);
		plotManager.setCurrentPlayer(currentPlayer);
		if(plotManager.allPlotsAquired())
			gameOver();
		else
			plotAcquisitionStage();
	}
	
	private void nextPlayersTurn(){
		if(currentPlayer == firstPlayer){// assumes only 2 players...
			menu.hideNextStageButton();
			currentPlayer = (currentPlayer == humanPlayer)? AIPlayer : humanPlayer; // swap players
			menu.updatePlayerInventoryData(humanPlayer);
			plotManager.setCurrentPlayer(currentPlayer);
			plotAcquisitionStage();
		}
		else{
			produceResources();// followed immediately by the shared market phase
		}
	}
	
	private void gameOver(){
		// calculate scores, dialog box or something declaring the winner
		menu.hideNextStageButton();
		marketWindow.remove();
		marketWindow = null;
		stage.addActor(new GameOverWindow(humanPlayer,AIPlayer));
		
	}

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
			if(plotManager.allPlotsAquired()){
				gameOver();
			}
			else{
				nextRound();
			}
			break;
		case PLAYERINVENTORYUPDATE:
			menu.updatePlayerInventoryData(humanPlayer);
			break;
		default:
			throw new IllegalArgumentException("Unable to respond to message type "+event);
		}
	
		return true;
	}
}
