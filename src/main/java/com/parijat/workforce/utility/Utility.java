package com.parijat.workforce.utility;

import com.parijat.workforce.model.CleanerSet;
import org.springframework.stereotype.Component;

@Component
public class Utility
{
    public Integer computeTotalCapacity(CleanerSet cleanerSet, Integer seniorCapacity, Integer juniorCapacity)
    {
        return ((seniorCapacity*cleanerSet.getSenior())+juniorCapacity*cleanerSet.getJunior());
    }
}
