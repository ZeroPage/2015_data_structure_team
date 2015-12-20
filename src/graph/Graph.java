import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;

import javax.xml.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.swing.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.*;
import org.xml.sax.SAXException;

public class Graph {
	public ArrayList<StationVertex> vertices;
	public ArrayList<RouteEdge> edges;
	public Graph() {
		vertices = new ArrayList<StationVertex>();
		edges = new ArrayList<RouteEdge>();
	}
	public void MakeWholeMap() {
		File xmlFile = new File("savedv3.xml");

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(xmlFile);

			doc.getDocumentElement().normalize();

			Element dsd = (Element)doc.getElementsByTagName("dsd").item(0);
			Element station1 = (Element)dsd.getElementsByTagName("station").item(0);
			NodeList station = station1.getElementsByTagName("stationVerTex");
			for (int s = 0; s < station.getLength(); s++) {
				StationVertex sv = new StationVertex();
				Element elem = (Element)station.item(s);
				sv.stationID = elem.getAttribute("stnID");
				sv.stationName = elem.getAttribute("stdNm");
				sv.stationNumber = elem.getAttribute("stdNo");
				sv.gpsX = Float.parseFloat(elem.getAttribute("gpsX"));
				sv.gpsY= Float.parseFloat(elem.getAttribute("gpsY"));
				vertices.add(sv);
			}
		
		
			Element route1 = (Element)dsd.getElementsByTagName("route").item(0);
			NodeList route = route1.getElementsByTagName("routeEdge");
			for (int s = 0; s < route.getLength(); s++) {
				RouteEdge re = new RouteEdge();
				Element elem = (Element)route.item(s);
			
				for(int i=0; i<vertices.size(); i++){
					if((vertices.get(i).stationID.equals(elem.getAttribute("stnFROM"))) && !(elem.getAttribute("stnFROM").equals("0"))){
						re.startStation = vertices.get(i);
						vertices.get(i).routes.add(re); //간선의 startStation이 정해졌으면 그 해당 startStation도 멤버변수 routes 배열에 추가합니다.
					}
					else if((vertices.get(i).stationID.equals(elem.getAttribute("stnTO"))) && !(elem.getAttribute("stnTO").equals("0"))){
						re.endStation = vertices.get(i);
					}
				}
			
				re.distance = Float.parseFloat(elem.getAttribute("dist"));
				re.busID = elem.getAttribute("busID");
				edges.add(re);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
}
