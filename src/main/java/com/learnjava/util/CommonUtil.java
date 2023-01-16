package com.learnjava.util;

import org.apache.commons.lang3.time.StopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class CommonUtil {
	public static StopWatch stopWatch = new StopWatch();

	public static void delay(long delayMilliSeconds) {
		try {
			Thread.sleep(delayMilliSeconds);
		} catch (InterruptedException e) {
			LoggerUtil.log("Exception is :" + e.getMessage());
		}
	}

	public static void startTimer() {
		stopWatch.start();
	}

	public static void timeTaken() {
		stopWatch.stop();
		log("Total Time Taken : " + stopWatch.getTime());
	}

	public static void stopWatchReset() {
		stopWatch.reset();
	}
}
