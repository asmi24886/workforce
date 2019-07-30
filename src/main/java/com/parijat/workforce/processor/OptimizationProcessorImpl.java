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
        CleanerSet cleanerSet = new CleanerSet();

        if(numberOfRooms > 0 && numberOfRooms <= 100)
        {
            // Get both minimum Senior and Junior Count and deduct it from the room's capacity
            remainingRoomNumbers = numberOfRooms - ((minimumSerniorCount * seniorCapacity)
                    + (minimumJuniorCount * juniorCapacity));

            if (remainingRoomNumbers < 0)
            {
                remainingRoomNumbers = 0;
            }
            // First Case - If all seniors can complete remainingRooms
            if (remainingRoomNumbers % seniorCapacity == 0)
            {
                cleanerSet.setSenior(remainingRoomNumbers / seniorCapacity);
                cleanerSet.setJunior(0);
            }
            // Second Case - If all juniors can complete remainingRooms
            else if (remainingRoomNumbers % juniorCapacity == 0)
            {
                cleanerSet = new CleanerSet();
                cleanerSet.setSenior(0);
                cleanerSet.setJunior(remainingRoomNumbers / juniorCapacity);
            }
            // Third Case - It is needed to find out a optimum combination of seniors and juniors
            else
            {
                // Find the maximum number of senior can clean the remaining rooms
                Integer maxSeniors = getCeil(remainingRoomNumbers, seniorCapacity);

                cleanerSet = findOptimumCombination(remainingRoomNumbers, maxSeniors,
                        seniorCapacity, juniorCapacity);
            }

            // Add the minimum number of seniors and juniors
            cleanerSet.setSenior(cleanerSet.getSenior() + minimumSerniorCount);
            cleanerSet.setJunior(cleanerSet.getJunior() + minimumJuniorCount);
        }

        return cleanerSet;
    }

    private CleanerSet findOptimumCombination(Integer roomCapacity, Integer maxSeniors, Integer seniorCapacity,
                                              Integer juniorCapacity)
    {
        CleanerSet lowestCleanerSet = new CleanerSet();
        int lowestCap;
        int counter;

        if(roomCapacity > 0)
        {
            // Lets assume the remaining rooms will be cleaned by the seniors
            lowestCleanerSet.setSenior(maxSeniors);
            lowestCap = utility.computeTotalCapacity(lowestCleanerSet, seniorCapacity, juniorCapacity);

            // Now we will check the combination with the junior to achieve optimum capacity
            for (counter = 0; counter <= maxSeniors; counter++)
            {
                int capacity = roomCapacity;

                // Check the remaining part after seniors' clean up
                capacity = capacity - (counter * seniorCapacity);

                if (capacity > 0)
                {
                    // Find the number of Juniors required to complete the remaining rooms
                    int numberOfJuniors = getCeil(capacity, juniorCapacity);

                    // Find the total Capacity
                    int totalCap = (counter * seniorCapacity) + (numberOfJuniors * juniorCapacity);

                    if (totalCap >= roomCapacity && totalCap < lowestCap)
                    {
                        lowestCap = totalCap;
                        lowestCleanerSet.setSenior(counter);
                        lowestCleanerSet.setJunior(numberOfJuniors);
                    }
                }
            }
        }

        return lowestCleanerSet;
    }

    private Integer getCeil(Integer dividend, Integer divisor)
    {
        return (dividend % divisor == 0) ? (dividend / divisor) : ((dividend / divisor) + 1);
    }
}
