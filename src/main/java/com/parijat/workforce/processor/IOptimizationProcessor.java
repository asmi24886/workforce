package com.parijat.workforce.processor;

import com.parijat.workforce.model.CleanerSet;

import java.util.List;

public interface IOptimizationProcessor
{
    public List<CleanerSet> getOptimumSolution(Integer[] rooms, Integer seniorCapacity, Integer juniorCapacity);
}
