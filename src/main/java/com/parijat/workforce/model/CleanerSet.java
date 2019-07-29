package com.parijat.workforce.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
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
        return Objects.equals(senior, that.senior) &&
                Objects.equals(junior, that.junior);
    }
}
