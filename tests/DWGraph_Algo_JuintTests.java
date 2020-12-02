package tests;

import static org.junit.jupiter.api.Assertions.*;
import api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import api.*;
import api.directed_weighted_graph;
import api.DWGraph_DS;
import api.node_data;
import api.DWGraph_Algo;
import api.dw_graph_algorithms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DWGraph_Algo_JuintTests {
    private static dw_graph_algorithms g;
    private static directed_weighted_graph g1;

    @BeforeEach
    void generateGraph() {
        g = new DWGraph_Algo();
        //new graph g1
        g1 = new DWGraph_DS();
        for (int i = 1; i < 5; i++) {
            node_data n = new DWGraph_DS.NodeData(i);
            g1.addNode(n);
        }

        g1.connect(1, 2, 5.6);
        g1.connect(2, 1, 6.3);
        g1.connect(1, 3, 1.2);
        g1.connect(3, 1, 0.4);
        g1.connect(3, 4, 10);
        g1.connect(4, 3, 2.4);
        g1.connect(2, 4, 2.7);
        g1.connect(4, 2, 1);
    }

    @Test
    public void test1DeepCopy() {
        DWGraph_DS nodesGraph = new DWGraph_DS();
        nodesGraph.connect(2, 5, 20);
        nodesGraph.connect(2, 7, 40);
        assertEquals(0, nodesGraph.edgeSize());
        nodesGraph.addNode(new DWGraph_DS.NodeData(2));
        nodesGraph.addNode(new DWGraph_DS.NodeData(5));
        nodesGraph.addNode(new DWGraph_DS.NodeData(7));
        nodesGraph.addNode(new DWGraph_DS.NodeData(2));
        nodesGraph.addNode(new DWGraph_DS.NodeData(5));
        nodesGraph.connect(2, 5, 20);
        nodesGraph.connect(2, 7, 40);
        DWGraph_Algo cops = new DWGraph_Algo();
        cops.init(nodesGraph);
        DWGraph_DS newCopy = (DWGraph_DS) cops.copy();
        assertEquals(nodesGraph.nodeSize(), newCopy.nodeSize());
        nodesGraph.removeNode(2);
        assertNotEquals(nodesGraph.nodeSize(), newCopy.nodeSize());
    }

    @Test
    public void test2Connected() {
        DWGraph_DS nodesGraph = new DWGraph_DS();
        nodesGraph.connect(2, 5, 20);
        nodesGraph.connect(2, 7, 40);
        assertEquals(0, nodesGraph.edgeSize());
        nodesGraph.addNode(new DWGraph_DS.NodeData(2));
        nodesGraph.addNode(new DWGraph_DS.NodeData(5));
        nodesGraph.addNode(new DWGraph_DS.NodeData(7));
        nodesGraph.connect(2, 7, 1431);
        nodesGraph.connect(5, 7, 32);
        nodesGraph.connect(2, 5, 20);
        DWGraph_Algo emptyGraph = new DWGraph_Algo();
        emptyGraph.init(nodesGraph);
        assertFalse(emptyGraph.isConnected());
        nodesGraph.connect(5, 2, 12);
        assertFalse(emptyGraph.isConnected());
        // boolean flag = emptyGraph.isConnected();
        //System.out.println(flag);
        nodesGraph.connect(7, 2, 1);
        assertTrue(emptyGraph.isConnected());
        //  boolean flag = emptyGraph.isConnected();
        // System.out.println(flag);
    }

    @Test
    public void test3ShortestPathDist() {
        DWGraph_DS nodesGraph = new DWGraph_DS();

        nodesGraph.addNode(new DWGraph_DS.NodeData(2));
        nodesGraph.addNode(new DWGraph_DS.NodeData(5));
        // nodesGraph.addNode(new DWGraph_DS.NodeData(7));
        nodesGraph.addNode(new DWGraph_DS.NodeData(7));
        nodesGraph.addNode(new DWGraph_DS.NodeData(3));
        nodesGraph.connect(2, 5, 12);
        nodesGraph.connect(2, 5, 9);
        nodesGraph.connect(2, 3, 1);
        nodesGraph.connect(3, 7, 4);
        nodesGraph.connect(5, 7, 2);
        // nodesGraph.connect(2, 7, 40);
        DWGraph_Algo cops = new DWGraph_Algo();
        cops.init(nodesGraph);
        double x = cops.shortestPathDist(2, 7);
        assertEquals(5, cops.shortestPathDist(2, 7));
        System.out.println(x);
        List<node_data> list = cops.shortestPath(2, 7);
        for (int i = 0; i < list.size(); i++) {
            System.out.printf(list.get(i).getKey() + " --> ");
        }
    }

    @Test
    public void test4Save() {

        DWGraph_DS nodesGraph = new DWGraph_DS();
        nodesGraph.addNode(new DWGraph_DS.NodeData(2));
        nodesGraph.addNode(new DWGraph_DS.NodeData(5));
        nodesGraph.connect(2, 5, 12);
        DWGraph_Algo cops = new DWGraph_Algo();
        cops.init(nodesGraph);
        cops.save("graph.json");
        DWGraph_Algo graph_other = new DWGraph_Algo();
        //graph_other.load("A0");
       // DWGraph_DS newnew = new DWGraph_DS();
       // DWGraph_Algo cops2 = new DWGraph_Algo();

        graph_other.load("A0");
        //graph_other.printGraph();
    }

}
