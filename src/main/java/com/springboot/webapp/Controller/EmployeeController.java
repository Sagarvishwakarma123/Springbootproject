package com.springboot.webapp.Controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.webapp.exception.CustomNotFoundException;
import com.springboot.webapp.model.Employee;
import com.springboot.webapp.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/employees")
	@CrossOrigin
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
	
	@PostMapping("/employees")
	@CrossOrigin
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@GetMapping("/employees/{id}")
	@CrossOrigin
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) throws Exception {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() ->{return new CustomNotFoundException("Employee not exist with id:" +id);});
		return ResponseEntity.ok(employee);
	}
	
	@PutMapping("/employees/update/{id}")
	@CrossOrigin
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) throws Exception {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() ->{return new CustomNotFoundException("Employee not exist with id:" +id);});

		employee.setUsername(employeeDetails.getUsername());
		employee.setFirstname(employeeDetails.getFirstname());
		employee.setLastname(employeeDetails.getLastname());
		employee.setAge(employeeDetails.getAge());
		employee.setPassword(employeeDetails.getPassword());
		employee.setAddress(employeeDetails.getAddress());
		employee.setDate(employeeDetails.getDate());
		
		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	@DeleteMapping("/employees/{id}")
	@CrossOrigin
	public ResponseEntity<Employee> deleteEmployee(@PathVariable Long id) throws Exception {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() ->{return new CustomNotFoundException("Employee not exist with id:" +id);});
		employeeRepository.delete(employee);
		return ResponseEntity.ok(employee);
	}
	
}
