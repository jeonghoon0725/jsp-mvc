package controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.MemberDTO;
import model.ProductDAO;
import model.ProductDTO;
import service.Pagination;

/**
 * Servlet implementation class ProductController
 */
@WebServlet({"/ProductController", "/product-register.do", "/product-list.do", "/product-list1.do", "/product-info.do", "/product-detail.do", "/product-delete.do", "/product-update.do"})
@MultipartConfig(location="", fileSizeThreshold=1024*1024, maxFileSize=1024*1024*5, 
maxRequestSize=1024*1024*5*5)

public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
  //멤버변수 선언
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    ArrayList<ProductDTO> dtoList = null;//레코드들의 대한 집합 객체
    ProductDTO dto = null;//레코드와 매핑되는 객체
    HttpSession session = null;
    ProductDAO dao = new ProductDAO();
    Pagination pn = new Pagination();
    
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		// TODO Auto-generated method stub
	request.setCharacterEncoding("UTF-8");
	session = request.getSession(); // 현재 사용중인 세션 객체를 가져와서 참조변수에 배정
	//session.setMaxInactiveInterval(30); //세션 트래킹 (10)=>10초로 세션 유효 시간 지정
	
	
		String uri = request.getRequestURI();
		int lastIndex = uri.lastIndexOf('/');
		String action = uri.substring(lastIndex + 1);//마지막 인덱스가 /이고, 그 다음부터 잘라서 가져옴
	
		if(action.equals("product-register.do"))
			register(request, response);
		else if(action.equals("product-list.do"))
			list(request, response);
		else if(action.equals("product-list1.do"))
			list1(request, response);
		else if(action.equals("product-info.do"))
			info(request, response);
		else if(action.equals("product-update.do"))
			update(request, response);
		else if(action.equals("product-detail.do"))
			detail(request, response);
		else if(action.equals("product-delete.do"))
			delete(request, response);
		else
			;
		
	}
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {		
		dto = new ProductDTO();
		dto.setPno(Integer.parseInt(request.getParameter("pno")));
		
		int result = dao.delete(dto);
		if(result >= 1) { // 회원 탈퇴(삭제 성공)
			//삭제를 하면 넘길 내용이 없기 때문에 forward가 아닌 sendRedirect 사용
			response.sendRedirect("product-list.do");
		}
		else
			response.sendRedirect("delete-fail.jsp");
	}
	protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		ProductDTO p = new ProductDTO();
		p.setPno(Integer.parseInt(request.getParameter("pno")));
		dto = dao.selectOne(p);
		
		if(dto != null) {
			request.setAttribute("product", dto);
			request.getRequestDispatcher("product-detail.jsp").forward(request, response);
		}
		else {
			request.getRequestDispatcher("fail.jsp").forward(request, response);
		}
	}
	protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated constructor stub
		Collection<Part> parts = request.getParts();
		for(Part part : parts) {
			partName = part.getName();
			if(part.getContentType() != null) {
				partValue = getFileName(part);
				if(partValue != null && ! partValue.isEmpty()) {//널이거나 비어있다면
					String absolutePath = getServletContext().getRealPath("/files");//files라는 경로를 찾음
					//D:\oop2-2a\ws201512034\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\cs06a201512034\files
					part.write(absolutePath + File.separator + partValue);
				}
			}
			else {
				partValue = request.getParameter(partName);
			}
			request.setAttribute(partName, partValue);
		}
		
		dto = new ProductDTO();
		dto.setPno(Integer.parseInt(request.getParameter("pno")));
		dto.setName(request.getParameter("name"));
		dto.setPrice(Integer.parseInt(request.getParameter("price")));
		dto.setCno(request.getParameter("cno"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dto.setRegdate(sdf.parse(request.getParameter("regdate")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dto.setImage((String)request.getAttribute("image"));
		
		int result = dao.update(dto);
			
		if(result >= 1) { // 등록 성공
			request.setAttribute("name",  request.getParameter("name")); // map구조 (key와 value로 구성)
			request.setAttribute("message", "상품 정보 수정 작업");		
			request.getRequestDispatcher("success.jsp").forward(request, response);
		}
		else
			request.getRequestDispatcher("product-fail.jsp").forward(request, response);
	}
	protected void info(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		ProductDTO p = new ProductDTO();
		p.setPno(Integer.parseInt(request.getParameter("pno")));
		dto = dao.selectOne(p);
		
		if(dto != null) {
			request.setAttribute("product", dto);
			request.getRequestDispatcher("product-update.jsp").forward(request, response);
		}
		else {
			request.getRequestDispatcher("fail.jsp").forward(request, response);
		}
	}
	protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		//dtoList = new ArrayList<ProductDTO>();
		int totalRows = 0; //전체 상품 수, 레코드 or 행의 수
		totalRows = dao.selectCount();
		int rowsPerPage = 4; //한 페이지에 나타나는 상품 수
		int paginationPerPage = 2; // 한 페이지에 나타나는 페이지 번호 수
		int pageNum = 1;
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum")); //요청한 페이지 번호
			if(pageNum <= 0)
				pageNum = 1;
		}
		pn.processPaging(totalRows, pageNum, rowsPerPage, paginationPerPage);//페이지처리
		if((dtoList = dao.selectListBetween(pn.getStartRow(), pn.getEndRow())) != null) {
			request.setAttribute("productList", dtoList);
			request.setAttribute("pagination", pn);
			request.getRequestDispatcher("product-list.jsp").forward(request, response);
		}
		else {
			request.getRequestDispatcher("fail.jsp").forward(request, response);
		}
	}
	protected void list1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		//dtoList = new ArrayList<ProductDTO>();
		int totalRows = 0; //전체 상품 수, 레코드 or 행의 수
		totalRows = dao.selectCount();
		int rowsPerPage = 2; //한 페이지에 나타나는 상품 수
		int paginationPerPage = 3; // 한 페이지에 나타나는 페이지 번호 수
		int pageNum = 1;
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum")); //요청한 페이지 번호
			if(pageNum <= 0)
				pageNum = 1;
		}
		pn.processPaging(totalRows, pageNum, rowsPerPage, paginationPerPage);//페이지처리
		if((dtoList = dao.selectListBetween1(pn.getStartRow(), pn.getEndRow())) != null) {
			request.setAttribute("productList", dtoList);
			request.setAttribute("pagination", pn);
			request.getRequestDispatcher("product-list.jsp").forward(request, response);
		}
		else {
			request.getRequestDispatcher("fail.jsp").forward(request, response);
		}
	}
		
