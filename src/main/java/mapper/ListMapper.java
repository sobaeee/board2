package mapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import domain.BoardVO;

public class ListMapper {

	public Collection<BoardVO> read() {
		//DB불러오기
		String url = "jdbc:mysql://localhost:3306/smart?characterEncoding=UTF-8&serverTimezone=Asia/Seoul";
		String user = "root";
		String password = "smart";
		String sql = " SELECT * FROM board ORDER BY num DESC ";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		BoardVO vo = null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.prepareStatement(sql);

			rs = stmt.executeQuery();
			while(rs.next()){
				vo = new BoardVO();
				vo.setNum(rs.getInt("num"));
				vo.setTitle(rs.getString("title"));
				vo.setWriter(rs.getString("writer"));
				vo.setWriterDate(rs.getTimestamp("writerDate"));
				list.add(vo);
			}
		} catch (Exception e){
			e.getLocalizedMessage();
		} finally {
			try{
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch(Exception e){
				e.getLocalizedMessage();
			}
		}
		return list;
	}

	public int totalRow() {
		String url = "jdbc:mysql://localhost:3306/smart?characterEncoding=UTF-8&serverTimezone=Asia/Seoul";
		String user = "root";
		String password = "smart";
		String sql = " SELECT count(*) as cnt FROM board ORDER BY num DESC ";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int totalRow = 0;
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.prepareStatement(sql);
			

			rs = stmt.executeQuery();
			if(rs.next()){
				totalRow = rs.getInt("cnt");
			}
		} catch (Exception e){
			e.getLocalizedMessage();
		} finally {
			try{
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch(Exception e){
				e.getLocalizedMessage();
			}
		}
		return totalRow;
	}
	//totalRow(""); 가 아래의 totalRow(String keyWord) 를 호출한다.
	
	public int totalRow(String field, String keyWord) {
		//DB불러오기
				String url = "jdbc:mysql://localhost:3306/smart?characterEncoding=UTF-8&serverTimezone=Asia/Seoul";
				String user = "root";
				String password = "smart";
				
				StringBuffer qry = new StringBuffer()
						.append(" SELECT count(*) cnt FROM board ")
						.append(" WHERE 1=1 ");
				if("title".equals(field)) {
					qry.append(" AND title LIKE concat('%',?,'%') ");
				}

				if("content".equals(field)) {
					qry.append(" AND content LIKE concat('%',?,'%') ");
				}

				if("titleContent".equals(field)) {
					qry.append(" AND (title LIKE concat('%',?,'%') OR content LIKE concat('%',?,'%')) ");
				}
				
				String sql = qry.toString();
				
				
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				
				int totalRow = 0;
				int idx = 1;
				
				try{
					Class.forName("com.mysql.cj.jdbc.Driver");
					
					conn = DriverManager.getConnection(url, user, password);
					stmt = conn.prepareStatement(sql);
					
					if("title".equals(field)) {
						stmt.setString(idx++, keyWord);
					}
					if("content".equals(field)) {
						stmt.setString(idx++, keyWord);
					}
					if("titleContent".equals(field)) {
						stmt.setString(idx++, keyWord);
						stmt.setString(idx++, keyWord);				
					}
					
					
					rs = stmt.executeQuery();
					if(rs.next()){
						totalRow = rs.getInt("cnt");
					}
				} catch (Exception e){
					e.getLocalizedMessage();
				} finally {
					try{
						if(rs != null) rs.close();
						if(stmt != null) stmt.close();
						if(conn != null) conn.close();
					} catch(Exception e){
						e.getLocalizedMessage();
					}
				}
		return totalRow;
	}

