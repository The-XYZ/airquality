package xyz.airquality;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by naman on 27/09/15.
 */
public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.ViewHolder> {

    private Activity context;

    private int lastPosition = -1;

    public StationsAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        setAnimation(viewHolder.itemView, position);

    }
    public  class ViewHolder extends RecyclerView.ViewHolder {

        View indicator;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

//            indicator = itemLayoutView.findViewById(R.id.indicator);

        }
    }


    @Override
    public int getItemCount() {
        return 5;
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void updateDataset() {
        notifyDataSetChanged();
    }

}
