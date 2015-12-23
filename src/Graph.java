import java.io.*;
import java.util.ArrayList;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
						re.distance = Float.parseFloat(elem.getAttribute("dist"));
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
	public void PrintMap() {
		/*StationVertex currentVertex = null;
		RouteEdge currentEdge = null;
		for(int i=0; i<vertices.size(); i++) {
			currentVertex = vertices.get(i);
			System.out.println(i + "- 역이름  : " + currentVertex.stationName);
			System.out.println("역 ID : " + currentVertex.stationID);
			System.out.println("간선수 : " + currentVertex.routes.size());
			for(int j=0; j<currentVertex.routes.size(); j++) {
				currentEdge = currentVertex.routes.get(j);
				System.out.println("간선(버스ID : " + currentEdge.busID + ") - 시작역 : " + currentEdge.startStation.stationName + "(" + currentEdge.startStation.stationID + ") , 도착역 : " + currentEdge.endStation.stationName + "(" + currentEdge.endStation.stationID + "), 거리 : " + currentEdge.distance);
			}
			System.out.println("\n");
		}*/
		
		StationVertex currentVertex = null;
		RouteEdge currentEdge = null;
		for(int i=0; i<vertices.size(); i++) {
			currentVertex = vertices.get(i);
			System.out.print("역이름 : " + currentVertex.stationName);
			if(i<vertices.size()-1)
				System.out.println("-> 다음역 : " + edges.get(i).endStation.stationName + ", 버스 : " + edges.get(i).busID + ", 거리 : " + edges.get(i).distance);
		}
	}
	public RouteEdge GetNextSameBus(RouteEdge edge,StationVertex target) {
		StationVertex nextStation = edge.endStation;
		RouteEdge tempEdge = null;
		for(int i=0; i<nextStation.routes.size(); i++) {
			if(nextStation.routes.get(i).endStation.equals(target.stationName) && edge.busID.equals(nextStation.routes.get(i).busID)) {
				tempEdge = nextStation.routes.get(i);
				break;
			}
		}
		return tempEdge;
	}
	public int GetContinualBusCount(RouteEdge edge,StationVertex end,String[] stationPath,int currentStart) {
		int temp = currentStart;
		int count = 0;
		RouteEdge currentEdge = edge;
		while(!(currentEdge.startStation.stationName.equals(end.stationName)) && (currentEdge = GetNextSameBus(currentEdge,StationSearch(stationPath[currentStart--])))!=null){
			count++;
		}
		return count;
	}
	public StationVertex StationSearch(String name) {
		for(int i=0; i<vertices.size(); i++)
			if(name.equals(vertices.get(i).stationName)) {
				return vertices.get(i);
			}
		return null;
	}
	public int FindStationIndex(String name){
		int i;
		for(i=0; i<vertices.size(); i++)
			if(name.equals(vertices.get(i).stationName)) {
				return i;
			}
		return -1;
	}
	public Graph GetShortestPath(String startName,String endName) {
		Graph result = null;
		StationVertex start = StationSearch(startName);
		StationVertex end = StationSearch(endName);
		
		int i = 0,j = 0,k = 0;
		
		float[][] cost = new float[vertices.size()][vertices.size()];
		for(i=0; i<vertices.size(); i++) {
			StationVertex currentVertex = vertices.get(i);
			for(j=0; j<vertices.size(); j++) {
				if(i==j)
					cost[i][j] = 0;
				else {
					cost[i][j] = 9999;
					for(k=0; k<currentVertex.routes.size(); k++) {
						if(k==0) {
							cost[i][FindStationIndex(currentVertex.routes.get(k).endStation.stationName)] = currentVertex.routes.get(k).distance;
						}
						else if(k>0 && !currentVertex.routes.get(k).endStation.stationName.equals(currentVertex.routes.get(k-1).endStation.stationName)) {
							cost[i][FindStationIndex(currentVertex.routes.get(k).endStation.stationName)] = currentVertex.routes.get(k).distance;
						}
					}
				}
			}
		}
		
		float[] distance = new float[vertices.size()];
		int[] visited = new int[vertices.size()];
		int[] preD = new int[vertices.size()];
		float min;
		
		for(i=0; i<vertices.size(); i++) {
			visited[i] = 0;
			distance[i] = 9999;
			preD[i] = 0;
		}
		
		distance[FindStationIndex(start.stationName)] = 0;
		
		for(i=0; i<vertices.size(); i++) {
			min = 9999;
			for(j=0; j<vertices.size(); j++) {
				if(min>distance[j] && visited[j]==0) {
					min = distance[j];
					k = j;
				}
			}
			visited[k] = 1;
			if(min==9999) {
				break;
			}
			for(j=0; j<vertices.size(); j++) {
				if(distance[j]>distance[k] + cost[k][j]) {
					distance[j] = distance[k] + cost[k][j];
					preD[j] = k;
				}
			}
		}
		
		result = new Graph();
		
		String path[] = new String[vertices.size()];
		int pathCount = 0;
		k = FindStationIndex(end.stationName);
		while(true) {
			path[pathCount++] = vertices.get(k).stationName;
			if(k==FindStationIndex(start.stationName))
				break;
			k = preD[k];
		}
		ArrayList<RouteEdge> tempEdge = new ArrayList<RouteEdge>();
		for(i=pathCount-1; i>0; i--) {
			k = 0;
			int edgeCount = 0;
			int maxCount = -1;
			StationVertex currentVertex = StationSearch(path[i]);
			result.vertices.add(currentVertex);
			for(j=0; j<currentVertex.routes.size(); j++) {
				RouteEdge currentEdge = currentVertex.routes.get(j);
				if(currentEdge.endStation.stationName.equals(StationSearch(path[i-1]).stationName))
					tempEdge.add(currentEdge);
				
			}
			if(tempEdge.size()==1) {
				result.edges.add(tempEdge.get(0));
				//System.out.println(result.edges.get(result.edges.size()-1).startStation.stationName + result.edges.get(result.edges.size()-1).endStation.stationName + "들어갔습니다.");
			}
			else {
				int currentOptimal = 0;
				RouteEdge optimalBus = null;
				for(k=0; k<tempEdge.size(); k++) {
					optimalBus = tempEdge.get(k);
					edgeCount = GetContinualBusCount(optimalBus,end,path,i);
					if(edgeCount>maxCount) {
						maxCount = edgeCount;
						currentOptimal = k;
					}
				}
				result.edges.add(optimalBus);
				//System.out.println(result.edges.get(result.edges.size()-1).startStation.stationName + result.edges.get(result.edges.size()-1).endStation.stationName + "들어갔습니다.");
				for(k=1; k<=maxCount; k++) {
					optimalBus = GetNextSameBus(optimalBus,StationSearch(path[i-k]));
					result.edges.add(optimalBus);
					//System.out.println(result.edges.get(result.edges.size()-1).startStation.stationName + result.edges.get(result.edges.size()-1).endStation.stationName + "들어갔습니다.");
					result.vertices.add(StationSearch(path[i-k]));
				}
				i = i-k+1;
			}
			tempEdge.clear();
		}
		result.vertices.add(StationSearch(path[0]));
		
		return result;
	}
	public void dijkstra() {
		//StationVertex start = vertices.get(1868);
		//StationVertex end = vertices.get(1864);
		StationVertex start = StationSearch("올림픽공원평화의문광장");
		StationVertex end = StationSearch("천호역");
		
		int i = 0,j = 0,k = 0;
		
		float[][] cost = new float[vertices.size()][vertices.size()];
		for(i=0; i<vertices.size(); i++) {
			StationVertex currentVertex = vertices.get(i);
			for(j=0; j<vertices.size(); j++) {
				if(i==j)
					cost[i][j] = 0;
				else {
					cost[i][j] = 9999;
					for(k=0; k<currentVertex.routes.size(); k++) {
						if(k==0) {
							cost[i][FindStationIndex(currentVertex.routes.get(k).endStation.stationName)] = currentVertex.routes.get(k).distance;
						}
						else if(k>0 && !currentVertex.routes.get(k).endStation.stationName.equals(currentVertex.routes.get(k-1).endStation.stationName)) {
							cost[i][FindStationIndex(currentVertex.routes.get(k).endStation.stationName)] = currentVertex.routes.get(k).distance;
						}
					}
				}
			}
		}
		
		float[] distance = new float[vertices.size()];
		int[] visited = new int[vertices.size()];
		int[] preD = new int[vertices.size()];
		float min;
		
		for(i=0; i<vertices.size(); i++) {
			visited[i] = 0;
			distance[i] = 9999;
			preD[i] = 0;
		}
		
		distance[FindStationIndex(start.stationName)] = 0;
		//visited[0] = 1;
		
		for(i=0; i<vertices.size(); i++) {
			min = 9999;
			for(j=0; j<vertices.size(); j++) {
				if(min>distance[j] && visited[j]==0) {
					min = distance[j];
					k = j;
				}
			}
			visited[k] = 1;
			if(min==9999) {
				break;
			}
			for(j=0; j<vertices.size(); j++) {
				if(distance[j]>distance[k] + cost[k][j]) {
					distance[j] = distance[k] + cost[k][j];
					preD[j] = k;
				}
			}
		}
		
		System.out.print("시작점 : " + start.stationName + ", 도착점 : " + end.stationName + ", 거리  = " + distance[FindStationIndex(end.stationName)]);
		String path[] = new String[vertices.size()];
		int pathCount = 0;
		k = FindStationIndex(end.stationName);
		while(true) {
			path[pathCount++] = vertices.get(k).stationName;
			if(k==FindStationIndex(start.stationName))
				break;
			k = preD[k];
		}
		System.out.print("  경로 : ");
		for(i=pathCount-1; i>=0; i--)
			System.out.print(path[i] + "  ");
		System.out.print("\n");
	}
	
	
	public Graph dijkstraPQ(String start, String end) {
		Graph res = new Graph();
		PriorityQueue<StationVertex> pq = new PriorityQueue<StationVertex>();
		
		for (int i=0; i<vertices.size(); i++)
			pq.add(vertices.get(i));
		
		//pq.
		
		
		
		return res;
	}
}
