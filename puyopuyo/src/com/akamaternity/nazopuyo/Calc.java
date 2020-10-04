package com.akamaternity.nazopuyo;

import java.util.ArrayList;
import java.util.List;

import com.akamaternity.puyopuyo.CannotPutTsumoException;
import com.akamaternity.puyopuyo.Field;
import com.akamaternity.puyopuyo.Tsumo;
import com.akamaternity.puyopuyo.TsumoChildPositionEnum;

/**
 * なぞぷよ計算クラス
 * @author akamaternity
 */
public abstract class Calc {
	/** 正答数の最大、正答数がこの値に達した場合、計算処理を止める */
	protected static final int MAX_CORRECT_SIZE = 20;

	/** 正答リスト */
	private List<List<Tsumo>> correctList;

	/**
	 * コンストラクタ
	 */
	public Calc() {
		correctList = new ArrayList<>();
	}

	/**
	 * 正答リストを取得します。
	 * @return 正答リスト
	 */
	public List<List<Tsumo>> getCorrectList() {
		return correctList;
	}

	/**
	 * 正答を検索します。
	 * @param field フィールド
	 * @param tsumoList ツモのリスト
	 * @return 正答リスト
	 * @throws Exception
	 */
	public List<List<Tsumo>> calc(Field field, List<Tsumo> tsumoList) {
		calc(field, tsumoList, 0);

		return this.correctList;
	}

	/**
	 * 正答を再帰的に検索します。
	 * @param field フィールド
	 * @param tsumoList ツモリスト
	 * @param index ツモリストのインデックス
	 * @throws Exception
	 */
	private void calc(Field field, List<Tsumo> tsumoList, int index) {
		if (correctListMaxCheck()) {
			return;
		}

		if (tsumoList.size() <= index) {
			return;
		}

		// 軸ぷよループ
		for (TsumoChildPositionEnum childPos : TsumoChildPositionEnum.values()) {
			// ゾロの場合は、軸ぷよ子ぷよの入れ替え不要なので、子ぷよ位置右、子ぷよ位置上の計算を省く
			if (tsumoList.get(index).isZoro() && (childPos == TsumoChildPositionEnum.CHILD_RIGHT || childPos == TsumoChildPositionEnum.CHILD_TOP)) {
				continue;
			}

			// X座標ループ
			for (int x = 0; x < Field.X_SIZE; x++) {
				// 軸ぷよx座標 = 0 かつ 子ぷよ位置左はNGなのでスキップ
				if (x == 0 && childPos == TsumoChildPositionEnum.CHILD_LEFT) {
					continue;
				}
				// 軸ぷよx座標 = 5 かつ 子ぷよ位置右はNGなのでスキップ
				if (x == Field.X_SIZE - 1 && childPos == TsumoChildPositionEnum.CHILD_RIGHT) {

					continue;
				}
				try {
					// コピー
					Field fieldCopy = field.getFieldClone();

					List<Tsumo> tsumoListCopy = new ArrayList<>();
					for (Tsumo t : tsumoList) {
						tsumoListCopy.add(t.getTsumoClone());
					}
					Tsumo tsumo = tsumoListCopy.get(index);

					// ツモの座標設定
					tsumo.setTsumoPosition(x, childPos);

					// ツモを落とす
					int[] putResult = fieldCopy.put(tsumo);

					// 正解かチェック
					if (correctCheck(putResult, fieldCopy)) {
						correctList.add(tsumoListCopy);
					}

					// 到達不可能チェック
					if (impossibleCheck(fieldCopy, tsumoListCopy, index)) {
						return;
					}

					calc(fieldCopy, tsumoListCopy, index + 1);
				} catch (CannotPutTsumoException e) {
					// 設置できないケース
					continue;
				}
			}
		}
	}

	/**
	 * 正答数が最大値に達しているかをチェックします。
	 * @return true：最大値に達している / false：最大値に達していない
	 */
	private boolean correctListMaxCheck() {
		return this.correctList.size() >= MAX_CORRECT_SIZE;
	}

	/**
	 * 正答に到達不能であるかをチェックします。
	 * @param field フィールド
	 * @param tsumoList ツモリスト
	 * @param index ツモリストのインデックス
	 * @return true：到達不能 / false：到達可能
	 */
	protected abstract boolean impossibleCheck(Field field, List<Tsumo> tsumoList, int index);

	/**
	 * 正答であるかチェックします。
	 * @param result [0]得点, [1]連鎖数, [2]最大同時消し色数, [3]最大同時消し数
	 * @param field フィールド
	 * @return true：正答 / false：正答でない
	 */
	protected abstract boolean correctCheck(int[] result, Field field);

	/**
	 * インスタンスを取得します。
	 * @param type
	 * @param require
	 * @return Calcインスタンス
	 */
	public static Calc getInstance(String type, String require) {
		if ("1".equals(type)) {
			int chain = Integer.parseInt(require);
			return new NazoChainCalc(chain);
		} else if ("2".equals(type)) {
			return new NazoAllClearCalc();
		} else if ("3".equals(type)) {
			char color = require.toCharArray()[0];
			return new NazoColorAllClearCalc(color);
		} else if ("4".equals(type)) {
			int colorNum = Integer.parseInt(require);
			return new NazoMultiColorCalc(colorNum);
		} else {
			return null;
		}
	}

}