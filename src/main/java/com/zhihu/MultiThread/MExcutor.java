package com.zhihu.MultiThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MExcutor {
	// 创建线程池 execute() 指定阻塞队列
	public static void testExecutor() {
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.submit(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; ++i) {
					try {
						Thread.sleep(1000);
						System.out.println("Executor1:" + i);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});

		service.submit(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; ++i) {
					try {
						Thread.sleep(1000);
						System.out.println("Executor2:" + i);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});

		// executor的关闭 shutdown shutdownNow
		service.shutdown();
		// 观察状态
		while (!service.isTerminated()) {
			try {
				Thread.sleep(1000);
				System.out.println("等待关闭");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		testExecutor();
	}
}
