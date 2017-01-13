package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * A window that players can use to buy and sell resources to and from the market
 * @author jcn509
 */
public class ResourceMarket extends PopUpWindow {

	private Player player;
	private MarketInventoryTable marketInventoryTable; // Need to be able to update the inventory quantity values
	private SaleTable saleTable; // The purchase table requires access to the sale table
	private PurchaseTable purchaseTable; // The sale table requires access to the purchase table

	/**
	 * Constructor
	 * @param player The player who is using the market
	 */
	public ResourceMarket(Player player){
		super("Market: Buy and sell resources");
		
		this.player = player;

		marketInventoryTable = new MarketInventoryTable();
		
		purchaseTable = new PurchaseTable();
		saleTable = new SaleTable();
		
		add(purchaseTable).left().padLeft(10);
		add(marketInventoryTable).padLeft(45).padRight(45).top();
		add(saleTable).left().padRight(10);

		setSize(getPrefWidth(),getPrefHeight());
		moveToMiddleOfScreen();
	}
	
	
	/**
	 * Displays the quantities of the resources in the markets inventory
	 * @author jcn509
	 */
	private class MarketInventoryTable extends Table{
		private Label oreQuantityLabel;
		private Label energyQuantityLabel;
		
		/**
		 * Constructor
		 */
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
			add(new Label(Integer.toString(Market.getInstance().getCostOre(1)), new Skin(Gdx.files.internal("uiskin.json")))).left();
			row();
			add(new Label("energy", new Skin(Gdx.files.internal("uiskin.json")))).left();
			add(energyQuantityLabel).left();
			add(new Label(Integer.toString(Market.getInstance().getCostEnergy(1)), new Skin(Gdx.files.internal("uiskin.json")))).left();
			setSize(getPrefWidth(),getPrefHeight());
			
			update(); // Initialise the quantities of resources available
		}
		
