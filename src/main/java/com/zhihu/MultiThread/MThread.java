package com.zhihu.MultiThread;

/***
 *
 * 编写内容： Thread Runnable Synchronized BlockingQueue(生产者--消费者) ThreadLocal
 * Excutor Future
 **/
public class MThread extends Thread {
	String name;

	MThread(String name) {
		this.name = name;
	}

	public void say() {
		for (int i = 0; i < 5; i++) {
			try {
				sleep(1000);
				System.out.println("Thread " + name + " say " + i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	//重写run方法
	@Override
	public void run(){
		say();
	}

	//Thread--start()
//	public static void main(String[] args) {
//		// 创建线程
//		try {
//			for (int i = 0; i < 4; i++) {
//				new MThread(String.valueOf(i)).start();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
}
