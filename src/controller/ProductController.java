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
    
  //������� ����
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    ArrayList<ProductDTO> dtoList = null;//���ڵ���� ���� ���� ��ü
    ProductDTO dto = null;//���ڵ�� ���εǴ� ��ü
    HttpSession session = null;
    ProductDAO dao = new ProductDAO();
    Pagination pn = new Pagination();
    
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		// TODO Auto-generated method stub
	request.setCharacterEncoding("UTF-8");
	session = request.getSession(); // ���� ������� ���� ��ü�� �����ͼ� ���������� ����
	//session.setMaxInactiveInterval(30); //���� Ʈ��ŷ (10)=>10�ʷ� ���� ��ȿ �ð� ����
	
	
		String uri = request.getRequestURI();
		int lastIndex = uri.lastIndexOf('/');
		String action = uri.substring(lastIndex + 1);//������ �ε����� /�̰�, �� �������� �߶� ������
	
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
		if(result >= 1) { // ȸ�� Ż��(���� ����)
			//������ �ϸ� �ѱ� ������ ���� ������ forward�� �ƴ� sendRedirect ���
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
				if(partValue != null && ! partValue.isEmpty()) {//���̰ų� ����ִٸ�
					String absolutePath = getServletContext().getRealPath("/files");//files��� ��θ� ã��
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
			
		if(result >= 1) { // ��� ����
			request.setAttribute("name",  request.getParameter("name")); // map���� (key�� value�� ����)
			request.setAttribute("message", "��ǰ ���� ���� �۾�");		
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
		int totalRows = 0; //��ü ��ǰ ��, ���ڵ� or ���� ��
		totalRows = dao.selectCount();
		int rowsPerPage = 4; //�� �������� ��Ÿ���� ��ǰ ��
		int paginationPerPage = 2; // �� �������� ��Ÿ���� ������ ��ȣ ��
		int pageNum = 1;
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum")); //��û�� ������ ��ȣ
			if(pageNum <= 0)
				pageNum = 1;
		}
		pn.processPaging(totalRows, pageNum, rowsPerPage, paginationPerPage);//������ó��
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
		int totalRows = 0; //��ü ��ǰ ��, ���ڵ� or ���� ��
		totalRows = dao.selectCount();
		int rowsPerPage = 2; //�� �������� ��Ÿ���� ��ǰ ��
		int paginationPerPage = 3; // �� �������� ��Ÿ���� ������ ��ȣ ��
		int pageNum = 1;
		if(request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum")); //��û�� ������ ��ȣ
			if(pageNum <= 0)
				pageNum = 1;
		}
		pn.processPaging(totalRows, pageNum, rowsPerPage, paginationPerPage);//������ó��
		if((dtoList = dao.selectListBetween1(pn.getStartRow(), pn.getEndRow())) != null) {
			request.setAttribute("productList", dtoList);
			request.setAttribute("pagination", pn);
			request.getRequestDispatcher("product-list.jsp").forward(request, response);
		}
		else {
			request.getRequestDispatcher("fail.jsp").forward(request, response);
		}
	}
		
//		DAO�� �Ѱ���
//		rs = stmt.executeQuery("select pno, name, price, cno, image from ja_034_product"); //+"where id='" + id + "'");
//		// statement ��ü�� ������ sql����, result set�� ��ȯ ����
//		while(rs.next()) {
//			dto = new ProductDTO();
//			//if(email.equals(rs.getString(1)) && pw.equals(rs.getString(2))) {
//			dto.setPno(rs.getInt(1));
//			dto.setName(rs.getString(2));
//			dto.setPrice(rs.getInt(3));
//			dto.setCno(rs.getString(4));
//			dto.setImage(rs.getString(5));
//			dtoList.add(dto);
//			//request.setAttribute("attr", "�α��� ����");
//			//break;
//			//session.setAttribute(arg0, arg1);
//			//}else {
//			//request.setAttribute("attr", "�α��� ���� - ���̵� �Ǵ� ��ȣ Ȯ��: ");
//			//}
//		}
//		request.setAttribute("productList" , dtoList);
//		request.getRequestDispatcher("product-list.jsp").forward(request, response);
	
	private String getFileName(Part part) { // ����Ľ�
		String contentDispositionHeader = part.getHeader("content-disposition");//content-dispositionã�ƶ�
		String[] splitedContentDisposition = contentDispositionHeader.split(";");//;���� ����
		for (String cd : splitedContentDisposition ) {//;������ŭ �ݺ�
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
				if(partValue != null && ! partValue.isEmpty()) {//���̰ų� ����ִٸ�
					String absolutePath = getServletContext().getRealPath("/files");//files��� ��θ� ã��
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
//				 		"values(p_seq.nextval, ?, ?, ?, ?)"); // ���ǹ� ����
//		 // ���ǹ� ä���
//		 pstmt.setString(1, request.getParameter("name")); // ù��° ����ǥ�� ��ü
//		 pstmt.setInt(2, Integer.parseInt(request.getParameter("price"))); // �ι�° ����ǥ�� ��ü
//		 pstmt.setString(3, request.getParameter("cno")); // ���δ�Ʈ �������� name�κ� �Ӽ� ��������
//		 pstmt.setString(4, (String) request.getAttribute("image")); // �̹����� Attribute�� �ؾ��� , Attribute�� �ڷ��� (String)�������
//		 
		
//		int result = pstmt.executeUpdate();// ���ǹ��� ����
			
		if(result >= 1) { // ��� ����
			request.setAttribute("name",  request.getParameter("name")); // map���� (key�� value�� ����)
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
