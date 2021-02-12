package com.dontsutsu.nazopuyo;

import java.util.List;

import com.dontsutsu.puyopuyo.Field;
import com.dontsutsu.puyopuyo.Tsumo;

/**
 * 「XX連鎖するべし」
 * @author akamaternity
 */
public class NazoChainCalc extends Calc {
	private int chain;

	/**
	 * コンストラクタ
	 * @param chain 連鎖数
	 * @param maxCorrectSize 計算する正答数の上限
	 */
	public NazoChainCalc(int chain, int maxCorrectSize) {
		super(maxCorrectSize);

		if (!(chain >= 1 && chain <= 19)) {
			throw new IllegalArgumentException("argument is not correct.");
		}

		this.chain = chain;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean correctCheck(int[] result, Field field) {
		return result[1] >= chain;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean impossibleCheck(Field field, List<Tsumo> tsumoList, int index) {
		return false;
	}

}