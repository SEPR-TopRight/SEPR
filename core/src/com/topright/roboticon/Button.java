package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A class for creating and destroying UI Buttons
 * 
 * @author andrew
 *
 */
public class Button {
	
	TextButton button;
    TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;
	
    //TODO: figure out how not to stretch the button
    
    /**
     * 
     * Creates a button object and adds it to the Main.stage. 
     * Takes text as an input and an x & y co-ordinates.
     * 
     * @author andrew
     * @param text
     * @param x
     * @param y
     */
	public void create(String text, float x, float y, float width, float height, String texturePath, String up, String down, ClickListener click){
		
		font = new BitmapFont();
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal(texturePath));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable(up);
        textButtonStyle.down = skin.getDrawable(down);
        //textButtonStyle.checked = skin.getDrawable("checked-button");
        button = new TextButton(text, textButtonStyle);
        button.setX(x);
        button.setY(y);
        button.setSize(width, height);
        
        button.addListener(click);
        
        Main.stage.addActor(button);
		
	}
	
	public void setClickListener(){
		
		System.out.println("Hello");
		
	}
	
	/**
	 * Deletes the button from the stage and from existence .. forever. 
	 * 
	 * @author andrew
	 */
	public void remove(){
		
		button.remove();
		
	}
	
}
