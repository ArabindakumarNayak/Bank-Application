package Final_Account_Project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.Bank.Bean.ResultBean;
import com.Bank.DBConnection.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/allTransaction")
public class AllTransaction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        ResultBean rb = (ResultBean) session.getAttribute("ResultBean");
        long accno = rb.getAccno();
        Connection con = DBConnection.getConnection();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM transactiontable WHERE accno=?");
            ps.setLong(1, accno);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsm = rs.getMetaData();
            int columnCount = rsm.getColumnCount();

            out.println("<html><head>");
            out.println("<style>");
            out.println("body {");
            out.println("  background-image: url('images/sbi.png');");
            out.println("  background-size: cover;");
            out.println("  color: white;");
            out.println("  font-family: Arial, sans-serif;");
            out.println("  margin: 0;");
            out.println("  padding: 0;");
            out.println("}");
            out.println("header, footer {");
            out.println("  background-color: rgba(0, 0, 0, 0.7);");
            out.println("  color: white;");
            out.println("  text-align: center;");
            out.println("  padding: 10px 0;");
            out.println("}");
            out.println("main {");
            out.println("  padding: 20px;");
            out.println("  text-align: center;");
            out.println("}");
            out.println("table {");
            out.println("  width: 80%;");
            out.println("  margin: 20px auto;");
            out.println("  border-collapse: collapse;");
            out.println("  background-color: rgba(0, 0, 0, 0.5);");
            out.println("}");
            out.println("th, td {");
            out.println("  border: 2px solid white;");
            out.println("  padding: 10px;");
            out.println("  text-align: center;");
            out.println("}");
            out.println("th {");
            out.println("  background-color: rgba(0, 0, 0, 0.7);");
            out.println("  font-weight: bold;");
            out.println("}");
            out.println("td {");
            out.println("  background-color: rgba(0, 0, 0, 0.3);");
            out.println("}");
            out.println(".home-button {");
            out.println("  display: block;");
            out.println("  margin: 20px auto;");
            out.println("  padding: 10px 20px;");
            out.println("  background-color: #004d99;");
            out.println("  color: white;");
            out.println("  text-decoration: none;");
            out.println("  border-radius: 5px;");
            out.println("  font-size: 16px;");
            out.println("  text-align: center;");
            out.println("}");
            out.println(".home-button:hover {");
            out.println("  background-color: #003366;");
            out.println("}");
            out.println("</style>");
            out.println("</head><body>");
            out.println("<header>");
            out.println("<h1>Transaction Details</h1>");
            out.println("</header>");
            out.println("<main>");
            out.println("<h2>All the transactions of account number: " + accno + "</h2>");
            out.println("<br><br>");
            out.println("<table>");
            out.println("<tr>");
            for (int i = 1; i <= columnCount; i++) {
                out.println("<th>");
                out.print(rsm.getColumnName(i));
                out.println("</th>");
            }
            out.println("</tr>");

            while (rs.next()) {
                out.println("<tr>");
                for (int i = 1; i <= columnCount; i++) {
                    out.println("<td>");
                    out.print(rs.getString(i));
                    out.println("</td>");
                }
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<a href='Application.html' class='home-button'>Go to Home</a>");
            out.println("</main>");
            out.println("<footer>");
            out.println("<p>&copy; 2024 Your Bank. All rights reserved.</p>");
            out.println("</footer>");
            out.println("</body></html>");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
