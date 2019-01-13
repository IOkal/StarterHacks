package me.susieson.receiptsafe;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {

    private Context mContext;
    private List<Receipt> mReceipts;

    public ReceiptAdapter(Context context, List<Receipt> receipts) {
        mContext = context;
        mReceipts = receipts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.receipt_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Receipt receipt = mReceipts.get(i);
        if (receipt.getTotalAmount() < 50) {
            viewHolder.ind.setBackgroundColor(mContext.getResources().getColor(R.color.rallyGreen));
        } else if (receipt.getTotalAmount() < 100) {
            viewHolder.ind.setBackgroundColor(mContext.getResources().getColor(R.color.rallyYellow));
        } else {
            viewHolder.ind.setBackgroundColor(mContext.getResources().getColor(R.color.rallyOrange));
        }
        viewHolder.name.setText(TextUtils.titleFormat(receipt.getMerchantName()));
        viewHolder.cat.setText(TextUtils.titleFormat(receipt.getCategory()));
        viewHolder.total.setText(String.format("$%.2f", receipt.getTotalAmount()));
    }

    @Override
    public int getItemCount() {
        return mReceipts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View ind;
        TextView name;
        TextView cat;
        TextView total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ind = itemView.findViewById(R.id.spending_indicator);
            name = itemView.findViewById(R.id.receipt_name);
            cat = itemView.findViewById(R.id.receipt_category);
            total = itemView.findViewById(R.id.receipt_total);
        }
    }
}
