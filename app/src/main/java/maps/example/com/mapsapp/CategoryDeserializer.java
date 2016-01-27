package maps.example.com.mapsapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sneha on 26-Jan-16.
 */
public class CategoryDeserializer implements JsonDeserializer<List<Category>>
{
    public List<Category> deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException
    {
        JsonArray data = je.getAsJsonObject().getAsJsonArray("data");
        ArrayList<Category> myList = new ArrayList<Category>();

        for (JsonElement e : data)
        {
            myList.add((Category)jdc.deserialize(e, Category.class));
        }

        return myList;

    }
}
