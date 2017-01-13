package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A SpinBox widget that users can use to select an integer value that lies within an allowed range
 * @author jcn509
 *
 */
public class SpinBox extends Table{
	private Integer value;
	private Integer maxValue;
	private Integer minValue;
	private Label valueLabel;
	
	/**
	 * Constructor
	 * @param text The text that is to be displayed to the left of the SpinBox value
	 * @param initialValue The initial value of the SpinBox
	 * @param minValue The smallest number that the user can select
	 * @param maxValue The largest number that the user can select
	 */
	public SpinBox(String text,int initialValue, int minValue, int maxValue){
		value = initialValue;
		this.maxValue = maxValue;
		this.minValue = minValue;
		
		valueLabel = new Label(Integer.toString(value), new Skin(Gdx.files.internal("uiskin.json")));
		
		TextButton increaseQuantityButton = new TextButton("+", new Skin(Gdx.files.internal("uiskin.json")));		
		TextButton decreaseQuantityButton = new TextButton("-", new Skin(Gdx.files.internal("uiskin.json")));
		setButtonBehaviour(increaseQuantityButton, decreaseQuantityButton);
		
		add(new Label(text,new Skin(Gdx.files.internal("uiskin.json")))).left();
		add(valueLabel).right().expand().padRight(5);
		add(increaseQuantityButton).right();
		add(decreaseQuantityButton).right();
	}
	
	/**
	 * Sets the increase and decrease quantity buttons to call the incrementQuantity and decrementQuantity methods respectively when clicked.
	 * @param increaseQuantityButton The button that is clicked to increase the value of the SpinBox
	 * @param decreaseQuantityButton The button that is clicked to decrease the value of the SpinBox
	 */
	private void setButtonBehaviour(TextButton increaseQuantityButton, TextButton decreaseQuantityButton){
		increaseQuantityButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
	        {
	        	incrementQuantity(); // Called whenever increaseQuantityButton is clicked
	        }
			
		});
		
		decreaseQuantityButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
	        {
	        	decrementQuantity(); // Called whenever decreaseQuantityButton is clicked
	        }
		});
			
	}
	
	
	/**
	 * Returns the value selected using the SpinBox
	 * @return The value selected using the SpinBox
	 */
	public int getValue(){
		return value;
	}
	
	/**
	 * Sets the maximum value that can be selected using the SpinBox
	 * @param maxValue The maximum value that can be selected using the SpinBox
	 */
	public void setMaxValue(int maxValue){
		this.maxValue = maxValue;
		if(value>maxValue){ // If the value that is currently selected is greater than this new max value
			value=maxValue; // Reduce the selected value to this new value
			valueLabel.setText(value.toString());
		}
	}
	
	/**
	 * Sets the minimum value that can be selected using the SpinBox
	 * @param minValue The minimum value that can be selected using the SpinBox
	 */
	public void setMinValue(int minValue){
		this.minValue = minValue;
		if(value<minValue){ // If the value that is currently selected is less than this new max value
			value=minValue; // Increase the selected value to this new value
			valueLabel.setText(value.toString());
		}
	}
	
	/**
	 * Called whenever the incrementQauntityButton is clicked to increase the value of the SpinBox
	 */
	private void incrementQuantity(){
		if(value<maxValue){ // Only increase the selected value if its less than the maximum value that can be selected
			value++;
			valueLabel.setText(value.toString());
		}
	}
	
	/**
	 * Called whenever the decrementQauntityButton is clicked to decrease the value of the SpinBox
	 */
	private void decrementQuantity(){
		if(value>minValue){ // Only decrease the selected value if its greater than the minimum value that can be selected
			value--;
			valueLabel.setText(value.toString());
		}
	}
}
