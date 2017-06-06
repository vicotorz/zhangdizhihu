package com.zhihu.MultiThread;

public class MThreadLocal {
	private static ThreadLocal<Integer> local = new ThreadLocal<>();

	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			final int I = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					local.set(I);
					System.out.println(local.get());
				}
			}).start();
		}
	}

}
