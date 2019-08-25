package com.groo.viviscreen.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.groo.viviscreen.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {
    // 아이템 리스트
    private ArrayList<SwipeCard> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CardStackAdapter(ArrayList<SwipeCard> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardStackAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_carditem, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mAdTitle.setText(mDataset.get(position).getText1());
        holder.mAdWriter.setText(mDataset.get(position).getText2());

        DecimalFormat myFormatter = new DecimalFormat("###,###");
        holder.mGrooPoint.setText(myFormatter.format(mDataset.get(position).getGrooPoint()));

        holder.mAdBackground.setImageResource(mDataset.get(position).getBackground());
        holder.mAdBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(view.getContext(), ADDetailActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //view.getContext().startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mAdBackground;
        private TextView mAdTitle;
        private TextView mAdWriter;
        private TextView mGrooPoint;

        private ViewHolder(View itemView) {
            super(itemView);

            mAdBackground = itemView.findViewById(R.id.ad_background_imageview);
            mAdTitle = itemView.findViewById(R.id.ad_title_textview);
            mAdWriter = itemView.findViewById(R.id.ad_writer_textview);
            mGrooPoint = itemView.findViewById(R.id.ad_groopoint_textview);
        }
    }
}