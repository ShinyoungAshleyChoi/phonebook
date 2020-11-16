package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.ContactVo;


/**
 * @author shinyoung
 *
 */
public class ContactDao {
	
	/**
	 * 1. 연락처 전체 보기 코드
	 * 1) Connection 생성(DB연결)
	 * 2) PreparedStatement 생성 (쿼리 명령 전달)
	 * 3) ResultSet 생성 (실행 결과 저장)
	 * 4) ArrayList 생성 (실행 결과 저장)
	 * 5) select 문 작성하여 Stringbuilder에 저장
	 * 6) Vo 에 각 컬럼 내용을 미리 선언해 놓은 변수에 각각 저장
	 * 7) Vo클래스를 Arraylist 에 저장
	 * 8) 생성한 클래스 닫기
	 * @return
	 * ArrayList<ContactVo>
	 */
	public ArrayList<ContactVo> list() {
		ArrayList<ContactVo> contal = new ArrayList<ContactVo>();
		
		Connection con 			= getConnection(); 			//DB연결 클래스
		PreparedStatement pstmt = null;			   			//명령을 전달하는 클래스
		ResultSet rs 			= null;			   			//결과를 받는 클래스
		
		StringBuilder sql = new StringBuilder();  			//쿼리문 저장
		sql.append("select c.psname 					");
		sql.append("     , c.phnumber					");
		sql.append("     , c.pscity	   					");
		sql.append("     , p.categorynm 				");
		sql.append("  from contact c 					");
		sql.append("     , pscategory p     			");
		sql.append(" where c.pscategory = p.categoryno  ");  //회원목록 테이블의 FK처리된 종류번호 컬럼과 종류 테이블의 PK처리된 종류번호 컬럼을 동등 조건으로 조인
		sql.append(" order by c.psname					");
		
		try {
			pstmt = con.prepareStatement(sql.toString()); 	//위에 작성한 쿼리 명령 전달
			rs	  = pstmt.executeQuery();				  	//쿼리를 실행하여 ResultSet rs에 저장
			
			while(rs.next()) {								//rs에 저장 결과 없을 때 까지 아래 반복
				ContactVo cont = new ContactVo();		  	//Vo클래스 new하여 불러옴
				
				cont.setName(rs.getString("psname"));     	//Vo에 미리 선언해둔 Setter함수를 통해 Vo에 저장
				cont.setNumber(rs.getString("phnumber"));
				cont.setCity(rs.getString("pscity"));
				cont.setCategory(rs.getString("categorynm"));

				contal.add(cont);
			}
			
		} catch (SQLException e) {							//예기치 못한 예외를 처리
			e.printStackTrace();	
		} finally {
			close(con, pstmt, rs);							//생성 클래스(Connection, PreapredStatement, ResultSet)닫
		}
		return contal;										//저장된 Arraylist반환
	}
	
	/**
	 * 2. 이름으로 연락처를 검색하는 코드
	 * 1) Connection 생성 (DB연결)
	 * 2) PreparedStatement 생성 (쿼리 명령 전달)
	 * 3) ResultSet생성 (실행 결과를 저장)
	 * 4) ArrayList 생성 (최종 결과 저장)
	 * 5) 특정 이름을 조건으로 하여 회원 정보 불러오는 쿼리문 작성하여 StringBuilder에 저장
	 * 6) Vo 에 각 컬럼 내용을 미리 선언해 놓은 변수에 각각 저장
	 * 7) Vo클래스를 Arraylist 에 저장
	 * 8) 생성한 클래스 닫기
	 * @return
	 * ArrayList<ContactVo>
	 */
	public ArrayList<ContactVo> search(String name) {
		ArrayList<ContactVo> contal = new ArrayList<ContactVo>();  //ArrayList생성
		Connection con = getConnection();						   //Connection 생성 (DB 연결)
		PreparedStatement pstmt = null;							   //PreparedStatement 생성 (쿼리 명령 전달)
		ResultSet rs = null;									   //ResultSet 생성 (실행 결과 저장)
		
		StringBuilder sql = new StringBuilder();                   //StringBuilder에 아래 쿼리문 저장
		sql.append("select c.psname 				    ");
		sql.append("     , c.phnumber				    ");
		sql.append("     , c.pscity	   				    ");
		sql.append("     , p.categorynm				    ");
		sql.append("  from contact c 				    ");
		sql.append("     , pscategory p      		    ");
		sql.append(" where c.pscategory = p.categoryno  ");			//조인조건
		sql.append("   and c.psname = ?                 ");			//조회조건 (특정 이름을 가진 row만 검색)
		sql.append(" order by c.psname				    ");
		
		try {
			pstmt = con.prepareStatement(sql.toString()); 			//위 작성한 쿼리 명령 전달
			pstmt.setString(1, name);								//위 쿼리문에서 물음표 처리된 부분에 'name'변수 적
			rs = pstmt.executeQuery();								//실행 결과 저장
			
			while(rs.next()) {										//rs에 저장 결과가 없을 때 아 까지 반복
				ContactVo cont = new ContactVo();					//Vo클래스 new하여 불러옴
				cont.setName(rs.getString("psname"));				//Vo클래스에 미리 선언해둔 Setter를 통하여 해당 컬럼 내용들을 Vo클래스에 저장
				cont.setNumber(rs.getString("phnumber"));
				cont.setCity(rs.getString("pscity"));
				cont.setCategory(rs.getString("categorynm"));

				contal.add(cont);									//위에 저장한 Vo클래스를 ArrayList에 저장
			}	
		} catch (SQLException e) {									//예기치 못한 예외 처리
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);									//생성한 클래스들(Connection, PreparedStatement, ResultSet) 닫
		}
		
