package dao;

import entity.User;
import entity.UserClassEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import tools.DButils;

public class insert  {
	@Qualifier("sessionFactory")
	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String insertsql(String username, String password, String role) throws Exception{
		Session session = sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		UserClassEntity u2=new UserClassEntity();
		u2.setUsername(username);
		u2.setPassword(password);
		u2.setPermission(role);
		//User u1=new User();
		//u1.setPassword(password);
		//u1.setUsername(username);
		//u1.setPermission(role);
		session.save(u2);
		tx.commit();
		session.close();
		sessionFactory.close();
		//DButils.flush(session);
		return "success";
	}

}
