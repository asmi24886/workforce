package com.parijat.workforce.model;

import lombok.*;

import javax.validation.constraints.*;

/**
 * The type Request body to get optimum combination
 * of seniors and juniors.
 */
@Getter
@Setter
@NoArgsConstructor
public class RequestModel
{
    @Size(min = 1, max = 100, message = "The structure size can not be more than 100 or less than 1")
    @NotNull(message = "The structure can not be null")
    private Integer[] rooms;
    
    @Min(value = 1, message = "The cleaning capacity of senior must be more than 0")
    @NotNull(message = "The cleaning capacity of senior can not be null")
    private Integer senior;
    
    @Min(value = 1, message = "The cleaning capacity of junior must be more than 0")
    @NotNull(message = "The cleaning capacity of junior can not be null")
    private Integer junior;
}
