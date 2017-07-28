package com.zhihu.MultiThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MBlockingQueue {

	// 内部生产者
	// 内部消费者
	// 容器
	// 主函数

	class Customer implements Runnable {
		private BlockingQueue<String> queue;

		Customer(BlockingQueue queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// 队列里面放资源
			try {
				while (!queue.isEmpty()) {
					System.out.println(Thread.currentThread().getName() + " 取出蛋糕  " + queue.take().toString());
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	class Producer implements Runnable {
		private BlockingQueue<String> queue;

		Producer(BlockingQueue queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				// System.out.print("取出蛋糕");
				System.out.println(Thread.currentThread().getName() + " 生产蛋糕  ");
				queue.put("cake");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void test() {
		BlockingQueue<String> q = new ArrayBlockingQueue<String>(2);
		new Thread(new Producer(q), "P1").start();
		new Thread(new Producer(q), "P2").start();
		new Thread(new Producer(q), "P3").start();
		new Thread(new Customer(q), "C").start();

	}

	public static void main(String[] args) {
		new MBlockingQueue().test();
	}

}
