package com.youtolife.simple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Back {

	Sprite sprite;
	Texture texture;

	float rgb = 0;
	float delta_rgp = 0;

	float height = Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

	public Back() {
		texture = new Texture(Gdx.files.internal("data/backtest.png"));
		TextureRegion region = new TextureRegion(texture, 512, 1024);
		sprite = new Sprite(region);
		sprite.setSize(2f, height * 2);
		sprite.setPosition(-1f, -height);
	}

	public void update() {
		sprite.setY(sprite.getY() - Gdx.graphics.getDeltaTime() / 6);
		if (sprite.getY() <= -height * 3)
			sprite.setY(-height);

		if (delta_rgp > 0) {
			delta_rgp -= Gdx.graphics.getDeltaTime();
			rgb += Gdx.graphics.getDeltaTime();
			if (rgb > Math.PI * 2)
				rgb -= Math.PI * 2;
		}

		float con = 2f / 4f;
		float r = (float) Math.sin(rgb) * con;
		float g = (float) -Math.atan(Math.sin(rgb))*con;
		float b = (float) Math.cos(rgb / 4)*con;
		sprite.setColor(new Color(r, g, b, 1f));
	}

	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
		sprite.setY(sprite.getY() + height * 2);
		sprite.draw(batch);
		sprite.setY(sprite.getY() - height * 2);
	}

	public void dispose() {
		texture.dispose();
	}

}
