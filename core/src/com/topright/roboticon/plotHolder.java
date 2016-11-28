/**
 * 
 */
package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * @author jcn509
 *
 */
public class plotHolder extends Table{
	public plotHolder(Plot[][] plots, int width, int height){
		super();
		setWidth(width);
		setHeight(height);
		setX(0);
		setY(0);
		
		if(plots.length == 0 || plots[0].length==0)
			throw new IllegalArgumentException("the plots array must contain elements!");
		
		int numRows = plots.length;
		int numColumns = plots[0].length;
		
		for(int row=0; row<numRows; row++){
			for(int column=0; column<numColumns; column++){
				add(new TextButton(row+" "+column, new Skin(Gdx.files.internal("uiskin.json")))).expand().fill();
			}
			row();
		}
	}
}
