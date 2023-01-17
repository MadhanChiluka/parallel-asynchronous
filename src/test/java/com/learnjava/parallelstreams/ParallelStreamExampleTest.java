package com.learnjava.parallelstreams;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.learnjava.util.DataSet;

class ParallelStreamExampleTest {

	ParallelStreamExample parallelStreamExample = new ParallelStreamExample();

	@Test
	void testStringTransform() {
		// given
		List<String> inputList = DataSet.namesList();

		// when
		startTimer();
		List<String> result = parallelStreamExample.stringTransform(inputList);
		timeTaken();

		// then
		assertEquals(4, result.size());
		result.forEach(name -> {
			assertTrue(name.contains("-"));
		});
	}

	@ParameterizedTest
	@ValueSource(booleans = {false, true})
	void teststringTransform_1(boolean isParallel) {
		// given
		List<String> inputList = DataSet.namesList();

		// when
		startTimer();
		List<String> result = parallelStreamExample.stringTransform_1(inputList, isParallel);
		timeTaken();

		// then
		assertEquals(4, result.size());
		result.forEach(name -> {
			assertTrue(name.contains("-"));
		});
	}

}
