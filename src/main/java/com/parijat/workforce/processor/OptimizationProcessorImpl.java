package com.parijat.workforce.processor;

import com.parijat.workforce.exception.WorkforceValidationException;
import com.parijat.workforce.model.CleanerSet;
import com.parijat.workforce.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The class of Optimization processor.
 */
@Component
public class OptimizationProcessorImpl implements IOptimizationProcessor
{
    @Autowired
    private Utility utility;
    
    /**
     * Minimum number of Seniors required
     */
    @Value("${workforce.minimumSeniorCount:1}")
    private Integer minimumSerniorCount;
    
    /**
     * Minimum number of Juniors required
     */
    @Value("${workforce.minimumJuniorCount:0}")
    private Integer minimumJuniorCount;
    
    /**
     * Gets optimum solution.
     *
     * @param rooms          the array of rooms
     * @param seniorCapacity the senior capacity
     * @param juniorCapacity the junior capacity
     * @return the optimum solution
     */
    @Override
    public List<CleanerSet> getOptimumSolution(Integer[] rooms, Integer seniorCapacity, Integer juniorCapacity)
    {
        List<CleanerSet> cleanerSetList = new ArrayList<>();
        
        if(seniorCapacity <= juniorCapacity)
        {
            throw new WorkforceValidationException("Cleaning capacity of seniors should be greater than juniors'");
        }

        // Loop through the rooms array
        for(Integer numberOfRooms : rooms)
        {
            if(numberOfRooms > 100 || numberOfRooms < 0)
            {
                throw new WorkforceValidationException("Room size can not be bigger than 100 or less than 0");
            }
            
            CleanerSet cleanerSet = findOptimumCleanerSet(numberOfRooms, seniorCapacity, juniorCapacity);
            cleanerSetList.add(cleanerSet);
        }

        return cleanerSetList;
    }
    
    /**
     * This method finds out the total numner of cleaners including the
     * minimum number of Seniors and Juniors required for each set.
     *
     * To reduce the complexity, it tries to find the optimum combination of seniors and juniors using
     * three cases after deducting the capacity of minimum number of seniors and juniors.
     *
     * 1. It first tries if only the seniors can complete the remaining rooms without
     * over-capacity.
     * 2. Otherwise, it tries to find only juniors can complete the remaining rooms without
     * over-capacity
     * 3. Finally, it tries to find the optimum combination of seniors and juniors whose
     * total capacity is the closest number to the total number of rooms.
     *
     * @param numberOfRooms Each sum of rooms to be cleaned
     * @param seniorCapacity the senior capacity
     * @param juniorCapacity the junior capacity
     * @return the optimum combination of seniors and juniors whose total capacity
     * is the closest to the sum of the rooms
     */
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
    
    /**
     * To find the optimum combination of seniors and juniors for the
     * room capacity.
     *
     * @param roomCapacity the remaining room capacity
     * @param maxSeniors maximum number of seniors can clear the room
     * @param seniorCapacity cleaning capacity of the senior
     * @param juniorCapacity cleaning capacity of the junior
     * @return optimum cleaner set
     */
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
    
    /**
     * Function to get the ceiling
     *
     * @param dividend the dividend
     * @param divisor the divisor
     * @return Ceiling value as Integer
     */
    private Integer getCeil(Integer dividend, Integer divisor)
    {
        return (dividend % divisor == 0) ? (dividend / divisor) : ((dividend / divisor) + 1);
    }
}
