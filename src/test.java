import java.sql.*;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			System.out.println("���̿���ť�� ����̹� �˻� ����!");
			// Class.forName("oracle.jdbc.driver.OracleDriver"); //����Ŭ�ϰ��
		} catch (ClassNotFoundException e) {
			System.err.println("error = ����̹� �˻� ����!");
		}
		
		String url = "jdbc:mysql://165.194.27.173:3306";
		String id = "guest";
		String pass = "1q2w3e4r!!";
		
		try {
			Connection conn = DriverManager.getConnection(url, id, pass);

			System.out.println("���Ἲ��");
		} catch (SQLException e) {
			System.err.println("error sql = " + e);
		}
	}

}
