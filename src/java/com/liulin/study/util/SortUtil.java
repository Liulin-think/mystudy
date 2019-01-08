package com.liulin.study.util;

/**
 * Reason: 排序工具
 * @author liulin_think
 * @date 2019-01-08 14:28:37
 */
public class SortUtil {
	/**
	 * Reason: 对numarry数组中下标为min-max区间的数据进行排序
	 * @param numarry
	 * @param min
	 * @param max  
	 * @author liulin_think
	 * @date 2019-01-08 14:28:50
	 */
	public static void bubbleSort(Integer[] numarry, int min, int max) {
		for (int i = min; i < max; i++) {
			for (int j = i + 1; j < max; j++) {
				if (numarry[i] > numarry[j]) {
					int tem = numarry[i];
					numarry[i] = numarry[j];
					numarry[j] = tem;
				}
			}
		}
	}
}
