package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.PreparedStatement;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.MemberDAO;
import model.MemberDTO;
import service.Pagination;



/**
 * Servlet implementation class MemberController
 */
@WebServlet({"/member-list.do","/member-login.do", "/member-update.do", "/member-delete.do", "/member-register.do", "/member-info.do"})
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //������� ����
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    ArrayList<MemberDTO> alMember = null;//���ڵ���� ���� ���� ��ü
    MemberDTO dto = null;//���ڵ�� ���εǴ� ��ü
    HttpSession session = null;
    MemberDAO dao = new MemberDAO();
    Pagination pn = new Pagination();
    
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
	request.setCharacterEncoding("UTF-8");
	session = request.getSession(); // ���� ������� ���� ��ü�� �����ͼ� ���������� ����
	session.setMaxInactiveInterval(300); //���� Ʈ��ŷ (10)=>10�ʷ� ���� ��ȿ �ð� ����
	
	//String email = request.getParameter("email");
	//String pw = request.getParameter("pw");
//	try {
//		Class.forName("oracle.jdbc.OracleDriver"); //ojdbc6.jar�� �޸𸮿� ����
//	}catch(ClassNotFoundException e) {
//		e.printStackTrace();
//	}
//	try {
//		conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","ja_034", "cometrue");
//		// ���������� �̿��� ����(connection) ��ü ����
//		stmt = conn.createStatement();//���� ��ü�κ��� statement ��ü ����
//		} catch (SQLException e) {
//		e.printStackTrace();
//		}
		String uri = request.getRequestURI();
		int lastIndex = uri.lastIndexOf('/');
		String action = uri.substring(lastIndex + 1);//������ �ε����� /�̰�, �� �������� �߶� ������
		//"abcdefgh".substring(3, 5) : def �ε�����ȣ�� 3��°(d)���� 5��°(f) �߶�� 
		
		if(action.equals("member-list.do")) {
			list(request, response);
		}
		else if(action.equals("member-login.do"))
			login(request, response);
		else if(action.equals("member-register.do"))
			register(request, response);
		else if(action.equals("member-update.do"))
			update(request, response);
		else if(action.equals("member-delete.do"))
			delete(request, response);
		else if(action.equals("member-info.do"))
			info(request, response);
		else
			;
	}
	
	private void info(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String email = (String)session.getAttribute("email");
		//String pw = request.getParameter("pw");
//		rs = stmt.executeQuery("select * from ja_034_member where " + "email='" + email + "'"); // "' and " + "pw='" + pw + "'");
//		dto = null;
//		//statement ��ü�� ������ sql ����, result set�� ��ȯ����
//		if(rs.next()) { // ���� ����� ���� ���ڵ尡 �����ϸ� true, �ƴϸ� false
//			dto = new MemberDTO();
//			//if(email.equals(rs.getString(1)) && pw.equals(rs.getString(2))) {
//			dto.setEmail(rs.getString(1));
//			dto.setPw(rs.getString(2));
//			dto.setName(rs.getString(3));
//			dto.setPhone(rs.getString(4));
//		}
		dto = dao.info(email);
		if(dto != null) {
			request.setAttribute("name", dto.getName());
			session.setAttribute("phone", dto.getPhone());
			
			request.getRequestDispatcher("customer-update.jsp").forward(request, response);
		}
		else
			request.getRequestDispatcher("login-fail.jsp").forward(request, response);
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
//		pstmt = conn.prepareStatement("delete from ja_034_member where email=?");// ���ǹ� ����
///*				conn.prepareStatement("delete from ja_034_member where email='" + 
//				(String) session.getAttribute("email") + "'");
//	*/	
//		//���ǹ� ä���
//		pstmt.setString(1, request.getParameter("email"));//ù��° ����ǥ�� ��ü
//		
//		int result = pstmt.executeUpdate(); // ���ǹ� ����
		
		dto = new MemberDTO();
		dto.setEmail(request.getParameter("email"));//ù��° ����ǥ�� ��ü
		
		int result = dao.delete(dto);
		if(result >= 1) { // ȸ�� Ż��(���� ����)
			//������ �ϸ� �ѱ� ������ ���� ������ forward�� �ƴ� sendRedirect ���
			response.sendRedirect("member-list.do");
		}
		else
			response.sendRedirect("delete-fail.jsp");
	}
	
	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
//		pstmt = conn.prepareStatement("update ja_034_member set name=?, phone=? where email=?");// ���ǹ� ����
//		//���ǹ� ä���
//		pstmt.setString(3, request.getParameter("email"));//ù��° ����ǥ�� ��ü
//		pstmt.setString(1, request.getParameter("name"));
//		pstmt.setString(2, request.getParameter("phone"));
//		
//		int result = pstmt.executeUpdate(); // ���ǹ� ����
		dto = new MemberDTO();
		dto.setEmail(request.getParameter("email"));//ù��° ����ǥ�� ��ü
		dto.setName(request.getParameter("name"));
		dto.setPhone(request.getParameter("phone"));

		int result = dao.update(dto);
		if(result >= 1) { // ��� ����
			request.setAttribute("name", request.getParameter("email"));
			request.getRequestDispatcher("update-success.jsp").forward(request, response);
		}
		else
			request.getRequestDispatcher("update-fail.jsp").forward(request, response);
	}
	
	private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
