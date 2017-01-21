package com.topright.roboticon;

import static org.junit.Assert.*;



import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Test case for the CreatePlots class
 * @author jcn509
 *
 */
public class CreatePlotsTestCase {
	
	
	/**
	 * Tests {@link CreatePlots#createPlots(int, int)} ensures that the plot grid has the correct number of rows (4).
	 */
	@Test
	public void testCreatePlotsCorrectRowsFour(){
		Plot[][] plots = CreatePlots.createPlots(4, 5);
		assertEquals(plots.length, 4);
	}
	
	/**
	 * Tests {@link CreatePlots#createPlots(int, int)} ensures that the plot grid has the correct number of rows (1).
	 */
	@Test
	public void testCreatePlotsCorrectRowsOne(){
		Plot[][] plots = CreatePlots.createPlots(1, 5);
		assertEquals(plots.length, 1);
	}
	
	/**
	 * Tests {@link CreatePlots#createPlots(int, int)} ensures that the plot grid has the correct number of columns (3).
	 */
	@Test
	public void testCreatePlotsCorrectColumnsThree(){
		Plot[][] plots = CreatePlots.createPlots(1, 3);
		assertEquals(plots[0].length, 3);
	}
	
	/**
	 * Tests {@link CreatePlots#createPlots(int, int)} ensures that the plot grid has the correct number of columns (10).
	 */
	@Test
	public void testCreatePlotsCorrectColumnsTen(){
		Plot[][] plots = CreatePlots.createPlots(1, 10);
		assertEquals(plots[0].length, 10);
	}
	
	/**
	 * Tests {@link CreatePlots#createPlots(int, int)} ensures that an exception is thrown 
	 * if the specified number of rows is 0
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreatePlotsZeroRows(){
		CreatePlots.createPlots(0, 10);
	}
	
	/**
	 * Tests {@link CreatePlots#createPlots(int, int)} ensures that an exception is thrown 
	 * if the specified number of rows is negative
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreatePlotsNegativeRows(){
		CreatePlots.createPlots(-1, 10);
	}
	
	/**
	 * Tests {@link CreatePlots#createPlots(int, int)} ensures that an exception is thrown 
	 * if the specified number of columns is 0
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreatePlotsZeroColumns(){
		CreatePlots.createPlots(0, 10);
	}
	
	/**
	 * Tests {@link CreatePlots#createPlots(int, int)} ensures that an exception is thrown 
	 * if the specified number of columns is negative
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreatePlotsNegativeColumns(){
		CreatePlots.createPlots(-1, 10);
	}
	
	/**
	 * Tests {@link CreatePlots#createPlots(int, int)} ensures that every element in the array
	 * that has been returned actually contains a plot object (is not null)
	 * when an equal number of rows and columns are passed to it.
	 */
	@Test
	public void testCreatePlotsAllPlotsInstantiatedEqualRowsAndColumns(){
		Plot[][] plots = CreatePlots.createPlots(5, 5);
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(plots[row][column] == null){
					fail();
				}
			}
		}
	}
	
	/**
	 * Tests {@link CreatePlots#createPlots(int, int)} ensures that every plot created has
	 * not been acquired (does not have a player)
	 * when an equal number of rows and columns are passed to it.
	 */
	@Test
	public void testCreatePlotsAllPlotsNotAcquiredEqualRowsAndColumns(){
		Plot[][] plots = CreatePlots.createPlots(5, 5);
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(plots[row][column].hasBeenAcquired()){
					fail();
				}
			}
		}
	}
	
	/**
	 * Tests {@link CreatePlots#createPlots(int, int)} ensures that every plot created has 
	 * a specialism i.e. its specialism is not null
	 * when an equal number of rows and columns are passed to it.
	 */
	@Test
	public void testCreatePlotsAllPlotsHaveSpecialimsEqualRowsAndColumns(){
		Plot[][] plots = CreatePlots.createPlots(5, 5);
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(plots[row][column].getSpecialism() == null){
					fail();
				}
			}
		}
	}
	
	/**
	 * Tests {@link CreatePlots#createPlots(int, int)} ensures that every plot created has 
	 * does not have a robotion on it 
	 * when an unequal number of rows and columns are passed to it.
	 */
	@Test
	public void testCreatePlotsNoRoboticonsEqualRowsAndColumns(){
		Plot[][] plots = CreatePlots.createPlots(4, 4);
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(plots[row][column].hasRoboticon() == true){
					fail();
				}
			}
		}
	}
	
	/**
	 * Tests {@link CreatePlots#createPlots(int, int)} ensures that every element in the array
	 * that has been returned actually contains a plot object (is not null)
	 * when an unequal number of rows and columns are passed to it.
	 */
	@Test
	public void testCreatePlotsAllPlotsInstantiatedUnequalRowsAndColumns(){
		Plot[][] plots = CreatePlots.createPlots(4, 7);
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(plots[row][column] == null){
					fail();
				}
			}
		}
	}
	
	/**
	 * Tests {@link CreatePlots#createPlots(int, int)} ensures that every plot created has
	 * not been acquired (does not have a player)
	 * when an unequal number of rows and columns are passed to it.
	 */
	@Test
	public void testCreatePlotsAllPlotsNotAcquiredUnequalRowsAndColumns(){
		Plot[][] plots = CreatePlots.createPlots(4, 7);
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(plots[row][column].hasBeenAcquired()){
					fail();
				}
			}
		}
	}
	
	/**
	 * Tests {@link CreatePlots#createPlots(int, int)} ensures that every plot created has 
	 * a specialism i.e. its specialism is not null 
	 * when an unequal number of rows and columns are passed to it.
	 */
	@Test
	public void testCreatePlotsAllPlotsHaveSpecialimsUnequalRowsAndColumns(){
		Plot[][] plots = CreatePlots.createPlots(4, 7);
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(plots[row][column].getSpecialism() == null){
					fail();
				}
			}
		}
	}
	
	/**
	 * Tests {@link CreatePlots#createPlots(int, int)} ensures that every plot created has 
	 * does not have a robotion on it 
	 * when an unequal number of rows and columns are passed to it.
	 */
	@Test
	public void testCreatePlotsNoRoboticonsUnequalRowsAndColumns(){
		Plot[][] plots = CreatePlots.createPlots(4, 7);
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(plots[row][column].hasRoboticon() == true){
					fail();
				}
			}
		}
	}
	
}
