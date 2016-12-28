package com.topright.roboticon;

import java.util.EnumMap;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import box2dLight.ConeLight;
import box2dLight.DirectionalLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


//TODO: Window, Text, Image

public class Main extends ApplicationAdapter implements Telegraph{
	private SpriteBatch batch;
	private Texture img;
	private Stage stage;
	private MenuBar menu;
	
	//OrthographicCamera camera;
   // RayHandler rayHandler;
    //World world;
	
	private PopUpWindow marketWindow;    
	
	 // initialise later
	
	private Player humanPlayer;
	private Player AIPlayer;
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
       
        Plot[][] plots = initialisePlots();
        plotManager = new PlotManager(plots,"backgrounds/map.png");
        
       
        EnumMap<RoboticonCustomisation,Integer> HumanRoboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
        EnumMap<RoboticonCustomisation,Integer> AIRoboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
        humanPlayer = new Player(new PlayerInventory(0, 0, HumanRoboticonQuantities, 100000));
        humanPlayer.inventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 5);
        AIPlayer = new Player(new PlayerInventory(0, 0, AIRoboticonQuantities, 0)); // change to AI player class
     
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
	
	public Plot[][] initialisePlots(){
		Plot[][] plots = new Plot[4][4];
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
			    String[] bestAtChoices = {"ore","energy"};
			    int choice = new Random().nextInt(bestAtChoices.length);
			
			    // No Player owns each plot, no roboticon is placed on it and its best at attribute is random
				plots[row][column]= new Plot(null, bestAtChoices[choice], RoboticonCustomisation.UNCUSTOMISED);
				
				// For testing purposes
				//plots[row][column].setRoboticon(RoboticonCustomisation.ENERGY);
			}
		}
		return plots;
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
		menu.updatePlayerInventoryData(currentPlayer.inventory);
		plotManager.setCurrentPlayer(currentPlayer);
		//plotAquisitionStage();
		allPlayersMarketStage();
	}
	
	//called once done with
	public void roboticonPlacingStage(){
		menu.clearTimer();
		marketWindow.remove();
		marketWindow = null; // No longer need it
		plotManager.setPlotClickMode(PlotClickMode.PLACEROBOTICON);
		menu.setMenuText("Place roboticons if you want. Click next once you're done");
		menu.setAndShowNextStageButton("finished my turn",GameEvents.FINISHEDPLACINGROBOTICONS.ordinal());
	}
	
	public void plotAquisitionStage(){
		menu.setMenuText("Choose a plot to aquire");
		plotManager.setCurrentPlayer(currentPlayer);
        plotManager.setPlotClickMode(PlotClickMode.AQUIRE);
		if(currentPlayer == humanPlayer)
			plotManager.setAqusitionColour("blue");
		else
			plotManager.setAqusitionColour("orange");
		//t.addActor(p);
		// When done, want to call buying Roboticons phase method
	}
	
	public void buyingRoboticonsStage(){
		plotManager.setPlotClickMode(PlotClickMode.NOACTION);
		marketWindow = new BuyRoboticonsMarket(currentPlayer, market);
		menu.setAndShowNextStageButton("customising roboticons",GameEvents.FINISHEDBUYINGROBOTICONS.ordinal());
		
        stage.addActor(marketWindow);        
        menu.setMenuText("Purchase roboticons. Click the next button when you're done.");
        menu.setTimer(GameEvents.FINISHEDCUSTOMISINGROBOTICONS.ordinal(), 30); // If the timer runs out we may need to skip the customising stage entirely
	}
	
	public void customisingRoboticonsStage(){
		plotManager.setPlotClickMode(PlotClickMode.NOACTION);
		marketWindow.remove(); // get rid of the old window
		marketWindow = new CustomiseRoboticonsMarket(currentPlayer,market);
		menu.setAndShowNextStageButton("place roboticos",GameEvents.FINISHEDCUSTOMISINGROBOTICONS.ordinal());
		
        stage.addActor(marketWindow);        
        menu.setMenuText("Customise roboticons. Click the next button when you're done.");
        // Note that the timer is still set from the buying roboticons stage
	}
	
	public void allPlayersMarketStage(){
		marketWindow = new ResourceMarket(currentPlayer,market);
		menu.setAndShowNextStageButton("Finished with the market",GameEvents.FINISHEDWITHTHEMARKET.ordinal());
		
        stage.addActor(marketWindow);        
        menu.setMenuText("Market. Click the finished button when you are done.");
	}
	
	public void produceResources(){
		plotManager.produceResources();
		menu.updatePlayerInventoryData(currentPlayer.inventory);
		allPlayersMarketStage();// must happen straight after
	}
	
	public void nextRound(){
		marketWindow.remove();
		menu.hideNextStageButton();
		currentPlayer = (currentPlayer == humanPlayer)? AIPlayer : humanPlayer; // swap players
		System.out.println(currentPlayer == humanPlayer);
		menu.updatePlayerInventoryData(currentPlayer.inventory);
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
			System.out.println(currentPlayer == humanPlayer);
			menu.updatePlayerInventoryData(currentPlayer.inventory);
			plotManager.setCurrentPlayer(currentPlayer);
			plotAquisitionStage();
		}
		else{
			produceResources();// followed immediately by the shared market phase
		}
	}
	
	public void gameOver(){
		// calculate scores, dialog box or something declaring the winner
		System.out.println("do something");
	}

	@Override
	public boolean handleMessage(Telegram msg) {
		// TODO Auto-generated method stub
		GameEvents event = GameEvents.values()[msg.message]; //convert from the integer to code to an enum for readability
		switch(event){
		case PLOTAQUIRED:// When a plot has been acquired we want to move to the buying stage
			buyingRoboticonsStage();
			//new PointLight(rayHandler, 50, new Color((float) 0.239215686,(float) 0.796078431,(float) 0.921568627, 1), 10, 0, 0);
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
			nextRound();
			break;
		case ROBOTICONPLACED:
			menu.updatePlayerInventoryData(currentPlayer.inventory);
			break;
                case PLAYERPURCHASE:
			menu.updatePlayerInventoryData(currentPlayer.inventory);
			break;
		default:
			throw new IllegalArgumentException("Unable to respond to message type "+event);
		}
	
		return true;
	}
}
