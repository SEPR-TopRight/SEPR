package com.topright.roboticon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

//TODO: change fonts
//TODO: invisible background
public class Text extends Button {
	
	@Override
	public void create(String text, float x, float y){
		
		font = new BitmapFont();
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("ButtonOn");
        //textButtonStyle.down = skin.getDrawable("ButtonOff");
        //textButtonStyle.checked = skin.getDrawable("checked-button");
        button = new TextButton(text, textButtonStyle);
        button.setX(x);
        button.setY(y);
        
        Main.stage.addActor(button);
		
	}

}
