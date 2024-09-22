package Final_Account_Project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.Bank.Bean.ResultBean;
import com.Bank.DAO.LoginDAO;
import com.Bank.DBConnection.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/transferSer")
public class TransferServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Connection con = DBConnection.getConnection();
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// getting both the session...
		ResultBean rbsender = (ResultBean) session.getAttribute("ResultBean");

		PrintWriter out = response.getWriter();
		String amount = request.getParameter("amount");
		long accno = Long.parseLong(request.getParameter("accno"));

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
				if (depamt < rbsender.getBalance()) {
					// Check if the receiver account number is the same as the sender's account
					// number
					if (accno == rbsender.getAccno()) { // Modification 1: Check if transfer is to self
						response.sendRedirect("ownAccountTransfer.html");
					} else {
						// actual validation...
						ResultBean rbreceiver = LoginDAO.checkTransfer(accno);
						if (rbreceiver != null) {
							double senderacbalance = rbsender.getBalance();
							double receiveracbalance = rbreceiver.getBalance();
							long senderAcNo = rbsender.getAccno();
							long receiverACNO = accno;
							double senderUpdateAmount = senderacbalance - depamt;
							double receiverUpdateAmount = receiveracbalance + depamt;
							try {
								// updating for sender account
								PreparedStatement ps1 = con
										.prepareStatement("UPDATE bankaccount SET balance=? WHERE accno=?");
								ps1.setDouble(1, senderUpdateAmount);
								ps1.setLong(2, senderAcNo);
								ps1.executeUpdate(); // Modification 2: Change to executeUpdate()

								PreparedStatement ps2 = con
										.prepareStatement("UPDATE bankaccount SET balance=? WHERE accno=?");
								ps2.setDouble(1, receiverUpdateAmount);
								ps2.setLong(2, receiverACNO);
								ps2.executeUpdate(); // Modification 3: Change to executeUpdate()

								PreparedStatement pstransaction = con.prepareStatement(
										"INSERT INTO transactionTable VALUES(?, s1.nextval, SYSDATE, ?, ?)");
								pstransaction.setLong(1, accno);
								pstransaction.setDouble(2, depamt);
								pstransaction.setString(3, "transfer");
								int executeUpdate = pstransaction.executeUpdate();
								if (executeUpdate > 0) {

									response.sendRedirect("transactionSuccess.html");
									/*
									 * out.println("<html><body><h1>");
									 * out.println("Transaction Updated successfully...");
									 * out.println("</h1></body></html>");
									 */
									con.commit();
								}
								rbsender.setBalance(senderUpdateAmount);
								rbreceiver.setBalance(receiverUpdateAmount);
								/*
								 * out.println("<html><body><h1>");
								 * out.println("Amount Transferred successfully...");
								 * out.println("</h1></body></html>");
								 */

							} catch (Exception e) {
								e.printStackTrace();
							}

						} else {
							response.sendRedirect("invalidReceiver.html");
							/*
							 * out.println("<html><body><h1>");
							 * out.println("Enter a valid Receiver Bank Account...");
							 * out.println("</h1></body></html>");
							 */
						}
					}

				} else {
					response.sendRedirect("insufficientBalance.html");
					/*
					 * out.println("<html><body><h1>");
					 * out.println("Amount must be smaller than your current balance...");
					 * out.println("</h1></body></html>");
					 */
				}

			} else {
				response.sendRedirect("invalidAmount.html");
				/*
				 * out.println("<html><body><h1>");
				 * out.println("Amount must be greater than zero...");
				 * out.println("</h1></body></html>");
				 */
			}

		} else {
			response.sendRedirect("invalidAmount.html");
			/*
			 * out.println("<html><body><h1>"); out.println("Enter a valid amount...");
			 * out.println("</h1></body></html>");
			 */
		}
	}
}
