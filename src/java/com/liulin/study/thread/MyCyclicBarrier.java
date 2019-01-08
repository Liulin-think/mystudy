package com.liulin.study.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/** 
 * Reason: 
 * 			CyclicBarrier:让一组线程达到某个屏障，被阻塞，一直到组内最后一个线程达到屏障时，屏障开放，
 * 			组内所有被阻塞的线程会继续运行await之后的内容
 * @author liulin_think
 * @date 2019-01-08 16:16:39
 */
public class MyCyclicBarrier {
	private static final int parties = 5;
	private static CyclicBarrier cyclicBarrier = new CyclicBarrier(parties);

	public static void main(String[] args) throws InterruptedException {
		MyCyclicBarrier myCyclicBarrier = new MyCyclicBarrier();
		for (int i = 0; i < parties * 2 + (parties - 1); i++) {
			Thread.sleep(5);
			new Thread(myCyclicBarrier.new MyRunnable()).start();
		}

		Thread.sleep(5000);
		new Thread(myCyclicBarrier.new MyRunnable()).start();
	}

	class MyRunnable implements Runnable {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				System.out.println("开始执行:" + Thread.currentThread().getName());
				cyclicBarrier.await();
				System.out.println("执行await后:" + Thread.currentThread().getName());
			} catch (InterruptedException | BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
