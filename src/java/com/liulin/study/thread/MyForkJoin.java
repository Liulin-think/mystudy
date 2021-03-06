package com.liulin.study.thread;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import com.liulin.study.util.InitUtil;
import com.liulin.study.util.PrintUtil;
import com.liulin.study.util.SortUtil;

public class MyForkJoin {
	private static int size = 100000;

	public static void main(String[] args) throws InterruptedException {
		// =================初始化需要排序的数据==========================
		int[] numarry = new int[size];
		InitUtil.initArray(numarry);
		int[] clone = numarry.clone();
		PrintUtil.print(clone, "排序前");
		// =================初始化需要排序的数据==========================

		// =================使用forkJoin实现排序方式==========================
		MyForkJoin myForkJoin = new MyForkJoin();
		long begin1 = System.currentTimeMillis();
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		ForkJoinTask<ArrayList<Integer>> task = myForkJoin.new MyForkJoinTask(numarry, 0, size);
		ArrayList<Integer> invoke = forkJoinPool.invoke(task);
		task.join();
		long end1 = System.currentTimeMillis();
		PrintUtil.print(invoke, "排序后2");
		// =================使用forkJoin实现排序方式==========================

		// =================使用普通冒泡排序实现排序方式==========================
		long begin2 = System.currentTimeMillis();
		SortUtil.bubbleSort(clone, 0, size);
		long end2 = System.currentTimeMillis();
		PrintUtil.print(clone, "排序后1");
		// =================使用普通冒泡排序实现排序方式==========================

		// =================两种排序执行使用时间及执行时间对比==========================
		long forkJoinTime = PrintUtil.executionTime(begin1, end1, "forkJoin");
		long normalTime = PrintUtil.executionTime(begin2, end2, "normal");
		PrintUtil.timeContrast(forkJoinTime, "forkJoin", normalTime, "normal");
		// =================两种排序执行使用时间及执行时间对比==========================
	}

	/**
	 * @author liulin_think
	 * @date 2019-01-08 10:19:44
	 * Reason: 对numarry数组内的数据进行排序
	 */
	private class MyForkJoinTask extends RecursiveTask<ArrayList<Integer>> {
		private int[] numarry;
		private int min;
		private int max;

		// private static final int FA = Runtime.getRuntime().availableProcessors()*2;
		private static final int FA = 100;

		public MyForkJoinTask(int[] numarry, int min, int max) {
			super();
			this.numarry = numarry;
			this.min = min;
			this.max = max;
		}

		private static final long serialVersionUID = 7735697590253369641L;

		@Override
		protected ArrayList<Integer> compute() {
			int num = max - min;
			ArrayList<Integer> arrayList = new ArrayList<>();
			if (num > numarry.length / FA) {
				// 任务比预定的阀值大.对任务进行分解
				int middle = num / 2 + min;
				MyForkJoinTask left = new MyForkJoinTask(numarry, min, middle);
				MyForkJoinTask right = new MyForkJoinTask(numarry, middle, max);
				invokeAll(left, right);

				ArrayList<Integer> ljoin = left.join();
				ArrayList<Integer> rjoin = right.join();

				// =================排序完成后数据合并==========================
				int l1 = 0;
				int r1 = 0;
				for (; l1 != ljoin.size() || r1 != rjoin.size();) {
					if (r1 == rjoin.size()) {
						arrayList.add(ljoin.get(l1++));
					} else if (l1 == ljoin.size()) {
						arrayList.add(rjoin.get(r1++));
					} else if (ljoin.get(l1) == rjoin.get(r1)) {
						arrayList.add(ljoin.get(l1++));
						arrayList.add(rjoin.get(r1++));
					} else if (ljoin.get(l1) < rjoin.get(r1)) {
						arrayList.add(ljoin.get(l1++));
					} else {
						arrayList.add(rjoin.get(r1++));
					}
				}
				// =================排序完成后数据合并==========================
			} else {
				// 分解到足够小的单元,执行具体的任务内容
				SortUtil.bubbleSort(numarry, min, max);
				for (int i = min; i < max; i++) {
					arrayList.add(numarry[i]);
				}
			}
			return arrayList;
		}
	}
}
