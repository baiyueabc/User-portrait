package com.neuedu.mapreduce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.File;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;



public class shujuclean {
	public static String shuju="";
	public static void main(String[] args) throws Exception {
		read();
	
	}
	
	
	public static void read(){
		// 创建一个 配置对象
		Configuration conf=new Configuration();
		// 在配置对象中 设置 namenode
		conf.set("fs.defaultFS", "hdfs://192.168.22.141:9000");
		try {
			// 创建 FileSystem 
			FileSystem fs=FileSystem.get(conf) ;
			// 创建 输入流
			FSDataInputStream inputstream = fs.open(new Path("/hadoop/log.log"));
			// 字节流转字符流
			InputStreamReader isr=new InputStreamReader(inputstream);
			BufferedReader br=new BufferedReader(isr);
			String line=br.readLine();
			int i=0;
			while(line!=null){
				
				String ip = line.substring(0, line.indexOf(" "));
				String ip1 = getCityInfo(ip);
			    String[] words = ip1.split("\\|");
			    String ip2=words[3];
				String time = line.substring(line.indexOf("[")+1, line.indexOf("]"));
				String time1=time.replace(" ","");
				String day =  time.substring(0,time.indexOf("/"));
				String traffic =line.substring(StringUtils.ordinalIndexOf(line,"\"",2)+2, StringUtils.ordinalIndexOf(line,"\"",3));
				String tra = traffic.substring(traffic.indexOf(" ")+1);
				String str  = line.substring(StringUtils.ordinalIndexOf(line,"\"",5)+1, StringUtils.ordinalIndexOf(line,"\"",6));
				String type = null;
				String id = null;
				
				if(str != null)
				{
					if(str.contains("video"))
					{
						type = "video";
						id = str.substring(str.indexOf("video"));
						String str1 ,str2;
						if(id.contains("/"))
						{
							str1 = id.substring(id.indexOf("/")+1);
							if (str1.contains("?"))
							{
								str2 = str1.substring(0,str1.indexOf("?"));
								id =str2;
								
							}
							else if (str1.contains("/"))
							{
								str2 = str1.substring(0,str1.indexOf("/"));
								id =str2;
								
							}
							else
								id = str1;
						}
						else
							id = "";
						
					}
					else if (str.contains(".com/article"))
					{
						type = "article";
						id = str.substring(str.indexOf("article"));
						String str1,str2 ;
						if(id.contains("/"))
						{
							str1 = id.substring(id.indexOf("/")+1);
							if (str1.contains("?"))
							{
								str2 = str1.substring(0,str1.indexOf("?"));
								id =str2;
								
							}
							else if (str1.contains("/"))
							{
								str2 = str1.substring(0,str1.indexOf("/"));
								id =str2;
								
							}
							else
								id = str1;
							
						}
						else
							id = "";
						
					}
			
					if(type != null&& id !=""){
						 
						//shuju+=words[3]+"	"+time1+"	"+day+"	"+tra+"	"+type+"	"+id+"\n";
		  			    insert(ip2,time1,day,tra,type,id);
		  			    i++;
						System.out.println(i);    
					}
					
				}
				
				
				line=br.readLine();
			}
			//System.out.println(i);
			br.close();
			isr.close();
			inputstream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public static void write(){
		Configuration conf=new Configuration();
		conf.set("fs.defaultFS", "hdfs://192.168.22.141:9000");
		try {
			FileSystem fs=FileSystem.get(conf);
			FSDataOutputStream outputStream = fs.create(new Path("/hadoop/abcd.txt"));
			outputStream.writeBytes(shuju);;
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	private static void insert(String ip, String time,String day,String traffic,String type,String type_id){
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://192.168.22.141:3306/shuju?characterEncoding=utf-8";
			String user = "root";
			String password = "123456";
			con = DriverManager.getConnection(url, user, password);
			
			String sql = "insert into datat(ip,time,day,traffic,type,type_id) value(?,?,?,?,?,?)";
			pstm = con.prepareStatement(sql);
			pstm.setString(1, ip);
			pstm.setString(2, time);
			pstm.setString(3, day);
			pstm.setString(4, traffic);
			pstm.setString(5, type);
			pstm.setString(6, type_id);
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

	
		
		public static String getCityInfo(String ip){
	        //db
	        String dbPath = shujuclean.class.getResource("/ip2region.db").getPath();
	 
	        File file = new File(dbPath);
	        if ( file.exists() == false ) {
	            System.out.println("Error: Invalid ip2region.db file");
	        }
	 
	        //查询算法
	        int algorithm = DbSearcher.BINARY_ALGORITHM; //B-tree
	        try {
	            DbConfig config = new DbConfig();
	            DbSearcher searcher = new DbSearcher(config, dbPath);
	 
	            //define the method
	            Method method = null;
	            switch ( algorithm )
	            {
	            case DbSearcher.BTREE_ALGORITHM:
	                method = searcher.getClass().getMethod("btreeSearch", String.class);
	                break;
	            case DbSearcher.BINARY_ALGORITHM:
	                method = searcher.getClass().getMethod("binarySearch", String.class);
	                break;
	            case DbSearcher.MEMORY_ALGORITYM:
	                method = searcher.getClass().getMethod("memorySearch", String.class);
	                break;
	            }
	 
	            DataBlock dataBlock = null;
	            if ( Util.isIpAddress(ip) == false ) {
	                System.out.println("Error: Invalid ip address");
	            }
	 
	            dataBlock  = (DataBlock) method.invoke(searcher, ip);
	 
	            return dataBlock.getRegion();
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
		
}


