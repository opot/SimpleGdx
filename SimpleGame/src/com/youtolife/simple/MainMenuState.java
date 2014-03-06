package com.youtolife.simple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuState extends GameState {

	private Texture texture;
	private Sprite sprite;
	
	float h,w;
	float scale = 1f,scaleSpeed = 0.1f;
	
	public MainMenuState(int StateId) {
		super(StateId);
	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}

	@Override
	public void update(MySimpleGame game) {
		Input input = Gdx.input;
		if(input.isTouched()||input.isKeyPressed(Input.Keys.SPACE))
				game.enterState(MySimpleGame.GAMEPLAYSTATE);
		scale+=scaleSpeed*Gdx.graphics.getDeltaTime();
		if(scale>1.35f||scale<=1f)
			scaleSpeed*=-1;
			
		sprite.setScale(scale);
	}
	
	@Override
	public void init() {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		
		texture = new Texture(Gdx.files.internal("data/menu.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		sprite = new Sprite(texture);
		sprite.setSize(1f, 1f * h / w);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setPosition(-0.5f, -0.5f * h / w);
	}

	@Override
	public void dispose() {
		texture.dispose();
	}

	@Override
	public void enter() {

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
