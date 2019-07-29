package com.parijat.workforce.processor;

import com.parijat.workforce.model.CleanerSet;
import com.parijat.workforce.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OptimizationProcessorImpl implements IOptimizationProcessor
{
    @Autowired
    private Utility utility;

    @Value("${workforce.minimumSeniorCount:1}")
    private Integer minimumSerniorCount;

    @Value("${workforce.minimumJuniorCount:0}")
    private Integer minimumJuniorCount;

    @Override
    public List<CleanerSet> getOptimumSolution(Integer[] rooms, Integer seniorCapacity, Integer juniorCapacity)
    {
        List<CleanerSet> cleanerSetList = new ArrayList<>();

        // Loop through the rooms array
        for(Integer numberOfRooms : rooms)
        {
            CleanerSet cleanerSet = findOptimumCleanerSet(numberOfRooms, seniorCapacity, juniorCapacity);
            cleanerSetList.add(cleanerSet);
        }

        return cleanerSetList;
    }

    private CleanerSet findOptimumCleanerSet(Integer numberOfRooms, Integer seniorCapacity, Integer juniorCapacity)
    {
        Integer remainingRoomNumbers;
        CleanerSet cleanerSet;

        // Get both minimum Senior and Junior Count and deduct it from the room's capacity
        remainingRoomNumbers = numberOfRooms - ((minimumSerniorCount*seniorCapacity)
                +(minimumJuniorCount*juniorCapacity));

        if(remainingRoomNumbers < 0)
        {
            remainingRoomNumbers = 0;
        }

        // First Case - If all seniors can complete remainingRooms
        if(remainingRoomNumbers % seniorCapacity == 0)
        {
            cleanerSet = new CleanerSet();
            cleanerSet.setSenior(remainingRoomNumbers / seniorCapacity);
            cleanerSet.setJunior(0);
        }
        // Second Case - If all juniors can complete remainingRooms
        else if(remainingRoomNumbers % juniorCapacity == 0)
        {
            cleanerSet = new CleanerSet();
            cleanerSet.setSenior(0);
            cleanerSet.setJunior(remainingRoomNumbers / juniorCapacity);
        }
        // Third Case - It is needed to find out a optimum combination of seniors and juniors
        else
        {
            // Find the maximum number of junior can clean the remaining rooms
            Integer maxJuniors = getMaximumCleanerCount(remainingRoomNumbers,juniorCapacity);

            // Find the maximum number of senior can clean the remaining rooms
            Integer maxSeniors = getMaximumCleanerCount(remainingRoomNumbers,seniorCapacity);

            cleanerSet = findOptimumCombination(remainingRoomNumbers, maxSeniors, maxJuniors,
                    seniorCapacity, juniorCapacity);
        }

        // Add the minimum number of seniors and juniors
        cleanerSet.setSenior(cleanerSet.getSenior()+minimumSerniorCount);
        cleanerSet.setJunior(cleanerSet.getJunior()+minimumJuniorCount);

        return cleanerSet;
    }

    private CleanerSet findOptimumCombination(Integer roomCapacity, Integer maxSeniors,
                                              Integer maxJuniors, Integer seniorCapacity,
                                              Integer juniorCapacity)
    {
        int seniorCounter;
        int juniorCounter;

        CleanerSet lowestCleanerSet = new CleanerSet();
        lowestCleanerSet.setSenior(maxSeniors);
        lowestCleanerSet.setJunior(maxJuniors);

        for(seniorCounter = 0 ; seniorCounter <= maxSeniors ; seniorCounter++)
        {
            for(juniorCounter = 0 ; juniorCounter <= maxJuniors ; juniorCounter++)
            {
                CleanerSet currentCleanerSet = new CleanerSet();
                currentCleanerSet.setSenior(seniorCounter);
                currentCleanerSet.setJunior(juniorCounter);

                Integer currentCapacity = utility.computeTotalCapacity(currentCleanerSet,
                        seniorCapacity,juniorCapacity);

                Integer existingCapacity = utility.computeTotalCapacity(lowestCleanerSet,
                        seniorCapacity,juniorCapacity);

                if(currentCapacity >= roomCapacity && currentCapacity <= existingCapacity)
                {
                    lowestCleanerSet = currentCleanerSet;
                }
            }
        }

        return lowestCleanerSet;
    }

    private Integer getMaximumCleanerCount(Integer roomSize, Integer capacity)
    {
        return (roomSize / capacity) + 1;
    }
}
