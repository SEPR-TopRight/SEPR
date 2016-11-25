package com.topright.roboticon;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Contains all 
 * @author jcn509
 *
 */
public class PopUpWindow {
	
	private Images background;
	private Button buttonClose;
	private String titleText;

	public void create(String titleText){
		Images background = new Images();
		this.titleText = titleText;
		background.create("buttons/ButtonOn.9.png", 100, 100, 1480, 890);
		
		buttonClose = new Button();
		buttonClose.create("", 1535, 945, 40, 40, "buttons/close_button.pack", "close_button", "close_button_flipped", new ClickListener() {              
		    @Override
		    public void clicked(InputEvent event, float x, float y) { 
		        
		    }
		});
		//destroy();
	}
	
	public void destroy(){
		background.destroy();
		buttonClose.destroy();
	}
	
}
