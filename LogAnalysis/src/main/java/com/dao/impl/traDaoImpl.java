package com.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.been.trabeen;
import com.dao.traDao;

public class traDaoImpl implements traDao{

	public List<trabeen> getListAll() {
		// TODO Auto-generated method stub
		List<trabeen> nums=new ArrayList<>();
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
			String sql = "select *  from traffic";
			pstm = conn.prepareStatement(sql);
			ResultSet set = pstm.executeQuery();
			while(set.next()){
				String type=set.getString("type");
				String type_id = set.getString("type_id");
				String number = set.getString("number");
				int id=set.getInt("id");
				System.out.println("ID:" + type_id + ",数量:" + number);
				trabeen num=new trabeen(type,type_id,number,id);
				nums.add(num);
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
		return nums;
	}
	
}
