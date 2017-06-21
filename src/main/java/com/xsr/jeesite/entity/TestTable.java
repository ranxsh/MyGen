package com.xsr.jeesite.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TestTable {

	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    private Integer id;

	/**
	 * 
	 */
    private String value;

    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}