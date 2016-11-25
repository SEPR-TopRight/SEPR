package com.topright.roboticon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

//TODO: Window, Text, Image

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture testimage;
	static Stage stage;
	Texture texture;
	TextureRegion region;
	MenuBar menu;
	private PopUpWindow window;
	private Images background;
    
	
	@Override
	public void create(){
		batch = new SpriteBatch();
		stage = new Stage();
		MenuBar menu = new MenuBar();
        Gdx.input.setInputProcessor(stage);
        
        img = new Texture(Gdx.files.internal("badlogic.jpg"));
        
        background = new Images();
        background.create("backgrounds/test.png", 0, 0, 1680, 990);
        //---/TEST CREATIONS\---
        //images testImage = new Images();
        //testImage.create("buttons/ButtonOff.9.png", 64, 64, 128, 128);
        
		
		
		//Text testText = new Text();
		//testText.create("Example text!", 500, 500);
		//---\TEST CREATIONS/---
		
		menu.create();
		window = new PopUpWindow();
		window.create("title");
		
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
	
	public void StartGame() {
		//TODO: create players
			//TODO: & inventories
		//Market market = new Market();
		//TODO: create maps
			//TODO: array to keep all plots
		
		
		//TODO: call gameloop
	}
	
	public void GameLoop(){
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
