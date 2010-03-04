package org.concord.geniverse.client;

import java.io.Serializable;

public class GOrganism implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private int sex;
	private String alleles;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getSex() {
		return sex;
	}

	public void setAlleles(String alleles) {
		this.alleles = alleles;
	}

	public String getAlleles() {
		return alleles;
	}
}
