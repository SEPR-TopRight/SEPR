package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CustomiseRoboticonsMarket extends PopUpWindow {
	public CustomiseRoboticonsMarket(){
		super("Market: Customise roboticons");
		
		Label energyCustomisationCostLabel = new Label("Energy customisation cost: 12",new Skin(Gdx.files.internal("uiskin.json")));
		Label oreCustomisationCostLaebl = new Label("Ore customisation cost: 13",new Skin(Gdx.files.internal("uiskin.json")));
		
		TextButton
		
		
		add(energyCustomisationCostLabel).left();
	}
}
