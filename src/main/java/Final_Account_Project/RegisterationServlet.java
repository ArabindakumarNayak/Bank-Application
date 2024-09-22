package Final_Account_Project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Bank.DBConnection.DBConnection;

@WebServlet("/register")
public class RegisterationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String name = request.getParameter("name");
        long contact = Long.parseLong(request.getParameter("contact"));
        double initialBal = Double.parseDouble(request.getParameter("amount"));
        String acctype = request.getParameter("accountType");
        String address = request.getParameter("address");
        String password = request.getParameter("password");

        Connection con = DBConnection.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("insert into bankaccount values(ACCNOGENERATOR.nextval,?,?,?,?,?,?)");
            ps.setString(1, name);
            ps.setDouble(2, initialBal);
            ps.setString(3, acctype);
            ps.setLong(4, contact);
            ps.setString(5, address);
            ps.setString(6, password);
            int executeUpdate = ps.executeUpdate();

            if (executeUpdate > 0) {
                PreparedStatement ps1 = con.prepareStatement("select accno from bankaccount where name=?");
                ps1.setString(1, name);
                ResultSet rs = ps1.executeQuery();
                rs.next(); // Move to the first row
                long accno = rs.getLong(1);
                
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Account Creation Successful</title>");
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
                out.println("    text-align: center;");
                out.println("    width: 50%;");
                out.println("}");
                out.println("h2 {");
                out.println("    color: #333;");
                out.println("}");
                out.println("p {");
                out.println("    color: #555;");
                out.println("}");
                out.println("a {");
                out.println("    display: inline-block;");
                out.println("    margin-top: 20px;");
                out.println("    padding: 10px 20px;");
                out.println("    color: #fff;");
                out.println("    background-color: #007bff;");
                out.println("    text-decoration: none;");
                out.println("    border-radius: 5px;");
                out.println("}");
                out.println("a:hover {");
                out.println("    background-color: #0056b3;");
                out.println("}");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='container'>");
                out.println("<h2>Account Creation Successful</h2>");
                out.println("<p>Your account has been created successfully.</p>");
                out.println("<p><strong>Account Number:</strong> " + accno + "</p>");
                out.println("<p>Please remember your account number for future use.</p>");
                out.println("<a href='index.html'>Login Here</a>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
