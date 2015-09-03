package com.youtolife.simple;

import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class MainMenuState extends GameState {

	private Texture texture;
	private Sprite sprite;
	//private Texture gui_texture;
	//private TextureRegion hSc, sGm;
	//private Sprite highScore, startGame;
	//private BitmapNumeric font;

	float h, w;
	float scale = 1f, scaleSpeed = 0.05f;

	float timeToStart = 1f;
	
	int hScore = 0,lScore = 0;

	public MainMenuState(int StateId) {
		super(StateId);
	}

	@Override
	public void draw(SpriteBatch batch) {
			sprite.draw(batch);
		/*highScore.draw(batch);
		startGame.draw(batch);
		font.drawString(batch, String.valueOf(hScore), -0.2f, h / w / 2 - 0.12f
				* hSc.getRegionHeight() / hSc.getRegionWidth());
		font.drawString(batch, String.valueOf(lScore), -0.2f, h / w / 2 - 0.29f
				* hSc.getRegionHeight() / hSc.getRegionWidth());*/
	}

	@Override
	public void update(MySimpleGame game) {
		Input input = Gdx.input;
		if (input.isTouched() || input.isKeyPressed(Input.Keys.SPACE))
			timeToStart-=Gdx.graphics.getDeltaTime();
		else
			timeToStart = 1f;
		if (timeToStart<=0)
			game.enterState(MySimpleGame.GAMEPLAYSTATE);
		scale += scaleSpeed * Gdx.graphics.getDeltaTime();
		if (scale > 1.35f || scale <= 1f)
			scaleSpeed *= -1;
System.out.println(timeToStart);
		sprite.setScale(scale);
	}

	@Override
	public void init() {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		texture = new Texture(Gdx.files.internal("data/menu.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//gui_texture = new Texture(Gdx.files.internal("data/gui.png"));

		//sGm = new TextureRegion(gui_texture, 290, 160);
		//hSc = new TextureRegion(gui_texture, 289, 0, 218,97);

		/*highScore = new Sprite(hSc);
		highScore.setSize(0.3f,
				0.3f * hSc.getRegionHeight() / hSc.getRegionWidth());
		highScore.setPosition(-0.5f, h / w / 2 - 0.3f * hSc.getRegionHeight()
				/ hSc.getRegionWidth());
		font = new BitmapNumeric(0.1f * hSc.getRegionHeight()
				/ hSc.getRegionWidth(), new Color(1f, 0, 0, 1f));

		startGame = new Sprite(sGm);
		startGame.setSize(0.8f,
				h / w * sGm.getRegionHeight() / sGm.getRegionWidth());
		startGame.setPosition(-0.4f,
				-h / w * sGm.getRegionHeight() / sGm.getRegionWidth() / 2);
		*/
		sprite = new Sprite(texture);
		sprite.setSize(1f, 1f * h / w);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setPosition(-0.5f, -0.5f * h / w);
	}

	@Override
	public void dispose() {
		texture.dispose();
		//gui_texture.dispose();
		//font.dispose();
	}

	@Override
	public void enter() {
		/*try {
			FileHandle file = Gdx.files.local("data/saves/score.dat");
			Scanner scan = new Scanner(file.read());
			hScore = scan.nextInt();
			lScore = scan.nextInt();
			scan.close();
		} catch (GdxRuntimeException e) {
			e.printStackTrace();
		}*/
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
