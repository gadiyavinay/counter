/*
 * Copyright © 2021 Assa Abloy. All Rights Reserved.
 */
package com.assaabloy.counter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "counter")
public class Counter {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "name", nullable = false, length = 255, unique = true)
	private String name;
	
	@Column(name = "value", nullable = false, length = 255)
	private long value;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Counter [id=" + id + ", name=" + name + ", value=" + value + "]";
	}
	
	
	
}
