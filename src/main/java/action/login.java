package action;

import service.product_service;
import tools.MyMd5;

import com.opensymphony.xwork2.ActionSupport;


public class login extends ActionSupport{

    //private static final long serialVersionUID = 7922979648150320921L;

    private String username;
    private String password;
    private product_service service;

    public void setService(product_service service) {
        this.service = service;
    }

    public String checkLogin() throws Exception{
    	//product_service service =new product_service();
    	String password2=MyMd5.encrypt(password);
    	String resultpassword =service.loginservice(username); 	
        if(password2.equals(resultpassword)&&!resultpassword.equals("")){
            return SUCCESS;    //µÇÂ½³É¹¦·µ»ØSUCCESS
        }else{
            return NONE;    //µÇÂ½Ê§°Ü·µ»ØNONE
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
}

