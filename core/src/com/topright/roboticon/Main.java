package com.topright.roboticon;

import java.util.EnumMap;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;

import box2dLight.RayHandler;

//TODO: Window, Text, Image

public class Main extends ApplicationAdapter implements Telegraph{
	private SpriteBatch batch;
	private Texture img;
	private Stage stage;
	private MenuBar menu;
	
	private PopUpWindow marketWindow;    
	
	private Plot[][] plots = new Plot[4][4]; // initialise later
	
	private Player humanPlayer;
	private Player AIPlayer;
	private Player currentPlayer;
	private PlotHolder plotHolder;
	
	@Override
	public void create(){
		batch = new SpriteBatch();
		stage = new Stage();
		menu = new MenuBar();
        Gdx.input.setInputProcessor(stage);
        //stage.addActor(menu);
        initialisePlots();
        plotHolder = new PlotHolder(plots,"backgrounds/test.png");
        
       
        EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
        humanPlayer = new Player(new PlayerInventory(0, 0, roboticonQuantities, 0));
        AIPlayer = new Player(new PlayerInventory(0, 0, roboticonQuantities, 0));
     
        currentPlayer = humanPlayer;//change so is random
        plotHolder.setClickActionNone();
        
        Table mainGuiContainer = new Table();
        mainGuiContainer.setFillParent(true);
        mainGuiContainer.add(menu).expandX().fillX();
        mainGuiContainer.row();
        mainGuiContainer.add(plotHolder).fill().expand();
        
        MessageManager.getInstance().addListener(this, 1);
        
              
  
        stage.addActor(mainGuiContainer);
      
        
        startGame();
        

	}
	
	public void initialisePlots(){
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
			    String[] bestAtChoices = {"ore","energy"};
			    int choice = new Random().nextInt(bestAtChoices.length);
			
			    // No Player owns each plot, no roboticon is placed on it and its best at attribute is random
				plots[row][column]= new Plot(null, bestAtChoices[choice], RoboticonCustomisation.UNCUSTOMISED);
			}
		}
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
	
	public void startGame() {
		
		//TODO: create players
			//TODO: & inventories
		//Market market = new Market();
		//TODO: create maps
			//TODO: array to keep all plots
		
		plotAquisitionStage();
	}
	
	//called once done with
	public void roboticonPlacingStage(){
		menu.clearTimer();
		marketWindow.remove();
		menu.removeNextStageButton();
		marketWindow = null;
	}
	
	public void plotAquisitionStage(){
		menu.setMenuText("Choose a plot to aquire");
		plotHolder.setCurrentPlayer(humanPlayer);
        plotHolder.setClickActionAquire();
		
		//t.addActor(p);
		// When done, want to call buying Roboticons phase method
	}
	
	//probably going to rename
	public void buyingRoboticonsStage(){
		plotHolder.setClickActionNone();
		marketWindow = new PopUpWindow("Market");
		menu.createAndSetNextStageButton(this::roboticonPlacingStage);
        stage.addActor(marketWindow);        
        menu.setMenuText("Purchase roboticons. Click the next button when you're done.");
        menu.setTimer(this::roboticonPlacingStage, 6);
	}
	

		//plotAquisitionStage();//spelling?
		//buyingRoboticonsStage();
		//phase 1, 2 & 3:
		// for all players
			//TODO: acquire 
			//TODO: purchase & customisation 
			//TODO: install roboticon
		
		//phase 4 & 5
		
		//TODO: produce all resources
		//TODO: auction of resources
		
		//next turn
		//TODO: next turn

	@Override
	public boolean handleMessage(Telegram msg) {
		// TODO Auto-generated method stub
		if((int) msg.message == 1){// When a plot has been acquired we want to move to the buying stage
			buyingRoboticonsStage();
		}
		return true;
	}
}
