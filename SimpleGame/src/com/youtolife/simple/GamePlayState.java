package com.youtolife.simple;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GamePlayState extends GameState {

	private Texture texture;
	private Texture enem;
	private Sprite sprite;
	private Player player;
	private Vector<Enemy> enemies = new Vector<Enemy>();
	private Vector<Bonus> bonuses = new Vector<Bonus>();
	private Vector<Bang> bangs = new Vector<Bang>();
	public Vector<Bullet> bullets = new Vector<Bullet>();
	private EnemySpawner spawner;
	private BitmapNumeric font;

	float h, w;

	float Score = 0;
	Random r = new Random();

	public GamePlayState(int StateId) {
		super(StateId);
	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
		player.render(batch);
		synchronized (enemies) {
			for (Enemy enemy : enemies)
				enemy.render(batch);
		}
		for (Bullet b : bullets)
			b.render(batch);
		for (Bonus b : bonuses)
			b.render(batch);
		for (Bang b : bangs)
			b.render(batch);
		font.drawString(batch, String.valueOf((int)Score), -0.5f,h/w/2f-font.getSize());
	}

	@Override
	public void update(MySimpleGame game) {

		spawner.update(w, h);

		Input input = Gdx.input;
		if (input.isKeyPressed(Input.Keys.ESCAPE)
				|| input.isKeyPressed(Input.Keys.BACK))
			game.enterState(MySimpleGame.MAINMENUSTATE);

		Iterator<Bonus> bonIt = bonuses.iterator();
		while (bonIt.hasNext()) {
			Bonus b = bonIt.next();
			if (b.update(player, h, w)) {
				player.upgrade();
				b.dispose();
				bonIt.remove();
			}
		}

		player.update(this, h, w);
		Iterator<Enemy> enemIt = enemies.iterator();
		synchronized (enemies) {
			while (enemIt.hasNext()) {
				Enemy enemy = enemIt.next();
				if (enemy.update(player)) 
					game.enterState(MySimpleGame.MAINMENUSTATE);
				if (enemy.sprite.getY() < -h / w || enemy.hp <= 0) {
					if (enemy.hp <= 0) {
						Score++;
						int b_chance = r.nextInt(100);
						if (b_chance >= 90)
							bonuses.add(new Bonus(enemy.sprite.getX(),
									enemy.sprite.getY() - 0.06f));
					}
					enemIt.remove();
				}
			}
		}
		Iterator<Bullet> bulIt = bullets.iterator();
		while (bulIt.hasNext()) {
			Bullet b = bulIt.next();
			b.update(h, w);
			if (b.sprite.getY() > h / w) {
				b.dispose();
				bulIt.remove();
			} else {
				synchronized (enemies) {
					enemIt = enemies.iterator();
					while (enemIt.hasNext()) {
						Enemy enemy = enemIt.next();
						if (enemy.sprite.getBoundingRectangle().overlaps(
								b.sprite.getBoundingRectangle())) {
							enemy.hp -= player.damage;
							bangs.add(new Bang(b.sprite.getX(), b.sprite.getY()));
							b.dispose();
							bulIt.remove();
							if (bulIt.hasNext()) {
								b = bulIt.next();
								b.update(h, w);
							} else {
								return;
							}
						}
					}
				}
			}

		}
		Iterator<Bang> bangIt = bangs.iterator();
		while (bangIt.hasNext()) {
			Bang b = bangIt.next();
			if (b.update(w, h))
				bangIt.remove();
		}
	}

	@Override
	public void init() {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		font = new BitmapNumeric(0.1f,new Color(1,0,0,1));
		texture = new Texture(Gdx.files.internal("data/back.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		enem = new Texture(Gdx.files.internal("data/player.png"));

		sprite = new Sprite(texture);
		sprite.setSize(1f, 1f * h / w);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setPosition(-0.5f, -0.5f * h / w);
		player = new Player(h / w);
	}

	@Override
	public void dispose() {
		enem.dispose();
		texture.dispose();
		player.dispose();
		font.dispose();
	}

	@Override
	public void enter() {
		enemies = new Vector<Enemy>();
		bangs = new Vector<Bang>();
		bonuses = new Vector<Bonus>();
		spawner = new EnemySpawner(enemies, enem);
		player.bullet_cout = 1;
		player.damage = 25;
		player.sprite.setX(0);
		player.color = new Color(1,0,0,1);
		bullets = new Vector<Bullet>();
		Score = 0;

	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

}
