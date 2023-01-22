package com.learnjava.parallelstreams;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.learnjava.util.LoggerUtil;

public class ParallelStreamResultOrder {
	public static List<Integer> listOrder(List<Integer> inputList) {
		return inputList.stream().map(integer -> integer * 2).collect(Collectors.toList());
	}

	public static Set<Integer> setOrder(Set<Integer> inputSet) {
		return inputSet.stream().map(integer -> integer * 2).collect(Collectors.toSet());
	}

	public static void main(String[] args) {
		List<Integer> inputList = List.of(1, 2, 3, 4, 5, 6, 7, 8);
		LoggerUtil.log("inputList " + inputList);
		List<Integer> resultList = listOrder(inputList);
		LoggerUtil.log("resultList  " + resultList);
		
		Set<Integer> inputSet = Set.of(1, 2, 3, 4, 5, 6, 7, 8);
		LoggerUtil.log("inputSet " +inputSet);
		Set<Integer> resultSet = setOrder(inputSet);
		LoggerUtil.log("resultSet " + resultSet);

	}
}
