package Final_Account_Project;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/appln")
public class ApplicationServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String value = request.getParameter("selection");

		if (value.equals("Deposit")) {
			response.sendRedirect("deposite.html");
		} 
		
		else if (value.equals("Transfer")) {
//			RequestDispatcher rd = request.getRequestDispatcher("transfer.html");
			response.sendRedirect("transfer.html");
		} 
		
		else if (value.equals("Withdraw")) {
			response.sendRedirect("withdraw.html");
		} 
		
		else if (value.equals("All_Details")) {
			//response.sendRedirect("allDetails");
			request.getRequestDispatcher("allDetails").forward(request, response);
		} 
		
		else if (value.equals("All_Transition")) {
			request.getRequestDispatcher("allTransaction").forward(request, response);
		} 
		
		else {
			request.getRequestDispatcher("deleteAcc").forward(request, response);
		}

	}

}
