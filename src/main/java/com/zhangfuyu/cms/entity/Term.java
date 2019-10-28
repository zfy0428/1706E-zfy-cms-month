package com.zhangfuyu.cms.entity;

import java.io.Serializable;
//文章的标签
public class Term implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5598851035567656863L;
	Integer id;
	String display_name;
	String unique_name;
	
	
	public Term(String display_name, String unique_name) {
		super();
		this.display_name = display_name;
		this.unique_name = unique_name;
	}
	public Term(Integer id, String display_name, String unique_name) {
		super();
		this.id = id;
		this.display_name = display_name;
		this.unique_name = unique_name;
	}
	public Term() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getUnique_name() {
		return unique_name;
	}
	public void setUnique_name(String unique_name) {
		this.unique_name = unique_name;
	}
	@Override
	public String toString() {
		return "Term [id=" + id + ", display_name=" + display_name
				+ ", unique_name=" + unique_name + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((display_name == null) ? 0 : display_name.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((unique_name == null) ? 0 : unique_name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Term other = (Term) obj;
		if (display_name == null) {
			if (other.display_name != null)
				return false;
		} else if (!display_name.equals(other.display_name))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (unique_name == null) {
			if (other.unique_name != null)
				return false;
		} else if (!unique_name.equals(other.unique_name))
			return false;
		return true;
	}
	
	
}
