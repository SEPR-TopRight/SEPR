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
 * Manages all the plots and allows Players (both AI and human) to acquire plots and place roboticons on them
 * <p>
 * Design decision: decided against having one class for the GUI portion of the plot manager 
 * and one to actually manage the plot objects (that the AI could interact with)
 * as every interaction with a plot (whether by an AI player or a human player) requires to GUI to be updated
 * </p>
 * @author jcn509
 */
public class PlotManager extends Table{
	private PlotClickMode clickMode = PlotClickMode.NOACTION; // What happens when a plot is clicked
	private Plot[][] plots;
	private ButtonWithIcon[][] buttons; // One button for each plot (users click these buttons to interact with the plots)
	public Player currentPlayer = null;
	public Player humanPlayer = null;
	public Player AIPlayer = null;
	
	// The menu should not be open initially
	private RoboticonPlaceMenu roboticonPlaceMenu = null;
	
	/**
	 * Constructor.
	 * @param backgroundImage A String that stores the file path of the background image 
	 * (i.e. a picture of a map to be displayed behind the plots).
	 */
	public PlotManager(String backgroundImage, Player humanPlayer, Player AIPlayer){
		super();
		setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(backgroundImage)))));
	
		this.humanPlayer = humanPlayer;
		this.AIPlayer = AIPlayer;
		initialisePlots(4,5);		
		createPlotGrid();
	}
	
	/**
	 * Returns a 2D array containing all of the buttons that are clicked to interact with the various plots.
	 * <p>
	 * Needed for testing purposes or for any code where the programmer wishes to simulate a button click.
	 * </p>
	 * @return A 2D array containing all of the buttons that are clicked to interact with the various plots.
	 */
	public ButtonWithIcon[][] getPlotButtons(){
		return buttons;
	}
	
	/**
	 * Called whenever a plot is clicked, ensures that the correct operation (if any) is carried out on the plot.
	 * @param row The row number of the plot that was clicked.
	 * @param column The column number of the plot that was clicked.
	 */
	private void plotClicked(int row,int column, float x, float y){
		if(clickMode == PlotClickMode.ACQUIRE){
			acquirePlot(row,column);
		}
		else if(clickMode == PlotClickMode.PLACEROBOTICON && plots[row][column].getPlayer() == currentPlayer){
			openPlaceRoboticonMenu(column,row,x,y);
		}
	}

	/**
	 * Creates the 2d array of plot objects that players can interact with
	 * @param rows The number of rows of plots that is required
	 * @param columns The number of columns of plots that is required
	 */
	private void initialisePlots(int rows, int columns){
		plots = new Plot[rows][columns];
		
		for(int row=0;row<rows;row++){
			
			for(int column=0;column<columns;column++){
				
				// Randomly choose a specialism for each plot
			    PlotSpecialism[] specialismChoice = {PlotSpecialism.ORE,PlotSpecialism.ENERGY};
			    int choice = new Random().nextInt(specialismChoice.length);
			
			    // No Player owns each plot, no roboticon is placed on it and its specialism is random
				plots[row][column]= new Plot(specialismChoice[choice]);
			}
		}
		
	}

	/**
	 * Tries to place an energy roboticon at the chosen plot
	 * @param plotColumn The column number of the plot
	 * @param plotRow The row number of the plot
	 */
	public void placeEnergyRoboticon(int plotColumn, int plotRow){
		placeRoboticon(plotColumn,plotRow,RoboticonCustomisation.ENERGY,"roboticons/energyRoboticon.png");
	}
	
	private void placeRoboticon(int plotColumn, int plotRow, RoboticonCustomisation customisation, String imagePath){
		if(currentPlayer.attemptToPlaceRoboticon(plots[plotRow][plotColumn],customisation)){
			if(currentPlayer == humanPlayer){
				MessageManager.getInstance().dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal()); // Trigger main to update the menubar
				removeRoboticonPlaceMenu(); // A roboticon has been placed on the given plot
			}
			Image roboticonImage =  new Image(new Texture(Gdx.files.internal(imagePath)));
			buttons[plotRow][plotColumn].add(roboticonImage);
		}
	}
	
	/**
	 * Tries to place an ore roboticon at the chosen plot
	 * @param plotColumn The column number of the plot
	 * @param plotRow The row number of the plot
	 */
	public void placeOreRoboticon(int plotColumn, int plotRow){
		placeRoboticon(plotColumn,plotRow,RoboticonCustomisation.ORE,"roboticons/oreRoboticon.png");
	}	

	/**
	 * Opens a pop up window that contains a menu that users can use to place roboticons on a given plot
	 * @param plotColumn The column number of the plot
	 * @param plotRow The row number of the plot
	 * @param menuX The X coordinate of location on the screen where the menu should be placed
	 * @param menuY The Y coordinate of location on the screen where the menu should be placed
	 */
	private void openPlaceRoboticonMenu(int plotColumn, int plotRow, float menuX, float menuY){
		removeRoboticonPlaceMenu(); // If a menu is already open (possibly for a different plot) then remove it
		roboticonPlaceMenu = new RoboticonPlaceMenu(menuX, menuY, plotColumn, plotRow, currentPlayer, this);
		addActor(roboticonPlaceMenu); // Add the menu to the screen / Plot GUI
	}
	
	/**
	 * If a RoboticonPlaceMenu is open it will be closed
	 */
	public void removeRoboticonPlaceMenu(){
		if(roboticonPlaceMenu != null){ // If a RoboticonPlaceMenu is open
			roboticonPlaceMenu.remove(); // Remove it from the screen
			roboticonPlaceMenu=null; // We no longer need a reference to this object (makes it clear that a menu is not open)
		}
	}
	
	/**
	 * Called if a plot is to be acquired by the current player.
	 * <p>
	 * If the plot is not currently owned by another player then it is acquired by the current player
	 * and a message is dispatched to let the main class know that this has happened.
	 * </p>
	 * @param row The row number of the plot.
	 * @param column The column number of the plot.
	 */
	public void acquirePlot(int row, int column){
		Plot plot = plots[row][column]; // The plot that is being looked at.
		if(!plot.hasBeenAcquired()){ // If the Plot has yet to be acquired
			plot.setPlayer(currentPlayer);
			if(currentPlayer == humanPlayer){
				buttons[row][column].setImages("plot_overlays/human.pack", "human", "human");
				buttons[row][column].add(new Label("Specialism: "+plot.getSpecialism().toString(),new Skin(Gdx.files.internal("uiskin.json")))).top().row();
				buttons[row][column].row(); // add a new row to the table so that when a roboticon is placed, it will be appear below the specialism label
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
				if(!plots[row][column].hasBeenAcquired()){// If a plot has not been acquired
					return false;  // Then not all of the plots have been acquired
				}
			}
		}
		return true; // If not returned yet, then all plots have been acquired
	}
	
	/**
	 * Create a grid of buttons that can be clicked on so that users can interact with the plots.
	 */
	private void createPlotGrid(){
		int numRows = plots.length;
		int numColumns = plots[0].length;
		
		buttons = new ButtonWithIcon[plots.length][plots[0].length];
		
		for(int row=0; row<numRows; row++){
			for(int column=0; column<numColumns; column++){
				
				// Only variables from the enclosing scope that are final may be accessed in an anonymous class.
				// So these variables are declared so that they can be used below.
				final int r = row;
				final int c = column;
				
				ButtonWithIcon button = new ButtonWithIcon("", "plot_overlays/unacquired.pack", "unacquired", "unacquired",
						new ClickListener(){
							@Override
							public void clicked(InputEvent event, float x, float y)
							{
								plotClicked(r,c,event.getStageX(),event.getStageY()); // Called whenever this button is clicked
							}
						}
				);
				
				
				add(button).expand().fill();
				buttons[row][column] = button;
			}
			row(); // Cause the next button to be added to the next row of buttons (rather than adding it to the current one)
		}
	}
	
	/**
	 * Returns the array of plots in use by this object
	 * <p>
	 * Some other classes (e.g. the AIPlayer class) need this information
	 * </p>
	 * @return The array of plots in use by this object
	 */
	public Plot[][] getPlots(){
		return plots;
	}
}
