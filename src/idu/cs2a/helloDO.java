package idu.cs2a;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class helloDO
 */
@WebServlet("/customer-controller.do") /* /�� ������ WebContent�� ���� ������*/
public class helloDO extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public helloDO() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("<h1> Served at:</h1> ").append(request.getContextPath());
		
		//response.setContentType("text/html; charset=UTF-8");
		//HttpSession session = request.getSession();
		
		CustomerDTO customer = new CustomerDTO();
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if(email.equals(customer.getEmail()) &&
				password.equals(customer.getPassword()))
			request.setAttribute("attr", "�α��� ����");
			//session.setAttribute(arg0, arg1);
		
		else
			request.setAttribute("attr", "�α��� ���� - ���̵� �Ǵ� ��ȣ Ȯ��: ");
		
		request.getRequestDispatcher("result-view.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
