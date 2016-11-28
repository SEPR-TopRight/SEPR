/**
 * 
 */
package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * @author jcn509
 *
 */
public class PlotHolder extends Table{
	private boolean aquisitionEnabled;
	
	private PlotClickMode clickMode = PlotClickMode.NOACTION;
	
	private Plot[][] plots;
	
	private Buttons[][] buttons;
	
	private Runnable callOncePlotAquired;
	
	private Player currentPlayer = null; // Want to throw an exception if used when null.
	
	public void plotClicked(int row,int column){
		if(clickMode == PlotClickMode.AQUIRE)
			aquirePlot(row,column);
	}
	
	public void aquirePlot(int row, int column){
		Plot plot = plots[row][column];
		if(!plot.hasBeenAquired()){
			plot.setPlayer(currentPlayer);
			callOncePlotAquired.run();
			buttons[row][column].setImage("plot_overlays/human.pack", "human", "human");
		}
	}
	
	public void setCurrentPlayer(Player player){
		this.currentPlayer = player;
	}
	
	public void setClickActionAquire(Runnable callOncePlotAquired){
		clickMode = PlotClickMode.AQUIRE;
		this.callOncePlotAquired = callOncePlotAquired;
	}
	
	public void setClickActionNone(){
		clickMode = PlotClickMode.NOACTION;
	}
	
	public PlotHolder(final Plot[][] plots, String backgroundImage){
		super();
		setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(backgroundImage)))));
	
		this.plots = plots;
		
		aquisitionEnabled = false;
		buttons = new Buttons[plots.length][plots[0].length];
		
		if(plots.length == 0 || plots[0].length==0)
			throw new IllegalArgumentException("the plots array must contain elements!");
		
		final int numRows = plots.length;
		final int numColumns = plots[0].length;
		
		for(int row=0; row<numRows; row++){
			for(int column=0; column<numColumns; column++){
				final int r = row; // interesting dirty hack
				final int c = column;
				Buttons button = new Buttons("", "plot_overlays/AI2.pack", "AI", "AI",
						new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y)
						{
							plotClicked(r,c);
						}
						}
				);
				add(button).expand().fill();
				buttons[row][column] = button;
			}
			row();
		}
	}
}
