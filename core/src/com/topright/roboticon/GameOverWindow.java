package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Popup Window used to inform the player as to who has won (and display all player's scores)
 * @author jcn509
 *
 */
public class GameOverWindow extends PopUpWindow {

	/**
	 * Constructor
	 * @param human The human player that is currently playing the game
	 * @param AI The AI player that is currently playing the game
	 */
	public GameOverWindow(Player human, Player AI){
		super("Game over");
		createLabels(human, AI);
		setSize(getPrefWidth(),getPrefHeight());
		moveToMiddleOfScreen();
	}	
	
	/**
	 * Creates the labels that inform the player as to who has won and the score earned by each player
	 * @param human The human player that is currently playing the game
	 * @param AI The AI player that is currently playing the game
	 */
	private void createLabels(Player human, Player AI){
		
		String winnerText = getWinnerText(human,AI); // States which player has won
		Label winnerLabel = new Label(winnerText, new Skin(Gdx.files.internal("uiskin.json")));
		
		String humanPlayerScore = human.calculateScore().toString();
		Label humanPlayerScoreLabel = new Label("Your final score: "+humanPlayerScore, new Skin(Gdx.files.internal("uiskin.json")));
		String AIPlayerScore = human.calculateScore().toString();
		Label AIPlayerScoreLabel = new Label("AI final score: "+AIPlayerScore, new Skin(Gdx.files.internal("uiskin.json")));
	
		add(winnerLabel);
		row();
		add(humanPlayerScoreLabel);
		row();
		add(AIPlayerScoreLabel);
	}
	
	/**
	 * Returns a string that states which player has won
	 * @param human The human player that is currently playing the game
	 * @param AI The AI player that is currently playing the game
	 * @return a string that states which player has won
	 */
	private String getWinnerText(Player human, Player AI){
		if(human.calculateScore() > AI.calculateScore()){ // Human player has won
			return "You win!";
		}
		else if(human.calculateScore() == AI.calculateScore()){
			return "Its a draw!";
		}
		else{
			return "The AI won!";
		}
	}
	
}
