package com.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.EmployeeDAO;
import com.model.Employee;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		HttpSession session = request.getSession(true);
		if(email.equals("admin@codegnan.com")&&password.equals("admin@cg")) {
			session.setAttribute("email", email);
			response.sendRedirect("admin.jsp");
		} else {
			EmployeeDAO dao = new EmployeeDAO();
			try {
				boolean status = dao.checkLogin(email,password);
				if(status) {
					Employee employee = dao.searchEmployee(email);
					session.setAttribute("email", email);
					session.setAttribute("name", employee.getName());
					response.sendRedirect("employee.jsp");
				}else {
					request.setAttribute("status", "Invalid Credentials");
					RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
					dispatcher.forward(request, response);
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
}