package com.topright.roboticon;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Test case for the PlotManager class
 * @author jcn509
 *
 */
public class SpinBoxTestCase extends GuiTest { 
	private SpinBox spinBox;
	
	private TextButton increaseQuantityButton;
	private TextButton decreaseQuantityButton;
	
	/**
	 * Runs before any tests and creates the required ButtonWithIcon object
	 */
	@Before
	public void setup(){
		spinBox = new SpinBox("text", 2, 0, 4);
		
		increaseQuantityButton = spinBox.getIncreaseQuantityButton();
		decreaseQuantityButton = spinBox.getDecreaseQuantityButton();		
	}
	
	/**
	 * Tests {@link SpinBox#SpinBox} ensures that the initial value is set up correctly
	 */
	@Test
	public void testInitiallValue(){
		// Should initially be 2
		assertEquals(2,spinBox.getValue());
	}
	
	/**
	 * Tests whether the increase quantity button works when the current value is less 2 less than the maximum.
	 * The value stored by the SpinBox should be incremented
	 */
	@Test
	public void testIncreaseValueTwoLessThanMax(){
		// Max value is initially 4 and value is initially 2
		int valueBefore = spinBox.getValue();
		clickActor(increaseQuantityButton);
		assertEquals(valueBefore+1,spinBox.getValue());
	}	
	
	/**
	 * Test whether the increase quantity button works when the current value is less one less than the maximum.
	 * The value stored by the SpinBox should be incremented
	 */
	@Test
	public void testIncreaseValueOneLessThanMax(){
		spinBox.setMaxValue(spinBox.getValue()+1); // 1 greater than the current value
		int valueBefore = spinBox.getValue();
		clickActor(increaseQuantityButton);
		assertEquals(valueBefore+1,spinBox.getValue());
	}
	
	/**
	 * Test whether the increase quantity button works when the current value is the maximum value
	 * The value stored by the SpinBox should be incremented
	 */
	@Test
	public void testIncreaseValueAlreadyAtMax(){
		spinBox.setMaxValue(spinBox.getValue()); // Value already at max
		int valueBefore = spinBox.getValue();
		clickActor(increaseQuantityButton);
		assertEquals(valueBefore,spinBox.getValue());
	}
	
	/**
	 * Tests whether the decrease quantity button works when the current value is less 2 less than the minimum.
	 * The value stored by the SpinBox should be incremented
	 */
	@Test
	public void testDecreaseValueTwoGreaterThanMin(){
		// Min value is initially 0 and value is initially 2
		int valueBefore = spinBox.getValue();
		clickActor(decreaseQuantityButton);
		assertEquals(valueBefore-1,spinBox.getValue());
	}	
	
	/**
	 * Test whether the decrease quantity button works when the current value is less one greater than the minimum.
	 * The value stored by the SpinBox should be decremented
	 */
	@Test
	public void testDecreaseValueOneLessThanMin(){
		spinBox.setMinValue(spinBox.getValue()-1); // 1 less than the current value
		int valueBefore = spinBox.getValue();
		clickActor(decreaseQuantityButton);
		assertEquals(valueBefore-1,spinBox.getValue());
	}
	
	/**
	 * Test whether the decrease quantity button works when the current value is the minimum value
	 * The value stored by the SpinBox should be incremented
	 */
	@Test
	public void testDecreaseValueAlreadyAtMin(){
		spinBox.setMinValue(spinBox.getValue()); // Value already at min
		int valueBefore = spinBox.getValue();
		clickActor(decreaseQuantityButton);
		assertEquals(valueBefore,spinBox.getValue());
	}
	
	/**
	 * Tests {@link SpinBox#setMaxValue(int)} ensures that when the max value is set to a value 1 higher than the current
	 * value the current value is not changed
	 */
	@Test
	public void testSetMaxValueOneAboveCurrentValue(){
		int valueBefore = spinBox.getValue();
		spinBox.setMaxValue(spinBox.getValue()+1); // Max value set 1 above the current value
		assertEquals(valueBefore,spinBox.getValue());
	}
	
