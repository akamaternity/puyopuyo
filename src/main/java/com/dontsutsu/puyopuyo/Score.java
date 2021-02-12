package com.dontsutsu.puyopuyo;

import java.util.Set;

/**
 * 得点計算クラス
 * @author akamaternity
 */
public class Score {
	/** 連鎖ボーナス */
	private static final int[] CHAIN_BONUS = {0, 8, 16, 32, 64, 96, 128, 160, 192, 224, 256, 288, 320, 352, 384, 416, 448, 480, 512};
	/** 連結ボーナス */
	private static final int[] CONNECT_BONUS = {0, 2, 3, 4, 5, 6, 7, 10};
	/** 色数ボーナス */
	private static final int[] COLOR_BONUS = {0, 3, 6, 12, 24};

	/**
	 * スコアを計算します。
	 * @param chain 連鎖数
	 * @param erase 消去数
	 * @param connectSet 連結数のセット
	 * @param colorSet 色のセット
	 * @return [0]得点, [1]連鎖数, [2]最大同時消し色数, [3]最大同時消し数
	 */
	public static int[] calcScore(int chain, int erase, Set<Connect> connectSet, Set<Character> colorSet) {
		// 連結ボーナス
		int connectBonus = 0;
		for (Connect c : connectSet) {
			int connect = c.getSize();
			connect = (connect > 11 ? 11 : connect) - 4;
			connectBonus += CONNECT_BONUS[connect];
		}

		// 色数ボーナス
		int colorBonus = COLOR_BONUS[colorSet.size() - 1];

		// 連鎖ボーナス
		int chainBonus = CHAIN_BONUS[chain - 1];

		int bonus = connectBonus + colorBonus + chainBonus;
		if (bonus == 0) {
			bonus = 1;
		}

		int score = erase * bonus * 10;

		int[] result = new int[4];
		result[0] = score;
		result[1] = chain;
		result[2] = colorSet.size();
		result[3] = erase;

		return result;
	}

}
