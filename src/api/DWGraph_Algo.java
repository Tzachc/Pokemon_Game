package api;
import java.io.Serializable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class DWGraph_Algo implements dw_graph_algorithms,Serializable{
    directed_weighted_graph graph;
    public DWGraph_Algo(){
        this.graph = new DWGraph_DS();
    }
    @Override
    public void init(directed_weighted_graph g) {
    this.graph = g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return this.graph;
    }

    @Override
    public directed_weighted_graph copy() {
        DWGraph_DS newGraph = new DWGraph_DS(graph);
        return newGraph;
    }

    @Override
    public boolean isConnected() {
        if (graph.nodeSize() == 0 || graph.nodeSize() == 1)
            return true;
        Iterator<node_data> it = graph.getV().iterator();
        while(it.hasNext()) {
            node_data curr = it.next();
            BFS(curr.getKey());
            for (node_data node : this.graph.getV()) {
                if (this.graph.getNode(node.getKey()).getTag() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        double distance = Dijkstra(this.graph.getNode(src), this.graph.getNode(dest));
        InitDijkstra();
        if (distance == Integer.MAX_VALUE) {
            return -1;
        }
        return distance;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        List<node_data> ans = new ArrayList<node_data>();
        Dijkstra(graph.getNode(src), graph.getNode(dest));

        if (src == dest) {
            ans.add(graph.getNode(src));
            return ans;
        }

        int key = dest;
        double result = 0;
        node_data tem = graph.getNode(key);
        DWGraph_DS.NodeData temp = (DWGraph_DS.NodeData) tem;
        while (key != src && key != -1 && result != -1 && temp != null) {
            ans.add(temp);
            //result = graph.getNode(key).getTag();
            result = temp.getTag();
            temp = (DWGraph_DS.NodeData) temp.getPre();
        }
        if (result == -1) {
            return null;
        }
        // ans.add(graph.getNode(src));
        Collections.reverse(ans);
        return ans;
    }

    @Override
    public boolean save(String file) {
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(graph);
        System.out.printf(json);
        try
        {
            PrintWriter pw = new PrintWriter(new File(file));
            pw.write(json);
            pw.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean load(String file) {
        //Gson gson = new Gson();
        try
        {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(DWGraph_DS.class,new JsonDeserializer());
            Gson gson = builder.create();

            FileReader reader = new FileReader(file);
            DWGraph_DS g = gson.fromJson(reader,DWGraph_DS.class);
            this.graph=g;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;

        }
        return true;
    }
    /**
     * BFS algorithm to find if there is a path between
     * given node to every node
     * created to be used in the graph connectivity method.
     * @param s
     */
    void BFS(int s) {
        // init(graph);
        //Initialize();
        InitBFS();
        LinkedList<Integer> queue = new LinkedList<>();
        node_data src = this.graph.getNode(s);
        src.setTag(1);
        // visited[s] = true;
        queue.add(s);
        while (!queue.isEmpty()) {
            s = queue.poll();
            node_data nodeS = this.graph.getNode(s);
            // System.out.printf(s + " ");
            // Iterator<node_data> it = this.graph.getNode(s).getNi().iterator();
            Iterator<edge_data> it = this.graph.getE(s).iterator();
            while (it.hasNext()) {
                edge_data n = it.next();
                if (n.getTag() == 0) {
                    //visited[n.getKey()] = true;
                    n.setTag(1);
                    graph.getNode(n.getSrc()).setTag(1);
                    queue.add(n.getDest());
                }
            }
           // nodeS.setTag(3);
        }
    }

    /**
     * init the BFS, tags = 0
     *  0 = white, 1 = gray, 3 = black.
     */
    private void InitBFS() {
        Iterator<node_data> it = this.graph.getV().iterator();
        for (edge_data node : this.graph.getE(it.next().getKey())) {
            node.setTag(0);
        }
    }

    /**
     * this method implements Dijkstra algorithm in order
     * to find the shortest path on weighted graph.
     * the algorithm use Priority queue.
     * @param src
     * @param dest
     * @return
     */
    private double Dijkstra(node_data src, node_data dest) {
        InitDijkstra();
        PriorityQueue<node_data> pq = new PriorityQueue<>();
        src.setInfo("gray");
        src.setTag(0);
        pq.add(src);
        boolean flag;
        double shortDist = Integer.MAX_VALUE;
        if(src == dest){
            return src.getTag();
        }
        while (!pq.isEmpty()) {
            DWGraph_DS.NodeData temp = (DWGraph_DS.NodeData) pq.poll();
            Iterator<edge_data> it = graph.getE(temp.getKey()).iterator();
            for(edge_data edge : graph.getE(temp.getKey())) {
                node_data destNode = graph.getNode(edge.getDest());
                if (destNode.getInfo() == "white" && destNode !=null) {
                    DWGraph_DS.NodeData destNodeNode = (DWGraph_DS.NodeData) destNode;
                    DWGraph_DS.EdgeData temp2 = (DWGraph_DS.EdgeData) edge;
                    if (destNode.getTag() > temp.getTag() + edge.getWeight()) {
                        destNode.setTag(extractMin(temp.getKey(),destNode.getKey()));
                        //temp2.setPre(temp);
                       // destNode.setPre(temp);
                      //  temp2.setPre(temp);
                      //  ((DWGraph_DS.EdgeData) edge).setPre(temp);
                        destNodeNode.setPre(temp);

                    }
                    pq.add(this.graph.getNode(destNode.getKey()));
                }
            }
            temp.setInfo("gray");
            flag = finish(temp.getKey(),dest.getKey());
            if (flag) {
                return graph.getNode(dest.getKey()).getTag();
            }
        }
        return shortDist;
    }

    public boolean finish(int node1,int node2){
        if(node1 == node2){
            return true;
        }
        return false;
    }

    /**
     * this method is a helper method for the Dijkstra algorithm,
     * to find the min in the priority queue and extract it.
     * @param n
     * @param temp
     * @return
     */
    private int extractMin(int n,int temp){
        node_data nodeTemp = this.graph.getNode(temp);
        node_data nodeN = this.graph.getNode(n);
        double tempTag = (nodeN.getTag() + this.graph.getEdge(nodeN.getKey(),nodeTemp.getKey()).getWeight());
        double min = Math.min(nodeTemp.getTag(),tempTag);
        return (int)min;
    }

    /**
     * before every Dijkstra run, we initialize the graph
     * with the given data:
     * tag = Integer.Max_Value
     * info = white (white , gray , black)
     * pre = null (used to save the path itself for the ShortestPath method).
     */
    private void InitDijkstra() {
        DWGraph_DS.NodeData preNode;
        Iterator<node_data> it = this.graph.getV().iterator();
        while(it.hasNext()){
            node_data node = it.next();
            preNode = (DWGraph_DS.NodeData)node;
            node.setTag(Integer.MAX_VALUE);
            node.setInfo("white");
            preNode.setPre(null);
        }
        Iterator<node_data> itr = this.graph.getV().iterator();
        DWGraph_DS.EdgeData preEdge;
        for (edge_data edge : this.graph.getE(itr.next().getKey())) {
            preEdge = (DWGraph_DS.EdgeData)edge;
            edge.setTag(Integer.MAX_VALUE);
            edge.setInfo("white");
            preEdge.setPre(null);
        }
    }
    public void printGraph() {
        int src_vertex = 0;
        int i = 0;
        int list_size = graph.nodeSize();
        ArrayList<node_data> list = new ArrayList<>();
        Iterator<node_data> it = graph.getV().iterator();
        while (it.hasNext()) {
            node_data s = it.next();
            list.add(s);
        }
        System.out.println("The contents of the graph:");
        while (i < list_size) {
            src_vertex = list.get(i).getKey();
            DWGraph_DS.NodeData srcVertex2 = (DWGraph_DS.NodeData) list.get(i);
            if (graph.getE(srcVertex2.getKey()).size() == 0) {
                System.out.println("Vertex:" + src_vertex + " ==> " + "\t");
            } else {
                //traverse through the adjacency list and print the edges
                for (edge_data edge : graph.getE(srcVertex2.getKey())) {
                    if (graph.getE(srcVertex2.getKey()).size() != 0) {
                        System.out.print("Vertex:" + src_vertex + " ==> " + edge.getSrc() + "\t");
                    }
                }
            }
            System.out.println();
            i++;
        }
    }
}

