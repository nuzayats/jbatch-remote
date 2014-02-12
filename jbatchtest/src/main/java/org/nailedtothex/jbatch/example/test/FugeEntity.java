package org.nailedtothex.jbatch.example.test;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FugeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public FugeEntity() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Long id;

	@Column
	private String fuge;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFuge() {
		return fuge;
	}

	public void setFuge(String fuge) {
		this.fuge = fuge;
	}

}
