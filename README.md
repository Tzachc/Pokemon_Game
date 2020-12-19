# Pokemon Game!

![alt text](https://github.com/Tzachc/Pokemon_Game/blob/main/data/logo.jpg)

This Object oriented programming task written by:
Tzach Cohen && vladi pukashensky.
# About this project:
This project we create a pokemon game, based on directional Weighted graph.

# Classes and functions descripsion:
in this project we have 4 classes:

**DWGraph_DS** :

implements the interface "directed_weighted_graph",and represent the graph of our game.
in this class we have severle of methods:

**getEdge** : method that get the data on the edge between src node and dest node.

**addNode** : method to add new Node to the graph.

**connect** : connect's 2 nodes and put weight on the edge between them.

**getV** : method to get collection of all nodes in the graph.

**getE**: method to get collection of edge's of specific node in the graph.

**removeNode** : method that remove node and all his edges from the graph.

**removeEdge** : method to remove edge.

in additional to all that, we have 2 inside classes **NodeData** and **EdgeData** which represent 
a simple node and edge in the graph.


**DWGraph_Algo** : 

implements the interface dw_graph_algorithms, and in this class we implements some algorithms that help us do "moves" in our game:

**copy** : preform a deep copy of the graph.

**isConnected** : check the graph Connectivity - "Strongly Connected" as the graph is directed.

**shortestPathDist** : method that find the sum of the cheapest weight of the shortestPath.

**shortestPath** : method that return the path itself (List).

**save** : save the graph into Json format.

**load** : load a graph from Json format.

**BFS** : algorithm to find if there is a path between one node to every node.

**Dijkstra** : helper method to finnd the shortest path on weighted graph.

**extractMin** : helper method used in Dijkstra algorithm,that extract min from the priority queue.


# About the game:

