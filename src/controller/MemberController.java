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
    //멤버변수 선언
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    ArrayList<MemberDTO> alMember = null;//레코드들의 대한 집합 객체
    MemberDTO dto = null;//레코드와 매핑되는 객체
    HttpSession session = null;
    MemberDAO dao = new MemberDAO();
    Pagination pn = new Pagination();
    
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
	request.setCharacterEncoding("UTF-8");
	session = request.getSession(); // 현재 사용중인 세션 객체를 가져와서 참조변수에 배정
	session.setMaxInactiveInterval(300); //세션 트래킹 (10)=>10초로 세션 유효 시간 지정
	
	//String email = request.getParameter("email");
	//String pw = request.getParameter("pw");
//	try {
//		Class.forName("oracle.jdbc.OracleDriver"); //ojdbc6.jar를 메모리에 적재
//	}catch(ClassNotFoundException e) {
//		e.printStackTrace();
//	}
//	try {
//		conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","ja_034", "cometrue");
//		// 연결정보를 이용한 연결(connection) 객체 생성
//		stmt = conn.createStatement();//연결 객체로부터 statement 객체 생성
//		} catch (SQLException e) {
//		e.printStackTrace();
//		}
		String uri = request.getRequestURI();
		int lastIndex = uri.lastIndexOf('/');
		String action = uri.substring(lastIndex + 1);//마지막 인덱스가 /이고, 그 다음부터 잘라서 가져옴
		//"abcdefgh".substring(3, 5) : def 인덱스번호로 3번째(d)부터 5번째(f) 잘라옴 
		
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
//		//statement 객체로 지정된 sql 실행, result set을 반환받음
//		if(rs.next()) { // 질의 결과에 다음 레코드가 존재하면 true, 아니면 false
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
//		pstmt = conn.prepareStatement("delete from ja_034_member where email=?");// 질의문 생성
///*				conn.prepareStatement("delete from ja_034_member where email='" + 
//				(String) session.getAttribute("email") + "'");
//	*/	
//		//질의문 채우기
//		pstmt.setString(1, request.getParameter("email"));//첫번째 물음표를 대체
//		
//		int result = pstmt.executeUpdate(); // 질의문 실행
		
		dto = new MemberDTO();
		dto.setEmail(request.getParameter("email"));//첫번째 물음표를 대체
		
		int result = dao.delete(dto);
		if(result >= 1) { // 회원 탈퇴(삭제 성공)
			//삭제를 하면 넘길 내용이 없기 때문에 forward가 아닌 sendRedirect 사용
			response.sendRedirect("member-list.do");
		}
		else
			response.sendRedirect("delete-fail.jsp");
	}
	
	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
//		pstmt = conn.prepareStatement("update ja_034_member set name=?, phone=? where email=?");// 질의문 생성
//		//질의문 채우기
//		pstmt.setString(3, request.getParameter("email"));//첫번째 물음표를 대체
//		pstmt.setString(1, request.getParameter("name"));
//		pstmt.setString(2, request.getParameter("phone"));
//		
//		int result = pstmt.executeUpdate(); // 질의문 실행
		dto = new MemberDTO();
		dto.setEmail(request.getParameter("email"));//첫번째 물음표를 대체
		dto.setName(request.getParameter("name"));
		dto.setPhone(request.getParameter("phone"));

		int result = dao.update(dto);
		if(result >= 1) { // 등록 성공
			request.setAttribute("name", request.getParameter("email"));
			request.getRequestDispatcher("update-success.jsp").forward(request, response);
		}
		else
			request.getRequestDispatcher("update-fail.jsp").forward(request, response);
	}
	
	private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
//		pstmt = conn.prepareStatement("insert into ja_034_member " + "values(?, ?, ?, ?)");// 질의문 생성
//		//질의문 채우기
//		pstmt.setString(1, request.getParameter("email"));//첫번째 물음표를 대체
//		pstmt.setString(2, request.getParameter("pw")); //두번째 물음표 대체
//		pstmt.setString(3, request.getParameter("name"));
//		pstmt.setString(4, request.getParameter("phone"));
//		
//		int result = pstmt.executeUpdate(); // 질의문 실행
		dto = new MemberDTO();
		dto.setEmail(request.getParameter("email"));//첫번째 물음표를 대체
		dto.setPw(request.getParameter("pw")); //두번째 물음표 대체
		dto.setName(request.getParameter("name"));
		dto.setPhone(request.getParameter("phone"));
		
		int result = dao.insert(dto);
		if(result >= 1) { // 등록 성공
			request.setAttribute("name", request.getParameter("email"));
			request.getRequestDispatcher("register-success.jsp").forward(request, response);
		}
		else
			request.getRequestDispatcher("register-fail.jsp").forward(request, response);
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");
		
		dto = dao.login(email,pw); //dao 로그인 실행 리턴형(memberDTO)
		
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
		
//		MemberDTO member = new MemberDTO();		//dao에 넘길 MemberDTO자료형 member객체 생성
//		member.setEmail(email);
//		member.setPw(pw);
//		
		dto = dao.login(email,pw); //dao 로그인 실행 리턴형(memberDTO)
//		rs = stmt.executeQuery("select * from ja_034_member where email = '"+email+"' and pw = '"+pw+"'");
//		
//		//statement 객체로 지정된 sql 실행, result set을 반환받음
//		if(rs.next()) { // 질의 결과에 다음 레코드가 존재하면 true, 아니면 false
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
//1번		if((alMember = dao.selectListBetween(pn.getStartRow(), pn.getEndRow())) != null) {
//			request.setAttribute("list", alMember);
//			request.getRequestDispatcher("customer-list.jsp").forward(request, response);
//		}
//		else {
//			request.getRequestDispatcher("fail.jsp").forward(request, response);
//		}
				//2번
				alMember = dao.list();
				request.setAttribute("list" , alMember);
				request.getRequestDispatcher("customer-list.jsp").forward(request, response);
//		alMember = new ArrayList<MemberDTO>();
//
//		rs = stmt.executeQuery("select * from ja_034_member"); //+"where id='" + id + "'");
//		// statement 객체로 지정된 sql실행, result set을 반환 받음
//		while(rs.next()) {
//			MemberDTO member =null;
//			member = new MemberDTO();
//			//if(email.equals(rs.getString(1)) && pw.equals(rs.getString(2))) {
//			member.setEmail(rs.getString(1));
//			member.setPw(rs.getString(2));
//			member.setName(rs.getString(3));
//			member.setPhone(rs.getString(4));
//			alMember.add(member);
//			//request.setAttribute("attr", "로그인 성공");
//			//break;
//			//session.setAttribute(arg0, arg1);
//			//}else {
//			//request.setAttribute("attr", "로그인 실패 - 아이디 또는 암호 확인: ");
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
