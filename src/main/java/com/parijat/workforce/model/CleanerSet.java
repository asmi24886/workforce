package com.parijat.workforce.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Cleaner object of senior and junior.
 */
@Getter
@Setter
@ToString
public class CleanerSet implements Serializable
{
    private static final long serialVersionUID = 2297554345340826575L;

    private Integer senior;
    private Integer junior;
	
	/**
	 * Instantiates a new Cleaner set object with zero.
	 */
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
	
	@Override
	public int hashCode()
	{
		return Objects.hash(senior, junior);
	}
}
