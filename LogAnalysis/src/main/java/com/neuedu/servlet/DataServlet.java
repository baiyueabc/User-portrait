package com.neuedu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.been.Numbeen;
import com.been.trabeen;
import com.dao.NumDao;
import com.dao.traDao;
import com.dao.impl.NumDaoImpl;
import com.dao.impl.traDaoImpl;

public class DataServlet extends HttpServlet {

	public void service(HttpServletRequest req,HttpServletResponse resp)throws 
	ServletException,IOException{
		req.setCharacterEncoding("UTF-8");
		String method=req.getParameter("method");
		if("number".equals(method))
		{
			returnNumber(req,resp);
		}
		if("traffic".equals(method))
		{
			returntraber(req,resp);
		}
		
	}

	private void returnNumber(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		System.out.println("≤È—ØNumber");
		
		NumDao numdao=new NumDaoImpl();
		List<Numbeen> nums=numdao.getListAll();
		
		PrintWriter out=resp.getWriter();
		System.out.println(JSONObject.toJSONString(nums));
		out.print(JSONObject.toJSONString(nums));
		out.flush();
		out.close();

	}

	private void returntraber(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		System.out.println("≤È—ØNumber");
		
		traDao tradao= new traDaoImpl();
		List<trabeen> tras=tradao.getListAll();
		
		PrintWriter out=resp.getWriter();
		System.out.println(JSONObject.toJSONString(tras));
		out.print(JSONObject.toJSONString(tras));
		out.flush();
		out.close();

	}
}
