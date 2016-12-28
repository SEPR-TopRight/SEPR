package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class BuyRoboticonsMarket extends PopUpWindow{
	private SpinBox transactionQuantitySpinBox;
	private Label transactionCostLabel;
        Player player;
        Market market;
	Label roboticonsInStockLabel;
	
	public BuyRoboticonsMarket(Player player, Market market){	
		super("Market: buy roboticons");
                this.market = market;
                this.player = player;
		
		transactionQuantitySpinBox = new SpinBox("Buy ",0,0, market.mark_inventory.getRoboticonQuantity());
		transactionQuantitySpinBox.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
	        {
	        	updateTransactionCostLabel();
	        }
		});
		
		roboticonsInStockLabel = new Label("Roboticons in stock: "+Integer.toString(market.mark_inventory.getRoboticonQuantity()), new Skin(Gdx.files.internal("uiskin.json")));
		Label costPerRoboticonLabel = new Label("Price per roboticon: "+Integer.toString(market.getCostRoboticons(1)), new Skin(Gdx.files.internal("uiskin.json")));
		transactionCostLabel = new Label("Total cost: 0",new Skin(Gdx.files.internal("uiskin.json")));
		
		TextButton completeTransactionButton = new TextButton("Purchase",new Skin(Gdx.files.internal("uiskin.json")));
		completeTransactionButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
	        {
				attemptTransaction();
                         
	        }
		});

		TextButton produceRoboticonButton = new TextButton("Produce roboticon", new Skin(Gdx.files.internal("uiskin.json")));
		produceRoboticonButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
	        {
	        	attemptToProduceRoboticon();
	        }
		});
		
		add(roboticonsInStockLabel).expand().fill().left();
		row();
		add(costPerRoboticonLabel).expand().fill().right();
		row();
		add(transactionQuantitySpinBox).expand().fill().left().padTop(20);
		row();
		add(transactionCostLabel).expand().fill().left();
		row();
		add(completeTransactionButton).expand().fill().left().padTop(20);
		row();
		add(produceRoboticonButton);

		setSize(getPrefWidth(),getPrefHeight());
		moveToMiddleOfScreen();
	}

	private void updateTransactionCostLabel(){
		Integer transactionCost = market.getCostRoboticons(transactionQuantitySpinBox.getValue()); 
		transactionCostLabel.setText("Total cost: "+transactionCost.toString());
	}
	
	private void attemptToProduceRoboticon(){
		if(market.attemptToProduceRoboticon())
			updateRoboticonsInStockLabel();
		else
			getParent().addActor(new MessagePopUp("Not enough ore","The market does not have enough ore to producec a roboticon!"));
	}

	private void attemptTransaction(){
		if(transactionQuantitySpinBox.getValue()>0){
			if(player.attemptToBuyRoboticons(market,transactionQuantitySpinBox.getValue())){
				MessageManager.getInstance().dispatchMessage(GameEvents.PLAYERPURCHASE.ordinal());
				updateRoboticonsInStockLabel();
				updateTransactionCostLabel();			
			}
			else{ // As the player cannot attempt to purchase more Roboticons than the market has in stock
                              // (Due to the upper bound imposed by the spinbox)
                              // Then if the purchase fails it must be because they don't have enough money!
				getParent().addActor(new MessagePopUp("Not enough money","You don't have enough money!"));
			}
		}
	}
	
	private void updateRoboticonsInStockLabel(){
		int roboticonsInStock = market.mark_inventory.getRoboticonQuantity();
		roboticonsInStockLabel.setText("Roboticons in stock: "+Integer.toString(roboticonsInStock));
		transactionQuantitySpinBox.setMaxValue(roboticonsInStock);
	}
}
