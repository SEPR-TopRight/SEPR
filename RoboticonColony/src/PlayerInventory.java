import java.util.*;

/**
 * 
 * Manages a players inventory
 * A data structure to store the contents of a player's inventory
 * @author jcn509
 *
 */
public class PlayerInventory extends Inventory{
	private EnumMap<RoboticonCustomisation, Integer> roboticons = new EnumMap<RoboticonCustomisation, Integer>(RoboticonCustomisation.class);
	
	/**
	 * 
	 * @param oreQuantity the initial quantity of ore stored in the inventory
	 * @param energyQuantity the initial quantity of energy stored in the inventory
	 * @param roboticons Maps the customisation type of roboticons onto the initial quantity of each roboticon of that type to be stored in the inventory
	 */
	public PlayerInventory(int oreQuantity, int energyQuantity, EnumMap<RoboticonCustomisation, Integer> roboticons){
		super(oreQuantity,energyQuantity);
		initialiseRoboticonQuantities(roboticons);
	}
	/**
	 * Initialise the quantities of roboticons with given customisations
	 * @param roboticons Maps the customisation type of roboticons onto the initial quantity of each roboticon of that type to be stored in the inventory
	 */
	private void initialiseRoboticonQuantities(EnumMap<RoboticonCustomisation, Integer> roboticons){
		for (EnumMap.Entry<RoboticonCustomisation, Integer> entry : roboticons.entrySet())
		{
		    if(entry.getValue()<0)
		    	throw new IllegalArgumentException(entry.getKey().name()+ " is mapped to a value less than 0 in the roboticons map");
		}
		this.roboticons = roboticons;
	}
	public PlayerInventory(){
		super();
	}
	/**
	 * This method adds a roboticon with the specified customisation type to the inventory
	 * @param customisation the customisation type of the roboticon to be added to the inventory
	 */
	public void increaseRoboticonQuantity(RoboticonCustomisation customisation, int roboticonQuantityIncrease){
		if(roboticonQuantityIncrease<=0)
			throw new IllegalArgumentException("roboticonQuantityIncrease must be positive");
		else if(roboticons.containsKey(customisation))
			roboticons.put(customisation,roboticons.get(customisation)+roboticonQuantityIncrease);
		else
			roboticons.put(customisation, roboticonQuantityIncrease);
	}
	/**
	 * This method removes a roboticon of the specified customisation type from the inventory
	 * @param customisation specifies the customisation type that the roboticon to be removed has
	 */
	public void decreaseRoboticonQuantity(RoboticonCustomisation customisation, int roboticonQuantityDecrease){
		if((!roboticons.containsKey(customisation)) || (roboticons.get(customisation) < roboticonQuantityDecrease))// Inventory contains no roboticons with this customisation
			throw new IllegalArgumentException("The inventory does not contain enough roboticons with customisation "+customisation.name());
		else if(roboticonQuantityDecrease <= 0)
			throw new IllegalArgumentException("roboticonQuantityDecrease must be positive");
		roboticons.put(customisation, roboticons.get(customisation)-roboticonQuantityDecrease);
	}
	/**
	 * This method returns the quantity of roboticons stored with a given customisation
	 * @param customisation the customisation type of the roboticon
	 * @return the quantity of roboticons stored with the specified customisation type
	 */
	public int getRoboticonQuantity(RoboticonCustomisation customisation){
		if(roboticons.containsKey(customisation))
			return roboticons.get(customisation);
		return 0;
	}
}
