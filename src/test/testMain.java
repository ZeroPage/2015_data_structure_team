
public class testMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph myGraph = new Graph();
		myGraph.MakeWholeMap();
		
		int i;
		
		StationVertex currentVertex = null;
		RouteEdge currentEdge = null;
		for(i=0; i<myGraph.vertices.size(); i++) {
			currentVertex = myGraph.vertices.get(i);
			System.out.println("역이름  : " + currentVertex.stationName);
			System.out.println("역 ID : " + currentVertex.stationID);
			System.out.println("간선수 : " + currentVertex.routes.size());
			for(int j=0; j<currentVertex.routes.size(); j++) {
				currentEdge = currentVertex.routes.get(j);
				System.out.println("간선(버스ID : " + currentEdge.busID + ") - 시작역 : " + currentEdge.startStation.stationName + " , 도착역 : " + currentEdge.endStation.stationName);
			}
			System.out.println("\n");
		}
	}

}
