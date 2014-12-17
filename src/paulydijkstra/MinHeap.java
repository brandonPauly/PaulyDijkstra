package paulydijkstra;



/**
 *
 * @author Brandon Pauly
 */
public class MinHeap {
    private Vertex[] heap;
    private int numVertices;
    private int[] updateMap;


    public MinHeap(int size) {
        heap = new Vertex[size + 1];
        numVertices = 0;
        updateMap = new int[size];
    }

    public void insert(int v, int dv) {
        Vertex vertex = new Vertex(v, dv);
        heap[++numVertices] = vertex;
        updateMap[v] = numVertices;
        percolateUp(numVertices);
    }

    public boolean isEmpty() {
        return numVertices == 0;
    }

    public Vertex extractMin() {
        swap(1, numVertices);
        Vertex toReturn = heap[numVertices--];
        percolateDown(1);
        return toReturn;
    }

    public void update(int head, int d) {
        int i = updateMap[head];
        heap[i].setDistance(d);
        percolateUp(i);
    }
    
    private void percolateUp(int p){
        while (p > 1 && (heap[p/2].getDistToSrc() > heap[p].getDistToSrc())) {
            swap(p, p/2);
            p = p/2;
        }
    }
    
    private void swap(int posA, int posB){
        final Vertex tempH = heap[posA];
        final int tempUM = updateMap[tempH.getVertex()];
        heap[posA] = heap[posB];
        updateMap[tempH.getVertex()] = updateMap[heap[posB].getVertex()];
        final int tempUM2 = heap[posB].getVertex();
        heap[posB] = tempH;
        updateMap[tempUM2] = tempUM;
    }
    
    private void percolateDown(int p){
        while (2*p <= numVertices) {
            int j = 2*p;
            if (j < numVertices && (heap[j].getDistToSrc() > heap[j+1].getDistToSrc())) j++;
            if (heap[p].getDistToSrc() <= heap[j].getDistToSrc()) break;
            swap(p, j);
            p = j;
        }
    }
}