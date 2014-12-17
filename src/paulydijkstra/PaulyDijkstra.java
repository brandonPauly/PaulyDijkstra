package paulydijkstra;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

/**
 *
 * @author Brandon Pauly
 * 
 * This class is the main driver of the assignment.  It contains all of the build structure for the algorithm.  Supporting the algorithm
 * is the class MinHeap, which uses the Vertex class to store info about each vertex.  The input files have been provided in the project,
 * and each can be turned on/off in main() to switch them.  Output is to System.out.
 */
public class PaulyDijkstra {
    private static final int CHAR_OFFSET = 10;
    private static final int INFINITY = Integer.MAX_VALUE;
    private static final int NIL = -1;
    private static final int SOURCE = 0;
    private int[] distance;
    private int[] predecessor;
    private ArrayList<LinkedList<Edge>> adjV;
    private int numVertices;
    private boolean[] inHeap;
    private char[] charMap;
    
    public PaulyDijkstra(int vertices){
        distance = new int[vertices]; 
        predecessor = new int[vertices];
        adjV = new ArrayList<LinkedList<Edge>>();
        inHeap = new boolean[vertices];
        numVertices = vertices;
        charMap = new char[vertices];
        for (int i = 0; i < vertices; i++){
            adjV.add(new LinkedList());
        }
    }
    
    private class Edge{
        private final int head;
        private final int wgt;
        private Edge next;
        private Edge(int h, int w) { head = h; wgt = w; next = null; }
        private Edge(int h, int w, Edge ptr) { head = h; wgt = w; next = ptr; }
    }
    
    public void addEdge(int tail, int head, int wgt){
        adjV.get(tail).add(new Edge(head, wgt));
    }
    
    public void addToCharMap(int corrCharVal, char c){
        charMap[corrCharVal] = c;
    }
    
    public void getPath(){
        for (int v = 0; v < distance.length; v++){
            distance[v] = INFINITY;
            predecessor[v] = NIL;
        }
        distance[SOURCE] = 0;
        MinHeap minHeap = new MinHeap(numVertices);
        for (int v = 0; v < numVertices; v++){
            minHeap.insert(v, distance[v]);
            inHeap[v] = true;
        }
        while (!minHeap.isEmpty()){
            Vertex minVert = minHeap.extractMin();
            int u = minVert.getVertex();
            inHeap[u] = false;
            for (Edge w : adjV.get(u)){
                if (inHeap[w.head]){
                    if (distance[w.head] > distance[u] + w.wgt){
                        distance[w.head] = distance[u] + w.wgt;
                        predecessor[w.head] = u;
                        minHeap.update(w.head, distance[w.head]);
                    }
                }
            }
        }
        System.out.println(distance[1]);
        int parent = predecessor[1];
        Stack rev = new Stack();
        rev.push(charMap[1]);
        rev.push(charMap[parent]);
        while (parent != 0){
            parent = predecessor[parent];
            rev.push(charMap[parent]);
        }
        while (!rev.isEmpty()){
            System.out.print(rev.pop() + " ");
        }
        System.out.println();
    }
    
    /**
     * Test client.
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        //String graphFile = "data/Case1.txt";
        //String graphFile = "data/Case2.txt";
        String graphFile = "data/Case3.txt";
        BufferedReader bufR;
        String line;
        ArrayList<String> lines = new ArrayList();
        bufR = new BufferedReader(new FileReader(graphFile));
        while ((line = bufR.readLine()) != null) {
            lines.add(line);
        }
        int numVerts = Integer.parseInt(lines.get(0));
        PaulyDijkstra dijkstra = new PaulyDijkstra(numVerts);
        for (int i = 1; i < lines.size(); i++){
            String edge = lines.get(i);
            char tail = edge.charAt(0);
            char head = edge.charAt(2);
            int weight = Integer.parseInt(edge.substring(4));
            dijkstra.addEdge(Character.getNumericValue(tail) - CHAR_OFFSET, Character.getNumericValue(head) - CHAR_OFFSET, weight);
            dijkstra.addToCharMap(Character.getNumericValue(tail) - CHAR_OFFSET, tail);
            dijkstra.addToCharMap(Character.getNumericValue(head) - CHAR_OFFSET, head);
        }
        dijkstra.getPath();
    }
}
