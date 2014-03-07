package com.youtolife.simple;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
	public Vector<Bullet> bullets = new Vector<Bullet>();
	private EnemyFactory fact;
	private BitmapFont font;

	float h, w;
	float bonus_time = 10;
	
	float Score = 0;

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
		font.draw(batch, String.valueOf(Score), -0.4f, 0);
	}

	@Override
	public void update(MySimpleGame game) {
		
		Input input = Gdx.input;
		if(input.isKeyPressed(Input.Keys.ESCAPE)||input.isKeyPressed(Input.Keys.BACK))
			game.enterState(MySimpleGame.MAINMENUSTATE);
		
		bonus_time-=Gdx.graphics.getDeltaTime();
		if(bonus_time<0){
			bonus_time+=new Random().nextInt(20);
			bonuses.add(new Bonus());
		}
		Iterator<Bonus> bonIt = bonuses.iterator();
		while(bonIt.hasNext()){
			Bonus b = bonIt.next();
			if(b.update(player, h, w)){
				player.bullet_cout++;
				bonIt.remove();
			}
		}
		
		player.update(this, h, w);
		Iterator<Enemy> enemIt = enemies.iterator();
		synchronized (enemies) {
			while (enemIt.hasNext()) {
				Enemy enemy = enemIt.next();
				if (enemy.update(player)) {
					fact.Kill();
					game.enterState(MySimpleGame.MAINMENUSTATE);
				}
				if (enemy.sprite.getY() < -h / w||enemy.hp<=0)
					enemIt.remove();
			}
		}
		Iterator<Bullet> bulIt = bullets.iterator();
		while (bulIt.hasNext()) {
			Bullet b = bulIt.next();
			b.update(h, w);
			if (b.sprite.getY() > h / w * 2)
				bulIt.remove();
			else {
				synchronized (enemies) {
					enemIt = enemies.iterator();
					while (enemIt.hasNext()) {
						Enemy enemy = enemIt.next();
						if (enemy.sprite.getBoundingRectangle().overlaps(
								b.sprite.getBoundingRectangle())) {
							enemy.hp-=b.damage;
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
		Score += Gdx.graphics.getDeltaTime();
	}

	@Override
	public void init() {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		font = new BitmapFont();
		font.setUseIntegerPositions(true);
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
		if (fact != null)
			fact.Kill();
		enem.dispose();
		texture.dispose();
		player.dispose();
		font.dispose();
	}

	@Override
	public void enter() {
		enemies = new Vector<Enemy>();
		bonuses = new Vector<Bonus>();
		fact = new EnemyFactory(this, enemies, enem);
		player.bullet_cout = 1;
		bullets = new Vector<Bullet>();
		Score = 0;
		new Thread(fact).start();

	}

	@Override
	public void pause() {
		fact.Kill();
	}

	@Override
	public void resume() {
		fact = new EnemyFactory(this, enemies, enem);
		new Thread(fact).start();
	}

}
