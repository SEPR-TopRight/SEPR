package com.topright.roboticon;

import java.util.EnumMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.Stage;

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
	private Market market;
	
	@Override
	public void create(){
		batch = new SpriteBatch();
		stage = new Stage();
		menu = new MenuBar();
        Gdx.input.setInputProcessor(stage);
       
        plotManager = new PlotManager("backgrounds/map.png");
        
        EnumMap<RoboticonCustomisation,Integer> HumanRoboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
        EnumMap<RoboticonCustomisation,Integer> AIRoboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
        humanPlayer = new Player(new PlayerInventory(10, 0, HumanRoboticonQuantities, 500));
        AIPlayer = new AIPlayer(new PlayerInventory(0, 0, AIRoboticonQuantities, 500)); // change to AI player class

	plotManager.setHumanPlayer(humanPlayer);
	plotManager.setAIPlayer(AIPlayer);
     
        firstPlayer = humanPlayer; //change so is random
        currentPlayer = firstPlayer;
        plotManager.setPlotClickMode(PlotClickMode.NOACTION);

	market = new Market(10,10,10);
        
        Table mainGuiContainer = new Table();
        mainGuiContainer.setFillParent(true);
        mainGuiContainer.add(menu).expandX().fillX();
        mainGuiContainer.row();
        mainGuiContainer.add(plotManager).fill().expand();
        
    	
        
        //this class handles all game events
        for (GameEvents event : GameEvents.values()) {
        	  MessageManager.getInstance().addListener(this, event.ordinal());
        }
  
        stage.addActor(mainGuiContainer);
        
        //camera = new OrthographicCamera(48, 32);
        //camera.update();
        //world = new World(new Vector2(5, -10), true);
        //rayHandler = new RayHandler(world);
        //rayHandler.setAmbientLight(new Color(.1f, .1f, .1f, .2f));
        //new PointLight(rayHandler, 50, new Color(1,1,1, 1), 100,30,20).setXray(true); // No bodies to collide with...
        
     
        
        startGame();
        

	}
	
	
	

	@Override
	public void render () {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(); //TODO: figure out what this does
		//limited to 30fps
		
		batch.begin(); //everything between .begin() and .end() is drawn
		stage.draw(); // used to draw UI elements like Buttons & windows
		
		batch.end();
		

      //  rayHandler.setCombinedMatrix(camera.combined);
       // rayHandler.updateAndRender();

	}
	
	@Override
	public void resize (int width, int height) {
	    stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	
	public void startGame() { // rename to startRound?
		
		//TODO: create players
			//TODO: & inventories
		
		//TODO: create maps
			//TODO: array to keep all plots
		menu.updatePlayerInventoryData(humanPlayer.inventory);
		plotManager.setCurrentPlayer(currentPlayer);
		plotAquisitionStage();
	}
	
	//called once done with
	public void roboticonPlacingStage(){
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
	
	public void plotAquisitionStage(){	
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
	
	public void buyingRoboticonsStage(){
		if(currentPlayer == humanPlayer){
			plotManager.setPlotClickMode(PlotClickMode.NOACTION);
			marketWindow = new BuyRoboticonsMarket(currentPlayer, market);
			menu.setAndShowNextStageButton("customising roboticons",GameEvents.FINISHEDBUYINGROBOTICONS.ordinal());
		
        		stage.addActor(marketWindow);        
        		menu.setMenuText("Purchase roboticons. Click the next button when you're done.");
        		menu.setTimer(GameEvents.FINISHEDCUSTOMISINGROBOTICONS.ordinal(), 30); // If the timer runs out we may need to skip the customising stage entirely
		}
		else{
			AIPlayer.buyRoboticons(market,plotManager);
			customisingRoboticonsStage();
		}
	}
	
	public void customisingRoboticonsStage(){
		if(currentPlayer == humanPlayer){
			plotManager.setPlotClickMode(PlotClickMode.NOACTION);
			marketWindow.remove(); // get rid of the old window
			marketWindow = new CustomiseRoboticonsMarket(currentPlayer,market);
			menu.setAndShowNextStageButton("place roboticos",GameEvents.FINISHEDCUSTOMISINGROBOTICONS.ordinal());
		
       			stage.addActor(marketWindow);        
        		menu.setMenuText("Customise roboticons. Click the next button when you're done.");
		}
		else{
			AIPlayer.customiseRoboticons(market, plotManager);
			roboticonPlacingStage();
		}
        // Note that the timer is still set from the buying roboticons stage (if human player)
	}
	
	public void allPlayersMarketStage(){
		AIPlayer.buyAndSellResources(market);// currently only does it once..		

		marketWindow = new ResourceMarket(humanPlayer,market);
		menu.setAndShowNextStageButton("Finished with the market",GameEvents.FINISHEDWITHTHEMARKET.ordinal());
		
        	stage.addActor(marketWindow);        
        	menu.setMenuText("Market. Click the finished button when you are done.");
	}
	
	public void produceResources(){
		plotManager.produceResources();
		menu.updatePlayerInventoryData(humanPlayer.inventory);
		allPlayersMarketStage();// must happen straight after
	}
	
	public void nextRound(){
		marketWindow.remove();
		menu.hideNextStageButton();
		currentPlayer = (currentPlayer == humanPlayer)? AIPlayer : humanPlayer; // swap players
		menu.updatePlayerInventoryData(humanPlayer.inventory);
		plotManager.setCurrentPlayer(currentPlayer);
		if(plotManager.allPlotsAquired())
			gameOver();
		else
			plotAquisitionStage();
	}
	
	public void nextPlayersTurn(){
		if(currentPlayer == firstPlayer){// assumes only 2 players...
			menu.hideNextStageButton();
			currentPlayer = (currentPlayer == humanPlayer)? AIPlayer : humanPlayer; // swap players
			menu.updatePlayerInventoryData(humanPlayer.inventory);
			plotManager.setCurrentPlayer(currentPlayer);
			plotAquisitionStage();
		}
		else{
			produceResources();// followed immediately by the shared market phase
		}
	}
	
	public void gameOver(){
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
			if(plotManager.allPlotsAquired())
				gameOver();
			else
				nextRound();
			break;
		case ROBOTICONPLACED:
			menu.updatePlayerInventoryData(humanPlayer.inventory);
			break;
                case PLAYERPURCHASE:
			menu.updatePlayerInventoryData(humanPlayer.inventory);
			break;
		default:
			throw new IllegalArgumentException("Unable to respond to message type "+event);
		}
	
		return true;
	}
}
