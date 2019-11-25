package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import tools.DButils;
import entity.User;

public class login {
	@Qualifier("sessionFactory")
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public String findpassword(String username) throws Exception{
		Session session = sessionFactory.getCurrentSession();
		Transaction tx=session.beginTransaction();
		Query<User> q=session.createQuery(" from User user where user.username=?1");
		q.setParameter(1, username);
		List<User> u1=q.list();
		if(u1.size()==0){
			tx.commit();
			
			//DButils.flush(session);
			return "";
		}else{
			String result=u1.get(0).getPassword();
			tx.commit();
			//DButils.flush(session);
			return result;
		}

	}


}
