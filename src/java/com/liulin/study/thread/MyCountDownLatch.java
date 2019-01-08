package com.liulin.study.thread;

import java.util.concurrent.CountDownLatch;

/** Reason: 是一组线程等待其他的线程完成工作以后在执行，加强版join
 * 			await用来等待，countDown负责计数器的减一
 * 			当countDown减到0时,await开始执行.
 * @author liulin_think
 * @date 2019-01-08 15:41:51
 */
public class MyCountDownLatch {
	private static final int count = 10;
	private static CountDownLatch countDownLatch = new CountDownLatch(count);

	public static void main(String[] args) {
		MyCountDownLatch myCountDownLatch = new MyCountDownLatch();
		myCountDownLatch.new MyThread2().start();
		for (int i = 0; i < count+5; i++) {
			myCountDownLatch.new MyThread1().start();
		}
	}

	class MyThread1 extends Thread {
		@Override
		public void run() {
			System.out.println("----MyThread1开始执行:" + Thread.currentThread().getName());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("----MyThread1执行结束:" + Thread.currentThread().getName());
			countDownLatch.countDown();
			System.out.println("countDown后:"+countDownLatch.getCount());
		}
	}

	class MyThread2 extends Thread {
		@Override
		public void run() {
			System.out.println("MyThread2开始执行:" + Thread.currentThread().getName());
			try {
				countDownLatch.await();
				System.out.println("await开始执行时:"+countDownLatch.getCount());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("MyThread2执行结束:" + Thread.currentThread().getName());

		}

	}
}
