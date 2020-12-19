package gameClient;

import Server.Game_Server_Ex2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import static java.lang.System.exit;

public class Ex2 implements Runnable {
    //private static int numGame;
    private static TzachFrame _win;
    private static Arena _ar;
    private static LinkedList<Integer> agentAndPokemons = new LinkedList<>();
    public List<CL_Pokemon> pokemon_list = new ArrayList<>(); // list of pokemons we have
    private static node_data temp;
    private static int _id = -1;
    private static int numGame = 0;
    private boolean flag = false;
    private static LoginFrame login;
    private static boolean CMDactive = false;
    // private static LinkedList<Integer> pokemon_LIST = new LinkedList<>();
    //private static Vector<Set> agentsAndPoke = new Vector<>();

    /**
     * main method to check if the user play from CMD
     * or using the GUI login.
     * then start the game.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Thread client = new Thread(new Ex2());
        //  client.start();
        Thread player1 = new Thread(new SimplePlayer());
        // player1.start();
        if (args.length == 0) {
            login = new LoginFrame();
            login.loginPanel();
            while (login.isActiive()) {
                try {
                    client.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            _id = login.getID();
            numGame = login.getStage();
            login.exit();
        } else {
            try {
                CMDactive = true;
                _id = Integer.parseInt(args[0]);
                numGame = Integer.parseInt(args[1]);
            }
            catch (Exception e){
                _id = -1;
                numGame =0;
            }
            client.start();
            player1.start();
        }
        if(!CMDactive) {
            if (login.getIndex() == 2) {
                exit(0);
            }
        }
        if(!CMDactive) {
            client.start();
            player1.start();
            //  exit(0);
        }
    }

    /**
     * override method used in the Thread EX2.
     */
    @Override
    public void run() {
        //  numGame = 0;
        game_service game = Game_Server_Ex2.getServer(numGame);
        if (thereIsID()) {
            game.login(_id);
        }
        String s = game.getGraph();
        directed_weighted_graph graph = loadGraph(s);
        init(game);
        pokemon_list = _ar.getPokemons();
        game.startGame();
        int ind = 0;
        long dt = 100;
        while (game.isRunning()) {
                moveAgents(game, graph);
                try {
                    if (ind % 1 == 0) {
                        _win.repaint();
                    }

                    _ar.setTime("Timer: " + (double) game.timeToEnd() / 1000);
                    Thread.sleep(dt);
                    ind++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (game.timeToEnd() < 25000) dt = 85;
                if(game.timeToEnd() < 17000) dt = 100;
        }
        String res = game.toString();

        System.out.println(res);
        exit(0);
    }

    /**
     * load graph from Json format,same as in DWGraph_Algo.
     * @param s
     * @return
     */
    private directed_weighted_graph loadGraph(String s) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DWGraph_DS.class, new JsonDeserializer());
        Gson gson = builder.create();
        try {
            directed_weighted_graph g = gson.fromJson(s, DWGraph_DS.class);
            return g;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * initiallize the game,and set Arena.
     * start GUI for the graph.
     * added pokemons and agents to the graph.
     * @param game
     */
    private void init(game_service game) {
        String pokemons = game.getPokemons();
        directed_weighted_graph graph = loadGraph(game.getGraph());
        _ar = new Arena();
        _ar.setGraph(graph);
        _ar.setPokemons(Arena.json2Pokemons(pokemons));
        _win = new TzachFrame("Pokemon game - Ex2 OOP");
        // ImageIcon image = new ImageIcon("data/Pokemon-Logo.png");
        //_win.setIconImage(image.getImage());
        _win.setSize(900, 600);
        _win.update(_ar);
        _win.show();
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            int index = 0;
            JSONObject ttt = line.getJSONObject("GameServer");
            int numAgents = ttt.getInt("agents");
            System.out.println(info);
            System.out.println(game.getPokemons());
            int src_node = 0;
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            for (int a = 0; a < cl_fs.size(); a++) { //insert pokemons
                Arena.updateEdge(cl_fs.get(a), graph);
            }
            Collections.sort(cl_fs); // sort the pokemons by their values, which we can eat the most valuest pokemon first.
            for (int a = 0; a < numAgents; a++) { // insert agents
                edge_data edge = cl_fs.get(index).get_edge();
                game.addAgent(edge.getSrc());
                agentAndPokemons.add(-1);
                index++;
                pokemon_list = _ar.getPokemons();
                updateE();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * helper method to update the Arena.
     */
    private void updateE() {
        for (CL_Pokemon temp : pokemon_list) {
            Arena.updateEdge(_ar.getPokemons().get(0), _ar.getGraph());
        }
    }

    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination we active Shortests path Algorithm.
     * @param game
     * @param graph
     * @param
     */
    private static void moveAgents(game_service game, directed_weighted_graph graph) {
        List<CL_Agent> agentList = Arena.getAgents(game.move(), graph);
        _ar.setAgents(agentList);
        _ar.setPokemons(Arena.json2Pokemons(game.getPokemons()));

        for (int i = 0; i < agentList.size(); i++) {
            CL_Agent ag = agentList.get(i);
            int id = ag.getID();
            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
            double v;
            if (dest == -1) { // the agent is not in the way meaning he just stand in some node
                for (int j = 0; j < agentList.size(); j++) {
                    ag = agentList.get(i);
                    id = ag.getID();
                    dest = ag.getNextNode();
                    src = ag.getSrcNode();
                    v = ag.getValue();
                    agentAndPokemons.set(id, -1); //if the agent stand, then set with -1.
                    List<node_data> pathToNextPokemon = pathToPok(graph, src, id);
                    if (pathToNextPokemon != null) {
                        for (node_data temp2 : pathToNextPokemon) {
                            temp = temp2;
                            game.chooseNextEdge(ag.getID(), temp2.getKey());
                        }
                    }
                    System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + temp.getKey());
                }
            }
        }
    }

    /**
     * helper method to link every agent to his closest pokemon.
     * @param g
     * @param src
     * @param id
     * @return
     */
    //link every agent with nearest  pokemon.
    public static List<node_data> pathToPok(directed_weighted_graph g, int src, int id) {
        dw_graph_algorithms algo = new DWGraph_Algo();
        algo.init(g);
        double minDirection = Double.MAX_VALUE;
        int index = -1;
        int dest = 0;
        int pos = 0;
        for (int i = 0; i < _ar.getPokemons().size(); i++) {
            Arena.updateEdge(_ar.getPokemons().get(i), g);
            dest = _ar.getPokemons().get(i).get_edge().getSrc();
            if (someoneOnTheWay(dest)) {
                double tempD = algo.shortestPathDist(src, dest);
                if (tempD <= minDirection) {
                    minDirection = tempD;
                    index = dest;
                    pos = i;
                }
            }
        }
        List<node_data> path = getPath(src, index, pos, algo, id, g);
        return path;
    }

    /**
     * helper methos to return the shortest path, used ShortestPath algorithm.
     * @param src
     * @param index
     * @param pos
     * @param algo
     * @param id
     * @param g
     * @return
     */
    public static List<node_data> getPath(int src, int index, int pos, dw_graph_algorithms algo, int id, directed_weighted_graph g) {
        List<node_data> shortPath;
        shortPath = algo.shortestPath(src, index);
        int direction = _ar.getPokemons().get(pos).get_edge().getDest();
        agentAndPokemons.set(id, index);
        shortPath.add(g.getNode(direction));
        return shortPath;
    }

    /**
     * helper method to check if another Agent is on the way to the same pokemon.
     * goal is to prevent that 2 Agents goes to the same pokemon.
     * @param dest
     * @return true if does, false if path is clear to go.
     */
    public static boolean someoneOnTheWay(int dest) {
        for (int i = 0; i < agentAndPokemons.size(); i++) {
            if (agentAndPokemons.get(i) == dest) {
                return false;
            }
        }
        return true;
    }

    /**
     * helper method to check if the user enter ID to save result.
     * @return
     */
    public boolean thereIsID() {
        if (_id != -1) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

}
