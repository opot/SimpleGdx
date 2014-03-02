package com.youtolife.simple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {

	Sprite sprite;
	Texture texture;

	public Player(float AspektRatio) {
		texture = new Texture(Gdx.files.internal("data/player.png"));
		TextureRegion region = new TextureRegion(texture, 0, 0, 50, 64);
		sprite = new Sprite(region);
		sprite.setSize(0.1f, 0.2f);
		sprite.setPosition(0, -AspektRatio / 2);
	}

	public void dispose() {
		texture.dispose();
	}

	public void update(float w, float h) {
		Input input = Gdx.input;
		int direction = 0;
		if (input.isKeyPressed(Input.Keys.RIGHT))
			direction = 1;
		if (input.isKeyPressed(Input.Keys.LEFT))
			direction = -1;
		sprite.setX(sprite.getX() + direction * Gdx.graphics.getDeltaTime()
				- input.getAccelerometerX() * Gdx.graphics.getDeltaTime());
		if (sprite.getX() < -0.5f)
			sprite.setX(-0.5f);
		if (sprite.getX() > 0.45f)
			sprite.setX(0.45f);
	}

	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}

}
