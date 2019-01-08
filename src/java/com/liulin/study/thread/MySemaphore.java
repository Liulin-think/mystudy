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

	private static Semaphore asemaphore = new Semaphore(permits);// 借信号量
	private static Semaphore rsemaphore = new Semaphore(permits);// 还信号量

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
		for (int i = 0; i < permits * 5; i++) {
			mySemaphore.new MyThread2().start();
		}
		System.out.println();
		Thread.sleep(1000);
		for (int i = 0; i < permits * 5; i++) {
			mySemaphore.new MyThread1().start();
		}
		// =================先还后借=================
		// 默认还的信号量相当于是都借出去了.
		for (int i = 0; i < permits; i++) {
			rsemaphore.acquire();
		}

		Thread.sleep(1000);
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("------------正常情况下,定义有几个信号量,可用信号量不得大于定义型号量,\n"
				+ "例如:停车位五个,停车为借,离开为还.离开6次.停车位不可能多一个出来.所以,需要定义两个型号量,借和还.可用停车位永远不会大于定义的车位数--------------------");
		System.out.println();
		System.out.println();
		System.out.println();

		// =================先借先还=================
		for (int i = 0; i < permits * 5; i++) {
			mySemaphore.new MyThread11().start();
		}
		Thread.sleep(1000);
		System.out.println();
		for (int i = 0; i < permits * 5; i++) {
			mySemaphore.new MyThread22().start();
		}
		// =================先借先还=================
		Thread.sleep(1000);
		System.out.println("++++++++++++++++++++++++++++++++++");
		// =================先还后借=================
		for (int i = 0; i < permits * 5; i++) {
			mySemaphore.new MyThread22().start();
		}
		System.out.println();
		Thread.sleep(1000);
		for (int i = 0; i < permits * 5; i++) {
			mySemaphore.new MyThread11().start();
		}
		// =================先还后借=================
	}

	class MyThread1 extends Thread {
		@Override
		public void run() {
			System.out.println("--------------排队数量:" + semaphore.getQueueLength() + ",可用数量"
					+ semaphore.availablePermits() + "----------------开始:" + Thread.currentThread().getName());
			try {
				// 拿取
				semaphore.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("结束---------------排队数量:" + semaphore.getQueueLength() + ",可用数量"
					+ semaphore.availablePermits() + "---------------:" + Thread.currentThread().getName());
		}
	}

	class MyThread2 extends Thread {
		@Override
		public void run() {
			System.out.println("***排队数量:" + semaphore.getQueueLength() + ",可用数量" + semaphore.availablePermits()
					+ "**开始:" + Thread.currentThread().getName());
			// 归还
			semaphore.release();
			System.out.println("结束**排队数量:" + semaphore.getQueueLength() + ",可用数量" + semaphore.availablePermits()
					+ "***:" + Thread.currentThread().getName());
		}
	}

	class MyThread11 extends Thread {
		@Override
		public void run() {
			System.out.println("--------------排队数量:" + asemaphore.getQueueLength() + ",可用数量"
					+ asemaphore.availablePermits() + "----------------开始:" + Thread.currentThread().getName());
			try {
				// 拿取
				asemaphore.acquire();
				rsemaphore.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("结束---------------排队数量:" + asemaphore.getQueueLength() + ",可用数量"
					+ asemaphore.availablePermits() + "---------------:" + Thread.currentThread().getName());
		}
	}

	class MyThread22 extends Thread {
		@Override
		public void run() {
			System.out.println("***排队数量:" + asemaphore.getQueueLength() + ",可用数量" + asemaphore.availablePermits()
					+ "**开始:" + Thread.currentThread().getName());
			// 归还
			try {
				rsemaphore.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			asemaphore.release();
			System.out.println("结束**排队数量:" + asemaphore.getQueueLength() + ",可用数量" + asemaphore.availablePermits()
					+ "***:" + Thread.currentThread().getName());
		}
	}
}
