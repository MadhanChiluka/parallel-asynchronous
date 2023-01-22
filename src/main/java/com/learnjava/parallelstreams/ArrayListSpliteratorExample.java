package com.learnjava.parallelstreams;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListSpliteratorExample {
	public List<Integer> multiplyEachValue(ArrayList<Integer> inputList, int multiplyValue, boolean isParallel) {
		startTimer();
		Stream<Integer> intStream = inputList.stream();
		if(isParallel)
			intStream.parallel();
		
		List<Integer> resultList = intStream.map(integer -> integer * multiplyValue).collect(Collectors.toList());
		timeTaken();
		return resultList;
	}
}
