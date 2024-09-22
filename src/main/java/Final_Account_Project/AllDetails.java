package Final_Account_Project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import com.Bank.Bean.ResultBean;
import com.Bank.DAO.LoginDAO;

@WebServlet("/allDetails")
public class AllDetails extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        ResultBean rb = (ResultBean) session.getAttribute("ResultBean");
        long accno = rb.getAccno();

        ResultBean allDetails = LoginDAO.allDetails(accno);
        if (allDetails != null) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Account Details</title>");
            out.println("<style>");
            out.println("body {");
            out.println("    background: url('sbi.png') no-repeat center center fixed;");
            out.println("    background-size: cover;");
            out.println("    display: flex;");
            out.println("    justify-content: center;");
            out.println("    align-items: center;");
            out.println("    height: 100vh;");
            out.println("    margin: 0;");
            out.println("}");
            out.println(".container {");
            out.println("    background: rgba(255, 255, 255, 0.8);");
            out.println("    padding: 20px;");
            out.println("    border-radius: 10px;");
            out.println("    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);");
            out.println("}");
            out.println("table {");
            out.println("    width: 100%;");
            out.println("    border-collapse: collapse;");
            out.println("}");
            out.println("th, td {");
            out.println("    padding: 10px;");
            out.println("    text-align: left;");
            out.println("    border-bottom: 1px solid #ddd;");
            out.println("}");
            out.println("th {");
            out.println("    background-color: #f2f2f2;");
            out.println("}");
            out.println("h2 {");
            out.println("    text-align: center;");
            out.println("}");
            out.println(".home-link {");
            out.println("    display: inline-block;");
            out.println("    margin-top: 20px;");
            out.println("    padding: 10px 20px;");
            out.println("    background-color: #4CAF50;");
            out.println("    color: white;");
            out.println("    text-decoration: none;");
            out.println("    border-radius: 5px;");
            out.println("    text-align: center;");
            out.println("}");
            out.println(".home-link:hover {");
            out.println("    background-color: #45a049;");
            out.println("}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h2>Account Details</h2>");
            out.println("<table>");
            out.println("<tr><th>Field</th><th>Value</th></tr>");
            out.println("<tr><td>Account Number</td><td>" + allDetails.getAccno() + "</td></tr>");
            out.println("<tr><td>Account Type</td><td>" + allDetails.getAcctype() + "</td></tr>");
            out.println("<tr><td>Address</td><td>" + allDetails.getAddress() + "</td></tr>");
            out.println("<tr><td>Balance</td><td>" + allDetails.getBalance() + "</td></tr>");
            out.println("<tr><td>Contact</td><td>" + allDetails.getContact() + "</td></tr>");
            out.println("<tr><td>Name</td><td>" + allDetails.getName() + "</td></tr>");
            out.println("</table>");
            
            // Adding Home Page Link
            out.println("<a href='Application.html' class='home-link'>Home Page</a>");
            
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } else {
            out.println("<html><body><center>");
            out.println("Account not found/ Account is deleted...");
            out.println("<br>");
            out.println("<a href='index.html' class='home-link'>Home Page</a>");
            out.println("</center></body></html>");
        }

    }
}
