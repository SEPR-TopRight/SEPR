package com.topright.roboticon;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;

import box2dLight.RayHandler;

//TODO: Window, Text, Image

public class Main extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private Stage stage;
	private MenuBar menu;
	
	private PopUpWindow marketWindow;    
	
	private Plot[][] plots = new Plot[4][4]; // initialise later
	
	@Override
	public void create(){
		batch = new SpriteBatch();
		stage = new Stage();
		menu = new MenuBar();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(menu);
        
        img = new Texture(Gdx.files.internal("badlogic.jpg"));
        
        Images backgroundImage = new Images("backgrounds/test.png", 0, 0, 1680, 990);
        stage.addActor(backgroundImage);		
        
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
		batch.draw(img, 100, 200);
		stage.draw(); // used to draw UI elements like Buttons & windows
		
		batch.end();
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
		
		
		gameLoop();
	}
	
	//purely for testing purposes
	public void nextStage(){
		menu.clearTimer();
		marketWindow.remove();
		menu.removeNextStageButton();
		marketWindow = null;
	}
	
	public void plotAquisitionStage(){
		plotHolder p = new plotHolder(plots,1680,990);
		stage.addActor(p);
		// When done, want to call buying Roboticons phase method
	}
	
	//probably going to rename
	public void buyingRoboticonsStage(){
		marketWindow = new PopUpWindow("Market");
		menu.createAndSetNextStageButton(this::nextStage);
        stage.addActor(marketWindow);        
        menu.setMenuText("Buying Roboticons");
        menu.setTimer(this::nextStage, 6);
	}
	
	public void gameLoop(){// Each stage calls the next stage/gameloop method?
		plotAquisitionStage();//spelling?
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
	}
}
