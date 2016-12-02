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
	
	public BuyRoboticonsMarket(){	
		super("Market: buy roboticons");
		
		transactionQuantitySpinBox = new SpinBox("Buy ",0,0, 100);// update later
		
		Label roboticonsInStockLabel = new Label("Roboticons in stock: 6", new Skin(Gdx.files.internal("uiskin.json")));
		Label costPerRoboticonLabel = new Label("Price per roboticon: 12", new Skin(Gdx.files.internal("uiskin.json")));
		transactionCostLabel = new Label("Total cost: 0",new Skin(Gdx.files.internal("uiskin.json")));
		
		TextButton completeTransactionButton = new TextButton("Purchase",new Skin(Gdx.files.internal("uiskin.json")));
		completeTransactionButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
	        {
				attemptTransaction();
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
		
		transactionQuantitySpinBox.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
	        {
	        	updateTransactionCostLabel();
	        }
		});

		setSize(getPrefWidth(),getPrefHeight());
		moveToMiddleOfScreen();
	}

	private void updateTransactionCostLabel(){
		Integer transactionCost = transactionQuantitySpinBox.getValue() * 12; // update with cost per roboticon later
		transactionCostLabel.setText("Total cost: "+transactionCost.toString());
	}

	private void attemptTransaction(){
		if(transactionQuantitySpinBox.getValue()>0){
			// do later
		}
	}
}
