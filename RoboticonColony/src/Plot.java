/**
 * 
 * Manages a individual plot
 * A data structure to store the information of an individual plot
 * @author Ben
 *
 */
public class Plot {
	private String best;
	private int player;
	private String roboticon;
	/**
	 * 
	 * @param player an integer corresponding to whichever player owns the plot (1 or 2) or  no player owns the plot(0)
	 * @param best the resource which the plot produces more of
	 * @param roboticon the roboticon type which is on the plot if there is one or none otherwise 
	 */
	public Plot(int player,String best, String roboticon){
		setPlayer(player);
		setBest(best);
		setRoboticon(roboticon);
	}
	public Plot() {
		initialisePlayer();
		setBest("ore");
		setRoboticon("none");
	}


	/**
	 * 
	 * Sets the resource that the plot is best at producing
	 * @param best the name of the resource the plot is best at producing( 'ore' or 'energy') 
	 */
	public void setBest(String best) {
		this.best = best;
		
	}
	/**
	 * 
	 * Sets the roboticon type on the plot of land
	 * @param roboticon the type of roboticon on the plot of land ('ore' or 'energy')
	 */
	public void setRoboticon(String roboticon) {
		this.roboticon = roboticon;
		
	}
	/**
	 * 
	 * Initialises the plot to be not owned by a player
	 */
	private void initialisePlayer(){
		this.player = 0;
	}
	/**
	 * 
	 * Changes the plot to be owned by a player
	 * @param player a number referencing the player that owns the plot
	 */
	public void setPlayer(int player){
		if (this.player == 0){
			this.player = player;
		}
		else{
			throw new IllegalStateException("Cannot take plots owned by another player");
		}
	}
	/**
	 * 
	 * Returns 0 if plot is not owned or a number referencing the player that owns the plot of land
	 */
	public int getPlayer(){
		return player;
	}
	/**
	 * 
	 * returns 'none' if no roboticon on the land or the name of the specialisation of the roboticon on the land
	 */
	public String getRoboticon(){
		return roboticon;
	}
	/**
	 * 
	 * returns the resource that the plot will produce more of ('ore' or 'energy')
	 */
	public String getBest(){
		return best;
	}
	/**
	 * 
	 * Adds resources to players inventory if they own the plot and have a roboticon on it 
	 */
	public void produceRoboticon(){
		if ((player == 0) ||(roboticon == "none")){
			return;
		}
		else{
			//TO DO Increase relevant resource in players inventory, 2* if best? (will do once player class is done)
			return;
		}
		
	}
	
}
