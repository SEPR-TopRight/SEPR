package com.topright.roboticon;
/**
 * 
 * Manages a individual plot
 * A data structure to store the information of an individual plot
 * @author Ben
 *
 */
public class Plot {
	private PlotSpecialism specialism;
	private Player player;
	private RoboticonCustomisation roboticon;
	/**
	 * 
	 * @param Player an integer corresponding to whichever player owns the plot (1 or 2) or  no player owns the plot(0)
	 * @param best the resource which the plot produces more of
	 * @param Roboticon the roboticon type which is on the plot if there is one or none otherwise 
	 */
	public Plot(PlotSpecialism specialism){
		player = null;
		this.specialism = specialism;
		roboticon = RoboticonCustomisation.UNCUSTOMISED;
	}

	/**
	 * 
	 * Sets the roboticon type on the plot of land
	 * @param Roboticon the type of roboticon on the plot of land ('ore' or 'energy')
	 */
	public void setRoboticon(RoboticonCustomisation roboticon) {
		this.roboticon = roboticon;
		
	}
	/**
	 * 
	 * Initialises the plot to be not owned by a player
	 */
	private void initialisePlayer(){
		this.player = null;
	}
	/**
	 * 
	 * Changes the plot to be owned by a player
	 * @param Player a number referencing the player that owns the plot
	 */
	public void setPlayer(Player player){
		if (this.player == null){
			this.player = player;
		}
		else{
			throw new IllegalStateException("Cannot take plots owned by another player");
		}
	}
	
	public boolean hasBeenAquired(){
		return !(player == null);
	}
	
	/**
	 * 
	 * Returns 0 if plot is not owned or a number referencing the player that owns the plot of land
	 */
	public Player getPlayer(){
		return player;
	}
	/**
	 * 
	 * returns 'none' if no roboticon on the land or the name of the specialisation of the roboticon on the land
	 */
	public RoboticonCustomisation getRoboticon(){
		return roboticon;
	}
	
	public boolean hasRoboticon(){
		return roboticon != RoboticonCustomisation.UNCUSTOMISED;
	}
	/**
	 * 
	 * returns the resource that the plot will produce more of ('ore' or 'energy')
	 */
	public PlotSpecialism getSpecialism(){
		return specialism;
	}
	/**
	 * 
	 * Adds resources to players inventory if they own the plot and have a roboticon on it 
	 */
	public void produce(){
		if ((player == null) ||(roboticon == RoboticonCustomisation.UNCUSTOMISED)){
			return;
		}
		else{
			//TO DO Increase relevant resource in players inventory, 2* if best? (will do once player class is done)
			if(roboticon == RoboticonCustomisation.ENERGY && specialism == PlotSpecialism.ENERGY)
				player.inventory.increaseEnergyQuantity(2);
			else if(roboticon == RoboticonCustomisation.ORE && specialism == PlotSpecialism.ORE)
				player.inventory.increaseOreQuantity(2);
			else if(roboticon == RoboticonCustomisation.ENERGY)
				player.inventory.increaseEnergyQuantity(1);
			else if(roboticon == RoboticonCustomisation.ORE)
				player.inventory.increaseOreQuantity(1);
			return;
		}
		
	}
	
}
