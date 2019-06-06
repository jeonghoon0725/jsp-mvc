package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class ProductDAO extends DAOBase{
	Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    ArrayList<ProductDTO> dtoList = null;
    ProductDTO dto = null;
    
    public int update(ProductDTO dto) {
    	int result = 0;
    	try {
			conn = getConnection();
	    	pstmt = conn.prepareStatement("update ja_034_product set name=?, price=?, cno=?, regdate=?, image=? where pno=?");// 질의문 생성
			//질의문 채우기
			pstmt.setInt(6, dto.getPno());//첫번째 물음표를 대체
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getPrice());
			pstmt.setString(3, dto.getCno());
			pstmt.setDate(4, new java.sql.Date(dto.getRegdate().getTime()));
			pstmt.setString(5, dto.getImage());
			
			
			result = pstmt.executeUpdate(); // 질의문 실행
			return result; // 성공 영향받은 row의 수
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { // 예외 발생 여부와 관계없이 항상 실행
			this.closeDBResources(rs, stmt, pstmt, conn);
		}
		return result;
	}
    public ProductDTO selectOne(ProductDTO p) {
    	try {
			conn = getConnection();//DAOBAse에 정의되어 있음
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from ja_034_product " + 
			"where pno=" + p.getPno());
			if(rs.next()) {
				dto = new ProductDTO();
				dto.setPno(rs.getInt(1));
				dto.setName(rs.getString(2));
				dto.setPrice(rs.getInt(3));
				dto.setCno(rs.getString(4));
				dto.setRegdate(rs.getDate(7));
				dto.setImage(rs.getString(8));
			}
			return dto;
		} catch (SQLException e) { //SQLException이 발생한 경우 실행
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally { // 예외 발생 여부와 관계없이 항상 실행
			this.closeDBResources(rs, stmt, pstmt, conn);
		}
		return dto;
	}
    public int insert(ProductDTO dto) { //dto에 파라미터를 저장  register
    	int result = 0;
    	// 질의문 채우기
		 try {
		   conn = getConnection();
		   pstmt = 
					conn.prepareStatement("insert into ja_034_product(pno, name, price, cno, regdate, image) " + 
						 		"values(p_seq.nextval, ?, ?, ?, ?, ?)"); // 질의문 생성
				 
			pstmt.setString(1, dto.getName());
			pstmt.setInt(2, dto.getPrice()); // 두번째 물음표를 대체
			pstmt.setString(3, dto.getCno()); // 프로덕트 레지스터 name부븐 속성 가져오기
			// java.util.Date <-> java.sql.Date 변환해줌
			pstmt.setDate(4, new java.sql.Date(dto.getRegdate().getTime()));
			pstmt.setString(5, dto.getImage()); // 이미지는 Attribute로 해야함 , Attribute는 자료형 (String)맞춰야함
			
			result = pstmt.executeUpdate();//질의문 실행
			return result; 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 질의문 생성
		 finally {
			 this.closeDBResources(rs, stmt, pstmt, conn);
		 }
		return result;
    }
    public ArrayList<ProductDTO> selectListBetween(int start, int end) {//list
    	try {
			conn = getConnection();
			stmt = conn.createStatement();
			String query = "select * from (select rownum rnum, pno, name, price, cno, regdate, image from ja_034_product order by pno desc) " +   
					"a where a.rnum between " + start + " and " + end;
	    	rs = stmt.executeQuery(query); //+"where id='" + id + "'");
			ProductDTO dto = null;
	    	dtoList = new ArrayList<ProductDTO>();
	    	// statement 객체로 지정된 sql실행, result set을 반환 받음
			while(rs.next()) {
				dto = new ProductDTO();
				dto.setPno(rs.getInt(2));
				dto.setName(rs.getString(3)); //rs.getString("name");
				dto.setPrice(rs.getInt(4));
				dto.setCno(rs.getString(5));
				dto.setRegdate(rs.getDate(6));
				dto.setImage(rs.getString(7));
				dtoList.add(dto);
			}
			//rs.close();	stmt.close();	conn.close();
			return dtoList; //전체 중에서 지정한 범위에 있는 행 또는 레코드들을 원소로 하는 ArrayList 객체
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally { // 예외 발생 여부와 관계없이 항상 실행
			this.closeDBResources(rs, stmt, pstmt, conn);
		}
		return dtoList;
    }
    public ArrayList<ProductDTO> selectListBetween1(int start, int end) {//list
    	try {
			conn = getConnection();
			stmt = conn.createStatement();//asc
			String query = "select a.* from (select rownum rnum, s.pno, s.name, s.price, s.cno, s.regdate, s.image from (select pno, name, price, cno, regdate, image from ja_034_product order by name desc) " +   
					"s where rownum <= " + end + " ) a where a.rnum >= " + start;
			String query1 ="select a.* from (select rownum rnum, s.pno, s.name, s.price, s.cno, s.regdate, s.image from (select pno, name, price, cno, regdate, image from ja_034_product order by name desc) " +   
				"s) a where a.rnum between " + start + " and " + end;
			
	    	rs = stmt.executeQuery(query1); //+"where id='" + id + "'");
			ProductDTO dto = null;
	    	dtoList = new ArrayList<ProductDTO>();
	    	// statement 객체로 지정된 sql실행, result set을 반환 받음
			while(rs.next()) {
				dto = new ProductDTO();
				dto.setPno(rs.getInt(2));
				dto.setName(rs.getString(3)); //rs.getString("name");
				dto.setPrice(rs.getInt(4));
				dto.setCno(rs.getString(5));
				dto.setRegdate(rs.getDate(6));
				dto.setImage(rs.getString(7));
				dtoList.add(dto);
			}
			//rs.close();	stmt.close();	conn.close();
			return dtoList; //전체 중에서 지정한 범위에 있는 행 또는 레코드들을 원소로 하는 ArrayList 객체
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally { // 예외 발생 여부와 관계없이 항상 실행
			this.closeDBResources(rs, stmt, pstmt, conn);
		}
		return dtoList;
    }
    
    public int selectCount() {
    	int totalRows = 0;
    	try {
			conn = getConnection();//DAOBAse에 정의되어 있음
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from ja_034_product");
			if(rs.next())
				totalRows = rs.getInt(1);
			return totalRows;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally { // 예외 발생 여부와 관계없이 항상 실행
			this.closeDBResources(rs, stmt, pstmt, conn);
		}
    	return totalRows;
    }
	public int delete(ProductDTO dto) {
		int result = 0;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement("delete ja_034_product where pno = ?");
			
			pstmt.setInt(1, dto.getPno());
			result = pstmt.executeUpdate(); // 질의문 실행
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 질의문 생성
		finally { // 예외 발생 여부와 관계없이 항상 실행
			this.closeDBResources(rs, stmt, pstmt, conn);
		}
		return result;
	}
	
	

}
