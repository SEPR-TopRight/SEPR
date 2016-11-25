package com.topright.roboticon;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Contains all 
 * @author andrew
 *
 */
public class MenuBar extends WidgetGroup{

	public MenuBar(){
		
		/*Images buttonIconMarket = new Images();
		Images robEnergy = new Images();
		Images robOre = new Images();
		Button buttonStage = new Button();
		Button buttonNextTurn = new Button();*/
		
		Images iconMoney = new Images("icon/icon-coin.png", 10, 1000, 40, 40);
		Images iconEnergy = new Images("icon/icon-energy.png", 110, 1000, 40, 40);
		Images iconOre = new Images("icon/icon-ore.png", 210, 1000, 40, 40);
		
		addActor(iconMoney);
		addActor(iconEnergy);
		addActor(iconOre);
		
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