	/**
	 * Tests {@link SpinBox#setMaxValue(int)} ensures that when the max value is set to a value 2 higher than the current
	 * value the current value is not changed
	 */
	@Test
	public void testSetMaxValueTwoAboveCurrentValue(){
		int valueBefore = spinBox.getValue();
		spinBox.setMaxValue(spinBox.getValue()+2); // Max value set 2 above the current value
		assertEquals(valueBefore,spinBox.getValue());
	}
	
	/**
	 * Tests {@link SpinBox#setMaxValue(int)} ensures that when the max value is set to the current
	 * value the current value is not changed
	 */
	@Test
	public void testSetMaxValueToCurrentValue(){
		int valueBefore = spinBox.getValue();
		spinBox.setMaxValue(spinBox.getValue()); // Max value set to the current value
		assertEquals(valueBefore,spinBox.getValue());
	}
	
	/**
	 * Tests {@link SpinBox#setMaxValue(int)} ensures that when the max value is set to a value
	 * 1 below the current value, the current value is reduced to this new maximum value
	 */
	@Test
	public void testSetMaxValueOneBelowCurrentValue(){
		int valueBefore = spinBox.getValue();
		spinBox.setMaxValue(spinBox.getValue()-1); // Max value set to a value below the current value
		assertEquals(valueBefore-1,spinBox.getValue()); // Value should be reduced by 1
	}
	
	/**
	 * Tests {@link SpinBox#setMaxValue(int)} ensures that when the max value is set to a value
	 * 2 below the current value, the current value is reduced to this new maximum value
	 */
	@Test
	public void testSetMaxValueTwoBelowCurrentValue(){
		int valueBefore = spinBox.getValue();
		spinBox.setMaxValue(spinBox.getValue()-2); // Max value set to a value below the current value
		assertEquals(valueBefore-2,spinBox.getValue()); // Value should be reduced by 2
	}
	
 /** Tests {@link SpinBox#setMinValue(int)} ensures that when the min value is set to a value 1 lower than the current
	 * value the current value is not changed
	 */
	@Test
	public void testSetMinValueOneAboveCurrentValue(){
		int valueBefore = spinBox.getValue();
		spinBox.setMinValue(spinBox.getValue()-1); // Min value set 1 below the current value
		assertEquals(valueBefore,spinBox.getValue());
	}
	
	/**
	 * Tests {@link SpinBox#setMinValue(int)} ensures that when the min value is set to a value 2 lower than the current
	 * value the current value is not changed
	 */
	@Test
	public void testSetMinValueTwoAboveCurrentValue(){
		int valueBefore = spinBox.getValue();
		spinBox.setMinValue(spinBox.getValue()-2); // Min value set 2 below the current value
		assertEquals(valueBefore,spinBox.getValue());
	}
	
	/**
	 * Tests {@link SpinBox#setMinValue(int)} ensures that when the min value is set to the current
	 * value the current value is not changed
	 */
	@Test
	public void testSetMinValueToCurrentValue(){
		int valueBefore = spinBox.getValue();
		spinBox.setMinValue(spinBox.getValue()); // Min value set to the current value
		assertEquals(valueBefore,spinBox.getValue());
	}
	
	/**
	 * Tests {@link SpinBox#setMinValue(int)} ensures that when the min value is set to a value
	 * 1 above the current value, the current value is increased to this new minimum value
	 */
	@Test
	public void testSetMinValueOneBelowCurrentValue(){
		int valueBefore = spinBox.getValue();
		spinBox.setMinValue(spinBox.getValue()+1); // Min value set to a value above the current value
		assertEquals(valueBefore+1,spinBox.getValue()); // Value should be increased by 1
	}
	
	/**
	 * Tests {@link SpinBox#setMinValue(int)} ensures that when the min value is set to a value
	 * 2 above the current value, the current value is increased to this new minimum value
	 */
	@Test
	public void testSetMinValueTwoBelowCurrentValue(){
		int valueBefore = spinBox.getValue();
		spinBox.setMinValue(spinBox.getValue()+2); // Min value set to a value above the current value
		assertEquals(valueBefore+2,spinBox.getValue()); // Value should be increased by 2
	}

}
