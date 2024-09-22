package Final_Account_Project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import oracle.sql.DATE;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.Bank.Bean.ResultBean;
import com.Bank.DBConnection.DBConnection;

@WebServlet("/depositeSer")
public class DepositeServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		ResultBean rb = (ResultBean) session.getAttribute("ResultBean");

		double lastBalance = rb.getBalance();
		Connection con = DBConnection.getConnection();

		String amount = request.getParameter("money");

		char[] arr = amount.toCharArray();
		boolean flag = true;
		int dotcnt = 0;
		int nevcnt = 0;
		try {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] == '-') {
					nevcnt++;
				}
				if (arr[i] == '.') {
					dotcnt++;
				}
				if (Character.isAlphabetic(arr[i])) {
					flag = false;
				} else if (!(Character.isDigit(arr[i]) || arr[i] == '.' || arr[i] == '-')) {
					flag = false;
				}
			}

			if (dotcnt > 1 || nevcnt > 1) {
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag) {
			double depamt = Double.parseDouble(amount);
			if (depamt > 0) {
				double updatedAmount = lastBalance + depamt;
				try {
					PreparedStatement ps = con.prepareStatement("update bankaccount set balance=? where accno=?");
					ps.setDouble(1, updatedAmount);
					ps.setLong(2, rb.getAccno());
					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						rb.setBalance(updatedAmount);
						PreparedStatement pstransaction = con
								.prepareStatement("insert into transactionTable values(?,s1.nextval,SYSDATE,?,?)");
						pstransaction.setLong(1, rb.getAccno());
						pstransaction.setDouble(2, depamt);
						pstransaction.setString(3, "deposite");
						int executeUpdate = pstransaction.executeUpdate();
						if (executeUpdate > 0) {
							response.sendRedirect("Dep_transactionSuccess.html");
							/*
							 * out.println("<html><body><h1>");
							 * out.println("Transaction Updated sucessfully....");
							 * out.println("</h1></body></html>"); out.println("<html><body><h1>");
							 * out.println("Amount Updated sucessfully....");
							 * out.println("</h1></body></html>");
							 */
						}

					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					response.sendRedirect("error.html");
				}
			} else {
				/*
				 * out.println("<html><body><h1>");
				 * out.println("Amount must be greater then zero...");
				 * out.println("</h1></body></html>");
				 */
				response.sendRedirect("Dep_invalidAmount.html");
			}

		} else {
			/*
			 * out.println("<html><body><h1>"); 
			 * out.println("Enter a valid amount...");
			 * out.println("</h1></body></html>");
			 */
			response.sendRedirect("Dep_invalidAmount.html");
		}

	}

}
