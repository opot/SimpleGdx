package com.youtolife.simple;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameState {

	private int StateId = -1;
	
	public GameState(int StateId){
		this.StateId = StateId;
	}
	public int getID(){
		return StateId;
	}
	
	public abstract void draw(SpriteBatch batch);
	public abstract void update(MySimpleGame game);
	public abstract void init();
	public abstract void dispose();
	public abstract void enter();
	public abstract void pause();
	public abstract void resume();
}
