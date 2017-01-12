/**
 * 
 */
package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import java.util.Random;

/**
 * 
 * @author jcn509
 */
public class PlotManager extends Table{
	
	private PlotClickMode clickMode = PlotClickMode.NOACTION;
	private Plot[][] plots;
	private ButtonWithIcon[][] buttons;
	public Player currentPlayer = null;
	public Player humanPlayer = null;
	public Player AIPlayer = null;
	private RoboticonPlaceMenu roboticonPlaceMenu = null;

	/**
	 * Constructor.
	 * @param backgroundImage A String that stores the file path of the background image.
	 */
	public PlotManager(String backgroundImage, Player humanPlayer, Player AIPlayer){
		super();
		setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(backgroundImage)))));
	
		this.humanPlayer = humanPlayer;
		this.AIPlayer = AIPlayer;
		initialisePlots();		
		createPlotGrid();
	}
	
	/**
	 * Called whenever a plot is clicked, ensures that the correct operation (if any) is carried out on the plot.
	 * @param row The row number of the plot that was clicked.
	 * @param column The column number of the plot that was clicked.
	 */
	public void plotClicked(int row,int column, float x, float y){
		if(clickMode == PlotClickMode.AQUIRE)
			aquirePlot(row,column);
		if(clickMode == PlotClickMode.PLACEROBOTICON && plots[row][column].getPlayer() ==currentPlayer)
			openPlaceRoboticonMenu(column,row,x,y);
		//TODO add extra case for if placing roboticons
	}

	

	private void initialisePlots(){
		plots = new Plot[5][5];
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
			    PlotSpecialism[] bestAtChoices = {PlotSpecialism.ORE,PlotSpecialism.ENERGY};
			    int choice = new Random().nextInt(bestAtChoices.length);
			
			    // No Player owns each plot, no roboticon is placed on it and its best at attribute is random
				plots[row][column]= new Plot(bestAtChoices[choice]);
				
				// For testing purposes
				//plots[row][column].setRoboticon(RoboticonCustomisation.ENERGY);
			}
		}
	}

	public void placeEnergyRoboticon(int plotX, int plotY){
		if(currentPlayer.attemptToPlaceRoboticon(plots[plotY][plotX],RoboticonCustomisation.ENERGY)){
	        	MessageManager.getInstance().dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal()); // Trigger main to update the menubar
	        	removeRoboticonPlaceMenu();
			Image roboticonImage =  new Image(new Texture(Gdx.files.internal("roboticons/energyRoboticon.png")));
			buttons[plotY][plotX].add(roboticonImage);
	        }
		
	}
	
	public void placeOreRoboticon(int plotX, int plotY){
		if(currentPlayer.attemptToPlaceRoboticon(plots[plotY][plotX],RoboticonCustomisation.ORE)){
	        	MessageManager.getInstance().dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal()); // Trigger main to update the menubar
	        	removeRoboticonPlaceMenu();
			Image roboticonImage =  new Image(new Texture(Gdx.files.internal("roboticons/oreRoboticon.png")));
			buttons[plotY][plotX].add(roboticonImage);
	        }
	}	

	private void openPlaceRoboticonMenu(int plotX, int plotY, float menuX, float menuY){
		removeRoboticonPlaceMenu();
		roboticonPlaceMenu = new RoboticonPlaceMenu(menuX, menuY, plotX, plotY, currentPlayer, this);
		addActor(roboticonPlaceMenu);
	}
	
	public void removeRoboticonPlaceMenu(){
		if(roboticonPlaceMenu != null){
			roboticonPlaceMenu.remove();
			roboticonPlaceMenu=null;
		}
	}
	
	/**
	 * Called if a plot is to be aquired by the current player.
	 * <p>
	 * If the plot is not currently owned by another player then it is acquired by the current player
	 * and a message is dispatched to let the main class know that this has happened.
	 * </p>
	 * @param row The row number of the plot.
	 * @param column The column number of the plot.
	 */
	public void aquirePlot(int row, int column){
		Plot plot = plots[row][column]; // The plot that is being looked at.
		if(!plot.hasBeenAquired()){ // If the Plot has yet to be acquired
			plot.setPlayer(currentPlayer);
			if(currentPlayer == humanPlayer){
				buttons[row][column].setImages("plot_overlays/human.pack", "human", "human");
				buttons[row][column].add(new Label("Specialism: "+plot.getSpecialism().toString(),new Skin(Gdx.files.internal("uiskin.json")))).top().row();
			}
			else{
				buttons[row][column].setImages("plot_overlays/AI.pack", "AI", "AI");
			}
			MessageManager.getInstance().dispatchMessage(GameEvents.PLOTAQUIRED.ordinal());
		}
	}
	
	/**
	 * Set the current player (this information is needed by some other methods).
	 * @param player The current player.
	 */
	public void setCurrentPlayer(Player player){
		currentPlayer = player;
	}
	
	
	/**
	 * Sets what action will be performed when a plot is clicked.
	 * @param mode
	 */
	public void setPlotClickMode(PlotClickMode mode){
		clickMode = mode;
	}
	
	/**
	 * Triggers all plots to produce resources for their players.
	 */
	public void produceResources(){
		for(int row = 0; row<plots.length;row++){
			for(int column = 0;column<plots[0].length;column++){
				plots[row][column].produce();
			}
		}
	}
	
	/**
	 * Returns true if every plot has already been acquired by some player.
	 * @return Whether or not every plot has already been acquired.
	 */
	public boolean allPlotsAquired(){
		for(int row = 0; row<plots.length;row++){
			for(int column = 0;column<plots[0].length;column++){
				if(!plots[row][column].hasBeenAquired())// if a plot has not been aquired
					return false;                       // then not all of the plots have been aquired
			}
		}
		return true; //if not returned yet, then all plots have been aquired
	}
	
	/**
	 * Create a grid of buttons that can be clicked on in order to interact with the plots.
	 */
	public void createPlotGrid(){
		int numRows = plots.length;
		int numColumns = plots[0].length;
		
		buttons = new ButtonWithIcon[plots.length][plots[0].length];
		
		for(int row=0; row<numRows; row++){
			for(int column=0; column<numColumns; column++){
				
				// Only variables from the enclosing scope that are final may be accessed in an anonymous class.
				// So these variables are declared so that they can be used below.
				final int r = row;
				final int c = column;
				
				ButtonWithIcon button = new ButtonWithIcon("", "plot_overlays/AI2.pack", "AI", "AI",
						new ClickListener(){
							@Override
							public void clicked(InputEvent event, float x, float y)
							{
								plotClicked(r,c,event.getStageX(),event.getStageY());
							}
						}
				);
				
				add(button).expand().fill();
				buttons[row][column] = button;
			}
			row();
		}
	}
	
	
	public Plot[][] getPlots(){
		return plots;
	}
}
