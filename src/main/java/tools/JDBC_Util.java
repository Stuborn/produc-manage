package tools;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jdbc ������
 * @author lx
 */

public class JDBC_Util {
	
	private static Connection conn = null ;
	private static PreparedStatement pstmt = null ;
	private static ResultSet rs = null ;
	
	/**
	 * ��������
	 */
	private static void createConn(){
		try{
			Class.forName(Const.driver);
			conn = DriverManager.getConnection(Const.url,Const.userName,Const.password);
		}catch(ClassNotFoundException ex){
			ex.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡList<Map>���͵����ݼ���
	 * @param sql Ҫִ�е�sql���
	 * @param args ��������
	 * @return ���ݼ���  List<Map<String,Object>>
	 */
	public static List getDataToListMap(String sql,Object[] args){
		
		List list = new ArrayList();
		
		createConn();
		
		try{
			pstmt = conn.prepareStatement(sql);
			if(args != null && args.length > 0){
				for(int i = 0; i < args.length ; i ++){
					pstmt.setObject(i+1, args[i]);
				}
			}
			rs = pstmt.executeQuery();
			
			ResultSetMetaData metaData = rs.getMetaData();
		    int col_len = metaData.getColumnCount();
		    
	        while (rs.next())
	        {
	        	Map map = new HashMap();
	        	/**
	        	 * ͨ�������ȡ����
	        	 */
	            for (int i = 0; i < col_len; i++)
	            {
	                String cols_name = metaData.getColumnName(i + 1);
	                Object cols_value = rs.getObject(cols_name);
	                if (cols_value == null)
	                {
	                    cols_value = "";
	                }
	                map.put(cols_name, cols_value);
	            }
	            list.add(map);
	        }
		}catch(SQLException ex){
			ex.printStackTrace();
		}catch (SecurityException e) {
			e.printStackTrace();
		} finally{
			releaseConn();
		}
		return list ;
	}
	
	/**
	 * ʹ��ʵ����ɼ�����
	 * @param sql Ҫִ�е�sql���
	 * @param beanClass ʵ���������
	 * @param params ���̶��Ĳ�������
	 * @return δ֪���͵����ݼ���    List<T>
	 * @throws SQLException sql�쳣
	 * @throws InstantiationException
	 * @throws IllegalAccessException  
	 */
	public static <T> List<T> queryBeanList( String sql, Class<T> beanClass, Object... params){
		
		createConn();
		
        List<T> lists = new ArrayList<T>();
        Field[] fields = null;
        try {
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++){
            	pstmt.setObject(i + 1, params[i]);// �±��1��ʼ
            }
            rs = pstmt.executeQuery();
            fields = beanClass.getDeclaredFields();
            for (Field f : fields){
            	//��˽�����Եķ���Ȩ��
            	f.setAccessible(true);
            }
            if(null != rs){
            	while (rs.next()) {
                    T t = beanClass.newInstance();
                    for (Field f : fields) {
                        String name = f.getName();
                        Object value = rs.getObject(name);
                        setValue(t, f, value);
                    }
                    lists.add(t);
                }
            }
        }catch(SQLException ex) {
        	ex.printStackTrace();
        } catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}finally {
        	releaseConn();
        }
        return lists;
    }
	
	/**
	 * ��ȡ��һʵ��
	 * @param con
	 * @param sql
	 * @param beanClass
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T> T queryBean(String sql, Class<T> beanClass,Object... params) throws SQLException,
	 	InstantiationException, IllegalAccessException {
		List<T> lists = queryBeanList(sql, beanClass,params);
		if ( lists != null && lists.size() > 0 )
			return lists.get(0);
		else
			return null;
	}
	
	/**
	 * ִ�в������ɾ������
	 * @param sql Ҫִ�е����
	 * @param params ���̶��Ĳ���
	 * @return ����ɹ��򷵻سɹ�
	 */
	public static boolean updateDb(String sql,Object... params){
		boolean flag = false;
		createConn();
		try{
			pstmt = conn.prepareStatement(sql);
			for(int i = 0;i < params.length; i++){
				pstmt.setObject(i+1, params[i]);
			}
			int count = pstmt.executeUpdate();
			flag = count > 0?true:false;
			return flag;
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			releaseConn();
		}
		return flag;
	}
	
	/**
	 * ʵ���ำֵ
	 * @param t ʵ������
	 * @param f ����
	 * @param value ֵ
	 * @throws IllegalAccessException
	 */
    private static <T> void setValue(T t, Field f, Object value) throws IllegalAccessException {
        if (null == value)
            return;
        String v = value.toString();
        String n = f.getType().getName();
        if ("java.lang.Byte".equals(n) || "byte".equals(n)) {
            f.set(t, Byte.parseByte(v));
        } else if ("java.lang.Short".equals(n) || "short".equals(n)) {
            f.set(t, Short.parseShort(v));
        } else if ("java.lang.Integer".equals(n) || "int".equals(n)) {
            f.set(t, Integer.parseInt(v));
        } else if ("java.lang.Long".equals(n) || "long".equals(n)) {
            f.set(t, Long.parseLong(v));
        } else if ("java.lang.Float".equals(n) || "float".equals(n)) {
            f.set(t, Float.parseFloat(v));
        } else if ("java.lang.Double".equals(n) || "double".equals(n)) {
            f.set(t, Double.parseDouble(v));
        } else if ("java.lang.String".equals(n)) {
            f.set(t, value.toString());
        } else if ("java.lang.Character".equals(n) || "char".equals(n)) {
            f.set(t, (Character) value);
        } else if ("java.lang.Date".equals(n)) {
            f.set(t, new Date(((java.sql.Date) value).getTime()));
        } else if ("java.lang.Timer".equals(n)) {
            f.set(t, new Time(((Time) value).getTime()));
        } else if ("java.sql.Timestamp".equals(n)) {
            f.set(t, (java.sql.Timestamp) value);
        } else if ("java.util.Date".equals(n)) {
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            f.set(t, format.format(value));
        } else {
            System.out.println("SqlError����ʱ��֧�ִ��������ͣ���ʹ���������ʹ�������ͣ�");
        }
    }
    
    /**
     * �ͷ���Դ
     */
	private static void releaseConn()
    {
		try{
			if(rs != null)
				rs.close();
			if(pstmt != null)
				pstmt.close();
			if(conn != null) 
				conn.close();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
    }
	
	public static <T> List<T> queryBeanList2( String sql, Class<T> beanClass, Object... params){
		
		createConn();
		
        List<T> lists = new ArrayList<T>();
        Field[] fields = null;
        try {
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++){
            	//pstmt.setObject(i + 1, params[i]);// �±��1��ʼ
            	pstmt.setString(i+1, (String)params[i]);
            }
            System.out.println(pstmt);
            rs = pstmt.executeQuery();
            fields = beanClass.getDeclaredFields();
            for (Field f : fields){
            	//��˽�����Եķ���Ȩ��
            	f.setAccessible(true);
            }
            if(null != rs){
            	while (rs.next()) {
                    T t = beanClass.newInstance();
                    for (Field f : fields) {
                        String name = f.getName();
                        Object value = rs.getObject(name);
                        setValue(t, f, value);
                    }
                    lists.add(t);
                }
            }
        }catch(SQLException ex) {
        	ex.printStackTrace();
        } catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}finally {
        	releaseConn();
        }
        return lists;
    }
	
}
