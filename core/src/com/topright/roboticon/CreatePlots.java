package com.topright.roboticon;

import java.util.Random;

/**
 * Contains a single static method that can be used to create and initialise a 2D array of plots
 * of a given size.
 * @author jcn509
 *
 */
public class CreatePlots {
	private CreatePlots(){} // No instantiations
	
	/**
	 * Creates the 2d array of plot objects that players can interact with
	 * @param rows The number of rows of plots that is required
	 * @param columns The number of columns of plots that is required
	 */
	public static Plot[][] createPlots(int rows, int columns){
		if(rows<=0){
			throw new IllegalArgumentException("rows must be >=0 rows = "+Integer.toString(rows));
		}
		if(columns<=0){
			throw new IllegalArgumentException("columns must be >=0 columns = "+Integer.toString(columns));
		}
		Plot[][] plots = new Plot[rows][columns];
		
		for(int row=0;row<rows;row++){
			
			for(int column=0;column<columns;column++){
				
				// Randomly choose a specialism for each plot
			    PlotSpecialism[] specialismChoice = {PlotSpecialism.ORE,PlotSpecialism.ENERGY};
			    int choice = new Random().nextInt(specialismChoice.length);
			
			    // No Player owns each plot, no roboticon is placed on it and its specialism is random
				plots[row][column]= new Plot(specialismChoice[choice]);
			}
		}
		return plots;
	}
}
