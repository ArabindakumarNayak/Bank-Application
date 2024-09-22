package Final_Account_Project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.Bank.Bean.ResultBean;
import com.Bank.DBConnection.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/withdraw")

public class WithdrawServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		ResultBean rb = (ResultBean) session.getAttribute("ResultBean");

		double witdrawAmt = Double.parseDouble(request.getParameter("amount"));
		if (witdrawAmt > 0) {
			Connection con = DBConnection.getConnection();
			try {
				if (rb.getBalance() >= witdrawAmt) {
					double oldbalance = rb.getBalance();
					long accno = rb.getAccno();
					PreparedStatement ps = con.prepareStatement("update bankaccount set balance=? where accno=?");
					double updatedBalance = oldbalance - witdrawAmt;
					ps.setDouble(1, updatedBalance);
					ps.setLong(2, accno);
					int executeUpdate = ps.executeUpdate();
					if (executeUpdate > 0) {

						PreparedStatement pswithdraw = con
								.prepareStatement("insert into transactionTable values(?,s1.nextval,SYSDATE,?,?)");
						pswithdraw.setLong(1, rb.getAccno());
						pswithdraw.setDouble(2, witdrawAmt);
						pswithdraw.setString(3, "Withdraw");
						int executeUpdate2 = pswithdraw.executeUpdate();
						{
							if (executeUpdate2 > 0) {
								rb.setBalance(updatedBalance);
								response.sendRedirect("withdraw_Successful_html_page.html");
								/*
								 * out.println("<html><body><h2>");
								 * out.println("Amount Debited Successfully..."); // Add a hyperlink to go back
								 * to the home page out.println("<br>");
								 * out.println("<a href='Application.html'>Go to Home Page</a>");
								 * out.println("</h2></body></html>");
								 */
							}
						}

					}

				} else {
					response.sendRedirect("withdraw_failed_html_page.html");

					/*
					 * out.println("<html><body><h1>");
					 * out.println("Amount must be smaller then current Balance..."); // Add a
					 * hyperlink to go back to the home page
					 * out.println("<a href='Application.html'>Go to Home Page</a>");
					 * out.println("</h1></body></html>");
					 */
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
