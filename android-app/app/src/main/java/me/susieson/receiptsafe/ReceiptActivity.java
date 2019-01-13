package me.susieson.receiptsafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.List;

public class ReceiptActivity extends AppCompatActivity {

    private List<Item> items;
    private Receipt mReceipt;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        Intent intent = getIntent();
        if (intent != null) {
            mReceipt = Parcels.unwrap(intent.getParcelableExtra("receipt-extra"));
            TextView textName = findViewById(R.id.name);
            TextView addressText = findViewById(R.id.address);
            TextView categoryText = findViewById(R.id.category);
            TextView subText = findViewById(R.id.subtotal);
            TextView taxText = findViewById(R.id.taxtotal);
            TextView totalText = findViewById(R.id.total);
            textName.setText(TextUtils.titleFormat(mReceipt.getMerchantName()));
            addressText.setText(TextUtils.titleFormat(mReceipt.getMerchantAddress()));
            categoryText.setText(TextUtils.titleFormat(mReceipt.getCategory()));
            subText.setText(String.format("Subtotal: $%.2f", mReceipt.getSubtotalAmount()));
            taxText.setText(String.format("Tax: $%.2f", mReceipt.getTaxAmount()));
            totalText.setText(String.format("Total: $%.2f", mReceipt.getTotalAmount()));
            items = mReceipt.getItems();
            mRecyclerView = findViewById(R.id.items_rv);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new ItemAdapter(items);
            mRecyclerView.setAdapter(mAdapter);

        }
    }
}
