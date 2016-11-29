package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Contains all 
 * @author andrew
 *
 */

public class MenuBar extends Table{
	private int timerTime;
	private Label menuLabel;
	private String menuText;
	private Buttons nextStageButton;
	@FunctionalInterface
	interface Action {
	  void execute();
	}
	
	public void createAndSetNextStageButton(final Runnable methodToCallUponClick){
		nextStageButton = new Buttons("done with market", "buttons/buttons.pack", "ButtonOn", "ButtonOn", new ClickListener() {
	        @Override
			public void clicked(InputEvent event, float x, float y)
	        {
	        	methodToCallUponClick.run();
	        }
	    } );
		add(nextStageButton).right();
	}
	
	public void setMenuText(String menuText){
		this.menuText = menuText;
		menuLabel.setText(menuText);
		
	}
	
	public void removeNextStageButton(){
		nextStageButton.remove();
		nextStageButton = null; // May not be the best solution?
	}
	
	public void clearTimer(){
		menuLabel.setText(menuText); // Remove the Time left: part
		timerTime=0;
	}
	
	public void setTimer(final Runnable methodToCallWhenTimeUp, final int time){
		timerTime = time;
		Timer.schedule(new Task(){
            @Override
            public void run() {
            	if(timerTime == 0){ // stops things from being broken if the timer cleared from elsewhere
            		cancel();
            	}
            	else if(timerTime>1){
            		timerTime-=1;
            		menuLabel.setText(menuText+" Time left: "+timerTime);
            	}
            	else{
            		methodToCallWhenTimeUp.run();
            	}
            }
        }
        , 0       //    (delay till start)
        , 1     //    (execute every x seconds)
        , timerTime-1 );
	}

	public MenuBar(){		
		
		menuLabel = new Label("", new Skin(Gdx.files.internal("uiskin.json")));
		
		Image iconMoney = new Image(new Texture(Gdx.files.internal("icon/icon-coin.png")));
		Image iconEnergy = new Image(new Texture(Gdx.files.internal("icon/icon-energy.png")));
		Image iconOre = new Image(new Texture(Gdx.files.internal("icon/icon-ore.png")));
		
		add(iconMoney).left();
		add(iconEnergy).left();
		add(iconOre).left();
		add(menuLabel).expandX().center();
		
		
	}
	
}