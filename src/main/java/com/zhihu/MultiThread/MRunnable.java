package com.zhihu.MultiThread;

public class MRunnable implements Runnable {
	public String name;

	MRunnable(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 4; i++) {
			try {
				Thread.sleep(1000);
				System.out.println("Thread " + name + " say " + i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

//	public static void main(String[] args) {
//		for (int i = 0; i < 3; i++) {
//			new MRunnable(String.valueOf(i)).run();
//		}
//
//	}

}
