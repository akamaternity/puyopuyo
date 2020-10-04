package com.akamaternity.nazopuyo;

import java.util.List;

import com.akamaternity.puyopuyo.Field;
import com.akamaternity.puyopuyo.Tsumo;

/**
 * 「XX色同時に消すべし」
 * @author akamaternity
 */
public class NazoMultiColorCalc extends Calc {
	private int colorNum;

	public NazoMultiColorCalc(int colorNum) {
		super();
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
