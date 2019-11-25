package service;


import dao.insert;
import dao.login;

public class product_service {
	private insert insert;

	public void setInsert(dao.insert insert) {
		this.insert = insert;
	}

	public String registservice(String username, String password, String role) throws Exception{
		//insert=new insert();
		String result=insert.insertsql(username,password,role);
		if(result.equals("success")){
			return "success";
		}else{
			return "";
		}
	}
	public String loginservice(String username) throws Exception{
		login login=new login();
		String resultpassword=login.findpassword(username);
		return resultpassword ;
	}

}
