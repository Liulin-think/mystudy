package com.liulin.study.thread;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import com.liulin.study.util.SortUtil;

public class MyForkJoin {
	private static int size = 100000;
	private static Random random = new Random();

	public static void main(String[] args) throws InterruptedException {
		Integer[] numarry = new Integer[size];
		System.out.println("排序前:");
		for (int i = 0; i < size; i++) {
			numarry[i] = random.nextInt(size);
			System.out.print(numarry[i] + ",");
		}
		System.out.println();
		Integer[] clone = numarry.clone();

		MyForkJoin myForkJoin = new MyForkJoin();
		long begin1 = System.currentTimeMillis();
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		ForkJoinTask<ArrayList<Integer>> task =myForkJoin.new MyForkJoinTask(numarry, 0, size);
		ArrayList<Integer> invoke = forkJoinPool.invoke(task);
		task.join();
		long end1 = System.currentTimeMillis();
		long begin2 = System.currentTimeMillis();
		SortUtil.bubbleSort(clone, 0, size);
		long end2 = System.currentTimeMillis();

		System.out.println("排序后1:");
		for (int i = 0; i < size; i++) {
			System.out.print(clone[i] + ",");
		}
		System.out.println();

		System.out.println("排序后2:");
		for (int i = 0; i < size; i++) {
			System.out.print(invoke.get(i) + ",");
		}
		System.out.println();

		long forkjoin = (end1 - begin1);
		System.out.println("forkjoin:" + forkjoin);
		long normal = (end2 - begin2);
		System.out.println("normal:" + normal);
		System.out.println("差值:" + (normal - forkjoin));
		System.out.println("forkjoin的效率是normal的效率:" + (normal / forkjoin) + "倍");
	}
	
	/**
	 * @author liulin_think
	 * @date 2019-01-08 10:19:44
	 * Reason: 对numarry数组内的数据进行排序
	 */
	private class MyForkJoinTask extends RecursiveTask<ArrayList<Integer>> {
		private Integer[] numarry;
		private int min;
		private int max;

		// private static final int FA = Runtime.getRuntime().availableProcessors()*2;
		private static final int FA = 100;

		public MyForkJoinTask(Integer[] numarry, int min, int max) {
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
				int middle = num / 2 + min;
				MyForkJoinTask left = new MyForkJoinTask(numarry, min, middle);
				MyForkJoinTask right = new MyForkJoinTask(numarry, middle, max);
				invokeAll(left, right);

				ArrayList<Integer> ljoin = left.join();
				ArrayList<Integer> rjoin = right.join();
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
			} else {
				SortUtil.bubbleSort(numarry, min, max);
				for (int i = min; i < max; i++) {
					arrayList.add(numarry[i]);
				}
			}
			return arrayList;
		}
	}
}


