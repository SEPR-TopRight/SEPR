package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

/**
 * Contains all 
 * @author jcn509
 *
 */
public class PopUpWindow extends Window{
	public PopUpWindow(String titleText){
		super(titleText, new Skin(Gdx.files.internal("uiskin.json")));
		
		
		setWidth(1000);
	    align(Align.center | Align.top);
	    setSize(1480,790);
	    setPosition(100,100);
	    setMovable(false);
	    setResizable(false);
	}
}
