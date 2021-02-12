package com.akamaternity.puyopuyo;

/**
 * ぷよクラス（基底クラス）
 * @author akamaternity
 */
public abstract class Puyo {
	public static final char GREEN = '1';
	public static final char RED = '2';
	public static final char BLUE = '3';
	public static final char YELLOW = '4';
	public static final char PURPLE = '5';
	public static final char NONE = '0';
	public static final char OJAMA = '9';

	private char color;

	/**
	 * コンストラクタ
	 */
	public Puyo() {
		this.color = NONE;
	}

	/**
	 * コンストラクタ
	 *
	 * @param color 色
	 */
	public Puyo(char color) {
		if (!checkPuyo(color)) {
			throw new IllegalArgumentException();
		}

		this.color = color;
	}

	/**
	 * ぷよの色をチェックします。
	 * @param color 色
	 * @return true：OK / false：NG
	 */
	protected abstract boolean checkPuyo(char color);

	/**
	 * 色を設定します。
	 * @param color 色
	 */
	public void setColor(char color) {
		if (!checkPuyo(color)) {
			throw new IllegalArgumentException();
		}
		this.color = color;
	}

	/**
	 * 色を取得します。
	 * @return 色
	 */
	public char getColor() {
		return this.color;
	}

	/**
	 * ぷよの色が同じか取得します。
	 * @param puyo ぷよ
	 * @return true：同じ色 / false：違う色
	 */
	public boolean isSameColor(Puyo puyo) {
		return this.color == puyo.getColor();
	}

	/**
	 * ぷよの色が無しか取得します。
	 * @return true：無し / false：無しでない
	 */
	public boolean isNone() {
		return this.color == NONE;
	}

	/**
	 * ぷよの色がおじゃまか取得します。
	 * @return true：おじゃま / false：おじゃまでない
	 */
	public boolean isOjama() {
		return this.color == OJAMA;
	}

	/**
	 * ぷよが色ぷよか取得します。
	 * @return true：色ぷよ / false：色ぷよでない
	 */
	public boolean isColorPuyo() {
		return (this.color == GREEN || this.color == RED || this.color == BLUE || this.color == YELLOW || this.color == PURPLE);
	}

	/**
	 * 指定の文字（char）がぷよを表す文字かチェックします。
	 * @param puyoChar ぷよを表す文字
	 * @return true：ぷよを表す文字 / false：ぷよを表す文字でない
	 */
	public static boolean checkPuyoChar(char puyoChar) {
		return (puyoChar == GREEN || puyoChar == RED || puyoChar == BLUE || puyoChar == YELLOW || puyoChar == PURPLE || puyoChar == NONE || puyoChar == OJAMA);
	}
}
