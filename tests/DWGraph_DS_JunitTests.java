import api.*;

import static org.junit.jupiter.api.Assertions.*;
import java.io.Serializable;
import api.DWGraph_DS;
import api.node_data;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class DWGraph_DS_JunitTests {
    @Test
    public void test1createNode(){
        DWGraph_DS nodesGraph = new DWGraph_DS();
        DWGraph_DS.NodeData v1 = new DWGraph_DS.NodeData(6);
        DWGraph_DS.NodeData v2 = new DWGraph_DS.NodeData(4);
        DWGraph_DS.NodeData v3 = new DWGraph_DS.NodeData(2);
        DWGraph_DS.NodeData v4 = new DWGraph_DS.NodeData(8);
        nodesGraph.addNode(v1);
        nodesGraph.addNode(v2);
        nodesGraph.addNode(v3);
        nodesGraph.addNode(v4);
        if(nodesGraph.nodeSize() != 4){fail("should be 4");}
    }
    @Test
    public void test2Connect(){
        DWGraph_DS nodesGraph = new DWGraph_DS();
        nodesGraph.connect(2, 5, 20);
        nodesGraph.connect(2, 7, 40);
        assertEquals(0, nodesGraph.edgeSize());
        nodesGraph.addNode(new DWGraph_DS.NodeData(2));
        nodesGraph.addNode(new DWGraph_DS.NodeData(5));
        nodesGraph.addNode(new DWGraph_DS.NodeData(7));
        nodesGraph.addNode(new DWGraph_DS.NodeData(2));
        nodesGraph.addNode(new DWGraph_DS.NodeData(5));
        nodesGraph.connect(2,5,20);
        nodesGraph.connect(2,7,40);
        assertEquals(2,nodesGraph.edgeSize());
      //  assertTrue(nodesGraph.hasEdge(2,5));
    }
    @Test
    public void test3RemoveNodes(){
        DWGraph_DS nodesGraph = new DWGraph_DS();
        nodesGraph.addNode(new DWGraph_DS.NodeData(2));
        nodesGraph.addNode(new DWGraph_DS.NodeData(5));
        nodesGraph.addNode(new DWGraph_DS.NodeData(7));
        nodesGraph.addNode(new DWGraph_DS.NodeData(3));
        nodesGraph.addNode(new DWGraph_DS.NodeData(6));
        nodesGraph.addNode(new DWGraph_DS.NodeData(10));
        nodesGraph.connect(2,5,20);
        nodesGraph.connect(2,7,40);
        assertEquals(2,nodesGraph.edgeSize());
        nodesGraph.removeNode(2);
        assertEquals(0,nodesGraph.edgeSize());

    }
    @Test
    public void test4RemoveEdge(){
        DWGraph_DS nodesGraph = new DWGraph_DS();
        nodesGraph.addNode(new DWGraph_DS.NodeData(2));
        nodesGraph.addNode(new DWGraph_DS.NodeData(5));
        nodesGraph.addNode(new DWGraph_DS.NodeData(7));
        nodesGraph.addNode(new DWGraph_DS.NodeData(3));
        nodesGraph.addNode(new DWGraph_DS.NodeData(6));
        nodesGraph.addNode(new DWGraph_DS.NodeData(10));
        nodesGraph.connect(2,5,20);
        nodesGraph.connect(3,5,45);
        nodesGraph.connect(3,10,33);
        nodesGraph.removeEdge(3,5);
        assertEquals(2,nodesGraph.edgeSize());

    }
    @Test
    public void test5GetEdge(){
        DWGraph_DS g = new DWGraph_DS();
        g.addNode(new DWGraph_DS.NodeData(0));
        g.addNode(new DWGraph_DS.NodeData(1));
        g.addNode(new DWGraph_DS.NodeData(2));
        g.addNode(new DWGraph_DS.NodeData(3));
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
      //  g.connect(0,1,1);
        int e_size =  g.edgeSize();
        assertEquals(3, e_size);
        double w03 = g.getEdge(g.getNode(0).getKey(),g.getNode(3).getKey()).getWeight();
      //  double w30 = g.getEdge(3,0).getWeight();
        System.out.println(w03);
       // System.out.println(w30);
      //  assertNotEquals(w03, w30);
        assertEquals(w03, 3);
    }
}
