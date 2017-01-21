package com.topright.roboticon;
/**
 * 
 * Stores the information required by a plot and provides the methods necessary to perform any actions that the plot needs to perform
 * @author Ben
 *
 */
public class Plot {
	private PlotSpecialism specialism; // What the plot is best at producing
	private Player acquiredBy; // the player that owns the plot (null if not yet acquired)
	
	// The type of roboticon placed on the plot. 
	// A value of null indicates that no roboticon has been placed on this plot
	private RoboticonCustomisation roboticon;
	
	/**
	 * Constructor
	 * @param specialism The resource that the plot is best at producing
	 */
	public Plot(PlotSpecialism specialism){
		acquiredBy = null; // Indicates that no player has acquired this plot yet
		this.specialism = specialism;
		roboticon = null; // Indicates that no roboticon has been placed on this plot yet
	}

	/**
	 * Sets the customisation type of the roboticon that is on this plot (equivalent to placing a roboticon on the plot)
	 * @param roboticonCustomisation the customisation type of the roboticon
	 */
	public void placeRoboticon(RoboticonCustomisation roboticonCustomisation) {
		this.roboticon = roboticonCustomisation;
	}

	/**
	 * Sets the player that owns this plot
	 * <p>
	 * To be called when a player acquires this plot
	 * </p>
	 * @param player the player who owns the plot
	 */
	public void setPlayer(Player player){
		if (!hasBeenAcquired()){
			this.acquiredBy = player;
		}
		else{
			throw new IllegalArgumentException("Cannot acquire a plot that has already been acquired!");
		}
	}
	
	/**
	 * Returns true if this plot has been acquired and false otherwise
	 * @return true if this plot has been acquired and false otherwise
	 */
	public boolean hasBeenAcquired(){
		return !(acquiredBy == null);
	}
	
	/**
	 * Returns the Player that owns this plot
	 * @return The player that owns this plot
	 */
	public Player getPlayer(){
		return acquiredBy;
	}
	
	/**
	 * Returns the customisation type of the roboticon on this plot
	 * @return The customisation type of the roboticon on this plot
	 */
	public RoboticonCustomisation getRoboticon(){
		return roboticon;
	}
	
	/**
	 * Returns true if this plot has a roboticon on it and false otherwise
	 * @return true if this plot has a roboticon on it and false otherwise
	 */
	public boolean hasRoboticon(){
		return roboticon != null;
	}
	/**
	 * Returns the resource type that the plot is best at producing
	 * @return The resource type that the plot is best at producing
	 */
	public PlotSpecialism getSpecialism(){
		return specialism;
	}
	/**
	 * Causes the plot to produce resources for the player that owns it (if applicable)
	 * <p>
	 * A plot will only produce resources if it has been acquired and had a roboticon placed on it
	 * The type of resource produced depends on the customisation of the roboticon placed on the plot
	 * The amount of whatever resource that is produced depends on whether the roboticons customisation matches the plots speciality
	 * Resources produced are added to the inventory of the player who owns this plot
	 * </p>
	 */
	public void produce(){
		
		// If no player has acquired this plot of if it does not have a roboticon on it
		if ((acquiredBy == null) ||(!hasRoboticon())){
			return; 
		}
		else{
			if(roboticon == RoboticonCustomisation.ENERGY){ 
				if(specialism == PlotSpecialism.ENERGY){
					acquiredBy.increaseEnergyQuantity(2);
				}
				else{
					acquiredBy.increaseEnergyQuantity(1);
				}
			}
			else if(roboticon == RoboticonCustomisation.ORE){
				if(specialism == PlotSpecialism.ORE){
					acquiredBy.increaseOreQuantity(2);
				}
				else{
					acquiredBy.increaseOreQuantity(1);
				}
			}
		}
		
	}
	
}
