package com.akamaternity;

import java.util.ArrayList;
import java.util.List;

import com.akamaternity.nazopuyo.Calc;
import com.akamaternity.puyopuyo.Field;
import com.akamaternity.puyopuyo.Tsumo;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NazopuyoCalculator {
	/**
	 * エントリーポイント<br>
 	 * args[0]：なぞぷよのお題を表す文字列
	 * args[1]：なぞぷよの条件を表す文字列
	 * args[2]：計算する正答数の上限
	 * args[3]：フィールドを表す文字列
	 * args[4]：ツモを表す文字列
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 5) {
			System.out.println("args != 5");
			System.exit(1);
		}

		String out = null;

		try {
			Calc calc = Calc.getInstance(args[0], args[1], Integer.parseInt(args[2]));
			Field field = new Field(args[3]);
			List<Tsumo> tsumoList = Tsumo.getTsumoList(args[4]);

			List<List<Tsumo>> tsumosList = calc.calc(field, tsumoList);
			List<List<CalculatorResponseBean>> beansList = convert(tsumosList);

			ObjectMapper mapper = new ObjectMapper();
			out = mapper.writeValueAsString(beansList);

		} catch (Throwable e) {
			e.printStackTrace();
			System.err.println("error");
			System.exit(1);
		}

		System.out.println(out);
		System.exit(0);
	}

	/**
	 * calcの結果を変換
	 * @param tsumosList
	 * @return
	 */
	private static List<List<CalculatorResponseBean>> convert(List<List<Tsumo>> tsumosList) {
		List<List<CalculatorResponseBean>> beansList = new ArrayList<>();

		for (int i = 0; i < tsumosList.size(); i++) {
			List<Tsumo> tsumos = tsumosList.get(i);
			List<CalculatorResponseBean> beans = new ArrayList<>();

			for (int j = 0; j < tsumos.size(); j++) {
				Tsumo tsumo = tsumos.get(j);
				CalculatorResponseBean bean = CalculatorResponseBean.createBeanFromTsumo(tsumo);
				beans.add(bean);
			}
			beansList.add(beans);
		}
		return beansList;
	}
}
