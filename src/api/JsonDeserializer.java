package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map.Entry;

public class JsonDeserializer implements com.google.gson.JsonDeserializer<DWGraph_DS> {
    @Override
    public DWGraph_DS deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        DWGraph_DS graph = new DWGraph_DS();

        JsonArray vertexObj = jsonObject.get("Nodes").getAsJsonArray();
        int j=0;
        while(j<vertexObj.size()) {

            JsonElement jsonValueElement = vertexObj.get(j);
            int id = jsonValueElement.getAsJsonObject().get("id").getAsInt();
            String s = jsonValueElement.getAsJsonObject().get("pos").getAsString();
            String [] arr= s.split(",");

            double x=Double.valueOf(arr[0]);
            double y=Double.valueOf(arr[1]);
            double z=Double.valueOf(arr[2]);

            geo_location ge= new GeoLocation(x,y,z);
            node_data n = new DWGraph_DS.NodeData(id,ge);
            graph.addNode(n);
            j++;
        }

        JsonArray edgesObj = jsonObject.get("Edges").getAsJsonArray();
        for (int i=0; i<edgesObj.size(); i++) {

            JsonElement jsonValueElement = edgesObj.get(i);
            int src = jsonValueElement.getAsJsonObject().get("src").getAsInt();
            int dest = jsonValueElement.getAsJsonObject().get("dest").getAsInt();
            double weight = jsonValueElement.getAsJsonObject().get("w").getAsDouble();
            graph.connect(src, dest, weight);
        }
        return graph;
    }
}
