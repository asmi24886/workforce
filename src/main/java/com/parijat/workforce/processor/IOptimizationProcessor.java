package com.parijat.workforce.processor;

import com.parijat.workforce.model.CleanerSet;

import java.util.List;

/**
 * The interface Optimization processor.
 */
public interface IOptimizationProcessor
{
	/**
	 * Gets optimum solution.
	 *
	 * @param rooms          the array of rooms
	 * @param seniorCapacity the senior capacity
	 * @param juniorCapacity the junior capacity
	 * @return the optimum solution
	 */
	List<CleanerSet> getOptimumSolution(Integer[] rooms, Integer seniorCapacity, Integer juniorCapacity);
}
