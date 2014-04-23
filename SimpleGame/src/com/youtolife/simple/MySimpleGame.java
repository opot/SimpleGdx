package com.youtolife.simple;

import java.util.Vector;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MySimpleGame implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;

	Vector<GameState> states = new Vector<GameState>();
	private int CurrentState = -1;

	public static final int GAMEPLAYSTATE = 1;
	public static final int MAINMENUSTATE = 2;

	float w;
	float h;

	private float lastAngle = 0;
	
	@Override
	public void create() {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(1, h / w);
		batch = new SpriteBatch();

		states.add(new GamePlayState(GAMEPLAYSTATE));
		states.add(new MainMenuState(MAINMENUSTATE));
		
		for (GameState state : states)
			state.init();
		this.enterState(MAINMENUSTATE);
	}

	@Override
	public void dispose() {
		batch.dispose();
		for (GameState state : states)
			state.dispose();
	}

	@Override
	public void render() {
		update();

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		{
			for (GameState state : states)
				if (state.getID() == CurrentState)
					state.draw(batch);
		}
		batch.end();
	}

	public void update() {
		for (GameState state : states)
			if (state.getID() == CurrentState)
				state.update(this);
		
		int angle = (int) (Gdx.input.getAccelerometerX()*9);
		if (Math.abs(angle)<15) {
			camera.rotate(lastAngle);
			camera.rotate(-angle);
			camera.zoom = (float)(0.5f/(Math.abs(angle/9f)+1f));
			lastAngle = angle;
		//	camera.update();
		}
	}

	public void enterState(int StateId) {
		CurrentState = StateId;
		for (GameState state : states)
			if (state.getID() == CurrentState)
				state.enter();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
