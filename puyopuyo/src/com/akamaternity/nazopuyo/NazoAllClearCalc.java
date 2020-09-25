package com.akamaternity.nazopuyo;

import java.util.List;

import com.akamaternity.puyopuyo.Field;
import com.akamaternity.puyopuyo.Tsumo;

public class NazoAllClearCalc extends Calc {

	/**
	 * コンストラクタ
	 */
	public NazoAllClearCalc() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean impossibleCheck(Field field, List<Tsumo> tsumoList, int index) {
		// TODO 自動生成されたメソッド・スタブ
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
