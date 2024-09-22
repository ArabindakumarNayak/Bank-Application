package com.Bank.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.Bank.Bean.ResultBean;
import com.Bank.Bean.TransactionBean;
import com.Bank.DBConnection.DBConnection;

public class LoginDAO {
	public static ResultBean checkLogin(String pword, long accno) {
		Connection con;
		ResultBean rb = null;
		try {
			con = DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from bankaccount where accno=? and password=?");
			ps.setLong(1, accno);
			ps.setString(2, pword);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				rb = new ResultBean();
				rb.setAccno(rs.getLong(1));
				rb.setName(rs.getString(2));
				rb.setBalance(rs.getDouble(3));
				rb.setAcctype(rs.getString(4));
				rb.setContact(rs.getLong(5));
				rb.setAddress(rs.getString(6));
				rb.setPassword(rs.getString(7));
			}
		} catch (Exception e) {

		}

		return rb;
	}

	public static ResultBean checkTransfer(long accno) {
		Connection con1;
		ResultBean rb1 = null;
		try {
			con1 = DBConnection.getConnection();
			PreparedStatement ps1 = con1.prepareStatement("select * from bankaccount where accno=?");
			ps1.setLong(1, accno);
			ResultSet rs1 = ps1.executeQuery();
			if(rs1.next()) {
				rb1=new ResultBean();
				rb1.setAccno(rs1.getLong(1));
				rb1.setName(rs1.getString(2));
				rb1.setBalance(rs1.getDouble(3));
				rb1.setAcctype(rs1.getString(4));
				rb1.setContact(rs1.getLong(5));
				rb1.setAddress(rs1.getString(6));
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rb1;

	}
	
	public static ResultBean allDetails(long accno) {
		Connection con2;
		ResultBean rb2=null;
		try {
			con2=DBConnection.getConnection();
			PreparedStatement ps2 = con2.prepareStatement("select * from bankaccount where accno=?");
			ps2.setLong(1, accno);
			ResultSet rs2 = ps2.executeQuery();
			if(rs2.next()) {
				rb2=new ResultBean();
				rb2.setAccno(rs2.getLong(1));
				rb2.setName(rs2.getString(2));
				rb2.setBalance(rs2.getDouble(3));
				rb2.setAcctype(rs2.getString(4));
				rb2.setContact(rs2.getLong(5));
				rb2.setAddress(rs2.getString(6));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rb2;
	}
	
	public static TransactionBean allTransaction(long accno) {
		Connection con3;
		TransactionBean tb1=null;
		try {
			con3=DBConnection.getConnection();
			PreparedStatement ps3 = con3.prepareStatement("select * from transactiontable where accno=?");
			ps3.setLong(1, accno);
			ResultSet rs3 = ps3.executeQuery();
			if(rs3.next()) {
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return tb1;
	}
}
