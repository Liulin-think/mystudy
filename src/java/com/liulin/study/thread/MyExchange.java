package com.liulin.study.thread;

import java.util.concurrent.Exchanger;

/** 
 * Reason: 两个线程间的数据交换
 * @author liulin_think
 * @date 2019-01-08 17:38:05
 */
public class MyExchange {
	private static Exchanger<String> exchanger = new Exchanger<String>();
	public static void main(String[] args) throws InterruptedException {
		MyExchange myExchange = new MyExchange();
		myExchange.new MyThread1().start();
		myExchange.new MyThread1().start();
		
		myExchange.new MyThread1().start();
		myExchange.new MyThread1().start();
	}
	class MyThread1 extends Thread {
		@Override
		public void run() {
			System.out.println("----MyThread1开始执行:" + Thread.currentThread().getName());
			try {
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName()+"执行结果:"+exchanger.exchange(Thread.currentThread().getName()));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("----MyThread1执行结束:" + Thread.currentThread().getName());
		}
	}
}
