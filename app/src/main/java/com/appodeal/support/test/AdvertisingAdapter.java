package com.appodeal.support.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appodeal.ads.NativeAd;
import com.appodeal.ads.native_ad.views.NativeAdViewNewsFeed;

import java.util.ArrayList;
import java.util.List;

public class AdvertisingAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final String APP_KEY = "9c85d2cfc5bf39fb654168042c0b44febcf500b36d60d4bc";

    private List<NativeAd> nativeAds = new ArrayList<>();
    private Callback callback;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdvertisingAdapter.AddressViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false)
        );

    }

    public void setItems(List<NativeAd> nativeAds) {
        this.nativeAds.addAll(nativeAds);
        notifyDataSetChanged();
        if (!this.nativeAds.isEmpty()){
            callback.showItems();
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {

        void showItems();
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return nativeAds.size();
    }


    public class AddressViewHolder extends BaseViewHolder {

        private ImageView image;
        private Context context;

        public AddressViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            image = itemView.findViewById(R.id.image_view);
            image.setImageBitmap(nativeAds.get(position).getImage());
        }
    }
}
