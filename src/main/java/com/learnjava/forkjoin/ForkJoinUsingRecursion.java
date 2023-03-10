package com.learnjava.forkjoin;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import com.learnjava.util.DataSet;

public class ForkJoinUsingRecursion extends RecursiveTask<List<String>> {
	private List<String> inputList;

	public ForkJoinUsingRecursion(List<String> inputList) {
		this.inputList = inputList;
	}

	public static void main(String[] args) {
		stopWatch.start();
		List<String> resultList = new ArrayList<>();
		List<String> names = DataSet.namesList();
		ForkJoinUsingRecursion forkJoinUsingRecursion = new ForkJoinUsingRecursion(names);
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		resultList = forkJoinPool.invoke(forkJoinUsingRecursion);
		
		stopWatch.stop();
		log("Final Result : " + resultList);
		log("Total Time Taken : " + stopWatch.getTime());
	}

	private static String addNameLengthTransform(String name) {
		delay(500);
		return name.length() + " " + name;
	}

	@Override
	protected List<String> compute() {
		if (inputList.size() <= 1) {
			List<String> result = new ArrayList<>();
			inputList.forEach(name -> result.add(addNameLengthTransform(name)));
			return result;
		}
		int midPoint = inputList.size() / 2;
		ForkJoinTask<List<String>> leftInputList = new ForkJoinUsingRecursion(inputList.subList(0, midPoint)).fork();
		inputList = inputList.subList(midPoint, inputList.size());
		List<String> rightList = compute();
		List<String> leftResult = leftInputList.join();
		leftResult.addAll(rightList);
		return leftResult;
	}
}
