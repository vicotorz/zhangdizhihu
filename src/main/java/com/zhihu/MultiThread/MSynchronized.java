package com.zhihu.MultiThread;

/**
 * Synchronized
 *
 */
public class MSynchronized {
	public String name;
	static Object o = new Object();

	MSynchronized(String name) {
		this.name = name;
	}

	public static void say() {
		// 方法区上锁
		synchronized (o) {
			try {
				for (int j = 0; j < 3; j++) {
					Thread.sleep(1000);
					System.out.println(Thread.currentThread().getName()+ " say " + j);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static void say1() {
		for (int j = 0; j < 5; j++) {
			//Thread.sleep(1000);
			System.out.println(Thread.currentThread().getName()+" say from say " + j);
		}

	}

	public static void main(String[] args) {

		// 启动多个线程--访问同一个区域
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					say();

				}
			}).start();
		}

	}

}
