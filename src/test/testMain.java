
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
			System.out.println("���̸�  : " + currentVertex.stationName);
			System.out.println("�� ID : " + currentVertex.stationID);
			System.out.println("������ : " + currentVertex.routes.size());
			for(int j=0; j<currentVertex.routes.size(); j++) {
				currentEdge = currentVertex.routes.get(j);
				System.out.println("����(����ID : " + currentEdge.busID + ") - ���ۿ� : " + currentEdge.startStation.stationName + " , ������ : " + currentEdge.endStation.stationName);
			}
			System.out.println("\n");
		}
	}

}
