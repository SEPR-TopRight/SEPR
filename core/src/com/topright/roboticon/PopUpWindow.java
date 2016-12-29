package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

/**
 * Base class to be used for the market window
 * @author jcn509
 *
 */
public class PopUpWindow extends Window{
	public PopUpWindow(String titleText){
		super(titleText, new Skin(Gdx.files.internal("uiskin.json")));
		setResizable(false);
	}
	
	protected void moveToMiddleOfScreen(){
		float windowWidth = getWidth();
		float windowHeight = getHeight();
		
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		
		setX((screenWidth/2)-(windowWidth/2));
		setY((screenHeight/2)-(windowHeight/2));
	}
	
}
