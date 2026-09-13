package com.Fee.MyFee.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="admin_myfees")
public class MyFee_Entity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(nullable = false, name="program_name")
	private String programName;
	
	@Column(nullable=false, name="category_name")
	private String categoryName;
	
	@Column(name="fees")
	private Double fees;
	
	@Column(name="paid")
	private Double paid = 0.0;
	
	@Column(name="balance")
	private Double balance;
	
	public MyFee_Entity() {};

	public MyFee_Entity(String programName, String categoryName, Double fees, Double paid, Double balance) {
		this.programName = programName;
		this.categoryName = categoryName;
		this.fees = fees;
		this.paid = paid;
		this.balance = balance;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Double getFees() {
		return fees;
	}

	public void setFees(Double fees) {
		this.fees = fees;
	}

	public Double getPaid() {
		return paid;
	}

	public void setPaid(Double paid) {
		this.paid = paid;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	
	
}
