package com.neuedu.mapreduce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class logtongji {

	public static void main(String[] args) {
		query();

	}
	private static void query(){
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			//加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			//建立连接
			String url = "jdbc:mysql://192.168.22.141:3306/shuju?characterEncoding=utf-8";
			String user = "root";
			String password = "123456";
			conn = DriverManager.getConnection(url, user, password);
			//获取数据库预编译对象
			String sql = "select type,type_id,count(*) as count from data where type='video' group by type_id order by count desc limit 10";
			pstm = conn.prepareStatement(sql);
			ResultSet set = pstm.executeQuery();
			while(set.next()){
				String type=set.getString("type");
				String type_id = set.getString("type_id");
				String count = set.getString("count");
				insert(type,type_id,count);
				System.out.println("ID:" + type_id + ",数量:" + count);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			//关闭资源
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstm != null){
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	private static void insert(String type,String type_id, String number){
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://192.168.22.141:3306/shuju?characterEncoding=utf-8";
			String user = "root";
			String password = "123456";
			con = DriverManager.getConnection(url, user, password);
			
			String sql = "insert into video(type,type_id,number) value(?,?,?)";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, type);
			pstm.setString(2, type_id);
			pstm.setString(3, number);
			int row = pstm.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstm != null){
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}

