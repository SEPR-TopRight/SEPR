package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ResourceMarket extends PopUpWindow {

	Player player;
	Market market;
	TextButton energyCustomisationButton;
	MarketInventoryTable marketInventoryTable;
	SaleTable saleTable; // The purchase table requires access to the sale table
	PurchaseTable purchaseTable; // The sale table requires access to the purchase table

	public ResourceMarket(Player player, Market market){
		super("Market: Buy and sell resources");

		this.player = player;
		this.market = market;

		marketInventoryTable = new MarketInventoryTable();
		marketInventoryTable.update();
		
		purchaseTable = new PurchaseTable();
		saleTable = new SaleTable();
		
		add(purchaseTable).left().padLeft(10);
		add(marketInventoryTable).padLeft(45).padRight(45).top();
		add(saleTable).left().padRight(10);

		setSize(getPrefWidth(),getPrefHeight());
		moveToMiddleOfScreen();
	}
	
	

	private class MarketInventoryTable extends Table{
		private Label oreQuantityLabel;
		private Label energyQuantityLabel;
		
		public MarketInventoryTable(){
			oreQuantityLabel = new Label("uninitialised", new Skin(Gdx.files.internal("uiskin.json")));
			energyQuantityLabel = new Label("uninitialised", new Skin(Gdx.files.internal("uiskin.json")));

			add(new Label("Market stock", new Skin(Gdx.files.internal("uiskin.json")))).left().colspan(3);
			row();
			add(new Label("Resource  ", new Skin(Gdx.files.internal("uiskin.json")))).left().padRight(5);
			add(new Label("Quantity  ", new Skin(Gdx.files.internal("uiskin.json")))).left().padRight(5);
			add(new Label("Price  ", new Skin(Gdx.files.internal("uiskin.json")))).left();
			row();
			add(new Label("ore", new Skin(Gdx.files.internal("uiskin.json")))).left();
			add(oreQuantityLabel).left();
			add(new Label(Integer.toString(market.getCostOre(1)), new Skin(Gdx.files.internal("uiskin.json")))).left();
			row();
			add(new Label("energy", new Skin(Gdx.files.internal("uiskin.json")))).left();
			add(energyQuantityLabel).left();
			add(new Label(Integer.toString(market.getCostEnergy(1)), new Skin(Gdx.files.internal("uiskin.json")))).left();
			setSize(getPrefWidth(),getPrefHeight());
		}

		public void update(){
			oreQuantityLabel.setText(Integer.toString(market.mark_inventory.getOreQuantity()));
			energyQuantityLabel.setText(Integer.toString(market.mark_inventory.getEnergyQuantity()));
			
		}
	}
	
	private class TransactionTable extends Table{
		protected SpinBox oreSpinBox;
		protected SpinBox energySpinBox;
		protected TextButton energyButton;
		protected TextButton oreButton;
		private Label oreCostLabel;
		private Label energyCostLabel;
		
		public TransactionTable(String tableTitle){
			oreSpinBox = new SpinBox("Ore ",0, 0, 0);
			energySpinBox = new SpinBox("Energy ",0, 0, 0);
			energyButton = new TextButton("Complete transaction", new Skin(Gdx.files.internal("uiskin.json")));
			oreButton = new TextButton("Complete transaction", new Skin(Gdx.files.internal("uiskin.json")));
			oreCostLabel = new Label("uninitialised", new Skin(Gdx.files.internal("uiskin.json")));
			energyCostLabel = new Label("uninitialised", new Skin(Gdx.files.internal("uiskin.json")));

			oreSpinBox.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y)
	        		{
	        			updateOreCostLabel();
	       			}
			});
			
			energySpinBox.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y)
	        		{
	        			updateEnergyCostLabel();
	       			}
			});

			add(new Label(tableTitle, new Skin(Gdx.files.internal("uiskin.json")))).colspan(2).padBottom(5).left(); 
			row();
			add(oreSpinBox).colspan(2).expand().fill();
			row();
			add(new Label("Cost: ", new Skin(Gdx.files.internal("uiskin.json")))).left();
			add(oreCostLabel).padRight(5);
			row();
			add(oreButton).left().padBottom(20);
			
			row();
			add(energySpinBox).colspan(2).expand().fill();
			row();
			add(new Label("Cost: ", new Skin(Gdx.files.internal("uiskin.json")))).left();
			add(energyCostLabel).padRight(5);
			row();
			add(energyButton).left();
			
			// Give the cost labels their initial value
			updateOreCostLabel();
			updateEnergyCostLabel();
		}
				

		protected void updateOreCostLabel(){
			Integer oreCost = market.getCostOre(oreSpinBox.getValue()); 
			oreCostLabel.setText("Cost: "+oreCost.toString());
		}

		protected void updateEnergyCostLabel(){
			Integer energyCost = market.getCostEnergy(energySpinBox.getValue()); 
			energyCostLabel.setText("Cost: "+energyCost.toString());
		}
	}
	
	private class PurchaseTable extends TransactionTable{
		public PurchaseTable(){
			super("Purchase from the market");
			oreButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y)
	        		{
	        			attemptOrePurchase();
	       			}
			});
			
			energyButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y)
	        		{
	        			attemptEnergyPurchase();
	       			}
			});
			oreSpinBox.setMaxValue(market.mark_inventory.getOreQuantity());
			energySpinBox.setMaxValue(market.mark_inventory.getEnergyQuantity());
		}
		
		private void attemptOrePurchase(){
			if(oreSpinBox.getValue()>0){
				if(player.attemptToBuyOre(market,oreSpinBox.getValue())){
					MessageManager.getInstance().dispatchMessage(GameEvents.PLAYERPURCHASE.ordinal());
					marketInventoryTable.update();
					updateMaxOreValue();
					updateOreCostLabel();
					saleTable.updateMaxOreValue();
				}
				else{ // As the player cannot attempt to purchase more Roboticons than the market has in stock
					// (Due to the upper bound imposed by the spinbox)
                              		// Then if the purchase fails it must be because they don't have enough money!
					getParent().getParent().addActor(new MessagePopUp("Not enough money","You don't have enough money!"));
				}
			}

		}
		
		private void attemptEnergyPurchase(){
			if(energySpinBox.getValue()>0){
				if(player.attemptToBuyEnergy(market,energySpinBox.getValue())){
					MessageManager.getInstance().dispatchMessage(GameEvents.PLAYERPURCHASE.ordinal());
					marketInventoryTable.update();
					updateMaxEnergyValue();
					updateEnergyCostLabel();
					saleTable.updateMaxEnergyValue();
				}
				else{ // As the player cannot attempt to purchase more Roboticons than the market has in stock
					// (Due to the upper bound imposed by the spinbox)
                              		// Then if the purchase fails it must be because they don't have enough money!
					getParent().getParent().addActor(new MessagePopUp("Not enough money","You don't have enough money!"));
				}
			}

		}

		// When the player makes a sale, then there is more to buy and these values must be updated
		public void updateMaxOreValue(){
			int oreInStock = market.mark_inventory.getOreQuantity();
			oreSpinBox.setMaxValue(oreInStock);
		}
		
		public void updateMaxEnergyValue(){
			int energyInStock = market.mark_inventory.getEnergyQuantity();
			energySpinBox.setMaxValue(energyInStock);
		}
	}
	
	private class SaleTable extends TransactionTable{
		public SaleTable(){
			super("Sell to the market");
			oreButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y)
	        		{
	        			attemptOreSale();
	       			}
			});
			
			energyButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y)
	        		{
	        			attemptEnergySale();
	       			}
			});
			oreSpinBox.setMaxValue(player.inventory.getOreQuantity());
			energySpinBox.setMaxValue(player.inventory.getEnergyQuantity());
		}
		private void attemptOreSale(){
			if(oreSpinBox.getValue()>0){
				if(player.attemptToSellOre(market,oreSpinBox.getValue())){
					MessageManager.getInstance().dispatchMessage(GameEvents.PLAYERPURCHASE.ordinal());
					marketInventoryTable.update();
					updateMaxOreValue();
					updateOreCostLabel(); // After max value is set, may need to be updated
					purchaseTable.updateMaxOreValue();
				}
			}

		}
	
		private void attemptEnergySale(){
			if(energySpinBox.getValue()>0){
				if(player.attemptToSellEnergy(market,energySpinBox.getValue())){
					MessageManager.getInstance().dispatchMessage(GameEvents.PLAYERPURCHASE.ordinal());
					marketInventoryTable.update();
					updateMaxEnergyValue();
					updateEnergyCostLabel(); // After max value is set, may need to be updated
					purchaseTable.updateMaxEnergyValue();
					purchaseTable.updateEnergyCostLabel();
				}
			}

		}
	

		// When the player makes a purchase, they then have more to sell so these values must be updated
		public void updateMaxOreValue(){
			int oreInStock = player.inventory.getOreQuantity();
			oreSpinBox.setMaxValue(oreInStock);
		}
		
		public void updateMaxEnergyValue(){
			int energyInStock = player.inventory.getEnergyQuantity();
			energySpinBox.setMaxValue(energyInStock);
		}
		
	}
	
	
}
