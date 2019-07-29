package com.parijat.workforce.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;


@Data
@ToString
public class CleanerSet implements Serializable
{
    private static final long serialVersionUID = 2297554345340826575L;

    private Integer senior;
    private Integer junior;

    public CleanerSet()
    {
        this.senior=0;
        this.junior=0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CleanerSet that = (CleanerSet) o;
        return senior.equals(that.senior) &&
                junior.equals(that.junior);
    }
}
