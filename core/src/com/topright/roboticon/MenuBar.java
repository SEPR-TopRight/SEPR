package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
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
 * A menu bar widget that is placed at the top of the screen
 * <p>
 * Contains a visual representation of the players inventory and (if applicable) the next button.
 * </p>
 * @author jcn509
 *
 */

public class MenuBar extends Table{
	private int timerTime;
	private Label menuLabel;
	private String menuText;
	private ButtonWithIcon nextStageButton;
	private InventoryTable inventoryTable;
	
	/**
	 * Set the text that is to be displayed on the next button, the message that is to be dispatched (to be dealt with elsewhere) when the next button is clicked and show the next button on screen.
	 * @param text The text to be displayed under the next button.
	 * @param messageToPassWhenClicked The message that is to be dispatched when the next button is clicked.
	 */
	public void setAndShowNextStageButton(String text,int messageToPassWhenClicked){
		nextStageButton.clearListeners(); // Remove whatever click listener is currently set
		nextStageButton.setText(text);
		nextStageButton.addListener(new ClickListener() {
	        @Override
			public void clicked(InputEvent event, float x, float y)
	        {
	        	MessageManager.getInstance().dispatchMessage(messageToPassWhenClicked);
	        }
	    } );
		showNextStageButton();
	}
	
	/**
	 * Set the text that is displayed in the middle of the menu bar.
	 * @param menuText The text that is to be displayed.
	 */
	public void setMenuText(String menuText){
		this.menuText = menuText;
		if(timerTime == 0) // Don't want to display "Time left: " if the timer is not active
			menuLabel.setText(menuText);
		else // If the timer is active we want to display the time left right away (otherwise would have to wait until the next timer update before it is displayed)
			menuLabel.setText(menuText+" Time left: "+timerTime);	
	}
	
	/**
	 * Remove the next stage button from the screen (does not destroy it)
	 */
	public void hideNextStageButton(){
		nextStageButton.remove();
	}
	
	/**
	 * Show the next stage button (only call if it is hidden).
	 */
	public void showNextStageButton(){
		add(nextStageButton).right();
	}
	
	/**
	 * Reset the timer (does not cause the message was to be passed when the timer ran out to be passed)
	 * <p>
	 * Also removes the "Time left:" text from the menu bar.
	 * </p>
	 */
	public void clearTimer(){
		menuLabel.setText(menuText); // Remove the Time left: part
		timerTime=0;
	}
	
	/**
	 * Starts a timer that is displayed in the menu bar. When the time runs out a given message is dispatched (to be dealt with elsewhere).
	 * @param messageToPass The message that is to be passed.
	 * @param time The amount of time that the timer is to run for (in seconds).
	 */
	public void setTimer(int messageToPass, final int time){
		timerTime = time;
		Timer.schedule(new Task(){
            @Override
            public void run() {
            	if(timerTime == 0){ // Stops things from being broken if the timer cleared from elsewhere.
            		cancel();
            	}
            	else if(timerTime>1){
            		timerTime-=1;
            		menuLabel.setText(menuText+" Time left: "+timerTime);
            	}
            	else{
            		MessageManager.getInstance().dispatchMessage(messageToPass); // Time ran out - pass the message.
            	}
            }
        }
        , 0       //    (delay till start)
        , 1     //    (execute every 1 second)
        , timerTime-1 ); //   reduce the time displayed by 1 every time it is executed
	}
	
	/**
	 * Updates the inventory data is that is displayed in the menu bar.
	 * @param inventory The inventory that contains the data that is to be displayed.
	 */
	public void updatePlayerInventoryData(PlayerInventory inventory){
		inventoryTable.oreLabel.setText(Integer.toString(inventory.getOreQuantity()));
		inventoryTable.energyLabel.setText(Integer.toString(inventory.getEnergyQuantity()));
		inventoryTable.moneyLabel.setText(Integer.toString(inventory.getMoneyQuantity()));
	}
	
	/**
	 * Contains all of the widgets needed to display the players inventory contents in a table.
	 * @author jcn509
	 */
	private class InventoryTable extends Table{
		
		private Label moneyLabel;
		private Label energyLabel;
		private Label oreLabel;
		
		/**
		 * Constructor
		 */
		public InventoryTable(){
			Image iconMoney = new Image(new Texture(Gdx.files.internal("icon/icon-coin.png")));
			moneyLabel = new Label("uninitialised", new Skin(Gdx.files.internal("uiskin.json")));
		
			Image iconEnergy = new Image(new Texture(Gdx.files.internal("icon/icon-energy.png")));
			energyLabel = new Label("uninitialised", new Skin(Gdx.files.internal("uiskin.json")));
		
			Image iconOre = new Image(new Texture(Gdx.files.internal("icon/icon-ore.png")));
			oreLabel = new Label("uninitialised", new Skin(Gdx.files.internal("uiskin.json")));
			
			add(iconMoney).left();
			add(moneyLabel).left().padRight(40); // want separation between the numbers and the icons
		
			add(iconEnergy).left();
			add(energyLabel).left().padRight(40);
		
			add(iconOre).left();
			add(oreLabel).left().padRight(40);
			
			// Add icons and labels for uncustomised roboticons, ore roboticons and energy roboticons
		}
	}
	
	/**
	 * Constructor
	 */
	public MenuBar(){		
		
		inventoryTable = new InventoryTable();
		add(inventoryTable).left();
		
		menuLabel = new Label("", new Skin(Gdx.files.internal("uiskin.json")));
		add(menuLabel).expandX().center();
		
		nextStageButton = new ButtonWithIcon("", "buttons/buttons.pack", "ButtonOn", "ButtonOn"); // Dont't want to place it right away
		                                                                                          // May not be needed and we don't yet know what it should do
	}
	
}