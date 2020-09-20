package com.akamaternity.nazopuyo;

import java.util.List;

import com.akamaternity.puyopuyo.Field;
import com.akamaternity.puyopuyo.Tsumo;

/**
 * 「XXぷよ全て消すべし」
 * @author akamaternity
 */
public class NazoColorAllClearCalc extends Calc {
	private char color;

	/**
	 * コンストラクタ
	 * @param color 色
	 */
	public NazoColorAllClearCalc(char color) {
		super();
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