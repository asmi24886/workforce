package com.parijat.workforce.model;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@NoArgsConstructor
public class RequestModel
{
    private Integer[] rooms;
    private Integer senior;
    private Integer junior;
}
