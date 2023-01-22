package com.learnjava.parallelstreams;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;

import com.learnjava.util.DataSet;

class LinkedListSpliteratorExampleTest {
	LinkedListSpliteratorExample linkedListSpliteratorExample = new LinkedListSpliteratorExample();

	@RepeatedTest(5)
	@Order(1)
	void multiplyEachValue() {
		//given
		int size = 1000000;
		LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);
		
		//when
		List<Integer> resultList = linkedListSpliteratorExample.multiplyEachValue(inputList, 2, false);

		//then
		assertEquals(size, resultList.size());
	}
	
	@RepeatedTest(5)
	@Order(2)
	void multiplyEachValue_parallel() {
		//given
		int size = 1000000;
		LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);
		
		//when
		List<Integer> resultList = linkedListSpliteratorExample.multiplyEachValue(inputList, 2, true);

		//then
		assertEquals(size, resultList.size());
	}

}
