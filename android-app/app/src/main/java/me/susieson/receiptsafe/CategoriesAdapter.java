package me.susieson.receiptsafe;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private Context mContext;
    private HashMap<String, Double> categoryHash;
    private List<Category> mCategories;

    public CategoriesAdapter(Context context, List<Receipt> receipts) {
        mContext = context;
        categoryHash = new LinkedHashMap<>();
        for (Receipt receipt : receipts) {
            String category = receipt.getCategory();
            if (categoryHash.containsKey(category)) {
                Double amount = categoryHash.get(category);
                categoryHash.put(category, receipt.getTotalAmount() + amount);
            } else {
                categoryHash.put(category, receipt.getTotalAmount());
            }
        }
        List<String> keys = new ArrayList<>(categoryHash.keySet());
        List<Double> values = new ArrayList<>(categoryHash.values());
        mCategories = new ArrayList<>();
        for (int i = 0; i < categoryHash.size(); i++) {
            mCategories.add(new Category(keys.get(i), values.get(i)));
        }
        Log.d("CategoriesAdapter", categoryHash.toString());
        Log.d("CategoriesAdapter", mCategories.toString());
    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.category_item, viewGroup, false);
        return new CategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder viewHolder, int i) {
        Category category = mCategories.get(i);
        if (category.getTotal() < 50) {
            viewHolder.ind.setBackgroundColor(mContext.getResources().getColor(R.color.rallyGreen));
        } else if (category.getTotal() < 100) {
            viewHolder.ind.setBackgroundColor(mContext.getResources().getColor(R.color.rallyYellow));
        } else {
            viewHolder.ind.setBackgroundColor(mContext.getResources().getColor(R.color.rallyOrange));
        }
        viewHolder.name.setText(TextUtils.titleFormat(category.getName()));
        viewHolder.total.setText(String.format("$%.2f", category.getTotal()));
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View ind;
        TextView name;
        TextView total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ind = itemView.findViewById(R.id.spend_indicator);
            name = itemView.findViewById(R.id.category_name);
            total = itemView.findViewById(R.id.category_total);
        }
    }

}
