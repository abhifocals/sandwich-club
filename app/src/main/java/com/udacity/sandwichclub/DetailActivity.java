package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView origin;
    private TextView alsoKnownAs;
    private TextView description;
    private TextView ingredients;
    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        // Initializing detail views
        origin = (TextView) findViewById(R.id.origin_tv);
        alsoKnownAs = (TextView) findViewById(R.id.also_known_tv);
        description = (TextView) findViewById(R.id.description_tv);
        ingredients = (TextView) findViewById(R.id.ingredients_tv);

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        origin.setText(sandwich.getPlaceOfOrigin());
        alsoKnownAs.setText(getStringFromList(sandwich.getAlsoKnownAs()));
        description.setText(sandwich.getDescription());
        ingredients.setText(getStringFromList(sandwich.getIngredients()));
    }

    private String getStringFromList(List<String> list) {
        StringBuilder sb = new StringBuilder();

        for (String s : list) {
            sb.append(s);
            sb.append(", ");
        }

        // Remove the last trailing comma
        if (sb.length() > 2 && sb.charAt(sb.length() - 2) == ',') {
            sb.deleteCharAt(sb.length() - 2);
        }

        return sb.toString();
    }
}
