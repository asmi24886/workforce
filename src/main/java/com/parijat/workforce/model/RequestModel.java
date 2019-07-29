package com.parijat.workforce.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class RequestModel
{
    private Integer[] rooms;
    private Integer senior;
    private Integer junior;
}
