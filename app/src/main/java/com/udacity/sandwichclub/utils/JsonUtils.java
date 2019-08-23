package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        String mainName = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;

        JSONArray alsoKnownAs = null;
        JSONArray ingredients = null;

        List<String> alsoKnownAsList = new ArrayList<>();
        List<String> ingredientsList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);

            if (jsonObject != null) {
                mainName = jsonObject.getJSONObject("name").getString("mainName");
                alsoKnownAs = jsonObject.getJSONObject("name").getJSONArray("alsoKnownAs");
                placeOfOrigin = jsonObject.getString("placeOfOrigin");
                description = jsonObject.getString("description");
                image = jsonObject.getString("image");
                ingredients = jsonObject.getJSONArray("ingredients");

                alsoKnownAsList = jsonArrayToList(alsoKnownAs, alsoKnownAsList);
                ingredientsList = jsonArrayToList(ingredients, ingredientsList);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
    }

    private static List<String> jsonArrayToList(JSONArray jsonArray, List<String> output) throws JSONException {

        for (int i = 0; i < jsonArray.length(); i++) {
            output.add(jsonArray.getString(i));
        }

        return output;
    }
}
