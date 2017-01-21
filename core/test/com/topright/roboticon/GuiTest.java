package com.topright.roboticon;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;

import org.mockito.Mockito;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import mockit.Mocked;



/**
 * Base class that is extended by all classes that wish to test LibGDX components
 * Obtained from: http://manabreak.eu/java/2016/10/21/unittesting-libgdx.html (and then modified to suit our needs)
 * @author jcn509
 */

public class GuiTest {
    // This is our "test" application
    private static Application application;
    public Stage stage;
    @Before
    public void setupStage(){
    	stage = new Stage(Mockito.mock(ScreenViewport.class), Mockito.mock(SpriteBatch.class));
    	Gdx.input.setInputProcessor(stage);
    }
    
    // Before running any tests, initialise the application with the headless backend
    @BeforeClass
    public static void init() {
        // Note that we don't need to implement any of the listener's methods
        application = new HeadlessApplication(new ApplicationListener() {
            @Override public void create() {}
            @Override public void resize(int width, int height) {}
            @Override public void render() {}
            @Override public void pause() {}
            @Override public void resume() {}
            @Override public void dispose() {}
        },null);
        
        // Use Mockito to mock the OpenGL methods since we are running headlessly
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
   
    }

    // After we are done, clean up the application
    @AfterClass
    public static void cleanUp() {
        // Exit the application first
        application.exit();
        application = null;
    }
    
    /**
     * Used to simulate clicking a button or other widget. By calling of the clicke listeners attaced to the wdiget
     * @param actor The widget (actor) that is to be "clicked"
     */
    protected static void clickActor(Actor widget) {
	    Array<EventListener> listeners = widget.getListeners();
	    for(int listener=0;listener<listeners.size;listener++)
	    {
	    	// Ignore other types of listener
	    	if(listeners.get(listener) instanceof ClickListener){
	        	
	        	// Simulate the button click
	            ((ClickListener)listeners.get(listener)).clicked(new InputEvent(), 0, 0);
	        }
	    }
	}
}