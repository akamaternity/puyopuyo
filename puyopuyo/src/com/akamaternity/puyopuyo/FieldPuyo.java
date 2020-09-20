package com.akamaternity.puyopuyo;

/**
 * フィールド用ぷよクラス
 * @author akamaternity
 */
public class FieldPuyo extends Puyo {

	private Connect connect;

	/**
	 * コンストラクタ
	 */
	public FieldPuyo() {
		this(NONE);
	}

	/**
	 * コンストラクタ
	 * @param color 色
	 */
	public FieldPuyo(char color) {
		super(color);
		this.connect = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean checkPuyo(char color) {
		return (color == GREEN || color == RED || color == BLUE || color == YELLOW || color == PURPLE || color == NONE || color == OJAMA);
	}

	/**
	 * 連結数を取得します。
	 * @return 連結数
	 */
	public Connect getConnect() {
		return this.connect;
	}

	/**
	 * 連結数を設定します。
	 * @param connect 連結数
	 */
	public void setConnect(Connect connect) {
		this.connect = connect;
	}

	/**
	 * 連結数を初期化します。
	 */
	public void resetConnect() {
		this.connect = null;
	}

	/**
	 * 消去可能であるか取得します。
	 * @return true：消去可能 / false：消去不可
	 */
	public boolean isErasable() {
		if (this.connect == null) {
			return false;
		} else {
			return this.connect.isErasable();
		}
	}

	/**
	 * ぷよを消去します。
	 */
	public void erase() {
		setColor(Puyo.NONE);
	}
}
