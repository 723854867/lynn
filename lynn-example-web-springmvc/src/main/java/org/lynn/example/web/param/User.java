package org.lynn.example.web.param;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.lynn.common.validate.Upload;
import org.springframework.web.multipart.MultipartFile;

public class User {

	@NotBlank(message = "{user.name.null}")
	@Size(min = 2, max = 5, message = "{user.name.len}")
	private String name;
	private String pwd;
	@Upload(message = "{upload.size}", maxmium = 10240000)
	private MultipartFile avatar;
	private MultipartFile identity;
	private String date;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPwd() {
		return pwd;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public MultipartFile getAvatar() {
		return avatar;
	}
	
	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
	}
	
	public MultipartFile getIdentity() {
		return identity;
	}
	
	public void setIdentity(MultipartFile identity) {
		this.identity = identity;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
}
