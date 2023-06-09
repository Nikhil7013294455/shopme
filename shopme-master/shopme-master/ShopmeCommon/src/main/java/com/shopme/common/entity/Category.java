package com.shopme.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

@Entity
@Table(name="categories")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 128,nullable=false,unique=true)
	private String name;
	
	@Column(length = 64,nullable=false,unique=true)
	private String alias;
	
	@Column(length = 128,nullable=false)
	private String image;
	
	private boolean enabled;
	
	@OneToOne
	@JoinColumn(name="parent_id")
	private Category parent;
	
	@OneToMany(mappedBy="parent")
	private Set<Category> children = new HashSet<>();
	
	
	
	public Category() {
		
	}

	public Category(Integer id) {
		super();
		this.id = id;
	}

	public static Category copyIdAndName(Category cat) {
		Category copy = new Category();
		copy.setId(cat.getId());
		copy.setName(cat.getName());
		return copy;
	}

	public static Category copyIdAndName(Integer id,String name) {
		Category copy = new Category();
		copy.setId(id);
		copy.setName(name);
		return copy;
	}
	
	public static Category copyFull(Category cat, String name) {
		Category copy = Category.copyFull(cat);
		copy.setName(name);
		return copy;
	}

	public static Category copyFull(Category cat) {
		Category copy = new Category();
		copy.setId(cat.getId());
		copy.setName(cat.getName());
		copy.setImage(cat.getImage());
		copy.setAlias(cat.getAlias());
		copy.setEnabled(cat.isEnabled());
		return copy;
	}
	
	public Category(String name) {
		
		this.name = name;
		this.alias = name;
		this.image = "default.png";
	}
	
public Category(String name,Category parent) {
		this.parent = parent;
		this.name = name;
		this.alias = name;
		this.image = "default.png";
	}
	
	
	
	public Category(Integer id, String name, String alias) {
	
	this.id = id;
	this.name = name;
	this.alias = alias;
}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
	}
	
	@Transient
	public String getImagePath() {
		
		if(this.id==null) return "images/image-thumbnail.png";
		
		return "/categories-images/"+this.id+"/"+this.image;
	}
	
	
}
