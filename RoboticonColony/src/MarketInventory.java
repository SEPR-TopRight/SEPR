/**
 * A data structure used to keep track of the market's inventory.
 * <p>
 * Keeps track of:
 * 	The number of units of ore the market is in possession of
 * 	The number of units of energy the market is in possession of
 * 	The number of roboticons the market is in possession of
 * <p>
 * @author jcn509
 *
 */

public class MarketInventory extends Inventory{
	private int roboticonQuantity;
	
	/**
	 * Constructor
	 * @param oreQuantity The initial quantity of ore to be stored in the inventory.
	 * @param energyQuantity The initial quantity of energy to be stored in the inventory.
	 * @param roboticonQuantity The initial quantity of roboticons to be stored in the market.
	 */
	public MarketInventory(int oreQuantity, int energyQuantity, int roboticonQuantity){
		super(oreQuantity,energyQuantity); 
		initialiseRoboticonQuantity(roboticonQuantity);
	}
	
	/**
	 * Set the initial quantity of roboticons to be stored in the market.
	 * <p>
	 * Ensures that the number of roboticons to be stored is not negative.
	 * </p>
	 * @param roboticonQuantity The quantity of roboticons to be stored in the inventory.
	 */
	private void initialiseRoboticonQuantity(int roboticonQuantity){
		if(roboticonQuantity<0) // Cannot store a negative quantity of roboticons.
			throw new IllegalArgumentException("roboticonQuantity must be >=0");
		this.roboticonQuantity = roboticonQuantity;
	}
	
	/**
	 * Returns the quantity of roboticons that are stored in the inventory.
	 * @return The quantity of roboticons stored in the inventory.
	 */
	public int getRoboticonQuantity(){
		return roboticonQuantity;
	}
	
	/**
	 * Increases the quantity of roboticons stored in the inventory by the given amount.
	 * <p>
	 * Ensures that the number of roboticons to add to the inventory is not negative.
	 * </p>
	 * @param roboticonQuantityIncrease The number of roboticons to be added to the inventory.
	 */
	public void increaseRoboticonQuantity(int roboticonQuantityIncrease){
		if(roboticonQuantityIncrease < 0) // Cannot add a negative quantity of roboticons.
			throw new IllegalArgumentException("roboticonQuantityIncrease must be positive");
		roboticonQuantity += roboticonQuantityIncrease;
	}
	
	/**
	 * Decreases the quantity of roboticons stored in the market by the given amount.
	 * <p>
	 * Ensures that the number of roboticons to be taken away from the inventory is not negative.
	 * Ensures that the number of roboticons to be taken away is not greater than the number currently stored.
	 * </p>
	 * @param roboticonQuantityDecrease The number of roboticons to be removed from the market.
	 */
	public void decreaseRoboticonQuantity(int roboticonQuantityDecrease){
		if(roboticonQuantityDecrease < 0) // Cannot take away a negative quantity of roboticons.
			throw new IllegalArgumentException("roboticonQuantityDecrease must be positive");
		if(roboticonQuantity - roboticonQuantityDecrease < 0) // Cannot end up with a negative number of roboticons being stored.
			throw new IllegalArgumentException("roboticonQuantityDecrease must not be greater than roboticonQuantity");
		roboticonQuantity -= roboticonQuantityDecrease;
	}
}