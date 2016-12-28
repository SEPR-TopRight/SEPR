package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

public class MessagePopUp extends Dialog{
	public MessagePopUp(String title, String message){
		super(title,new Skin(Gdx.files.internal("uiskin.json")));
		Label notEnoughMoneyLabel = new Label(message, new Skin(Gdx.files.internal("uiskin.json")));
	
		TextButton closeButton = new TextButton("Ok", new Skin(Gdx.files.internal("uiskin.json")));
		closeButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				remove();
			}
		});

		text(notEnoughMoneyLabel);
	
		button(closeButton);
		setSize(getPrefWidth(),getPrefHeight());
		moveToMiddleOfScreen();

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
