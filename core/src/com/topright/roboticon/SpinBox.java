package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class SpinBox extends Table{
	Integer value;
	Integer maxValue;
	Integer minValue;
	Label valueLabel;
	
	public SpinBox(String text,int initialValue, int minValue, int maxValue){
		value = initialValue;
		this.maxValue = maxValue;
		this.minValue = minValue;
		
		valueLabel = new Label(Integer.toString(value), new Skin(Gdx.files.internal("uiskin.json")));
		
		TextButton increaseQuantityButton = new TextButton("+", new Skin(Gdx.files.internal("uiskin.json")));
		increaseQuantityButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
	        {
	        	incrementQuantity();
	        }
			
		});
		
		TextButton decreaseQuantityButton = new TextButton("-", new Skin(Gdx.files.internal("uiskin.json")));
		decreaseQuantityButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
	        {
	        	decrementQuantity();
	        }
			
		});
		add(new Label(text,new Skin(Gdx.files.internal("uiskin.json")))).left();
		add(valueLabel).right().expand().padRight(5);
		add(increaseQuantityButton).right();
		add(decreaseQuantityButton).right();
	}
	
	public int getValue(){
		return value;
	}
	
	public void setMaxValue(int maxValue){
		this.maxValue = maxValue;
		if(value>maxValue){
			value=maxValue;
			valueLabel.setText(value.toString());
		}
	}
	
	public void setMinValue(int minValue){
		this.minValue = minValue;
	}
	
	private void incrementQuantity(){
		if(value<maxValue){ //Update later
			value++;
			valueLabel.setText(value.toString());
		}
	}
	private void decrementQuantity(){
		if(value>minValue){
			value--;
			valueLabel.setText(value.toString());
		}
	}
}
