package api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map.Entry;

public class JsonDeserializer implements com.google.gson.JsonDeserializer<DWGraph_DS> {
    @Override
    public DWGraph_DS deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject vertex = jsonObject.get("Nodes").getAsJsonObject();
        JsonObject edgesD = jsonObject.get("Edges").getAsJsonObject();
        int MC = jsonObject.get("MC").getAsInt();
        int edgeCounter = jsonObject.get("edgesCounter").getAsInt();
        HashMap<Integer,node_data>nodes = new HashMap<Integer, node_data>();
        HashMap<Integer, edge_data> edges = new HashMap<Integer, edge_data>();
        int dest;
        HashMap<Integer,HashMap<Integer,edge_data>> bigEdge = new HashMap<Integer, HashMap<Integer, edge_data>>();

        for(Entry<String,JsonElement> set : vertex.entrySet()) {
            JsonElement jsonValueElement = set.getValue();
            int key = jsonValueElement.getAsJsonObject().get("_key").getAsInt();
            String info = jsonValueElement.getAsJsonObject().get("_info").getAsString();
            int tag = jsonValueElement.getAsJsonObject().get("_tag").getAsInt();
            double weight = jsonValueElement.getAsJsonObject().get("_weight").getAsDouble();
            double x = jsonValueElement.getAsJsonObject().get("location").getAsJsonObject().get("x").getAsDouble();
            double y = jsonValueElement.getAsJsonObject().get("location").getAsJsonObject().get("y").getAsDouble();
            double z = jsonValueElement.getAsJsonObject().get("location").getAsJsonObject().get("z").getAsDouble();
            node_data nodeToAdd = new DWGraph_DS.NodeData(key, info, tag, weight, x, y, z);
            nodes.put(key, nodeToAdd);
        }

        for(Entry<String,JsonElement> set : edgesD.entrySet()){ // run on the sets.
            JsonElement jsonValueElement = set.getValue();
            String key = set.getKey();
            JsonObject edgeConnects = jsonValueElement.getAsJsonObject(); // get the Json second hash contain the values.
            for(Entry<String,JsonElement> set_1 : edgeConnects.entrySet()){
                JsonElement jsonValueElement_1 = set_1.getValue();
                int src = jsonValueElement_1.getAsJsonObject().get("_src").getAsInt();
                dest = jsonValueElement_1.getAsJsonObject().get("_dest").getAsInt();
                double weight = jsonValueElement_1.getAsJsonObject().get("_weight").getAsDouble();
                int tag = jsonValueElement_1.getAsJsonObject().get("_tag").getAsInt();
                edge_data edgeToAdd = new DWGraph_DS.EdgeData(src,dest,weight,tag);
                edges.put(dest,edgeToAdd);
            }
            bigEdge.put(Integer.parseInt(key),edges);
            edges = new HashMap<Integer, edge_data>();
        }

        DWGraph_DS g = new DWGraph_DS(nodes,bigEdge,MC,edgeCounter);
        return g;
    }
}
