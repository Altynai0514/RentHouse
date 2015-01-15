package com.pb.entity;


public class HouseType implements java.io.Serializable {
	private static final long serialVersionUID = 7749380828170192278L;

	// Fields
	private Integer id;
	private String name;

	// Constructors
	/** default constructor */
	public HouseType() {
	}

	/** minimal constructor */
	public HouseType(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}