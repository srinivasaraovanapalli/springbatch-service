package com.springbatch.net.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="EMPLOYEE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	
	@Id
	@Column(name="EMP_ID")
	private int id;
	@Column(name="NAME")
	private String name;
	@Column(name="MOBILE")
	private String mobile;
	@Column(name="SALARY")
	private int salary;
	@Column(name="DEPT")
	private String dept;

}
