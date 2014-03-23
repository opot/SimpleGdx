package com.youtolife.simple;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {

	Random r = new Random();

	Sprite sprite;
	Texture texture;
	float ShotTime = 1f;
	Color color = new Color(1, 1, 1, 1);

	int level_points = 0;
	float PrefferedTime = 0.3f;
	int bullet_cout = 1;
	int damage = 25;
	float height = 0.04f;
	float width = 0.02f;

	public void upgrade() {
		level_points++;
		int perks = level_points;
		damage = 25;
		bullet_cout = 1;
		PrefferedTime = 0.3f;
		while (perks != 0) {
			int k = r.nextInt(3);
			if (k == 0)
				damage += 25;
			else
			if (k == 1)
				bullet_cout++;
			else
			if (k == 2)
				PrefferedTime *= 0.8f;
			
			perks--;
		}
		height = damage / 25 *0.02f + 0.02f;
		width = damage / 25 * 0.01f+ 0.01f;
		color = new Color(r.nextFloat(),r.nextFloat(), r.nextFloat(), 1f);
	}

	public Player(float AspektRatio) {
		texture = new Texture(Gdx.files.internal("data/player.png"));
		TextureRegion region = new TextureRegion(texture, 0, 0, 128, 128);
		sprite = new Sprite(region);
		sprite.setSize(0.1f, 0.2f);
		sprite.setPosition(0, -AspektRatio / 2);

		color = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1f);
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
			ShotTime += PrefferedTime;
			if (bullet_cout == 1) {
				game.bullets.add(new Bullet(0, sprite.getX()
						+ sprite.getWidth() / 2 - width/2, sprite.getY()
						+ sprite.getHeight(), width, height));
				game.bullets.get(game.bullets.size() - 1).sprite
						.setColor(color);
			} else {
				float minAngle = -bullet_cout * 2.5f;
				for (int i = 0; i < bullet_cout; i++) {
					game.bullets.add(new Bullet(minAngle + i * 5, sprite.getX()
							+ sprite.getWidth() / 2 - width/2, sprite.getY()
							+ sprite.getHeight(), width, height));
					game.bullets.get(game.bullets.size() - 1).sprite
							.setColor(color);
				}
			}
		}
	}

	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}

}
