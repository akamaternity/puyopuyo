package com.akamaternity.puyopuyo;

import java.util.HashSet;
import java.util.Set;

/**
 * フィールドクラス
 * @author akamaternity
 */
public class Field implements Cloneable {
	public static final int X_SIZE = 6;
	public static final int Y_SIZE = 13;

	public static final int X_DEAD_POS = 2;
	public static final int Y_DEAD_POS = 11;

	/** フィールド */
	private FieldPuyo[][] field;

	/**
	 * コンストラクタ
	 */
	public Field() {
		field = new FieldPuyo[Y_SIZE][X_SIZE];
		for (int y = 0; y < Y_SIZE; y++) {
			for (int x = 0; x < X_SIZE; x++) {
				field[y][x] = new FieldPuyo();
			}
		}
	}

	/**
	 * コンストラクタ
	 * @param fieldArray フィールド（char配列）
	 */
	public Field(char[][] fieldArray) {
		if (!(fieldArray.length == Y_SIZE && fieldArray[0].length == X_SIZE)) {
			throw new IllegalArgumentException();
		}

		field = new FieldPuyo[Y_SIZE][X_SIZE];

		for (int y = 0; y < Y_SIZE; y++) {
			for (int x = 0; x < X_SIZE; x++) {
				field[y][x] = new FieldPuyo(fieldArray[y][x]);
			}
		}
	}

	/**
	 * コンストラクタ
	 * @param fieldStr フィールド（文字列）
	 */
	public Field(String fieldStr) {
		if (fieldStr.length() != (X_SIZE * Y_SIZE)) {
			throw new IllegalArgumentException();
		}

		field = new FieldPuyo[Y_SIZE][X_SIZE];
		for (int y = 0; y < Y_SIZE; y++) {
			for (int x = 0; x < X_SIZE; x++) {
				field[y][x] = new FieldPuyo(fieldStr.charAt(y * X_SIZE + x));
			}
		}
	}

	/**
	 * ツモを設置した結果を取得します。
	 * @param tsumo ツモ
	 * @return [0]得点, [1]連鎖数, [2]最大同時消し色数, [3]最大同時消し数
	 * @throws CannotPutTsumoException
	 */
	public int[] put(Tsumo tsumo) throws CannotPutTsumoException {
		int score = 0;
		int chain = 0;
		int maxColor = 0;
		int maxErase = 0;

		// 1. ツモを落とす
		dropTsumo(tsumo);

		// 2. 消去チェックがTRUEの場合、処理を繰り返す
		while (isErasable()) {

			// 2-1. 消す処理
			int[] eraseResult = erase(++chain);

			// 2-2. スコア等計算
			score += eraseResult[0];
			maxColor = eraseResult[2] > maxColor ? eraseResult[2] : maxColor;
			maxErase = eraseResult[3] > maxErase ? eraseResult[3] : maxErase;

			// 2-3. 消した後、上のぷよを落とす
			dropField();
		}

		int[] result = new int[4];
		result[0] = score;
		result[1] = chain;
		result[2] = maxColor;
		result[3] = maxErase;

		return result;
	}

	/**
	 * ツモを落とします。
	 * @param tsumo ツモ
	 * @throws CannotPutTsumoException
	 */
	private void dropTsumo(Tsumo tsumo) throws CannotPutTsumoException {
		// 子ぷよが下の場合、子ぷよから処理
		// それ以外の場合は軸ぷよから処理
		if (tsumo.getChildPosition() == TsumoChildPositionEnum.CHILD_BOTTOM) {
			dropTsumoPuyo(tsumo.getChildPuyo(), tsumo.getChildX());
			dropTsumoPuyo(tsumo.getAxisPuyo(), tsumo.getAxisX());
		} else {
			dropTsumoPuyo(tsumo.getAxisPuyo(), tsumo.getAxisX());
			dropTsumoPuyo(tsumo.getChildPuyo(), tsumo.getChildX());
		}
	}


