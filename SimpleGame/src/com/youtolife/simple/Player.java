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
	float ShotTime = 0.1f;
	int bullet_cout = 1;

	public Player(float AspektRatio) {
		texture = new Texture(Gdx.files.internal("data/player.png"));
		TextureRegion region = new TextureRegion(texture, 0, 0, 128, 128);
		sprite = new Sprite(region);
		sprite.setSize(0.1f, 0.2f);
		sprite.setPosition(0, -AspektRatio / 2);
	}

	public void dispose() {
		texture.dispose();
	}

	public void update(GamePlayState game, float w, float h) {
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
		if (sprite.getX() > 0.40f)
			sprite.setX(0.40f);

		ShotTime -= Gdx.graphics.getDeltaTime();
		if (ShotTime <= 0) {
			ShotTime += 0.2f;
			if (bullet_cout == 1)
				game.bullets.add(new Bullet(0, sprite.getX()
						+ sprite.getWidth() / 2 - 0.01f, sprite.getY()
						+ sprite.getHeight()));
			else {
				float minAngle = -bullet_cout * 2.5f;
				for (int i = 0; i < bullet_cout; i++)
					game.bullets.add(new Bullet(minAngle + i * 5, sprite
							.getX() + sprite.getWidth() / 2 - 0.01f, sprite
							.getY() + sprite.getHeight()));
			}
		}
	}

	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}

}
