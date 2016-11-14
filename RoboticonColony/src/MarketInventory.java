/**
 * A class used to keep track of the resources that a market object is in possession of
 * @author jcn509
 *
 */

public class MarketInventory extends Inventory{
	private int roboticonQuantity;
	/**
	 * 
	 * @param oreQuantity the initial quantity of ore to be stored in the inventory
	 * @param energyQuantity the initial quantity of energy to be stored in the inventory
	 * @param roboticonQuantity the intiail quantity of roboticons to be stored in the market
	 */
	public MarketInventory(int oreQuantity, int energyQuantity, int roboticonQuantity){
		super(oreQuantity,energyQuantity);
		initialiseRoboticonQuantity(roboticonQuantity);
	}
	/**
	 * Set the initial quantity of roboticons to be stored in the market
	 * @param roboticonQuantity the quantity of roboticons to be stored in the market
	 */
	private void initialiseRoboticonQuantity(int roboticonQuantity){
		if(roboticonQuantity<0)
			throw new IllegalArgumentException("roboticonQuantity must be >=0");
		this.roboticonQuantity = roboticonQuantity;
	}
	public MarketInventory(){
		super();
		initialiseRoboticonQuantity(0);
	}
	/**
	 * 
	 * @return the quantity of roboticons stored in the inventory
	 */
	public int getRoboticonQuantity(){
		return roboticonQuantity;
	}
	/**
	 * increases the quantity of roboticons stored in the market by the given amount
	 * @param roboticonQuantityIncrease the number of roboticons to be added to the inventory
	 */
	public void increaseRoboticonQuantity(int roboticonQuantityIncrease){
		if(roboticonQuantityIncrease<=0)
			throw new IllegalArgumentException("roboticonQuantityIncrease must be positive");
		roboticonQuantity += roboticonQuantityIncrease;
	}
	/**
	 * decreases the quantity of roboticons stored in the market by the given amount
	 * @param roboticonQuantityDecrease the number of roboticons to be removed from the market
	 */
	public void decreaseRoboticonQuantity(int roboticonQuantityDecrease){
		if(roboticonQuantityDecrease<=0)
			throw new IllegalArgumentException("roboticonQuantityDecrease must be positive");
		roboticonQuantity -= roboticonQuantityDecrease;
	}
}