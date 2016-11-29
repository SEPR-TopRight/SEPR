package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A class for creating and destroying UI Buttons
 * 
 * @author andrew
 *
 */
public class Buttons extends TextButton{
	
	
    //TODO: figure out how not to stretch the button
    
    /**
     *      * Creates a button object and adds it to the Main.stage. 
     * Takes text as an input and an x & y co-ordinates.
     * 
     * @author andrew
     * @param text
     * @param x
     * @param y
     */
    
    
    //there has to be a less ridiculous way of doing this...
    private static TextButtonStyle getTextButtonStyle(String up, String down, String texturePath){
    	TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal(texturePath));
        skin.addRegions(buttonAtlas);
        textButtonStyle.up = skin.getDrawable(up);
        textButtonStyle.down = skin.getDrawable(down);
        return textButtonStyle;
    }
    
	public Buttons(String text, String texturePath, String up, String down, ClickListener click){
		super(text,getTextButtonStyle(up, down, texturePath)); 
        
        
        //textButtonStyle.checked = skin.getDrawable("checked-button");
        
       
        
        addListener(click);
        
		
	}
	
	public void setImage(String texturePath, String up, String down){
		this.setStyle(getTextButtonStyle(up,down,texturePath));
	}
	
}
