package com.example.appr;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class RecipeAdapter extends BaseAdapter {
    private Context context;
    private List<recipe> recipeList;
    private int userId;

    public RecipeAdapter(Context context, List<recipe> recipeList, int userId) {
        this.context = context;
        this.recipeList = recipeList;
        this.userId = userId;
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_recipe, parent, false);
        }

        DBConnect db = new DBConnect(context);

        TextView recipeName = convertView.findViewById(R.id.recipeName);
        TextView recipeCategory = convertView.findViewById(R.id.recipeCategory);

        recipe rec = recipeList.get(position);
        recipeName.setText(rec.getName());
        recipeCategory.setText(rec.getIngredients());

        convertView.setOnClickListener(v -> {

            Intent intent = new Intent(context, RecipeDetailsActivity.class);
            intent.putExtra("recipe", rec);
            intent.putExtra("currentUserId", userId);
            context.startActivity(intent);
        });

        return convertView;
    }

    public void updateList(List<recipe> newList) {
        recipeList.clear();
        recipeList.addAll(newList);
        notifyDataSetChanged();
    }
}
