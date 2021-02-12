package com.dontsutsu.nazopuyo;

import java.util.List;

import com.dontsutsu.puyopuyo.Field;
import com.dontsutsu.puyopuyo.Tsumo;

/**
 * 「ぷよすべて消すべし」
 * @author akamaternity
 */
public class NazoAllClearCalc extends Calc {

	/**
	 * コンストラクタ
	 * @param maxCorrectSize 計算する正答数の上限
	 */
	public NazoAllClearCalc(int maxCorrectSize) {
		super(maxCorrectSize);
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
		return field.isAllClear();
	}

}
