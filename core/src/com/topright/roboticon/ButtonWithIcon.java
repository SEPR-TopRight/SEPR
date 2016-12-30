package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Buttons that use given background images (one for the default state and one for the clicked state)
 * 
 * @author andrew
 *
 */
public class ButtonWithIcon extends TextButton{
    
    /**
     * Used to set the style of the button (what images are used for what state)
     * @param up The name of the image to be used when the button is in the up state
     * @param down The name of the image to be used when the button is in the down state
     * @param texturePath The location where to the texture file that is to be used by the button is stored
     * @return
     */
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
    
    /**
     * Constructor
     * @param text The text that is to appear on the button
     * @param texturePath The location of the texture file that is to be used by the button is stored
     * @param up The name of the image that is to be used when the button is in the up state
     * @param down The name of the image that is to be used when the button is in the down state
     * @param click The clickLister that is to be called when the button is clicked
     */
	public ButtonWithIcon(String text, String texturePath, String up, String down, ClickListener click){
		super(text,getTextButtonStyle(up, down, texturePath)); 
        addListener(click);
	}
	

	/**
	 * Change the images used by the button
	 * @param texturePath The location of the texture file that is to be used by the button is stored
     * @param up The name of the image that is to be used when the button is in the up state
     * @param down The name of the image that is to be used when the button is in the down state
	 */
	public void setImages(String texturePath, String up, String down){
		this.setStyle(getTextButtonStyle(up,down,texturePath));
	}
	
}