	public Collection<BoardVO> read(int startPage, int pageRow, String field, String keyWord) {
		//DB불러오기
				String url = "jdbc:mysql://localhost:3306/smart?characterEncoding=UTF-8&serverTimezone=Asia/Seoul";
				String user = "root";
				String password = "smart";
				StringBuffer qry = new StringBuffer()
						.append(" SELECT * FROM board ")
						.append(" WHERE 1=1 ");
				if("title".equals(field)) {
					qry.append(" AND title LIKE concat('%',?,'%') ");
				}	
				
				if("content".equals(field)) {
					qry.append(" AND content LIKE concat('%',?,'%') ");
				}
				
				if("titleContent".equals(field)) {
					qry.append(" AND (title LIKE concat('%',?,'%') OR content LIKE concat('%',?,'%')) ");
				}
				
				qry.append(" ORDER BY num DESC LIMIT ?,? ");
				
				String sql = qry.toString();
				
				Connection conn = null;
				PreparedStatement stmt = null;
				ResultSet rs = null;
				
				ArrayList<BoardVO> list = new ArrayList<BoardVO>();
				BoardVO vo = null;
				int idx = 1;
				try{
					Class.forName("com.mysql.cj.jdbc.Driver");
					
					conn = DriverManager.getConnection(url, user, password);
					stmt = conn.prepareStatement(sql);
					
					if("title".equals(field)) {
					stmt.setString(idx++, keyWord);
					}
					if("content".equals(field)) {
						stmt.setString(idx++, keyWord);
					}
					if("titleContent".equals(field)) {
						stmt.setString(idx++, keyWord);
						stmt.setString(idx++, keyWord);
					}
					stmt.setInt(idx++, startPage);
					stmt.setInt(idx++, pageRow);

					rs = stmt.executeQuery();
					while(rs.next()){
						vo = new BoardVO();
						vo.setNum(rs.getInt("num"));
						vo.setTitle(rs.getString("title"));
						vo.setWriter(rs.getString("writer"));
						vo.setWriterDate(rs.getTimestamp("writerDate"));
						list.add(vo);
					}
				} catch (Exception e){
					e.getLocalizedMessage();
				} finally {
					try{
						if(rs != null) rs.close();
						if(stmt != null) stmt.close();
						if(conn != null) conn.close();
					} catch(Exception e){
						e.getLocalizedMessage();
					}
				}
				return list;
			}
	//String keyWord 가 들어오고 sql문에 WHERE title Like concat('%',?,'%') 추가.
	
//	아래부턴 숙제
	
	public int totalRow(String titleF, String contentF, String writerF) {
		String url = "jdbc:mysql://localhost:3306/smart?characterEncoding=UTF-8&serverTimezone=Asia/Seoul";
		String user = "root";
		String password = "smart";
		
		StringBuffer qry = new StringBuffer()
				.append(" SELECT count(*) cnt FROM board ")
				.append(" WHERE 1=1 ");
		if(titleF == null) {
			titleF = "";
		} else {
			qry.append(" AND title LIKE concat('%',?,'%') ");
		}
		
		if(contentF == null) {
			contentF = "";
		} else {
			qry.append(" AND content LIKE concat('%',?,'%') ");
		}

		if(writerF == null) {
			writerF = "";
		} else {
			qry.append(" AND writer LIKE concat('%',?,'%') ");
		}
		
		String sql = qry.toString();
		
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int totalRow = 0;
		int idx = 1;
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.prepareStatement(sql);
			
			if(titleF != null) {
				stmt.setString(idx++, titleF);
			}
			if(contentF != null) {
				stmt.setString(idx++, contentF);
			}
			if(writerF != null) {
				stmt.setString(idx++, writerF);			
			}
			
			
			rs = stmt.executeQuery();
			if(rs.next()){
				totalRow = rs.getInt("cnt");
			}
		} catch (Exception e){
			e.getLocalizedMessage();
		} finally {
			try{
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch(Exception e){
				e.getLocalizedMessage();
			}
		}
return totalRow;
	}

	public Collection<BoardVO> read(int startPage, int pageRow, String titleF, String contentF, String writerF) {
		String url = "jdbc:mysql://localhost:3306/smart?characterEncoding=UTF-8&serverTimezone=Asia/Seoul";
		String user = "root";
		String password = "smart";
		StringBuffer qry = new StringBuffer()
				.append(" SELECT * FROM board ")
				.append(" WHERE 1=1 ");
		if(titleF == null) {
			titleF = "";
		} else {
			qry.append(" AND title LIKE concat('%',?,'%') ");
		}
		
		if(contentF == null) {
			contentF = "";
		} else {
			qry.append(" AND content LIKE concat('%',?,'%') ");
		}

		if(writerF == null) {
			writerF = "";
		} else {
			qry.append(" AND writer LIKE concat('%',?,'%') ");
		}
		
		qry.append(" ORDER BY num DESC LIMIT ?,? ");
		
		String sql = qry.toString();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		BoardVO vo = null;
		int idx = 1;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.prepareStatement(sql);
			
			if(titleF != null) {
				stmt.setString(idx++, titleF);
			}
			if(contentF != null) {
				stmt.setString(idx++, contentF);
			}
			if(writerF != null) {
				stmt.setString(idx++, writerF);			
			}
			
			stmt.setInt(idx++, startPage);
			stmt.setInt(idx++, pageRow);

			rs = stmt.executeQuery();
			while(rs.next()){
				vo = new BoardVO();
				vo.setNum(rs.getInt("num"));
				vo.setTitle(rs.getString("title"));
				vo.setWriter(rs.getString("writer"));
				vo.setWriterDate(rs.getTimestamp("writerDate"));
				list.add(vo);
			}
		} catch (Exception e){
			e.getLocalizedMessage();
		} finally {
			try{
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch(Exception e){
				e.getLocalizedMessage();
			}
		}
		return list;
	}
}
