package com.youtolife.simple;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GamePlayState extends GameState {

	private Texture enem;
	private Player player;
	private Vector<Enemy> enemies = new Vector<Enemy>();
	private Vector<Bonus> bonuses = new Vector<Bonus>();
	private Vector<Bang> bangs = new Vector<Bang>();
	public Vector<Bullet> bullets = new Vector<Bullet>();
	private EnemySpawner spawner;
	private BitmapNumeric font;
	private Back back;
	float h, w;

	float Score = 0;
	Random r = new Random();

	public GamePlayState(int StateId) {
		super(StateId);
	}

	@Override
	public void draw(SpriteBatch batch) {
		back.draw(batch);
		player.render(batch);
		for (Enemy enemy : enemies)
			enemy.render(batch);
		for (Bullet b : bullets)
			b.render(batch);
		for (Bonus b : bonuses)
			b.render(batch);
		for (Bang b : bangs)
			b.render(batch);
		font.drawString(batch, String.valueOf((int) Score), -0.5f, h / w / 2f
				- font.getSize());
	}

	@Override
	public void update(MySimpleGame game) {

		spawner.update(this, w, h);
		back.update();
		Score += Gdx.graphics.getDeltaTime();

		Input input = Gdx.input;
		if (input.isKeyPressed(Input.Keys.ESCAPE)
				|| input.isKeyPressed(Input.Keys.BACK))
			game.enterState(MySimpleGame.MAINMENUSTATE);

		if (input.isKeyPressed(Input.Keys.UP) || input.isTouched())
			for(Enemy enemy:enemies)
				enemy.sprite.setY(enemy.sprite.getY()-Gdx.graphics.getDeltaTime());


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
		while (enemIt.hasNext()) {
			Enemy enemy = enemIt.next();
			if (enemy.update(player))
				game.enterState(MySimpleGame.MAINMENUSTATE);
			if (enemy.sprite.getY() < -h / w || enemy.hp <= 0) {
				if (enemy.hp <= 0) {
					Score++;
					int b_chance = r.nextInt(100);
					if (b_chance >= 90)
						bonuses.add(new Bonus(enemy.sprite.getX(), enemy.sprite
								.getY() - 0.06f));
				}
				enemIt.remove();
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

		font = new BitmapNumeric(0.06f, new Color(1, 0, 0, 1));

		back = new Back();

		enem = new Texture(Gdx.files.internal("data/player.png"));

		player = new Player(h / w);
	}

	@Override
	public void dispose() {
		enem.dispose();
		back.dispose();
		player.dispose();
		font.dispose();
	}

	@Override
	public void enter() {
		enemies = new Vector<Enemy>();
		bangs = new Vector<Bang>();
		bonuses = new Vector<Bonus>();
		spawner = new EnemySpawner(enemies, enem);
		player = new Player(h / w);
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
