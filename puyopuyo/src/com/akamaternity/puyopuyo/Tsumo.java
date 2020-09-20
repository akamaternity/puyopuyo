package com.akamaternity.puyopuyo;

import java.util.ArrayList;
import java.util.List;

/**
 * ツモクラス
 * @author akamaternity
 */
public class Tsumo {
	public static final int SIZE = 2;
	public static final int AXIS_PUYO_INDEX = 0;
	public static final int CHILD_PUYO_INDEX = 1;

	public static final int DEFAULT_AXIS_X = 2;
	public static final TsumoChildPositionEnum DEFAULT_CHILD_POSITION = TsumoChildPositionEnum.CHILD_TOP;

	private TsumoPuyo axisPuyo;
	private TsumoPuyo childPuyo;
	private int axisX;

	private TsumoChildPositionEnum childPosition;

	/**
	 * コンストラクタ
	 * @param tsumoStr ツモ（文字列）
	 */
	public Tsumo(String tsumoStr) {
		if (tsumoStr.length() != SIZE) {
			throw new IllegalArgumentException();
		}

		axisPuyo = new TsumoPuyo(true, tsumoStr.charAt(AXIS_PUYO_INDEX));
		childPuyo = new TsumoPuyo(false, tsumoStr.charAt(CHILD_PUYO_INDEX));

		axisX = DEFAULT_AXIS_X;
		childPosition = DEFAULT_CHILD_POSITION;
	}

	/**
	 * コンストラクタ
	 * @param axisColor 軸ぷよの色
	 * @param childColor 子ぷよの色
	 */
	public Tsumo(char axisColor, char childColor) {
		axisPuyo = new TsumoPuyo(true, axisColor);
		childPuyo = new TsumoPuyo(false, childColor);

		axisX = DEFAULT_AXIS_X;
		childPosition = DEFAULT_CHILD_POSITION;
	}

	/**
	 * コンストラクタ
	 * @param axisColor 軸ぷよの色
	 * @param childColor 子ぷよの色
	 * @param axisX 軸ぷよのx座標
	 * @param childPosition 子ぷよの軸ぷよに対する相対位置
	 */
	public Tsumo(char axisColor, char childColor, int axisX, TsumoChildPositionEnum childPosition) {
		if (!check(axisX, childPosition)) {
			throw new IllegalArgumentException();
		}

		axisPuyo = new TsumoPuyo(true, axisColor);
		childPuyo = new TsumoPuyo(false, childColor);
		this.axisX = axisX;
		this.childPosition = childPosition;
	}

	/**
	 * ツモの文字列を取得します。
	 * @return ツモ（文字列）
	 */
	public String getTsumoString() {
		return String.valueOf(axisPuyo.getColor()) + String.valueOf(childPuyo.getColor());
	}

	/**
	 * インスタンスのコピーを取得します。
	 * @return ツモ（コピー）
	 */
	public Tsumo getTsumoClone() {
		return new Tsumo(axisPuyo.getColor(), childPuyo.getColor(), axisX, childPosition);
	}

	/**
	 * ツモの座標を設定します。
	 *
	 * @param x 軸ぷよのx座標
	 * @param axis 軸ぷよの位置
	 */
	public void setTsumoPosition(int axisX, TsumoChildPositionEnum childPosition) {
		if (!check(axisX, childPosition)) {
			throw new IllegalArgumentException();
		}

		this.axisX = axisX;
		this.childPosition = childPosition;
	}

	/**
	 * ゾロであるか取得します。
	 *
	 * @return true: ゾロ、false: ゾロでない
	 */
	public boolean isZoro() {
		if (axisPuyo.isSameColor(childPuyo)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ツモの情報をコンソールに出力します。
	 */
	public void print() {

	}

	/**
	 * 文字列からツモのリストを取得します。
	 *
	 * @param tsumoList ツモリスト（文字列）
	 * @return ツモ（配列）
	 */
	public static List<Tsumo> getTsumoList(String tsumoList) {
		if (!checkTsumoList(tsumoList)) {
			throw new IllegalArgumentException("argument is not correct.");
		}

		List<Tsumo> list = new ArrayList<>();

		for (int i = 0; i < tsumoList.length(); i += 2) {
			// 無しが出てきたら終わり
			if (tsumoList.charAt(i) == Puyo.NONE) {
				break;
			}

			String str = tsumoList.substring(i, i + 2);
			Tsumo t = new Tsumo(str);
			list.add(t);
		}

		return list;
	}

	/**
	 * ツモの文字列のフォーマットをチェックします。
	 *
	 * @param tsumoList ツモリスト（文字列）
	 * @return true: OK、false: NG
	 */
	private static boolean checkTsumoList(String tsumoList) {
		int length = tsumoList.length();

		// 長さチェック
		if (length < 2) {
			return false;
		}

		// 奇数チェック
		if (length % 2 == 1) {
			return false;
		}

		// 最初が空チェック
		if (tsumoList.charAt(0) == Puyo.NONE || tsumoList.charAt(1) == Puyo.NONE) {
			return false;
		}

		// ペアでどっちか空チェック
		for (int i = 0; i < length; i += 2) {
			if ((tsumoList.charAt(i) != Puyo.NONE && tsumoList.charAt(i+1) == Puyo.NONE) ||
					tsumoList.charAt(i) == Puyo.NONE && tsumoList.charAt(i+1) != Puyo.NONE) {
				return false;
			}
		}

		// 空のあとに空じゃないのあるかチェック
		boolean flg = false;
		for (int i = 0; i < length; i++) {
			if (flg && tsumoList.charAt(i) != Puyo.NONE) {
				return false;
			}

			if (!flg && tsumoList.charAt(i) == Puyo.NONE) {
				flg = true;
			}
		}

		return true;
	}

	/**
	 * 軸ぷよのx座標と子ぷよの相対位置に整合性があるかチェックします。
	 * @param axisX 軸ぷよのx座標
	 * @param childPosition 子ぷよの軸ぷよに対する相対位置
	 * @return true：OK / false：NG
	 */
	private boolean check(int axisX, TsumoChildPositionEnum childPosition) {
		if (!(axisX >= 0 && axisX < Field.X_SIZE)) {
			return false;
		}

		if (axisX == 0 && childPosition == TsumoChildPositionEnum.CHILD_LEFT) {
			return false;
		}

		if (axisX == Field.X_SIZE - 1 && childPosition == TsumoChildPositionEnum.CHILD_RIGHT) {
			return false;
		}

		return true;
	}

	/**
	 * 軸ぷよを取得します。
	 * @return 軸ぷよ
	 */
	public TsumoPuyo getAxisPuyo() {
		return axisPuyo;
	}

	/**
	 * 子ぷよを取得します。
	 * @return 子ぷよ
	 */
	public TsumoPuyo getChildPuyo() {
		return childPuyo;
	}

	/**
	 * 軸ぷよのx座標を取得します。
	 * @return 軸ぷよのx座標
	 */
	public int getAxisX() {
		return axisX;
	}

	public int getChildX() {
		return axisX + childPosition.getChildRelativeX();
	}

	/**
	 * 子ぷよの軸ぷよに対する相対位置を取得します。
	 * @return 子ぷよの軸ぷよに対する相対位置
	 */
	public TsumoChildPositionEnum getChildPosition() {
		return childPosition;
	}

}
