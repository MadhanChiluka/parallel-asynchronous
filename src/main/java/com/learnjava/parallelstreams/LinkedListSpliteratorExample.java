package com.learnjava.parallelstreams;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LinkedListSpliteratorExample {
	public List<Integer> multiplyEachValue(LinkedList<Integer> inputList, int multiplyValue, boolean isParallel) {
		startTimer();
		Stream<Integer> intStream = inputList.stream();
		if(isParallel)
			intStream.parallel();
		
		List<Integer> resultList = intStream.map(integer -> integer * multiplyValue).collect(Collectors.toList());
		timeTaken();
		return resultList;
	}
}
