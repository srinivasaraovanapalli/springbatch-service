package com.springbatch.net.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springbatch.net.entity.Employee;

//@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Serializable>{

}
