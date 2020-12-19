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

![alt text](https://github.com/Tzachc/Pokemon_Game/blob/main/data/game.png)

Pokemon game is a simple game, that have 24 stages for now, the idea of the game is that at every stage their different number of agents.
the goal is to catch the most of the pokemons on the graph in the limited time.
check out our best results at Wiki page.

**How to play??**

download the "Ex2.jar" file **and** the Data file and put them in the same folder.

 1) Run the Ex2.jar file, a login menu will be opened.
 you have 2 options:
 you can enter your ID and stage number and click "Login" - the game will run and your grade will be saved.
 othewise you can click on the "No ID game" and just enter stage .
 
 2) enter the folder space in the CMD and run the command: 
 
     java -jar Ex2.jar <**YourID**> <**stage**>
     
  by doing that, you will skip the login menu part.
  
  


**Enjoy!**