//		pstmt = conn.prepareStatement("insert into ja_034_member " + "values(?, ?, ?, ?)");// ���ǹ� ����
//		//���ǹ� ä���
//		pstmt.setString(1, request.getParameter("email"));//ù��° ����ǥ�� ��ü
//		pstmt.setString(2, request.getParameter("pw")); //�ι�° ����ǥ ��ü
//		pstmt.setString(3, request.getParameter("name"));
//		pstmt.setString(4, request.getParameter("phone"));
//		
//		int result = pstmt.executeUpdate(); // ���ǹ� ����
		dto = new MemberDTO();
		dto.setEmail(request.getParameter("email"));//ù��° ����ǥ�� ��ü
		dto.setPw(request.getParameter("pw")); //�ι�° ����ǥ ��ü
		dto.setName(request.getParameter("name"));
		dto.setPhone(request.getParameter("phone"));
		
		int result = dao.insert(dto);
		if(result >= 1) { // ��� ����
			request.setAttribute("name", request.getParameter("email"));
			request.getRequestDispatcher("register-success.jsp").forward(request, response);
		}
		else
			request.getRequestDispatcher("register-fail.jsp").forward(request, response);
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		
		dto = dao.login(email,pw); //dao �α��� ���� ������(memberDTO)
		
		if(dto.getEmail() != null) {
			request.setAttribute("name", dto.getName());
			session.setAttribute("email", dto.getEmail());
			request.getRequestDispatcher("login-success.jsp").forward(request, response);
		}
		else
			request.getRequestDispatcher("login-fail.jsp").forward(request, response);
	}
	
	
	/*
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		
//		MemberDTO member = new MemberDTO();		//dao�� �ѱ� MemberDTO�ڷ��� member��ü ����
//		member.setEmail(email);
//		member.setPw(pw);
//		
		dto = dao.login(email,pw); //dao �α��� ���� ������(memberDTO)
//		rs = stmt.executeQuery("select * from ja_034_member where email = '"+email+"' and pw = '"+pw+"'");
//		
//		//statement ��ü�� ������ sql ����, result set�� ��ȯ����
//		if(rs.next()) { // ���� ����� ���� ���ڵ尡 �����ϸ� true, �ƴϸ� false
//			dto = new MemberDTO();
//			//if(email.equals(rs.getString(1)) && pw.equals(rs.getString(2))) {
//			dto.setEmail(rs.getString(1));
//			dto.setPw(rs.getString(2));
//			dto.setName(rs.getString(3));
//			dto.setPhone(rs.getString(4));
//		}
//		//HttpSession session = request.getSession();
		
		if(dto.getEmail() != null) {
			request.setAttribute("name", dto.getName());
			session.setAttribute("email", dto.getEmail());
			request.getRequestDispatcher("login-success.jsp").forward(request, response);
		}
		else
			request.getRequestDispatcher("login-fail.jsp").forward(request, response);
	}
*/
	
	
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
//1��		if((alMember = dao.selectListBetween(pn.getStartRow(), pn.getEndRow())) != null) {
//			request.setAttribute("list", alMember);
//			request.getRequestDispatcher("customer-list.jsp").forward(request, response);
//		}
//		else {
//			request.getRequestDispatcher("fail.jsp").forward(request, response);
//		}
				//2��
				alMember = dao.list();
				request.setAttribute("list" , alMember);
				request.getRequestDispatcher("customer-list.jsp").forward(request, response);
//		alMember = new ArrayList<MemberDTO>();
//
//		rs = stmt.executeQuery("select * from ja_034_member"); //+"where id='" + id + "'");
//		// statement ��ü�� ������ sql����, result set�� ��ȯ ����
//		while(rs.next()) {
//			MemberDTO member =null;
//			member = new MemberDTO();
//			//if(email.equals(rs.getString(1)) && pw.equals(rs.getString(2))) {
//			member.setEmail(rs.getString(1));
//			member.setPw(rs.getString(2));
//			member.setName(rs.getString(3));
//			member.setPhone(rs.getString(4));
//			alMember.add(member);
//			//request.setAttribute("attr", "�α��� ����");
//			//break;
//			//session.setAttribute(arg0, arg1);
//			//}else {
//			//request.setAttribute("attr", "�α��� ���� - ���̵� �Ǵ� ��ȣ Ȯ��: ");
//			//}
//		}
//		request.setAttribute("list" , alMember);
//		request.getRequestDispatcher("customer-list.jsp").forward(request, response);
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	try {
			process(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			process(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
