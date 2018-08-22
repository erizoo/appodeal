package com.appodeal.support.test;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdvertisingAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdvertisingAdapter.AddressViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false)
        );

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class AddressViewHolder extends BaseViewHolder {

        private TextView textView;

        public AddressViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_recycler_item);
            textView.setText("sdfgsdg");
        }
    }
}
