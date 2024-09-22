package Final_Account_Project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

import java.io.IOException;
import java.io.PrintWriter;

import com.Bank.Bean.ResultBean;
import com.Bank.DAO.LoginDAO;
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession session = request.getSession();//---------
		
		
		PrintWriter out = response.getWriter();
		String pword = request.getParameter("pwod");
		long accno=Long.parseLong( request.getParameter("acc"));
		ResultBean rb = LoginDAO.checkLogin(pword, accno);
		if(rb!=null) {
			
			
			session.setAttribute("ResultBean", rb);//---------
			
			response.sendRedirect("Application.html");
		}
		else {
			out.println("you are not an customer of sbi bank...");
		}
	
	}

}
