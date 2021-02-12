package com.dontsutsu.puyopuyo;

/**
 * 軸ぷよEnumクラス
 *
 * @author akamaternity
 *
 */
public enum TsumoChildPositionEnum {
	CHILD_TOP(0, 1),		// 子ぷよが上
	CHILD_BOTTOM(0, -1),	// 子ぷよが下
	CHILD_LEFT(-1, 0),		// 軸ぷよが左
	CHILD_RIGHT(1, 0)		// 軸ぷよが右
	;

	private final int childRelativeX;
	private final int childRelativeY;

	private TsumoChildPositionEnum(final int childRelativeX, final int childRelativeY) {
		this.childRelativeX = childRelativeX;
		this.childRelativeY = childRelativeY;
	}

	public int getChildRelativeX() {
		return childRelativeX;
	}

	public int getChildRelativeY() {
		return childRelativeY;
	}

	/**
	 * 横置きか取得します。
	 *
	 * @return true：横置き / false：横置きでない
	 */
	public boolean isHorizontalSetting() {
		return (this == CHILD_LEFT || this == CHILD_RIGHT);
	}

	/**
	 * 縦置きか取得します。
	 *
	 * @return true：縦置き / false：縦置きでない
	 */
	public boolean isVerticalSetting() {
		return !isHorizontalSetting();
	}

}