		return contal;												//저장된 Arraylist 반환
		
	}
	
	/**
	 * 3. 데이터베이스에 연락처 추가하는 코드
	 * 1) Connection 생성 (DB 연결)
	 * 2) PreparedStatement 생성 (쿼리 명령 전달)
	 * 3) 쿼리문 작성하여 StringBuilder에 저장
	 * 4) 쿼리문 실행, 실행결과를 cnt변수에 담아 1이 아니면 (실행이 안됐으면) 실행결과 false 반환
	 * 5) SQL익셉션이 발생하면 "저장실패" 출력
	 * 6) 생성한 클래스들 (Connection, PreparedStatement 닫기)
	 * @return
	 * boolean
	 */
	public boolean insertCont(ContactVo cont) {
		boolean isInsert = true;
		
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into contact  ");
		sql.append("values (?		     ");
		sql.append("      , ?            ");
		sql.append("      , ?		     ");
		sql.append("      , ?)           ");

		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, cont.getName());
			pstmt.setString(2, cont.getNumber());
			pstmt.setString(3, cont.getCity());
			pstmt.setInt(4, cont.getCategoryno());
			
			int cnt = pstmt.executeUpdate();
			
			if(cnt != 1) {
				isInsert = false;
			}
		} catch (SQLException e) {								//예기치 못한 예외 처리
			System.out.println("저장실패");
			e.printStackTrace();
		} finally {
			close(con, pstmt);									//생성한 클래스들(Connection, PreparedStatement) 닫기
		}
		
		return isInsert;
	}
	
	/**
	 * 4.연락처 수정하는 메서드
	 * 1) Conenction 생성 (DB연결)
	 * 2) PreparedStatement 생성 (쿼리 명령 전달)
	 * 3) StringBuilder 생성하여 쿼리문 저장
	 * 4) 쿼리문 실행
	 * 5) 실행결과를 cnt에 담아 1이 아니면 (실행이 안됐으면) 결과 false 반환
	 * 6) 예기치 못한 SQLException 처리
	 * 7) 생성한 클래스들(Connection, PreparedStatement닫기)
	 * @param cont
	 * @return
	 * boolean
	 */
	public boolean updateCont(ContactVo cont) {
		boolean isUpdate = true;
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("update contact        ");
		sql.append("   set psname 	  = ? ");
		sql.append("     , phnumber   = ? ");
		sql.append("     , pscity 	  = ? ");
		sql.append("     , pscategory = ? ");
		sql.append(" where phnumber   = ? ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, cont.getName());
			pstmt.setString(2, cont.getNumber());
			pstmt.setString(3, cont.getCity());
			pstmt.setInt(4, cont.getCategoryno());
			pstmt.setString(5, cont.getNumberbefore());		//수정하려는 회원의 변경 전 전화번호
			
			int cnt = pstmt.executeUpdate();
			
			
			if(cnt != 1) {
				isUpdate = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
		return isUpdate;
	}
	
	/**
	 * 5. 연락처 삭제하는 메서드
	 * 1) Conenction 생성 (DB연결)
	 * 2) PreparedStatement 생성 (쿼리 명령 전달)
	 * 3) StringBuilder 생성하여 쿼리문 저장
	 * 4) 쿼리문 실행
	 * 5) 실행결과를 cnt에 담아 1이 아니면 (실행이 안됐으면) 결과 false 반환
	 * 6) 예기치 못한 SQLException 처리
	 * 7) 생성한 클래스들(Connection, PreparedStatement닫기)
	 * @param cont
	 * @return
	 * boolean
	 */
	public boolean deleteCont(ContactVo cont) {
		boolean isDelete = true;
		Connection con = getConnection();
		PreparedStatement pstmt = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("delete from contact         ");
		sql.append(" where phnumber        = ?  ");
		
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, cont.getNumber());

			int cnt = pstmt.executeUpdate();
			
			if(cnt != 1) {
				isDelete = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
		return isDelete;
	}
	
	/**
	 * 커넥션(DB연결 기능)을 불러오는 코드
	 * @return
	 * Connection
	 */
	private Connection getConnection() {
		Connection con = null;
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "system";
		String password = "oracle";
		
		try {
			con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	/**
	 * Connection과 preparesStatement를 닫 코드
	 * @param con
	 * @param pstmt
	 * void
	 */
	private void close(Connection con, PreparedStatement pstmt) {
			try {
				if(pstmt != null) {
				pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(con != null) {
				con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	/**
	 * Connection, PreparedStatement, Resultset을 닫는 코드
	 * 위 코드를 오버로딩 한 것
	 * @param con
	 * @param pstmt
	 * @param rs
	 * void
	 */
	private void close(Connection con
			         , PreparedStatement pstmt
			         , ResultSet rs) {
		try {
			if(rs != null)
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close(con,pstmt);
	}
}


