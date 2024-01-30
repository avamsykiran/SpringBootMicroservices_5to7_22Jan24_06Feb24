package com.cts.empsapi.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="emps")
@Data
@NoArgsConstructor
@XmlRootElement
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long empId;
	
	@NotNull(message = "Full Name is a manditory field")
	@NotBlank(message = "Full Name can not be blank")
	@Column(name="fnm",nullable = false)
	private String fullName;
	
	@NotNull(message = "BasicPay is a manditory field")
	@Min(value = 1,message = "BasicPay can not be zero or negative")
	@Column(name="bpay",nullable = false)
	private Double basicPay;
	
	@NotNull(message = "Mobile is a manditory field")
	@Pattern(regexp = "[1-9][0-9]{9}",message = "Mobile must be a 10 digit number")
	@Column(name="mno",nullable = false,unique = true)	
	private String mobile;
	
	@NotNull(message = "MailId is a manditory field")
	@NotBlank(message = "MailId can not be blank")
	@Email(message = "MailId can not be invalid")
	@Column(name="mid",nullable = false,unique = true)
	private String mailId;
	
	@NotNull(message = "JoinDate is a manditory field")
	@PastOrPresent(message = "Join Date must be either a past or a present date")
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name="dt",nullable = false)
	private LocalDate joinDate;
	
	@NotNull(message = "Gender is a manditory field")
	@Enumerated(EnumType.STRING)
	@Column(name="gen",nullable = false)
	private Gender gender;

	
}
