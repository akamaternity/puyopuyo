package com.akamaternity.nazopuyo;

import java.util.List;

import com.akamaternity.puyopuyo.Field;
import com.akamaternity.puyopuyo.Puyo;
import com.akamaternity.puyopuyo.Tsumo;

/**
 * 「XXぷよすべて消すべし」
 * @author akamaternity
 */
public class NazoColorAllClearCalc extends Calc {
	private char color;

	/**
	 * コンストラクタ
	 * @param color 色
	 * @param maxCorrectSize 計算する正答数の上限
	 */
	public NazoColorAllClearCalc(char color, int maxCorrectSize) {
		super(maxCorrectSize);

		if (!Puyo.checkPuyoChar(color)) {
			throw new IllegalArgumentException("argument is not correct.");
		}

		this.color = color;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean impossibleCheck(Field field, List<Tsumo> tsumoList, int index) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean correctCheck(int[] result, Field field) {
		return !field.contains(color);
	}
}