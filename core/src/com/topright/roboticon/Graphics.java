package com.topright.roboticon;

/**
 * Contains all 
 * @author andrew
 *
 */
public class Graphics {
	
	Images iconMoney;
	Images iconEnergy;
	Images iconOre;
	Images buttonIconMarket;
	Images robEnergy;
	Images robOre;

	public void createMenuBar(){
		Images iconMoney = new Images();
		Images iconEnergy = new Images();
		Images iconOre = new Images();
		Images buttonIconMarket = new Images();
		Images robEnergy = new Images();
		Images robOre = new Images();
		
		iconMoney.create("icon/icon-coin.png", 10, 500, 40, 40);
		
	}
	
}
