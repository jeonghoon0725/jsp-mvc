package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

public class MemberDAO extends DAOBase{
	//������� ����
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    ArrayList<MemberDTO> alMember = null;//���ڵ���� ���� ���� ��ü
    MemberDTO dto = null;//���ڵ�� ���εǴ� ��ü
    HttpSession session = null;
    
    public ArrayList<MemberDTO> list() {//list
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select * from ja_034_member"); //+"where id='" + id + "'");
			MemberDTO dto = null;
			alMember = new ArrayList<MemberDTO>();	
			// statement ��ü�� ������ sql����, result set�� ��ȯ ����
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
		finally { // ���� �߻� ���ο� ������� �׻� ����
			this.closeDBResources(rs, stmt, pstmt, conn);
		}
		return alMember;
    }
    public int insert(MemberDTO dto) {	
    	int result = 0;
    	try {
        conn = getConnection();
		pstmt = conn.prepareStatement("insert into ja_034_member values(?, ?, ?, ?)");
		pstmt.setString(1, dto.getEmail());//ù��° ����ǥ�� ��ü
		pstmt.setString(2, dto.getPw()); //�ι�° ����ǥ ��ü
		pstmt.setString(3, dto.getName());
		pstmt.setString(4, dto.getPhone());
		result = pstmt.executeUpdate();
		return result;
		} 
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// ���ǹ� ����
		//���ǹ� ä���
    	finally { // ���� �߻� ���ο� ������� �׻� ����
			this.closeDBResources(rs, stmt, pstmt, conn);
		}
		return result;
    }
    public int delete(MemberDTO dto) {
    	int result = 0;
    	
    	try {
			conn = getConnection();
			pstmt = conn.prepareStatement("delete from ja_034_member where email=?");// ���ǹ� ����
	    	pstmt.setString(1, dto.getEmail());
	    			
	    	result = pstmt.executeUpdate(); // ���ǹ� ����
	    	return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally { // ���� �߻� ���ο� ������� �׻� ����
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
    	dto = new MemberDTO(); //�α��� ��� member�� �����ֱ� ���� ����
    	try {
    		conn = getConnection();
			stmt = conn.createStatement();
			//pstmt = conn.prepareStatement("select * from ja_034_member where email = '"+email+"' and pw = '"+pw+"'");
			rs = stmt.executeQuery("select * from ja_034_member where email = '"+email+"' and pw = '"+pw+"'"); //���� ����
			
			//statement ��ü�� ������ sql ����, result set�� ��ȯ����
			if(rs.next()) { // ���� ����� ���� ���ڵ尡 �����ϸ� true, �ƴϸ� false	
				//if(email.equals(rs.getString(1)) && pw.equals(rs.getString(2))) {
				dto.setEmail(rs.getString(1));
				dto.setPw(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setPhone(rs.getString(4));
				}
			return dto;//�����ؼ� dto �������� �κ�
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
    	finally { // ���� �߻� ���ο� ������� �׻� ����
			this.closeDBResources(rs, stmt, pstmt, conn);
		}
		return dto;//�����ؼ� null�� ��ȯ�ϴ� �κ�
	}
	*/
		//HttpSession session = request.getSession();
    public MemberDTO info(String email) {
    	dto = new MemberDTO();
    	try {
    		conn = getConnection();
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("select * from ja_034_member where " + "email='" + email + "'");
			//statement ��ü�� ������ sql ����, result set�� ��ȯ����
			if(rs.next()) { // ���� ����� ���� ���ڵ尡 �����ϸ� true, �ƴϸ� false
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
    	finally { // ���� �߻� ���ο� ������� �׻� ����
			this.closeDBResources(rs, stmt, pstmt, conn);
		}
		return dto;
    	}		
    public int update(MemberDTO dto) {
    	int result = 0;
    	try {
			conn = getConnection();
			stmt = conn.createStatement();
	    	pstmt = conn.prepareStatement("update ja_034_member set name=?, phone=? where email=?");// ���ǹ� ����
			//���ǹ� ä���
			pstmt.setString(3, dto.getEmail());//ù��° ����ǥ�� ��ü
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getPhone());
			result = pstmt.executeUpdate(); // ���ǹ� ����
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	finally { // ���� �߻� ���ο� ������� �׻� ����
			this.closeDBResources(rs, stmt, pstmt, conn);
		}
		return result;
    }
}
    
    

