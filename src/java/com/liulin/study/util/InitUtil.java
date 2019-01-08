package com.liulin.study.util;

import java.util.Random;

/** 
 * Reason: 初始化工具类
 * @author liulin_think
 * @date 2019-01-08 15:16:52
 */
public class InitUtil {
	private static Random random = new Random();

	public static void initArray(int[] numarry) {
		int length = numarry.length;
		for (int i = 0; i < length; i++) {
			numarry[i] = random.nextInt(length);
		}
	}

}