//		DAO에 넘겨줌
//		rs = stmt.executeQuery("select pno, name, price, cno, image from ja_034_product"); //+"where id='" + id + "'");
//		// statement 객체로 지정된 sql실행, result set을 반환 받음
//		while(rs.next()) {
//			dto = new ProductDTO();
//			//if(email.equals(rs.getString(1)) && pw.equals(rs.getString(2))) {
//			dto.setPno(rs.getInt(1));
//			dto.setName(rs.getString(2));
//			dto.setPrice(rs.getInt(3));
//			dto.setCno(rs.getString(4));
//			dto.setImage(rs.getString(5));
//			dtoList.add(dto);
//			//request.setAttribute("attr", "로그인 성공");
//			//break;
//			//session.setAttribute(arg0, arg1);
//			//}else {
//			//request.setAttribute("attr", "로그인 실패 - 아이디 또는 암호 확인: ");
//			//}
//		}
//		request.setAttribute("productList" , dtoList);
//		request.getRequestDispatcher("product-list.jsp").forward(request, response);
	
	private String getFileName(Part part) { // 헤더파싱
		String contentDispositionHeader = part.getHeader("content-disposition");//content-disposition찾아라
		String[] splitedContentDisposition = contentDispositionHeader.split(";");//;으로 나눔
		for (String cd : splitedContentDisposition ) {//;갯수만큼 반복
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				}
			}
		return null;
	}
	
	private String partName = null;
	private String partValue = null;
	
	protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated constructor stub
		Collection<Part> parts = request.getParts();
		for(Part part : parts) {
			partName = part.getName();
			if(part.getContentType() != null) {
				partValue = getFileName(part);
				if(partValue != null && ! partValue.isEmpty()) {//널이거나 비어있다면
					String absolutePath = getServletContext().getRealPath("/files");//files라는 경로를 찾음
					//D:\oop2-2a\ws201512034\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\cs06a201512034\files
					part.write(absolutePath + File.separator + partValue);
				}
			}
			else {
				partValue = request.getParameter(partName);
			}
			request.setAttribute(partName, partValue);
		}
		
		dto = new ProductDTO();
		dto.setName(request.getParameter("name"));
		dto.setPrice(Integer.parseInt(request.getParameter("price")));
		dto.setCno(request.getParameter("cno"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dto.setRegdate(sdf.parse(request.getParameter("regdate")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dto.setImage((String)request.getAttribute("image"));
		
		int result = dao.insert(dto);
		
//		 pstmt = 
//				 conn.prepareStatement("insert into ja_034_product(pno, name, price, cno, image) " + 
//				 		"values(p_seq.nextval, ?, ?, ?, ?)"); // 질의문 생성
//		 // 질의문 채우기
//		 pstmt.setString(1, request.getParameter("name")); // 첫번째 물음표를 대체
//		 pstmt.setInt(2, Integer.parseInt(request.getParameter("price"))); // 두번째 물음표를 대체
//		 pstmt.setString(3, request.getParameter("cno")); // 프로덕트 레지스터 name부븐 속성 가져오기
//		 pstmt.setString(4, (String) request.getAttribute("image")); // 이미지는 Attribute로 해야함 , Attribute는 자료형 (String)맞춰야함
//		 
		
//		int result = pstmt.executeUpdate();// 질의문을 실행
			
		if(result >= 1) { // 등록 성공
			request.setAttribute("name",  request.getParameter("name")); // map구조 (key와 value로 구성)
			request.getRequestDispatcher("product-success.jsp").forward(request, response);
		}
		else
			request.getRequestDispatcher("product-fail.jsp").forward(request, response);
		
		
		/*
		System.out.println(request.getParameter("name"));
		System.out.println(request.getParameter("image"));
		
		System.out.println(request.getAttribute("name"));
		System.out.println(request.getAttribute("image"));
		*/
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated constructor stub
			try {
				process(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated constructor stub
			try {
				process(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
