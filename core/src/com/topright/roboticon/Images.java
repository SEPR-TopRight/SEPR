package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * A class used for creating and destroying images.
 * 
 * 
 * @author andrew
 *
 */
public class Images {

	Texture texture;
	Image actor;
	
	/**
	 * Create images where texturePath is the location of the image within the assests folder.
	 * x & y are the position of the image, imgWidth & imgHeight are how far you wish for the
	 * image to be stretched. The image is stored in Main.stage and drawn using stage.draw()
	 * 
	 * @author andrew
	 * @param texturePath
	 * @param x
	 * @param y
	 * @param imgWidth
	 * @param imgHeight
	 */
	public void create(String texturePath, int x, int y, int imgWidth, int imgHeight){
		
		texture = new Texture(Gdx.files.internal(texturePath));
        TextureRegion region = new TextureRegion(texture, x, y, imgWidth, imgHeight);          

        Image actor = new Image(region);
        
        Main.stage.addActor(actor);
		
	}
	
	/**
	 * Deletes the image from the stage and from existence .. forever. 
	 * 
	 * @author andrew
	 */
	public void destroy(){
		
		actor.remove();
		
	}
}
