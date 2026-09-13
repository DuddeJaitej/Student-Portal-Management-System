package com.Fee.MyFee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Fee.MyFee.Entity.MyFee_Entity;
import com.Fee.MyFee.Service.MyFee_Service;

@RestController
@RequestMapping("/Admin/admin_branch_fee")
@CrossOrigin("*")
public class MyFee_Controller {

	@Autowired
	private MyFee_Service service;
	
	@PostMapping("/create_adminFee")
	public List<MyFee_Entity> createFee(@RequestBody List<MyFee_Entity> create) {
		return service.createFee(create);
	}
	
	@GetMapping("/all")
	public List<MyFee_Entity> getAllFees() {
		return service.getAllFees();
	}
	
	@GetMapping("/by-program/{programName}")
	public List<MyFee_Entity> getFeesByProgram(@PathVariable String programName) {
		return service.getFeesByProgram(programName);
	}
	
}
