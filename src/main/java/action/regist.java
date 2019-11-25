package action;

import service.product_service;
import tools.MyMd5;

import com.opensymphony.xwork2.ActionSupport;

public class regist extends ActionSupport{
    
    private String username;
    private String password;
    private String role;
    private product_service service;

	public void setService(product_service service) {
		this.service = service;
	}

	public String  checkregist() throws Exception {
		System.out.println(password);
		String password2=MyMd5.encrypt(password);
		//product_service service=new product_service();
		System.out.println(password2);
		String result=service.registservice(username,password2,role);
		System.out.println(role);
		if(result.equals("success")){
			return SUCCESS;
		}else{
			return NONE;
		}
    	
    }
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
