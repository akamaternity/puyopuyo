package com.akamaternity.puyopuyo;

/**
 * 連結数クラス
 * @author akamaternity
 */
public class Connect {
	/** 消去連結数 */
	public static final int ERASE_CONNECT_SIZE = 4;

	/** 連結数 */
	private int size;

	/**
	 * コンストラクタ
	 */
	public Connect() {
		size = 1;
	}

	/**
	 * 連結数を取得します。
	 * @return 連結数
	 */
	public int getSize() {
		return size;
	}

	/**
	 * 連結数を設定します。
	 * @param size 連結数
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * 連結数を1増加します。
	 */
	public void increment() {
		size++;
	}

	/**
	 * 消去可能であるか取得します。
	 * @return true: 消去可能、false: 消去不可
	 */
	public boolean isErasable() {
		return size >= ERASE_CONNECT_SIZE;
	}
}
