package com.liulin.study.thread;

import java.util.concurrent.Semaphore;

/** 
 * Reason: 信号量:控制同时访问某个特定资源的线程数量，用在流量控制
 * 			acquire获取成功后才能继续执行执行acquire后面的内容.
 * 			release归还后,acquire可获取.
 * @author liulin_think
 * @date 2019-01-08 16:39:20
 */
public class MySemaphore {
	private static final int permits = 1;
	private static Semaphore semaphore = new Semaphore(permits);

	public static void main(String[] args) throws InterruptedException {
		MySemaphore mySemaphore = new MySemaphore();
		// =================先借先还=================
		for (int i = 0; i < permits * 5; i++) {
			mySemaphore.new MyThread1().start();
		}
		Thread.sleep(1000);
		System.out.println();
		for (int i = 0; i < permits * 5; i++) {
			mySemaphore.new MyThread2().start();
		}
		// =================先借先还=================
		Thread.sleep(1000);
		System.out.println("++++++++++++++++++++++++++++++++++");
		// =================先还后借=================
		for (int i = 0; i < 5; i++) {
			mySemaphore.new MyThread2().start();
		}
		System.out.println();
		Thread.sleep(1000);
		for (int i = 0; i < 5; i++) {
			mySemaphore.new MyThread1().start();
		}
		// =================先还后借=================
	}

	class MyThread1 extends Thread {
		@Override
		public void run() {
			System.out.println("--------------排队数量:"+semaphore.getQueueLength()+",可用数量"+semaphore.availablePermits()+"----------------开始:" + Thread.currentThread().getName());
			try {
				// 拿取
				semaphore.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("结束---------------排队数量:"+semaphore.getQueueLength()+",可用数量"+semaphore.availablePermits()+"---------------:" + Thread.currentThread().getName());
		}
	}

	class MyThread2 extends Thread {
		@Override
		public void run() {
			System.out.println("***排队数量:"+semaphore.getQueueLength()+",可用数量"+semaphore.availablePermits()+"**开始:" + Thread.currentThread().getName());
			// 归还
			semaphore.release();
			System.out.println("结束**排队数量:"+semaphore.getQueueLength()+",可用数量"+semaphore.availablePermits()+"***:" + Thread.currentThread().getName());
		}
	}
}
