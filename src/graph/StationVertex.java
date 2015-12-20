import java.util.ArrayList;

public class StationVertex {
	public String stationName;
	public String stationID;
	public String stationNumber;
	public ArrayList<RouteEdge> routes;
	public float gpsX;
	public float gpsY;
	
	public StationVertex() {
		routes = new ArrayList<RouteEdge>();
	}
}
