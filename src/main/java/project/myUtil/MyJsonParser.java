package project.myUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import project.entity.OrderedItem;

public class MyJsonParser {
/**
 * parse complex string to json object, 
 * convert json object to simple string, 
 * store simple string into a map, 
 * map will be used by Service layer 
 */
    public static Map<String, Object> parseOrderStr(String orderStr) {

	Map<String, Object> map = new HashMap<>();

	JsonObject obj = new JsonParser().parse(orderStr).getAsJsonObject();

	map.put("customerId", obj.get("customer").getAsJsonObject().get("id").getAsLong());

	JsonObject pa = obj.get("postAgent").getAsJsonObject();

	map.put("postAgentId", pa.get("id").getAsLong());

	map.put("trackNum", pa.get("trackNum").getAsString());

	map.put("shipping_fee", pa.get("shipping_fee").getAsString());

	List<OrderedItem> itemList = new ArrayList<>();
	JsonArray items = obj.get("items").getAsJsonArray();
	JsonObject current;
	for (JsonElement e : items) {
	    current = e.getAsJsonObject();
	    itemList.add(new OrderedItem(current.get("id").getAsLong(), current.get("quantity").getAsInt(),
		    current.get("price").getAsString(), current.get("price_sold").getAsString()));
	}

	map.put("itemList", itemList);

	return map;
    }

}
