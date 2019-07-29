package com.parijat.workforce.controller;

import com.parijat.workforce.model.CleanerSet;
import com.parijat.workforce.model.RequestModel;
import com.parijat.workforce.processor.IOptimizationProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("workforce")
public class WorkforceController
{
    @Autowired
    private IOptimizationProcessor optimizationProcessor;

   @PostMapping(
           value = "optimize",
           produces = {
                   MediaType.APPLICATION_JSON_VALUE
           }
   )
    public ResponseEntity<Object> getOptimumSolution(
           @RequestBody @Valid RequestModel request, Errors errors)
    {
        List<CleanerSet> response = optimizationProcessor.getOptimumSolution(
                request.getRooms(), request.getSenior(), request.getJunior()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
