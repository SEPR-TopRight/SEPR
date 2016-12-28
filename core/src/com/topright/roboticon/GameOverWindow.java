package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameOverWindow extends PopUpWindow {

	public GameOverWindow(Player human, Player AI){
		super("Game over");
		if(human.calculateScore() > AI.calculateScore()){
			add(new Label("You win! with a final score of "+human.calculateScore().toString(), new Skin(Gdx.files.internal("uiskin.json")))).pad(15);
		}
		setSize(getPrefWidth(),getPrefHeight());
		moveToMiddleOfScreen();
	}	
	
}
