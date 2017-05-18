package com.jx372.emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jx372.emaillist.vo.EmaillistVo;


public class EmaillistDao {
	private Connection getConnection() throws SQLException {
		
		Connection conn = null;
		
		try {
			//1. 드라이버 로딩
			Class.forName( "com.mysql.jdbc.Driver" );
			
			//2. Connection 하기
			String url = "jdbc:mysql://localhost:3306/webdb?useUnicode=true&characterEncoding=utf8";
			conn = DriverManager.getConnection( url, "webdb", "webdb" );
		} catch( ClassNotFoundException e ) {
			System.out.println( "JDBC Driver 를 찾을 수 없습니다." );
		} 
		
		return conn;
	}
	
	public List<EmaillistVo> getList(){
		Connection conn = null;
		List<EmaillistVo> list = new ArrayList<EmaillistVo>();
		Statement stmt  = null;
		ResultSet rs = null;
	
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			String sql = "select no,last_name,first_name,email from emaillist order by no desc";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				Long no = rs.getLong(1);
				String lastName = rs.getString(2);
				String firstName = rs.getString(3);
				String email = rs.getString(4);
				
				EmaillistVo vo = new EmaillistVo();
				
				vo.setFirstName(firstName);
				vo.setEmail(email);
				vo.setLastName(lastName);
				vo.setNo(no);

				list.add(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
				conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return list;
	}
	
	public boolean insert(EmaillistVo vo){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {	
			conn = getConnection();
			
			String sql = "insert into emaillist values( null, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getLastName());
			pstmt.setString(2, vo.getFirstName());
			pstmt.setString(3, vo.getEmail());
			
			int count = pstmt.executeUpdate();
			
			return count == 1;  //0이 반환되면 실패 1이 반환되면 성공
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try{
				
			if(pstmt != null){
				pstmt.close();
			}
			if(conn != null){
				conn.close();
			}
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
}
