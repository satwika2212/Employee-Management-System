package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.model.Employee;

public class EmployeeDAO {
	Connection connection = null;
	public boolean saveuser(Employee employee) throws ClassNotFoundException, SQLException {
		connection = ConnectionManager.getConnection();
		String query = "INSERT INTO Employee VALUES(?,?,?,?,?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, employee.getEmail());
		statement.setString(2, employee.getName());
		statement.setString(3, employee.getPassword());
		statement.setInt(4, employee.getAge());
		statement.setString(5, employee.getGender());
		statement.setString(6, employee.getMobile());
		statement.setString(7, employee.getDepartment());
		statement.setString(8, employee.getAddress());
		int count = statement.executeUpdate();
		if(count==1) return true;
		else return false;
	}
	public void commit() throws SQLException {
		connection.commit();
		connection.close();
	}
	public void rollback() throws SQLException {
		connection.rollback();
		connection.close();
	}
	public boolean checkLogin(String email, String password) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionManager.getConnection();
		connection.setAutoCommit(true);
		String query = "SELECT COUNT(*) FROM EMPLOYEE WHERE EMAIL = ? AND PASSWORD = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, email);
		statement.setString(2, password);
		ResultSet set = statement.executeQuery();
		int count = 0;
		if(set.next()) {
			count = set.getInt(1);
		}
		connection.close();
		if(count==1) {
			return true;
		}
		return false;
	}
	public List<Employee> findAll() throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionManager.getConnection();
		String query = "SELECT * FROM EMPLOYEE";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet set = statement.executeQuery();
		List<Employee> empList = new ArrayList<Employee>();
		while(set.next()) {
			Employee employee = new Employee();
			employee.setEmail(set.getString(1));
			employee.setName(set.getString(2));
			employee.setPassword(set.getString(3));
			employee.setAge(set.getInt(4));
			employee.setGender(set.getString(5));
			employee.setMobile(set.getString(6));
			employee.setDepartment(set.getString(7));
			employee.setAddress(set.getString(8));
			empList.add(employee);
		}
		return empList;
	}
	public boolean updateEmployee(Employee employee) throws ClassNotFoundException, SQLException {
		connection = ConnectionManager.getConnection();
		String query = "UPDATE EMPLOYEE SET NAME = ?, AGE = ?, GENDER = ?, MOBILE = ?, DEPARTMENT = ?, ADDRESS = ? WHERE EMAIL = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, employee.getName());
		statement.setInt(2, employee.getAge());
		statement.setString(3, employee.getGender());
		statement.setString(4, employee.getMobile());
		statement.setString(5, employee.getDepartment());
		statement.setString(6, employee.getAddress());
		statement.setString(7, employee.getEmail());
		int count = statement.executeUpdate();
		if(count==1) {
			return true;
		}
		return false;
	}
	public Employee searchEmployee(String email) throws ClassNotFoundException, SQLException {
		Connection connection = ConnectionManager.getConnection();
		connection.setAutoCommit(true);
		String query = "SELECT * FROM EMPLOYEE WHERE EMAIL=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1,email);
		ResultSet set = statement.executeQuery();
		Employee employee = new Employee();
		while(set.next()) {
			employee.setEmail(set.getString(1));
			employee.setName(set.getString(2));
			employee.setPassword(set.getString(3));
			employee.setAge(set.getInt(4));
			employee.setGender(set.getString(5));
			employee.setMobile(set.getString(6));
			employee.setDepartment(set.getString(7));
			employee.setAddress(set.getString(8));
		}
		return employee;
	}
	public void deleteEmployee(String email) throws ClassNotFoundException, SQLException {
		connection = ConnectionManager.getConnection();
		String query = "DELETE FROM EMPLOYEE WHERE EMAIL = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, email);
		statement.executeUpdate();
	}
}
