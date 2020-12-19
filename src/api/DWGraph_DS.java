package api;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class DWGraph_DS implements directed_weighted_graph, Serializable {
    private HashMap<Integer, node_data> Nodes;
    private HashMap<Integer,HashMap<Integer, edge_data>> Edges;
    private int edgesCounter;
    private int MC = 0;

    /**
     * defult constructor
     */
    public DWGraph_DS() {
        Nodes = new HashMap<Integer, node_data>();
        Edges = new HashMap<Integer, HashMap<Integer, edge_data>>();
        edgesCounter = 0;
        MC = 0;
    }

    /**
     * constructor used for the deep copy function.
     * @param other
     */
    public DWGraph_DS(directed_weighted_graph other){
        this();
        Iterator<node_data> it = other.getV().iterator();
        while(it.hasNext()){
            node_data node = it.next();
            addNode(node);
        }
        for(node_data node1 : other.getV()){
            for(edge_data edge : other.getE(node1.getKey())){
                double weight = edge.getWeight();
                this.connect(node1.getKey(),edge.getDest(),weight);

            }
        }
        this.MC = other.getMC();
        //this.edgesCounter = other.edgeSize();
    }
    @Override
    public node_data getNode(int key) {
        return Nodes.get(key);
    }

    /**
     * function to get the data on the edge between src and dest.
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        if (getNode(src) != null && getNode(dest) != null) {
            if (Edges.containsKey(src) && Edges.get(src).containsKey(getNode(dest).getKey())) {
                return Edges.get(src).get(getNode(dest).getKey());
            }
        }
        return null;
    }

    /**
     * add new node to the graph.
     * @param n - node data to add.
     */
    @Override
    public void addNode(node_data n) {
        if (Nodes.containsKey(n)) { // already exist
            return;
        } else {
            Nodes.put(n.getKey(), new NodeData(n));
            Edges.put(n.getKey(), new HashMap<Integer, edge_data>());
            this.MC++;
        }
    }

    /**
     * connect 2 nodes, and put weight on edge.
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        NodeData nodeOne = (NodeData) Nodes.get(src);
        if(w < 0){
            System.out.println("Weight should be positive!");
            return;
        }
        if (src != dest && Edges.containsKey(src) && Edges.containsKey(dest)) {
            this.Edges.get(src).put(dest,new EdgeData(src,dest,w));
            //this.edgesD.get(dest).put(src,new EdgeData(dest,src,w));
            edgesCounter++;
            MC++;
        }
    }

    /**
     * get Collection of all the nodes in the graph.
     * @return
     */
    @Override
    public Collection<node_data> getV() {
        return this.Nodes.values();
    }

    /**
     * get Collection of all the Edges from specific node.
     * @param node_id
     * @return
     */
    @Override
    public Collection<edge_data> getE(int node_id) {
        return this.Edges.get(node_id).values();
    }

    public Collection<HashMap<Integer,edge_data>> getE() {
        return Edges.values();
    }

    /**
     * remove node from the graph, and also the edge's he connected with.
     * @param key
     * @return
     */
    @Override
    public node_data removeNode(int key) {
        boolean flag = Nodes.containsKey(key);
        if(flag){
            NodeData nodeToRemove =(NodeData) Nodes.get(key);
            edgesCounter-= Edges.get(key).values().size();
            MC+= Edges.get(key).values().size()+1;
            Iterator<edge_data> it = Edges.get(key).values().iterator();
            while(it.hasNext()){
                edge_data node = it.next();
                Edges.get(node.getDest()).remove(key);
            }
            Edges.get(key).values().clear();
            Edges.remove(key);
        }
        return Nodes.remove(key);
    }

    /**
     * remove edge between src and dest.
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        if(src == dest){
            System.out.println("There's no edge with node and itself");
            return null;
        }
        if(Edges.get(src).containsKey(dest)){
            Edges.get(dest).remove(src);
            MC++;
            edgesCounter--;
            return Edges.get(src).remove(dest);
        }
        return null;
    }

    @Override
    public int nodeSize() {
        return this.Nodes.size();
    }

    @Override
    public int edgeSize() {
        return this.edgesCounter;
    }

    @Override
    public int getMC() {
        return this.MC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWGraph_DS that = (DWGraph_DS) o;
        if (MC != that.MC) return false;
        if (edgesCounter != that.edgesCounter) return false;
        if (!Nodes.equals(that.Nodes)) return false;
        return Edges.equals(that.Edges);
    }

    /**
     * class that represent Node in the graph.
     */
    public static class NodeData implements node_data,Comparable<NodeData>,Serializable {
        private int id;
        private String _info;
        private int _tag;
        private node_data pre;
        private double _weight;
        private geo_location location;

        public NodeData(int key) {
            this._info = "";
            this._tag = 0;
            this.id = key;
            this.pre = null;
            this.location = new GeoLocation();
        }

        public NodeData(node_data node) {
            this.id = node.getKey();
            this._info = node.getInfo();
            this._tag = node.getTag();
            this.location = node.getLocation();
        }

        public NodeData(int key, geo_location ge) {

            this.id = key;
            this._weight = -1;
            this.location = ge;
            this._info = "";
            this._tag = 0;
        }
        @Override
        public int getKey() {
            return this.id;
        }

        @Override
        public geo_location getLocation() {
            return this.location;
        }

        @Override
        public void setLocation(geo_location p) {
            this.location = new GeoLocation(p);

        }

        @Override
        public double getWeight() {
            return this._weight;
        }

        @Override
        public void setWeight(double w) {
            this._weight = w;
        }

        @Override
        public String getInfo() {
            return this._info;
        }

        @Override
        public void setInfo(String s) {
            this._info = s;
        }

        @Override
        public int getTag() {
            return this._tag;
        }

        @Override
        public void setTag(int t) {
            this._tag = t;
        }

        @Override
        public int compareTo(NodeData o) {
            return Double.compare(this.getTag(), o.getTag());
        }

        public node_data getPre() {
            return this.pre;
        }


        /**
         * used to set the previous node of the current one
         * will be used to find the shortest path.
         *
         * @param n node.
         */
        public void setPre(node_data n) {
            this.pre = n;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NodeData nodeData = (NodeData) o;

            return id == nodeData.id;
        }
    }

    /**
     * Class that represent an edge in the graph.
     * every edge have a weight on it.
     */
    public static class EdgeData implements edge_data,Serializable {
        int _src;
        int _dest;
        double _weight;
        String _info;
        int _tag;
        private node_data pre;

        public EdgeData() {
            this._src = 0;
            this._dest = 0;
            this._weight = 0;
            this._info = "";
            this._tag = 0;
            this.pre = null;
        }

        public EdgeData(int srcVertex, int destVerex, double weight) {
            this._src = srcVertex;
            this._dest = destVerex;
            this._weight = weight;
            this._tag = 0;
            this.pre = null;

        }

        public EdgeData(int srcVertex, int destVerex, double weight, int tag) {
            this._src = srcVertex;
            this._dest = destVerex;
            this._weight = weight;
            this._tag = tag;
            this.pre = null;
        }

        public EdgeData(EdgeData other) {
            this._src = other._src;
            this._dest = other._dest;
            this._weight = other._weight;
            this._info = other._info;
            this._tag = other._tag;
            this.pre = other.pre;
        }
        public EdgeData(HashMap<Integer,edge_data> edges){
        }
        @Override
        public int getSrc() {
            return this._src;
        }

        @Override
        public int getDest() {
            return this._dest;
        }

        @Override
        public double getWeight() {
            return this._weight;
        }

        @Override
        public String getInfo() {
            return this._info;
        }

        @Override
        public void setInfo(String s) {
            this._info = s;
        }

        @Override
        public int getTag() {
            return this._tag;
        }

        @Override
        public void setTag(int t) {
            this._tag = t;
        }
        public node_data getPre() {
            return this.pre;
        }

        /**
         * used to set the previous node of the current one
         * will be used to find the shortest path.
         *
         * @param n node.
         */
        public void setPre(node_data n) {
            this.pre = n;
        }
        public boolean equals(Object o) {
            if (!(o instanceof edge_data)) return false;
            if (_weight != (((edge_data) o).getWeight())) return false;
            if (this._src != ((edge_data) o).getSrc()) return false;
            if (this._dest != ((edge_data) o).getDest()) return false;
            return true;
        }
    }
}
