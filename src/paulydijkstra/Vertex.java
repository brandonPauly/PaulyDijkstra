package paulydijkstra;

/**
 *
 * @author Brandon Pauly
 */
public class Vertex {
    private final int vID;
    private int distToSrc;
    public Vertex(int vID, int distToSrc) { this.vID = vID; this.distToSrc = distToSrc; }

    public int getVertex() {
        return vID;
    }
    
    public int getDistToSrc() {
        return distToSrc;
    }
    
    public void setDistance(int newDist){
        distToSrc = newDist;
    }
}
