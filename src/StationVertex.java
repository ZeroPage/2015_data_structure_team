import java.util.ArrayList;

public class StationVertex implements Comparable<StationVertex> {
	public String stationName;
	public String stationID;
	public String stationNumber;
	public ArrayList<RouteEdge> routes;
	public float gpsX;
	public float gpsY;
	
	private int dijkstra_dist;
	private int ignore_count;
	private boolean ignore_flag;
	
	public StationVertex() {
		routes = new ArrayList<RouteEdge>();
		dijkstra_dist = Integer.MAX_VALUE;
		ignore_count = 0;
		ignore_flag = false;
	}
	
	@Override
	public int compareTo(StationVertex rhs) {
		return dijkstra_dist <= rhs.dijkstra_dist ? -1 : 1;
	}
	
	public int getPriority() {
		return dijkstra_dist;
	}
	
	public void setPriority(int p) {
		dijkstra_dist = p;
	}
	
	public void decreaseKey(int p) {
		dijkstra_dist = p;
		ignore_count++;
	}
	
	public void setIgnore() {
		ignore_flag = true;
	}
	
	public boolean getIgnore() {
		return ignore_flag;
	}
	
	public boolean shouldIgnore() {
		return ignore_count>0;
	}
	
	public void skip() {
		if (ignore_flag) {
			ignore_count--;
			if (ignore_count<=0)
				ignore_flag = false;
		}
	}
	
	public void cleanDijkstra() {
		dijkstra_dist = Integer.MAX_VALUE;
		ignore_count = 0;
		ignore_flag = false;
	}
}
