import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.mysql.jdbc.PreparedStatement;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;


public class test {
 public static void main(String[] args) {
  String driver = "com.mysql.jdbc.Driver"; 
  String url = "jdbc:mysql://localhost:3306/?user=root";
  String userId = "root";
  String passwd = "1234";
  Connection conn;
  Statement stmt;
  ResultSet rs;
  
  
  try {
	  Class.forName(driver); // Driver Loading
	  conn = DriverManager.getConnection(url, userId, passwd); // Connection
	   
	  //String sql = "CREATE DATABASE my_database"; // SQL ¿€º∫
	  //stmt = conn.createStatement(); // Statement
	  //stmt.execute(sql);
	  String sql;
	  stmt = conn.createStatement();
	   
	  sql = "USE my_database";
	  stmt.execute(sql);
	   
	  File fXmlFile = new File("test.xml");
	  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	  Document doc = dBuilder.parse(fXmlFile);
		
	  doc.getDocumentElement().normalize();
		
	  NodeList nList = doc.getElementsByTagName("CD");
	  
	  //sql = "create table cd_table ( TITLE varchar(30), COUNTRY varchar(20), PRICE varchar(30) )";
	  //stmt.execute(sql);
	  
	  for(int i=0; i<nList.getLength(); i++) {
		  Node nNode = nList.item(i);
			
		  System.out.println("\nCurrent Element : " + nNode.getNodeName());
			
		  if (nNode.getNodeType() == Node.ELEMENT_NODE) {

			  Element eElement = (Element) nNode;
			  
			  sql = "insert into cd_table (TITLE,COUNTRY,PRICE) values (?,?,?)";
			  PreparedStatement statement = (PreparedStatement) conn.prepareStatement(sql);
			  statement.setString(1, eElement.getElementsByTagName("TITLE").item(0).getTextContent());
			  statement.setString(2, eElement.getElementsByTagName("COUNTRY").item(0).getTextContent());
			  statement.setString(3, eElement.getElementsByTagName("PRICE").item(0).getTextContent());
			  statement.executeUpdate();  

			  //System.out.println("Title : " + eElement.getElementsByTagName("TITLE").item(0).getTextContent());
			  //System.out.println("Artist : " + eElement.getElementsByTagName("ARTIST").item(0).getTextContent());
			  //System.out.println("Country : " + eElement.getElementsByTagName("COUNTRY").item(0).getTextContent());
			  //System.out.println("Company : " + eElement.getElementsByTagName("COMPANY").item(0).getTextContent());
			  //System.out.println("Price : " + eElement.getElementsByTagName("PRICE").item(0).getTextContent());
			  //System.out.println("Year : " + eElement.getElementsByTagName("YEAR").item(0).getTextContent());

		  }
	  }
   //sql = "create table my_table ( id int, first_name varchar(20), email varchar(30) )";
   //stmt.execute(sql);
   
   /*sql = "insert into my_table (id, first_name, email) values "
     + "(1, \"woongjin\", \"finalboogi@naver.com\"), "
     + "(2, \"woongjin2\", \"finalboogi2@naver.com\"), "
     + "(3, \"woongjin3\", \"finalboogi3@naver.com\")";
   stmt.execute(sql);*/
   
   sql = "select * from cd_table";
   rs = stmt.executeQuery(sql); // ResultSet
   
   while(rs.next()) {
	   System.out.println("Title : " + rs.getString("TITLE") 
    	+ "\nCountry: " + rs.getString("COUNTRY") 
  		+ "\nPrice: " + rs.getString("PRICE") + "\n"); // Data get
   }
   
   stmt.close();
   conn.close(); // close
  } catch(ClassNotFoundException | SQLException e) {
	  e.printStackTrace();
  } catch(Exception e) {
	  e.printStackTrace();
  }
  
 }
}