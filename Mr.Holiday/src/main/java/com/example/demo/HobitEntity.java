package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Hobit")
@Data
@NoArgsConstructor
public class HobitEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Integer id;
	
	@Column( length=100,nullable=false)
	@NotBlank
	private String genre;
	
	@Column(nullable=false)
	private String mood;
	
	@Column(length=100,nullable=true)
	@NotEmpty
	@Size(max=100)
	private String place;
	
	@Column(length=100,nullable=true)
	@NotEmpty
	@Size(max=100)
	private String content;
	
	@Column(length=30,nullable=true)
	private String favorite;
}

