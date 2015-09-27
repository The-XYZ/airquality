package xyz.airquality;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by naman on 27/09/15.
 */
public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.ViewHolder> {

    private Activity context;
    private List<ParseObject> parseObjects;

    private int lastPosition = -1;

    public StationsAdapter(Activity context, List<ParseObject> parseObjects) {
        this.context = context;
        this.parseObjects = parseObjects;
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

        viewHolder.title1.setText(parseObjects.get(position).getString("Station"));
        viewHolder.title2.setText(getTextForPollutionLevel(parseObjects.get(position).getNumber("Remark").intValue()));
        viewHolder.itemImage.setImageResource(getDrawableForPollutionLevel(parseObjects.get(position).getNumber("Remark").intValue()));

    }
    public  class ViewHolder extends RecyclerView.ViewHolder {

        View indicator;
        TextView title1, title2;
        ImageView itemImage;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            indicator = itemLayoutView.findViewById(R.id.indicator);
            title1 = (TextView) itemLayoutView.findViewById(R.id.title1);
            title2 = (TextView) itemLayoutView.findViewById(R.id.title2);
            itemImage = (ImageView) itemLayoutView.findViewById(R.id.item_image);

        }
    }


    @Override
    public int getItemCount() {
        return parseObjects.size();
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void updateDataset(List<ParseObject> parseObjects) {
        this.parseObjects = parseObjects;
        notifyDataSetChanged();
    }


    private int getDrawableForPollutionLevel(int number) {
            switch (number) {
                case 1:
                    return R.drawable.safe;
                case 2:
                    return R.drawable.moderate;
                case 3:
                    return R.drawable.unsafe;
                case 4:
                    return R.drawable.highunsafe;
                default:
                    return R.drawable.moderate;
            }
    }

    private String getTextForPollutionLevel(int number) {
        switch (number) {
            case 1:
                return "Safe";
            case 2:
                return "Moderate";
            case 3:
                return "Unsafe";
            case 4:
                return "Highly unsafe";
            default:
                return "Moderate";
        }
    }

    private int getColorForPollutionLevel(int number) {
        switch (number) {
            case 1:
                return R.drawable.safe;
            case 2:
                return R.drawable.moderate;
            case 3:
                return R.drawable.unsafe;
            case 4:
                return R.drawable.highunsafe;
            default:
                return R.drawable.moderate;
        }
    }
}
