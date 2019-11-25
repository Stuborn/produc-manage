package tools;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DButils {
	private static SessionFactory sf = null ;
    private static Configuration config = null ;
    // private Session session = null ; // ���� ��session����Ϊ�����ֲ�����
    
    static {
    		config = new Configuration().configure();
		sf = config.buildSessionFactory();
    }
    
    public static Session getSession() throws Exception {
    		Session session = null ;
    		session = sf.openSession();
    		return session ;
    }
    
    public static Session getCurrentSession() throws Exception {
    		Session session = null ;
    		session = sf.getCurrentSession();
    		return session;
    }
    
    public static void flush(Session session) throws Exception {
    		session.close();
    		sf.close();
    }
    
}