		/**
		 * Called whenever the inventory tables data needs to be updated (i.e. when a purchase or sale has been completed)
		 */
		public void update(){
			oreQuantityLabel.setText(Integer.toString(Market.getInstance().getOreQuantity()));
			energyQuantityLabel.setText(Integer.toString(Market.getInstance().getEnergyQuantity()));
		}
	}
	
	/**
	 * Base class for the sale and purchase tables
	 * <p>
	 * Contains all the widgets needed to make a transaction or purchase
	 * </p>
	 * @author jcn509
	 *
	 */
	private class TransactionTable extends Table{
		protected SpinBox oreSpinBox;
		protected SpinBox energySpinBox;
		protected TextButton energyButton;
		protected TextButton oreButton;
		private Label oreCostLabel;
		private Label energyCostLabel;
		
		/**
		 * Constructor
		 * @param tableTitle The text that should be displayed at the top of the table
		 */
		public TransactionTable(String tableTitle){
			createWidgets();
			setSpinBoxClickBehaviour();

			addWidgetsToTable(tableTitle);
			
			// Give the cost labels their initial value
			updateOreCostLabel();
			updateEnergyCostLabel();
		}
		
		/**
		 * Creates all of the widgets (to which a reference needs to be kept) that allow the user to make transactions
		 */
		private void createWidgets(){
			oreSpinBox = new SpinBox("Ore ",0, 0, 0);
			energySpinBox = new SpinBox("Energy ",0, 0, 0);
			energyButton = new TextButton("Complete transaction", new Skin(Gdx.files.internal("uiskin.json")));
			oreButton = new TextButton("Complete transaction", new Skin(Gdx.files.internal("uiskin.json")));
			
			// Initially "uninitialised" but this value will be replaced
			oreCostLabel = new Label("uninitialised", new Skin(Gdx.files.internal("uiskin.json")));
			energyCostLabel = new Label("uninitialised", new Skin(Gdx.files.internal("uiskin.json")));
		}
		
		/**
		 * Sets up the SpinBoxs to update ore or energy cost labels when their used
		 */
		private void setSpinBoxClickBehaviour(){
			oreSpinBox.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y)
	        		{
	        			updateOreCostLabel(); // Called when the oreSpinBox is clicked
	       			}
			});
			
			energySpinBox.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y)
	        		{
	        			updateEnergyCostLabel(); // Called when the energySpinBox is clicked
	       			}
			});
		}
		
		/**
		 * Adds the widgets created by {@link #createWidgets()} to the table
		 * @param tableTitle
		 */
		private void addWidgetsToTable(String tableTitle){
			add(new Label(tableTitle, new Skin(Gdx.files.internal("uiskin.json")))).colspan(2).padBottom(5).left(); 
			row();
			add(oreSpinBox).colspan(2).expand().fill();
			row();
			add(new Label("Total: ", new Skin(Gdx.files.internal("uiskin.json")))).left();
			add(oreCostLabel).padRight(5).left();
			row();
			add(oreButton).left().padBottom(20);
			
			row();
			add(energySpinBox).colspan(2).expand().fill();
			row();
			add(new Label("Total: ", new Skin(Gdx.files.internal("uiskin.json")))).left();
			add(energyCostLabel).padRight(5).left();
			row();
			add(energyButton).left();
		}

		/**
		 * Called whenever the value of the ore spin box is changed to update the total cost/value of the ore that is displayed
		 */
		protected void updateOreCostLabel(){
			Integer oreCost = Market.getInstance().getCostOre(oreSpinBox.getValue()); 
			oreCostLabel.setText(oreCost.toString());
		}

		/**
		 * Called whenever the value of the energy spin box is changed to update the total cost/value of the energy that is displayed
		 */
		protected void updateEnergyCostLabel(){
			Integer energyCost = Market.getInstance().getCostEnergy(energySpinBox.getValue()); 
			energyCostLabel.setText(energyCost.toString());
		}
	}
	
	/**
	 * Used by the user to buy resources
	 * @author jcn509
	 */
	private class PurchaseTable extends TransactionTable{
		
		/**
		 * Constructor
		 */
		public PurchaseTable(){
			super("Purchase from the market");
			setButtonClickBehaviour();
			
			// The user cannot attempt to buy more of a given resource than the market has in stock
			oreSpinBox.setMaxValue(Market.getInstance().getOreQuantity());
			energySpinBox.setMaxValue(Market.getInstance().getEnergyQuantity());
		}
		
		/**
		 * Sets up the energy and ore purchase buttons to call the attemptEnergyPurchase and attemptOrePurchase respectively when clicked
		 */
		private void setButtonClickBehaviour(){
			oreButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y)
	        		{
	        			attemptOrePurchase(); // Called whenever oreButton is clicked
	       			}
			});
			
			energyButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y)
	        		{
	        			attemptEnergyPurchase(); // Called whenever energyButton is clicked
	       			}
			});
		}
		
		/**
		 * Called whenever the ore purchase button is clicked.
		 * <p>
		 * The purchase is attempted.
		 * If it is successful then the inventory table and max value of the ore SpinBox are updated and a message is dispatched to inform the main class of the purhcase.
		 * If it is not successful (because the player does not have enough money) then a pop up message is deployed to inform the player of this
		 * <p>
		 */
		private void attemptOrePurchase(){
			if(oreSpinBox.getValue()>0){ // Nothing happens if the player tries to purchase 0 ore
				
				// Get the player to attempt to buy the ore
				if(player.attemptToBuyOre(oreSpinBox.getValue())){ // If the purchase is successful
					MessageManager.getInstance().dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());
					marketInventoryTable.update();
					updateMaxOreValue();
					updateOreCostLabel();
					saleTable.updateMaxOreValue();
				}
				else{ // As the player cannot attempt to purchase more ore than the market has in stock
					// (Due to the upper bound imposed by the spinbox)
                    // If the purchase fails it must be because they don't have enough money!
					getParent().getParent().addActor(new MessagePopUp("Not enough money","You don't have enough money!"));
				}
				
			}

		}
		
		/**
		 * Called whenever the energy purchase button is clicked.
		 * <p>
		 * The purchase is attempted.
		 * If it is successful then the inventory table and max value of the energy SpinBox are updated and a message is dispatched to inform the main class of the purchase.
		 * If it is not successful (because the player does not have enough money) then a pop up message is deployed to inform the player of this
		 * <p>
		 */
		private void attemptEnergyPurchase(){
			if(energySpinBox.getValue()>0){ // Nothing happens if the player tries to purchase 0 energy
				
				// Get the player to attempt to buy the ore
				if(player.attemptToBuyEnergy(energySpinBox.getValue())){ // If the purchase is successful
					MessageManager.getInstance().dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());
					marketInventoryTable.update();
					updateMaxEnergyValue();
					updateEnergyCostLabel();
					saleTable.updateMaxEnergyValue();
				}
				else{ // As the player cannot attempt to purchase more energy than the market has in stock
					// (Due to the upper bound imposed by the spinbox)
                      // If the purchase fails it must be because they don't have enough money!
					getParent().getParent().addActor(new MessagePopUp("Not enough money","You don't have enough money!"));
				}
				
			}

		}

		/**
		 * Called whenever the player purchases ore
		 * <p>
		 * Now that the player has purchased ore there is less available 
		 * and the maximum amount of ore that the player can attempt to buy
		 * and the amount displayed in the market inventory table must be updated
		 * </p>
		 */
		public void updateMaxOreValue(){
			int oreInStock = Market.getInstance().getOreQuantity();
			oreSpinBox.setMaxValue(oreInStock);
		}
		
		/**
		 * Called whenever the player purchases energy
		 * <p>
		 * Now that the player has purchased energy there is less available 
		 * and the maximum amount of energy that the player can attempt to buy
		 * and the amount displayed in the market inventory table must be updated
		 * </p>
		 */
		public void updateMaxEnergyValue(){
			int energyInStock = Market.getInstance().getEnergyQuantity();
			energySpinBox.setMaxValue(energyInStock);
		}
	}
	
	/**
	 * Used by the user to sell resources
	 * @author jcn509
	 */
	private class SaleTable extends TransactionTable{
		
		/**
		 * Constructor
		 */
		public SaleTable(){
			super("Sell to the market");
			setButtonClickBehaviour();
			
			// The user cannot sell more of any resource than they have in their inventory
			oreSpinBox.setMaxValue(player.inventory.getOreQuantity());
			energySpinBox.setMaxValue(player.inventory.getEnergyQuantity());
		}
		
		/**
		 * Sets up the energy and ore purchase buttons to call the attemptEnergySale and attemptOreSale respectively when clicked
		 */
		private void setButtonClickBehaviour(){
			oreButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y)
	        		{
	        			attemptOreSale(); // Called whenever oreButton is clicked
	       			}
			});
			
			energyButton.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y)
	        		{
	        			attemptEnergySale(); // Called whenever energyButton is clicked
	       			}
			});
		}
		
		/**
		 * Called whenever the ore sale button is clicked.
		 * <p>
		 * The sale is attempted.
		 * If it is successful then the inventory table and max value of the ore SpinBox are updated and a message is dispatched to inform the main class of the sale.
		 * <p>
		 */
		private void attemptOreSale(){
			if(oreSpinBox.getValue()>0){ // Nothing happens if they player attempts to sell 0 ore
				if(player.attemptToSellOre(oreSpinBox.getValue())){
					MessageManager.getInstance().dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());
					marketInventoryTable.update();
					updateMaxOreValue();
					updateOreCostLabel(); // After max value is set, may need to be updated
					purchaseTable.updateMaxOreValue();
				}
			}

		}
	
		/**
		 * Called whenever the energy sale button is clicked.
		 * <p>
		 * The sale is attempted.
		 * If it is successful then the inventory table and max value of the energy SpinBox are updated and a message is dispatched to inform the main class of the sale.
		 * <p>
		 */
		private void attemptEnergySale(){
			if(energySpinBox.getValue()>0){ // Nothing happens if they player attempts to sell 0 energy
				if(player.attemptToSellEnergy(energySpinBox.getValue())){
					MessageManager.getInstance().dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());
					marketInventoryTable.update();
					updateMaxEnergyValue();
					updateEnergyCostLabel(); // After max value is set, may need to be updated
					purchaseTable.updateMaxEnergyValue();
					purchaseTable.updateEnergyCostLabel();
				}
			}

		}
	

		/**
		 * Called whenever the player sells ore
		 * <p>
		 * Now that the player has sold ore there is more available to buy
		 * so the amount displayed in the market inventory table must be updated
		 * </p>
		 */
		public void updateMaxOreValue(){
			int oreInStock = player.inventory.getOreQuantity();
			oreSpinBox.setMaxValue(oreInStock);
		}
		
		/**
		 * Called whenever the player sells energy
		 * <p>
		 * Now that the player has sold energy there is more available to buy
		 * so the amount displayed in the market inventory table must be updated
		 * </p>
		 */
		public void updateMaxEnergyValue(){
			int energyInStock = player.inventory.getEnergyQuantity();
			energySpinBox.setMaxValue(energyInStock);
		}
		
	}
	
	
}
