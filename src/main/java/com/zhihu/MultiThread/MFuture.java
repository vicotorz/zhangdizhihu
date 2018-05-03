package com.zhihu.MultiThread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MFuture {
	public void testFuture() {
		ExecutorService exe = Executors.newFixedThreadPool(10);
		Future future = exe.submit(new Callable<Integer>() {
			// callable中call()方法返回值
			public Integer call() throws Exception {
				Thread.sleep(1000);
				return 1;
			}

		});

		exe.shutdown();
		while (!exe.isTerminated()) {
			try {
				System.out.println(future.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

//	public static void main(String[] args) {
//		new MFuture().testFuture();
//	}

}
