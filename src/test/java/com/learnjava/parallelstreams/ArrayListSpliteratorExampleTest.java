package com.learnjava.parallelstreams;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestMethodOrder;

import com.learnjava.util.DataSet;

@TestMethodOrder(MethodOrderer.MethodName.class)
class ArrayListSpliteratorExampleTest {
	ArrayListSpliteratorExample arrayListSpliteratorExample = new ArrayListSpliteratorExample();

	@RepeatedTest(5)
	@Order(1)
	void multiplyEachValue() {
		//given
		int size = 1000000;
		ArrayList<Integer> inputList = DataSet.generateArrayList(size);
		
		//when
		List<Integer> resultList = arrayListSpliteratorExample.multiplyEachValue(inputList, 2, false);

		//then
		assertEquals(size, resultList.size());
	}
	
	@RepeatedTest(5)
	@Order(2)
	void multiplyEachValue_parallel() {
		//given
		int size = 1000000;
		ArrayList<Integer> inputList = DataSet.generateArrayList(size);
		
		//when
		List<Integer> resultList = arrayListSpliteratorExample.multiplyEachValue(inputList, 2, true);

		//then
		assertEquals(size, resultList.size());
	}

}
