import java.util.Iterator;
import java.util.LinkedList;

public class Dijkstra extends Algorithm {

	public Dijkstra(GridMap map, Node start, Node goal) 
	{
		super(map, start, goal);
	}

	@Override
	protected void work() 		
	{
		for (int i=0; i<map.getCountColumn(); ++i) {
			for (int j=0; j<map.getCountRow(); ++j) {
				map.getNode(j, i).setDistance(Float.MAX_VALUE);
				map.getNode(j, i).setStep(0);
			}
		}
		
		LinkedList<Node> frontier = new LinkedList<>();
		frontier.add(start);
		start.setDistance(0.0f);
		
		while (!frontier.isEmpty() && failed == false) {
			Node node = getNodeMinDistance(frontier);
			frontier.remove(node);
			
			if (node.equals(goal)) {	
				finished = true;
				return;
			}
			
			node.setVisualType(Node.VisualType.PROCESSED);
			
			Iterator<Node> i = getNeighbors(node).iterator();
			
			while (i.hasNext()) {
				Node neighbour = i.next();
				
				if (frontier.contains(neighbour)) continue;
				
				double dist = node.getDistance() + neighbour.getCost();
				if (dist < neighbour.getDistance()) {
					neighbour.setDistance(dist);
					neighbour.setParent(node);
					frontier.add(neighbour);
					
					neighbour.setStep(node.getStep() + 1);
					neighbour.setVisualType(Node.VisualType.FRONTIER);
				}
			}
			this.notifyRefresh();
		}
		
		failed = true;
	}

	@Override
	protected void discoverPath() {
		this.path = (LinkedList<Node>) constructPath(goal);
	}
	
	public Node getNodeMinDistance(LinkedList<Node> set)
	{
		Node bestNode = null;
		double bestDistance = Double.MAX_VALUE;
		
		for (Node node : set) {
			if (node.getDistance() < bestDistance) {
				bestDistance = node.getDistance();
				bestNode = node;
			}
		}
		
		return bestNode;
	}
}