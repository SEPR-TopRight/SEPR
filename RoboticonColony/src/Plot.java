/**
 * 
 * Manages a individual plot
 * A data structure to store the information of an individual plot
 * @author Ben
 *
 */
public class Plot {
	private String Best;
	private Player Player;
	private RoboticonCustomisation Roboticon;
	/**
	 * 
	 * @param Player an integer corresponding to whichever player owns the plot (1 or 2) or  no player owns the plot(0)
	 * @param Best the resource which the plot produces more of
	 * @param Roboticon the roboticon type which is on the plot if there is one or none otherwise 
	 */
	public Plot(Player Player,String Best, RoboticonCustomisation Roboticon){
		setPlayer(Player);
		setBest(Best);
		setRoboticon(Roboticon);
	}
	public Plot() {
		initialisePlayer();
		setBest("ore");
		setRoboticon(RoboticonCustomisation.UNCUSTOMISED);
	}


	/**
	 * 
	 * Sets the resource that the plot is best at producing
	 * @param Best the name of the resource the plot is best at producing( 'ore' or 'energy') 
	 */
	public void setBest(String Best) {
		this.Best = Best;
		
	}
	/**
	 * 
	 * Sets the roboticon type on the plot of land
	 * @param Roboticon the type of roboticon on the plot of land ('ore' or 'energy')
	 */
	public void setRoboticon(RoboticonCustomisation Roboticon) {
		this.Roboticon = Roboticon;
		
	}
	/**
	 * 
	 * Initialises the plot to be not owned by a player
	 */
	private void initialisePlayer(){
		this.Player = null;
	}
	/**
	 * 
	 * Changes the plot to be owned by a player
	 * @param Player a number referencing the player that owns the plot
	 */
	public void setPlayer(Player player){
		if (this.Player == null){
			this.Player = player;
		}
		else{
			throw new IllegalStateException("Cannot take plots owned by another player");
		}
	}
	/**
	 * 
	 * Returns 0 if plot is not owned or a number referencing the player that owns the plot of land
	 */
	public Player getPlayer(){
		return Player;
	}
	/**
	 * 
	 * returns 'none' if no roboticon on the land or the name of the specialisation of the roboticon on the land
	 */
	public RoboticonCustomisation getRoboticon(){
		return Roboticon;
	}
	
	public boolean hasRoboticon(){
		return Roboticon != RoboticonCustomisation.UNCUSTOMISED;
	}
	/**
	 * 
	 * returns the resource that the plot will produce more of ('ore' or 'energy')
	 */
	public String getBest(){
		return Best;
	}
	/**
	 * 
	 * Adds resources to players inventory if they own the plot and have a roboticon on it 
	 */
	public void produce(){
		if ((Player == null) ||(Roboticon == RoboticonCustomisation.UNCUSTOMISED)){
			return;
		}
		else{
			//TO DO Increase relevant resource in players inventory, 2* if best? (will do once player class is done)
			return;
		}
		
	}
	
}
