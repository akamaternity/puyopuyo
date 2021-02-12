package com.dontsutsu.puyopuyo;

/**
 * ツモ用ぷよクラス
 *
 * @author akamaternity
 *
 */
public class TsumoPuyo extends Puyo {
	private boolean axis;

	/**
	 * コンストラクタ
	 *
	 * @param isAxis 軸ぷよかどうか
	 * @param color 色
	 */
	public TsumoPuyo(boolean isAxis, char color) {
		super(color);
		this.axis = isAxis;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean checkPuyo(char color) {
		if (color == GREEN || color == RED || color == BLUE || color == YELLOW || color == PURPLE) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 軸ぷよか取得します。
	 * @return true：軸ぷよ / false：子ぷよ
	 */
	public boolean isAxis() {
		return this.axis;
	}

	public boolean isChild() {
		return !this.axis;
	}

	/**
	 * フィールド用ぷよに変換します。
	 *
	 * @return フィールド用ぷよ
	 */
	public FieldPuyo convertFieldPuyo() {
		return new FieldPuyo(this.getColor());
	}
}
