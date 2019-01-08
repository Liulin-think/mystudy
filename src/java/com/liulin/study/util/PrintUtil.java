package com.liulin.study.util;

import java.util.List;

/** 
 * Reason: TODO
 * @author liulin_think
 * @date 2019-01-08 15:00:51
 */
public class PrintUtil {
	/**
	 * Reason: 输出:list.get(i),list.get(i+1),....
	 * @param list
	 * @param msg  
	 * @author liulin_think
	 * @date 2019-01-08 15:20:39
	 */
	public static void print(List<?> list, String msg) {
		System.out.println(msg + ":");
		int size = list.size();
		for (int i = 0; i < size; i++) {
			System.out.print(list.get(i) + ",");
		}
		System.out.println();
	}

	public static void print(int[] array, String msg) {
		System.out.println(msg + ":");
		int length = array.length;
		for (int i = 0; i < length; i++) {
			System.out.print(array[i] + ",");
		}
		System.out.println();
	}

	public static void timeContrast(long time1, String msg1, long time2, String msg2) {
		System.out.println("差值:" + (time2 - time1));
		System.out.println("【" + "forkJoin" + "】的执行速度是【" + "normal" + "】的执行速度:" + (time2 / time1) + "倍");
	}

	public static long executionTime(long begin, long end, String msg) {
		long time = (end - begin);
		System.out.println("【" + msg + "】的执行时间是:" + time);
		return time;
	}

}
