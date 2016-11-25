package com.topright.roboticon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

//TODO: Window, Text, Image

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture testimage;
	Stage stage;
	Texture texture;
	TextureRegion region;
	MenuBar menu;
	Buttons nextStageButton;
	PopUpWindow marketWindow;
	int timer_time;
	Label timer_label;
	private PopUpWindow window;
	private Images background;
    
	
	@Override
	public void create(){
		batch = new SpriteBatch();
		stage = new Stage();
		menu = new MenuBar();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(menu);
        
        img = new Texture(Gdx.files.internal("badlogic.jpg"));
        
        timer_label = new Label("", new Skin(Gdx.files.internal("uiskin.json")));
        timer_label.setX(1000);
        timer_label.setY(1000);
        
        background = new Images("backgrounds/test.png", 0, 0, 1680, 990);
        stage.addActor(background);
        menu.addActor(timer_label);
        //---/TEST CREATIONS\---
        //images testImage = new Images();
        //testImage.create("buttons/ButtonOff.9.png", 64, 64, 128, 128);
        
		
		
		//Text testText = new Text();
		//testText.create("Example text!", 500, 500);
		//---\TEST CREATIONS/---
		
        
        gameLoop();
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
		marketWindow.remove();
		nextStageButton.remove();
		marketWindow = null;
		nextStageButton = null;
		timer_label.setText("");
		timer_time = 0;
	}
	
	
	//probably going to rename
	public void marketStage(){
		marketWindow = new PopUpWindow("Market");
		nextStageButton = new Buttons("done with market",1630, 1000, 40, 40, "buttons/buttons.pack", "ButtonOn", "ButtonOn", new ClickListener() {
	        @Override
			public void clicked(InputEvent event, float x, float y)
	        {
	            nextStage();
	        }
	    } );		
		menu.addActor(nextStageButton);
        stage.addActor(marketWindow);
        final int time_till_next_stage = 10;
        timer_time = time_till_next_stage;
        Timer.schedule(new Task(){
            @Override
            public void run() {
            	if(timer_time == 0){ // stops things from being broken if the user clicks the close button
            		cancel();
            	}
            	else if(timer_time>1){
            		timer_time-=1;
            		timer_label.setText("Time left: "+timer_time);
            	}
            	else
            		nextStage();
            }
        }
        , 0       //    (delay till start)
        , 1     //    (execute every x seconds)
        , time_till_next_stage -1
    );
        
	}
	
	public void gameLoop(){
		marketStage();
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
