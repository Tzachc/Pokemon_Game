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

    public DWGraph_DS() {
        Nodes = new HashMap<Integer, node_data>();
        Edges = new HashMap<Integer, HashMap<Integer, edge_data>>();
        edgesCounter = 0;
        MC = 0;
    }

public DWGraph_DS(HashMap<Integer,node_data>nodes,HashMap<Integer,HashMap<Integer,edge_data>> edgesD,int MC,int edgeCounter){
        this.Nodes = nodes;
        this.Edges = edgesD;
        this.MC = MC;
        this.edgesCounter = edgeCounter;
}
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

    @Override
    public edge_data getEdge(int src, int dest) {
        if (getNode(src) != null && getNode(dest) != null) {
            if (Edges.containsKey(src) && Edges.get(src).containsKey(getNode(dest).getKey())) {
                return Edges.get(src).get(getNode(dest).getKey());
            }
        }
        return null;
    }

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

    @Override
    public Collection<node_data> getV() {
        return this.Nodes.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        return this.Edges.get(node_id).values();
    }

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
            this.location = new Geo_Location();
        }

        public NodeData(node_data node) {
            this.id = node.getKey();
            this._info = node.getInfo();
            this._tag = node.getTag();
            this.location = node.getLocation();
        }
        public NodeData(int key,String info,int tag,double weight,double x,double y,double z){
            this.id = key;
            this._info = info;
            this._tag = tag;
            this._weight = weight;
            this.location = new Geo_Location(x,y,z);
        }
        public NodeData(int key, double w) {

            this.id = key;
            this._weight = w;
            this._info = "";
            this._tag = 0;
            this.pre = null;
            this.location = new Geo_Location();
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
            this.location = new Geo_Location(p);

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

        public class Geo_Location implements geo_location {
            private double x, y, z;

            public Geo_Location() {
                this.x=0.0;
                this.y=0.0;
                this.z=0.0;
            }

            public Geo_Location(double x, double y, double z) {
                this.x = x;
                this.y = y;
                this.z = z;

            }

            public Geo_Location(geo_location p) {
                this.x = p.x();
                this.y = p.y();
                this.z = p.z();
            }

            @Override
            public double x() {
                return x;
            }

            @Override
            public double y() {
                return y;
            }

            @Override
            public double z() {
                return z;
            }

            @Override
            public double distance(geo_location g) {
                double xd = Math.pow(x - g.x(), 2);
                double yd = Math.pow(y - g.y(), 2);
                double zd = Math.pow(z - g.z(), 2);
                return Math.sqrt(xd + yd + zd);
            }
        }
    }

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
    }
}
