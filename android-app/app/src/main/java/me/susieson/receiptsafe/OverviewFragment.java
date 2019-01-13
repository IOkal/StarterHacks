package me.susieson.receiptsafe;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class OverviewFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private User mUser;
    private RecyclerView mRecentRecyclerView;
    private LinearLayoutManager mRecentLayoutManager;
    private ReceiptAdapter mRecentAdapter;
    private RecyclerView mCategoryRecyclerView;
    private LinearLayoutManager mCategoryLayoutManager;
    private CategoriesAdapter mCategoryAdapter;

    public OverviewFragment() {
    }

    public static OverviewFragment newInstance(User user) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putParcelable("user", Parcels.wrap(user));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = Parcels.unwrap(getArguments().getParcelable("user"));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        mRecentRecyclerView = view.findViewById(R.id.overview_fragment_recent_rv);
        mRecentLayoutManager = new LinearLayoutManager(getContext());
        mRecentRecyclerView.setLayoutManager(mRecentLayoutManager);
        List<Receipt> recentReceipts = new ArrayList<>();
        recentReceipts.add(mUser.getReceipts().get(0));
        recentReceipts.add(mUser.getReceipts().get(1));
        mRecentAdapter = new ReceiptAdapter(getContext(), recentReceipts);
        mRecentRecyclerView.setAdapter(mRecentAdapter);
        mCategoryRecyclerView = view.findViewById(R.id.overview_fragment_category_rv);
        mCategoryLayoutManager = new LinearLayoutManager(getContext());
        mCategoryRecyclerView.setLayoutManager(mCategoryLayoutManager);
        mCategoryAdapter = new CategoriesAdapter(getContext(), mUser.getReceipts());
        mCategoryRecyclerView.setAdapter(mCategoryAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

    }

}
