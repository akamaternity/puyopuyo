package com.akamaternity.nazopuyo;

import java.util.List;

import com.akamaternity.puyopuyo.Field;
import com.akamaternity.puyopuyo.Tsumo;

/**
 * 「XX連鎖すべし」
 * @author akamaternity
 */
public class NazoChainCalc extends Calc {
	private int chain;

	/**
	 * コンストラクタ
	 * @param chain 連鎖数
	 */
	public NazoChainCalc(int chain) {
		super();
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
		// TODO
		return false;
	}

}