package idu.cs2a;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SendRedirect
 */
@WebServlet({ "/send-redirect.do", "/SendRedirect" })
public class SendRedirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendRedirect() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CustomerDTO customer = new CustomerDTO();
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if(email.equals(customer.getEmail()) &&
				password.equals(customer.getPassword()))
			request.setAttribute("attr", "로그인 성공");
			//session.setAttribute(arg0, arg1);
		
		else
			request.setAttribute("attr", "로그인 실패 - 아이디 또는 암호 확인: ");
		
		response.sendRedirect("result-view.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
