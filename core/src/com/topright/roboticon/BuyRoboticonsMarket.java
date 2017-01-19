package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * The window that allows players to buy roboticons from the market
 * @author josh
 *
 */
public class BuyRoboticonsMarket extends PopUpWindow{
	private SpinBox transactionQuantitySpinBox;  // Used to state how many roboticons that player wants to buy
	private Label transactionCostLabel; // Displays the total cost of the current transaction (number of roboticons * price per roboticon)
    private Player currentPlayer; // The current player who is using the market
	private Label roboticonsInStockLabel;
	private TextButton produceRoboticonButton;
	private TextButton completeTransactionButton;
	
	
	/**
	 * Constructor
	 * @param player The current player who is using the market
	 */
	public BuyRoboticonsMarket(Player player){	
		super("Market: buy roboticons"); // Argument is the window title
        this.currentPlayer = player;
		
        // Creates a new spinbox, the player can choose to purchase any number of roboticons between 0 and the number currently in stock
		transactionQuantitySpinBox = new SpinBox("Buy ",0,0, Market.getInstance().getRoboticonQuantity());
		transactionQuantitySpinBox.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
	        {
	        	updateTransactionCostLabel(); // To be called whenever the value of the spinbox is altered
	        }
		});
		
		createButtons();
		
		roboticonsInStockLabel = new Label("Roboticons in stock: "+Integer.toString(Market.getInstance().getRoboticonQuantity()), new Skin(Gdx.files.internal("uiskin.json")));
		Label costPerRoboticonLabel = new Label("Price per roboticon: "+Integer.toString(Market.getInstance().getCostRoboticons(1)), new Skin(Gdx.files.internal("uiskin.json")));
		transactionCostLabel = new Label("Total cost: 0",new Skin(Gdx.files.internal("uiskin.json")));
		
		
		
		Label roboticonOreConversionRateLabel = new Label("It costs the market " +Integer.toString(Market.getInstance().getRoboticonOreConversionRate()) 
				                                          +" ore to produce 1 roboticon", new Skin(Gdx.files.internal("uiskin.json")));
		
		// Place all items on the screen in the desired places 
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
		add(roboticonOreConversionRateLabel).padTop(20);
		row();
		add(produceRoboticonButton);

		setSize(getPrefWidth(),getPrefHeight());
		moveToMiddleOfScreen();
	}

	/**
	 * Updates the transaction cost label so that is displays the cost of the current transaction (number of roboticons * cost per roboticon)
	 */
	private void updateTransactionCostLabel(){
		Integer transactionCost = Market.getInstance().getCostRoboticons(transactionQuantitySpinBox.getValue()); 
		transactionCostLabel.setText("Total cost: "+transactionCost.toString());
	}
	
	/**
	 * Creates the complete transaction button and produce roboticon button and sets them to carry out the correct operations when clicked
	 */
	private void createButtons(){
		// To be clicked when the player wishes to purchase the specified number of roboticons
		completeTransactionButton = new TextButton("Purchase",new Skin(Gdx.files.internal("uiskin.json")));
		completeTransactionButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				attemptTransaction(); // Called whenever the button is clicked
		                         
			    }
			});

		// A button that can be clicked to try and make the market produce another roboticon
		produceRoboticonButton = new TextButton("Make the market produce another roboticon", new Skin(Gdx.files.internal("uiskin.json")));
		produceRoboticonButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
			       attemptToProduceRoboticon();
			}
		});
	}
	
	/**
	 * Tries to get the market to produce another roboticon. If it cannot then the player is notified via a popup message
	 */
	private void attemptToProduceRoboticon(){
		if(Market.getInstance().attemptToProduceRoboticon()){
			updateRoboticonsInStockLabel();
		}
		else{
			// Add the pop up to the parent widget of this window (otherwise it will be contained within the window itself)
			getParent().addActor(new MessagePopUp("Not enough ore","The market does not have enough ore to produce a roboticon!"));
		}
	}

	/**
	 * Attempt to carry out the roboticon purchase. The transaction will fail if the player does not have enough money.
	 * <p>
	 * No transaction will be carried out if the player attempts to purchase 0 roboticons
	 * </p>
	 */
	private void attemptTransaction(){
		// If the player is not trying to buy 0 roboticons
		if(transactionQuantitySpinBox.getValue()>0){ 
			if(currentPlayer.attemptToBuyRoboticons(transactionQuantitySpinBox.getValue())){
				// If the purchase was successful
				
				// Other parts of the game need to react (e.g. the players inventory on the menu bar must be updated)
				MessageManager.getInstance().dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal()); 
				
				updateRoboticonsInStockLabel();
				updateTransactionCostLabel();			
			}
			else{ // As the player cannot attempt to purchase more Roboticons than the market has in stock
                              // (Due to the upper bound imposed by the spinbox)
                              // Then if the purchase fails it must be because they don't have enough money!
				// Add the pop up to the parent widget of this window (otherwise it will be contained within the window itself)
				getParent().addActor(new MessagePopUp("Not enough money","You don't have enough money!"));
			}
		}
	}
	
	/**
	 * Update the robtoticons in stock label once a purchase has been made 
	 * (also updates the maximum number that can be selected using the transactonQuantitySpinbox so that the player cannot attempt to buy more roboticons that are available)
	 */
	private void updateRoboticonsInStockLabel(){
		int roboticonsInStock = Market.getInstance().getRoboticonQuantity();
		roboticonsInStockLabel.setText("Roboticons in stock: "+Integer.toString(roboticonsInStock));
		transactionQuantitySpinBox.setMaxValue(roboticonsInStock);
	}
}
