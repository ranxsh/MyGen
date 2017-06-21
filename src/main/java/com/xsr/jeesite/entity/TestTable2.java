package com.xsr.jeesite.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TestTable2 {

	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    private Integer id2;

	/**
	 * name
	 */
    private String name;
	/**
	 * name
	 */
    private Integer id;

    public Integer getId2() {
        return this.id2;
    }
    public void setId2(Integer id2) {
        this.id2 = id2;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}