	/**
	 * 消去可能であるか取得します。
	 * @return true：消去可能 / false：消去不可
	 */
	private boolean isErasable() {
		resetConnectSize();

		// ループ1回目は連結数の計算
		for (int y = 0; y < Y_SIZE - 1; y++) {
			for (int x = 0; x < X_SIZE; x++) {
				check(x, y, -1, -1);
			}
		}

		// ループ2回目は連結数をチェックし、消すことができるか確認
		for (int y = 0; y < Y_SIZE; y++) {
			for (int x = 0; x < X_SIZE; x++) {
				if (field[y][x].isErasable()) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * ぷよを消去し、結果を取得します。
	 * @param chain 連鎖数
	 * @return [0]得点, [1]連鎖数, [2]色数, [3]消去数
	 */
	private int[] erase(int chain) {
		// 消した数
		int erase = 0;
		// 連結数のSet
		Set<Connect> connectSet = new HashSet<>();
		// 色数のSet
		Set<Character> colorSet = new HashSet<>();


		for (int y = 0; y < Y_SIZE; y++) {
			for (int x = 0; x < X_SIZE; x++) {
				FieldPuyo fieldPuyo = field[y][x];
				if (fieldPuyo.isErasable()) {
					// 1. 消す処理の前に、得点計算
					// 消した数を増やす
					erase++;
					// 連結数Setに格納
					connectSet.add(fieldPuyo.getConnect());
					// 色数Setに格納
					colorSet.add(fieldPuyo.getColor());

					// 2. 自分自身を消す
					fieldPuyo.erase();

					// 3. 周りのおじゃまぷよを消す
					// up
					if ((y + 1 < Y_SIZE - 1) && field[y + 1][x].isOjama()) {
						field[y + 1][x].erase();
					}

					// down
					if ((y - 1 >= 0) && field[y - 1][x].isOjama()) {
						field[y - 1][x].erase();
					}

					// right
					if ((x + 1 < X_SIZE) && field[y][x + 1].isOjama()) {
						field[y][x + 1].erase();
					}

					// left
					if ((x - 1 >= 0) && field[y][x - 1].isOjama()) {
						field[y][x - 1].erase();
					}

				}
			}
		}

		// 得点の計算
		return Score.calcScore(chain, erase, connectSet, colorSet);

	}

	/**
	 * 浮いているぷよを落下します。
	 */
	private void dropField() {
		for (int y = 0; y < Y_SIZE - 1; y++) {
			for (int x = 0; x < X_SIZE; x++) {
				if (field[y][x].isNone()) {
					// 列上部で「NONE」以外のぷよを探す
					int y2 = y;
					do {
						y2++;
					} while (y2 < Y_SIZE - 1 && field[y2][x].isNone());

					field[y][x].setColor(field[y2][x].getColor());
					field[y2][x].erase();
				}
			}
		}
	}

	/**
	 * ぷよの連結数を再帰的にチェックします。
	 * @param x チェックするぷよのx座標
	 * @param y チェックするぷよのy座標
	 * @param preX 1つ前にチェックしたぷよのx座標
	 * @param preY 1つ前にチェックしたぷよのy座標
	 */
	private void check(int x, int y, int preX, int preY) {
		FieldPuyo current = field[y][x];

		if (current.getConnect() != null) {
			// コネクトがNULLでない場合（既にチェック済の場合）
			return;
		}

		if (!current.isColorPuyo()) {
			// ぷよが「なし」「おじゃま」の場合
			return;
		}

		Connect connect;
		FieldPuyo pre;

		if (preX == -1 && preY == -1) {
			// 初回（再帰呼出しでない）
			connect = new Connect();
		} else {
			// 再帰呼出し
			pre = field[preY][preX];

			if (!current.isSameColor(pre)) {
				// 現在チェック中のぷよと前チェックのぷよが異なる色の場合
				return;
			}

			connect = pre.getConnect();
			connect.increment();
		}

		current.setConnect(connect);

		// up
		if (y + 1 < Y_SIZE - 1) {
			check(x, y + 1, x, y);
		}

		// down
		if (y - 1 >= 0) {
			check(x, y - 1, x, y);
		}

		// right
		if (x + 1 < X_SIZE) {
			check(x + 1, y, x, y);
		}

		// left
		if (x - 1 >= 0) {
			check(x - 1, y, x, y);
		}

	}

	/**
	 * 連結数をリセットします。
	 */
	private void resetConnectSize() {
		for (int y = 0; y < Y_SIZE; y++) {
			for (int x = 0; x < X_SIZE; x++) {
				field[y][x].resetConnect();
			}
		}
	}

	/**
	 * ツモぷよを落下します。
	 * @param tsumoPuyo ツモぷよ
	 * @throws CannotPutTsumoException
	 */
	private void dropTsumoPuyo(TsumoPuyo tsumoPuyo, int x) throws CannotPutTsumoException {
		for (int y = 0; y < Field.Y_SIZE; y++) {
			if (field[y][x].isNone()) {
				field[y][x] = tsumoPuyo.convertFieldPuyo();
				return;
			}
		}

		// 最後まで置けなかったら例外
		throw new CannotPutTsumoException();
	}

	/**
	 * 連結数をコンソールに出力します。
	 */
	public void printConnect() {
		for (int y = Y_SIZE - 1; y >= 0; y--) {
			for (int x = 0; x < X_SIZE; x++) {
				Connect connect = field[y][x].getConnect();
				System.out.print(connect == null ? 0 : connect.getSize());
			}
			System.out.println();
		}
	}

	/**
	 * フィールドの状態をコンソールに出力します。
	 */
	public void print() {
		for (int y = Y_SIZE - 1; y >= 0; y--) {
			for (int x = 0; x < X_SIZE; x++) {
				char color = field[y][x].getColor();
				System.out.print(color);
			}
			System.out.println();
		}
		System.out.println("------");
	}

	/**
	 * インスタンスのコピーを取得します。
	 * @return フィールド（コピー）
	 */
	public Field getFieldClone() {
		return new Field(getFieldCharArray());
	}

	/**
	 * フィールドのchar配列を取得します。
	 * @return フィールド（char配列）
	 */
	public char[][] getFieldCharArray() {
		char[][] charArr = new char[Y_SIZE][X_SIZE];

		for (int y = 0; y < Y_SIZE; y++) {
			for (int x = 0; x < X_SIZE; x++) {
				charArr[y][x] = field[y][x].getColor();
			}
		}

		return charArr;
	}

	/**
	 * フィールドの文字列を取得します。
	 * @return フィールド（文字列）
	 */
	public String getFieldString() {
		StringBuilder fieldStr = new StringBuilder();

		for (int y = 0; y < Y_SIZE; y++) {
			for (int x = 0; x < X_SIZE; x++) {
				fieldStr.append(field[y][x].getColor());
			}
		}

		return fieldStr.toString();
	}

	/**
	 * 致死座標が埋まっているか取得します。
	 * @return true: 死亡、false: 生存
	 */
	public boolean isDead() {
		if (field[Y_DEAD_POS][X_DEAD_POS].getColor() == Puyo.NONE) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * フィールドに指定の色を含むか取得します。
	 * @param color 色
	 * @return true：含む / false：含まない
	 */
	public boolean contains(char color) {
		for (int y = 0; y < Y_SIZE; y++) {
			for (int x = 0; x < X_SIZE; x++) {
				if (field[y][x].getColor() == color) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * フィールドが全消し状態か取得します。
	 * @return true：全消し / false：全消しでない
	 */
	public boolean isAllClear() {
		for (int y = 0; y < Y_SIZE; y++) {
			for (int x = 0; x < X_SIZE; x++) {
				if (!field[y][x].isNone()) {
					return false;
				}
			}
		}
		return true;
	}

}
