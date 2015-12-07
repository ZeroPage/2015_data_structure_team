import java.sql.*;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			System.out.println("마이에스큐엘 드라이버 검색 성공!");
			// Class.forName("oracle.jdbc.driver.OracleDriver"); //오라클일경우
		} catch (ClassNotFoundException e) {
			System.err.println("error = 드라이버 검색 오류!");
		}
		
		String url = "jdbc:mysql://165.194.27.173:3306";
		String id = "guest";
		String pass = "1q2w3e4r!!";
		
		try {
			Connection conn = DriverManager.getConnection(url, id, pass);

			System.out.println("연결성공");
		} catch (SQLException e) {
			System.err.println("error sql = " + e);
		}
	}

}
