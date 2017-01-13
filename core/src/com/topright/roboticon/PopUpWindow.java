package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;

/**
 * Base class to be used for the market window and game over window classes (any pop up window that should not behave like MessagePopUp)
 * @see com.topright.roboticon.MessagePopUp()
 * @author jcn509
 */
public class PopUpWindow extends Window{
	
	/**
	 * Constructor
	 * @param titleText The title of the window
	 */
	public PopUpWindow(String titleText){
		super(titleText, new Skin(Gdx.files.internal("uiskin.json")));
		setResizable(false);
	}
	
	/**
	 * Moves the window to the middle of the screen
	 * <p>
	 * Afterwards the centre of the window will be in the middle of the screen
	 * </p>
	 */
	protected void moveToMiddleOfScreen(){
		
		// Width and height of this window
		float windowWidth = getWidth();
		float windowHeight = getHeight();
		
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		
		// Move the window
		setX((screenWidth/2)-(windowWidth/2));
		setY((screenHeight/2)-(windowHeight/2));
	}
	
}
