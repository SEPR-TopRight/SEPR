package com.topright.roboticon;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Contains all 
 * @author andrew
 *
 */
public class MenuBar {
	
	private Images iconMoney;
	private Images iconEnergy;
	private Images iconOre;
	private Images buttonIconMarket;
	private Images robEnergy;
	private Images robOre;
	private Button buttonStage;
	private Button buttonNextTurn;

	public void create(){
		Images iconMoney = new Images();
		Images iconEnergy = new Images();
		Images iconOre = new Images();
		Images buttonIconMarket = new Images();
		Images robEnergy = new Images();
		Images robOre = new Images();
		Button buttonStage = new Button();
		Button buttonNextTurn = new Button();
		
		iconMoney.create("icon/icon-coin.png", 10, 1000, 40, 40);
		iconEnergy.create("icon/icon-energy.png", 110, 1000, 40, 40);
		iconOre.create("icon/icon-ore.png", 210, 1000, 40, 40);
		
		//Button testButton = new Button();
		//testButton.create("asdasdasdasd", 900, 800);
		
		buttonNextTurn.create("", 1500, 1000, 40, 40, "buttons/arrow.pack", "arrow", "arrow", new ClickListener() {              
		    @Override
		    public void clicked(InputEvent event, float x, float y) {
		        System.out.println("hello");
		    }
		});
		
	}
	
}
