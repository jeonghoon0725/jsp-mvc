package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

public class MemberDAO extends DAOBase{
	//멤버변수 선언
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    ArrayList<MemberDTO> alMember = null;//레코드들의 대한 집합 객체
    MemberDTO dto = null;//레코드와 매핑되는 객체
    HttpSession session = null;
    
    public ArrayList<MemberDTO> list() {//list
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select * from ja_034_member"); //+"where id='" + id + "'");
			MemberDTO dto = null;
			alMember = new ArrayList<MemberDTO>();	
			// statement 객체로 지정된 sql실행, result set을 반환 받음
			while(rs.next()) {
				dto = new MemberDTO();
				//if(email.equals(rs.getString(1)) && pw.equals(rs.getString(2))) {
				dto.setEmail(rs.getString(1));
				dto.setPw(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setPhone(rs.getString(4));
				alMember.add(dto);
			}
			return alMember;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally { // 예외 발생 여부와 관계없이 항상 실행
			this.closeDBResources(rs, stmt, pstmt, conn);
		}
		return alMember;
    }
    public int insert(MemberDTO dto) {	
    	int result = 0;
    	try {
        conn = getConnection();
		pstmt = conn.prepareStatement("insert into ja_034_member values(?, ?, ?, ?)");
		pstmt.setString(1, dto.getEmail());//첫번째 물음표를 대체
		pstmt.setString(2, dto.getPw()); //두번째 물음표 대체
		pstmt.setString(3, dto.getName());
		pstmt.setString(4, dto.getPhone());
		result = pstmt.executeUpdate();
		return result;
		} 
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 질의문 생성
		//질의문 채우기
    	finally { // 예외 발생 여부와 관계없이 항상 실행
			this.closeDBResources(rs, stmt, pstmt, conn);
		}
		return result;
    }
    public int delete(MemberDTO dto) {
    	int result = 0;
    	
    	try {
			conn = getConnection();
			pstmt = conn.prepareStatement("delete from ja_034_member where email=?");// 질의문 생성
	    	pstmt.setString(1, dto.getEmail());
	    			
	    	result = pstmt.executeUpdate(); // 질의문 실행
	    	return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally { // 예외 발생 여부와 관계없이 항상 실행
			this.closeDBResources(rs, stmt, pstmt, conn);
		}
		return result;
    	
    }
    

    public MemberDTO login(String email,String pw) {
    	dto = new MemberDTO();
    	try {
        	conn = getConnection();
        	pstmt = conn.prepareStatement("select * from ja_034_member where email = ? and pw = ?");
        
        	pstmt.setString(1, email);
        	pstmt.setString(2, pw);
        	
        	rs = pstmt.executeQuery();
        	if(rs.next()) {
        	dto.setEmail(rs.getString(1));
			dto.setPw(rs.getString(2));
			dto.setName(rs.getString(3));
			dto.setPhone(rs.getString(4));
        	}
        	return dto;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	finally { 
			this.closeDBResources(rs, stmt, pstmt, conn);
		}
		return dto;
    }
    
    /*
     * 
    public MemberDTO login(String email,String pw) {
    	dto = new MemberDTO(); //로그인 결과 member를 보내주기 위해 생성
    	try {
    		conn = getConnection();
			stmt = conn.createStatement();
			//pstmt = conn.prepareStatement("select * from ja_034_member where email = '"+email+"' and pw = '"+pw+"'");
			rs = stmt.executeQuery("select * from ja_034_member where email = '"+email+"' and pw = '"+pw+"'"); //쿼리 실행
			
			//statement 객체로 지정된 sql 실행, result set을 반환받음
			if(rs.next()) { // 질의 결과에 다음 레코드가 존재하면 true, 아니면 false	
				//if(email.equals(rs.getString(1)) && pw.equals(rs.getString(2))) {
				dto.setEmail(rs.getString(1));
				dto.setPw(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setPhone(rs.getString(4));
				}
			return dto;//성공해서 dto 가져가는 부분
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
    	finally { // 예외 발생 여부와 관계없이 항상 실행
			this.closeDBResources(rs, stmt, pstmt, conn);
		}
		return dto;//실패해서 null로 반환하는 부분
	}
	*/
		//HttpSession session = request.getSession();
    public MemberDTO info(String email) {
    	dto = new MemberDTO();
    	try {
    		conn = getConnection();
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select * from ja_034_member where " + "email='" + email + "'");
			//statement 객체로 지정된 sql 실행, result set을 반환받음
			if(rs.next()) { // 질의 결과에 다음 레코드가 존재하면 true, 아니면 false
				//if(email.equals(rs.getString(1)) && pw.equals(rs.getString(2))) {
				dto.setEmail(rs.getString(1));
				dto.setPw(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setPhone(rs.getString(4));
				}
				return dto;
    		}
			catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} // "' and " + "pw='" + pw + "'");
    	finally { // 예외 발생 여부와 관계없이 항상 실행
			this.closeDBResources(rs, stmt, pstmt, conn);
		}
		return dto;
    	}		
    public int update(MemberDTO dto) {
    	int result = 0;
    	try {
			conn = getConnection();
			stmt = conn.createStatement();
	    	pstmt = conn.prepareStatement("update ja_034_member set name=?, phone=? where email=?");// 질의문 생성
			//질의문 채우기
			pstmt.setString(3, dto.getEmail());//첫번째 물음표를 대체
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getPhone());
			result = pstmt.executeUpdate(); // 질의문 실행
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally { // 예외 발생 여부와 관계없이 항상 실행
			this.closeDBResources(rs, stmt, pstmt, conn);
		}
		return result;
    }
}
    
    

