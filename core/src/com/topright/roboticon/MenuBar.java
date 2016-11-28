package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Contains all 
 * @author andrew
 *
 */

public class MenuBar extends WidgetGroup{
	private int timerTime;
	private Label menuLabel;
	private String menuText;
	private Buttons nextStageButton;
	@FunctionalInterface
	interface Action {
	  void execute();
	}

	
	public void createAndSetNextStageButton(final Runnable methodToCallUponClick){
		nextStageButton = new Buttons("done with market",1630, 1000, 40, 40, "buttons/buttons.pack", "ButtonOn", "ButtonOn", new ClickListener() {
	        @Override
			public void clicked(InputEvent event, float x, float y)
	        {
	        	methodToCallUponClick.run();
	        }
	    } );
		addActor(nextStageButton);
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
		
		/*Images buttonIconMarket = new Images();
		Images robEnergy = new Images();
		Images robOre = new Images();
		Button buttonStage = new Button();
		Button buttonNextTurn = new Button();*/
		
		
		menuLabel = new Label("", new Skin(Gdx.files.internal("uiskin.json")));
		menuLabel.setX(1000);
		menuLabel.setY(1000);
		
		Images iconMoney = new Images("icon/icon-coin.png", 10, 1000, 40, 40);
		Images iconEnergy = new Images("icon/icon-energy.png", 110, 1000, 40, 40);
		Images iconOre = new Images("icon/icon-ore.png", 210, 1000, 40, 40);
		
		addActor(iconMoney);
		addActor(iconEnergy);
		addActor(iconOre);
		addActor(menuLabel);
		
		//Button testButton = new Button();
		//testButton.create("asdasdasdasd", 900, 800);
		
		/*buttonNextTurn.create("", 1500, 1000, 40, 40, "buttons/arrow.pack", "arrow", "arrow", new ClickListener() {              
		    @Override
		    public void clicked(InputEvent event, float x, float y) {
		        System.out.println("hello");
		    }
		});*/
		
	}
	
}
