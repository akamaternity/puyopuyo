package com.akamaternity.nazopuyo;

import java.util.List;

import com.akamaternity.puyopuyo.Field;
import com.akamaternity.puyopuyo.Tsumo;

/**
 * 「XX匹同時に消すべし」
 * @author akamaternity
 */
public class NazoEraseSizeCalc extends Calc {
	private int eraseSize;

	/**
	 * コンストラクタ
	 * @param eraseSize 同時消し数
	 * @param maxCorrectSize 計算する正答数の上限
	 */
	public NazoEraseSizeCalc(int eraseSize, int maxCorrectSize) {
		super(maxCorrectSize);

		if (!(eraseSize >= 4 && eraseSize <= 72)) {
			throw new IllegalArgumentException("argument is not correct.");
		}

		this.eraseSize = eraseSize;
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
		return result[3] >= eraseSize;
	}

}
