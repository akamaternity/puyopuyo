package com.dontsutsu.nazopuyo;

import java.util.List;

import com.dontsutsu.puyopuyo.Field;
import com.dontsutsu.puyopuyo.Tsumo;

/**
 * 「XX色同時に消すべし」
 * @author akamaternity
 */
public class NazoMultiColorCalc extends Calc {
	private int colorNum;

	/**
	 * コンストラクタ
	 * @param colorNum 色数
	 * @param maxCorrectSize 計算する正答数の上限
	 */
	public NazoMultiColorCalc(int colorNum, int maxCorrectSize) {
		super(maxCorrectSize);

		if (!(colorNum >= 1 && colorNum <= 5)) {
			throw new IllegalArgumentException("argument is not correct.");
		}

		this.colorNum = colorNum;
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
		return result[2] >= colorNum;
	}

}
