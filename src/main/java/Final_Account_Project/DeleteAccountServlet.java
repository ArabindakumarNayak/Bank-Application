package Final_Account_Project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.Bank.Bean.ResultBean;
import com.Bank.DBConnection.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/deleteAcc")
public class DeleteAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        ResultBean rb = (ResultBean) session.getAttribute("ResultBean");
        long accno = rb.getAccno();

        // Base URL for images directory (adjust according to your setup)
        String imageBaseURL = request.getContextPath() + "/images/sbi.png";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT balance FROM bankaccount WHERE accno=?");
            ps.setLong(1, accno);
            ResultSet rs = ps.executeQuery();

            out.println("<html><head>");
            out.println("<title>Account Deletion</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }");
            out.println("header { background-color: #004d99; color: #fff; padding: 10px 0; text-align: center; }");
            out.println("img { width: 150px; height: auto; }");
            out.println(".container { width: 80%; margin: 0 auto; padding: 20px; background-color: #fff; border-radius: 8px; }");
            out.println(".message { margin: 20px 0; }");
            out.println("a { color: #004d99; text-decoration: none; }");
            out.println("a:hover { text-decoration: underline; }");
            out.println("</style>");
            out.println("</head><body>");
            out.println("<header>");
            out.println("<img src='" + imageBaseURL + "' alt='Bank Logo'>");
            out.println("</header>");
            out.println("<div class='container'>");

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance < 1) {
                    // If the balance is less than 1, delete the account
                    PreparedStatement ps2 = con.prepareStatement("DELETE FROM bankaccount WHERE accno=?");
                    ps2.setLong(1, accno);
                    ps2.executeUpdate();

                    // HTML message for successful deletion
                    out.println("<div class='message'>");
                    out.println("<p>Account with ID " + accno + " deleted successfully.</p>");
                    out.println("<a href='index.html'>Return to Login/Registration Page</a>");
                    out.println("</div>");
                } else {
                    // If the balance is greater than or equal to 1, prompt to withdraw funds
                    out.println("<div class='message'>");
                    out.println("<p>Your account has funds. Please withdraw all the funds before deleting your account.</p>");
                    out.println("<a href='withdraw.html'>Withdraw all your funds</a>");
                    out.println("</div>");
                }
            } else {
                // HTML message if the account is not found
                out.println("<div class='message'>");
                out.println("<p>Account not found.</p>");
                out.println("</div>");
            }

            out.println("</div>");
            out.println("</body></html>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<html><body><center>");
            out.println("<p>An error occurred. Please try again later.</p>");
            out.println("</center></body></html>");
        }
    }
}
