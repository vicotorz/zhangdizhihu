package com.zhihu.MultiThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MExcutor {
	// �����̳߳� execute() ָ����������
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

		// executor�Ĺر� shutdown shutdownNow
		service.shutdown();
		// �۲�״̬
		while (!service.isTerminated()) {
			try {
				Thread.sleep(1000);
				System.out.println("�ȴ��ر�");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		testExecutor();
	}
}
