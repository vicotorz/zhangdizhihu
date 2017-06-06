package com.zhihu.MultiThread;

/***
 * 
 * ��д���ݣ� Thread Runnable Synchronized BlockingQueue(������--������) ThreadLocal
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
	
	//��дrun����
	@Override
	public void run(){
		say();
	}

	//Thread--start()
	public static void main(String[] args) {
		// �����߳�
		try {
			for (int i = 0; i < 4; i++) {
				new MThread(String.valueOf(i)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
