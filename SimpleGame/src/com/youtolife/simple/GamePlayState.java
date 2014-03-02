package com.youtolife.simple;

import java.util.Iterator;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
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
	private EnemyFactory fact;
	private BitmapFont font;
	
	float h,w;
	
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
		font.draw(batch, String.valueOf(Score), 0, 0);
	}

	@Override
	public void update(MySimpleGame game) {
		player.update(h, w);
		synchronized (enemies) {
			Iterator<Enemy> i = enemies.iterator();
			while (i.hasNext()) {
				Enemy enemy = i.next();
				if (enemy.update(player)){
					fact.Kill();
					game.enterState(MySimpleGame.MAINMENUSTATE);
				}
				if (enemy.sprite.getY() < -h / w)
					i.remove();
			}
		}
		Score+=Gdx.graphics.getDeltaTime();
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
		if(fact != null)
			fact.Kill();
		enem.dispose();
		texture.dispose();
		player.dispose();
		font.dispose();
	}

	@Override
	public void enter() {
		enemies = new Vector<Enemy>();
		fact = new EnemyFactory(enemies, enem);
		new Thread(fact).start();
		
	}

}
