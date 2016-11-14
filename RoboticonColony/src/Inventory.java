/**
 * Generic inventory class that can keep track of the quantity of ore and energy held by a player or market
 * @author jcn509
 * 
 */
public class Inventory {
	private int oreQuantity;
	private int energyQuantity;
	
	/**
	 * 
	 * @param oreQuantity the initial quantity of ore stored in the inventory
	 * @param energyQuantity the initial quantity of energy stored in the inventory
	 */
	public Inventory(int oreQuantity,int energyQuantity){
		initialiseOreQuantity(oreQuantity);
		initialiseEnergyQuantity(energyQuantity);
	}
	/**
	 * Sets the initial quantity of ore stored in the inventory
	 * @param ore Quantity the initial quantity of ore to be stored in the inventory
	 */
	private void initialiseOreQuantity(int oreQuantity){
		if (oreQuantity < 0)
			throw new IllegalArgumentException("oreQuantity must be >=0");
		this.oreQuantity = oreQuantity;
	}
	/**
	 * Sets the initial quantity of energy stored in the inventory
	 * @param energyQuantity the initial quantity of energy to be stored in the inventory
	 */
	private void initialiseEnergyQuantity(int energyQuantity){
		if (energyQuantity < 0)
			throw new IllegalArgumentException("energyQuantity must be >=0");
		this.energyQuantity = energyQuantity;
	}
	public Inventory(){
		initialiseOreQuantity(0);
		initialiseEnergyQuantity(0);
	}
	
	/**
	 * Returns the quantity of ore stored in the inventory
	 * @return the quantity of ore stored in the inventory
	 */
	public int getOreQuantity(){
		return oreQuantity;
	}
	/**
	 * Returns the quantity of energy stored in the inventory
	 * @return the quantity of energy stored in the inventory
	 */
	public int getEnergyQuantity(){
		return energyQuantity;
	}
	/**
	 * Increases the quantity of energy stored in the inventory by the specified amount
	 * @param energyQuantityIncrease the number of units of energy to be added to the inventory
	 */
	public void increaseEnergyQuantity(int energyQuantityIncrease){
		if(energyQuantityIncrease < 0){
			throw new IllegalArgumentException("energyQuantityIncrease must be positive");
		}
		energyQuantity += energyQuantityIncrease;
	}
	/**
	 * Decreases the quantity of energy stored in the inventory by the specified amount
	 * @param energyQuantityDecrease the number of units of energy to be remove from the inventory
	 */
	public void decreaseEnergyQuantity(int energyQuantityDecrease){
		if(energyQuantityDecrease < 0){ // it should not be possible to have less than 0 units of energy in the inventory
			throw new IllegalArgumentException("energyQuantityDecrease must be positive");
		}
		energyQuantity -= energyQuantityDecrease;
	}
	/**
	 * Increases the quantity of ore stored in the inventory by the specified amount
	 * @param oreQuantityIncrease the number of units of ore to be added to the inventory
	 */
	public void increaseOreQuantity(int oreQuantityIncrease){
		if(oreQuantityIncrease < 0){
			throw new IllegalArgumentException("preQuantityIncrease must be positive");
		}
		oreQuantity += oreQuantityIncrease;
	}
	/**
	 * Decreases the quantity of ore stored in the inventory by the specified amount
	 * @param oreQuantityDecrease the number of units of ore to remove from the inventory
	 */
	public void decreaseOreQuantity(int oreQuantityDecrease){
		if(oreQuantityDecrease < 0){
			throw new IllegalArgumentException("oreQuantityDecrease must be positive");
		}
		oreQuantity -= oreQuantityDecrease;
	}
}


