/**
 * @author jcn509
 *
 */

public class MarketInventory extends Inventory{
	private int roboticonQuantity;
	public MarketInventory(int oreQuantity, int energyQuantity, int roboticonQuantity){
		super(oreQuantity,energyQuantity);
		setRoboticonQuantity(roboticonQuantity);
	}
	public MarketInventory(){
		super();
		setRoboticonQuantity(0);
	}
	public void setRoboticonQuantity(int roboticonQuantity){
		if(roboticonQuantity<0)
			throw new IllegalArgumentException("roboticonQuantity must be >=0");
		this.roboticonQuantity = roboticonQuantity;
	}
	public int getRoboticonQuantity(){
		return roboticonQuantity;
	}
	public void increaseRoboticonQuantity(int roboticonQuantityIncrease){
		if(roboticonQuantityIncrease<=0)
			throw new IllegalArgumentException("roboticonQuantityIncrease must be positive");
		roboticonQuantity += roboticonQuantityIncrease;
	}
	public void decreaseRoboticonQuantity(int roboticonQuantityDecrease){
		if(roboticonQuantityDecrease<=0)
			throw new IllegalArgumentException("roboticonQuantityDecrease must be positive");
		roboticonQuantity -= roboticonQuantityDecrease;
	}
}
