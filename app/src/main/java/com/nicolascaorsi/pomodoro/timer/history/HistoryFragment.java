package com.nicolascaorsi.pomodoro.timer.history;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nicolascaorsi.pomodoro.R;
import com.nicolascaorsi.pomodoro.data.Pomodoro;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment implements HistoryContract.View {

    private RecyclerView mRecyclerView;
    private TextView mEmptyMessage;
    private HistoryAdapter mAdapter;
    private HistoryContract.Presenter mPresenter;
    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HistoryFragment.
     */
    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);
        mEmptyMessage = (TextView) view.findViewById(R.id.empty_history_message);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mAdapter = new HistoryAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public void onAnimationFinished(RecyclerView.ViewHolder viewHolder) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });
        mPresenter.start();
        return view;
    }

    @Override
    public void setPomodoros(List<Pomodoro> pomodoros) {
        mEmptyMessage.setVisibility(View.GONE);
        mAdapter.setItems(pomodoros);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addPomodoro(Pomodoro pomodoro) {
        mEmptyMessage.setVisibility(View.GONE);
        mAdapter.addItems(pomodoro);
        mAdapter.notifyItemInserted(0);
    }

    @Override
    public void setNoDataAvaliable() {
        mEmptyMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(HistoryContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
