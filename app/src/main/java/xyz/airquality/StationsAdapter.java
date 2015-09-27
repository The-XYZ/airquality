package xyz.airquality;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by naman on 27/09/15.
 */
public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.ViewHolder> {

    private Activity context;
    private List<ParseObject> parseObjects;
    HomeFragment homeFragment;

    private int lastPosition = -1;

    private boolean firstSelected= false;
    private boolean secondSelected = false;

    private int firstSelectedPosition = -1;
    private int secondSelectedPosition = -1;

    public StationsAdapter(Activity context, List<ParseObject> parseObjects, HomeFragment homeFragment) {
        this.context = context;
        this.parseObjects = parseObjects;
        this.homeFragment = homeFragment;
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
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        setAnimation(viewHolder.itemView, position);

        viewHolder.title1.setText(parseObjects.get(position).getString("Station"));
        viewHolder.title2.setText(getTextForPollutionLevel(parseObjects.get(position).getNumber("Remark").intValue()));
        viewHolder.itemImage.setImageResource(getDrawableForPollutionLevel(parseObjects.get(position).getNumber("Remark").intValue()));
        viewHolder.indicator.setBackgroundColor(getColorForPollutionLevel(parseObjects.get(position).getNumber("Remark").intValue()));
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RegionDetailActivity.class);
                intent.putExtra("Station", viewHolder.title1.getText().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        if (firstSelectedPosition!=-1) {
            if (firstSelectedPosition == position){
                viewHolder.foreground.setVisibility(View.VISIBLE);
                viewHolder.check.setVisibility(View.VISIBLE);
            } else {
                viewHolder.foreground.setVisibility(View.GONE);
                viewHolder.check.setVisibility(View.GONE);
            }
        }

        if (secondSelectedPosition!=-1) {
            if (secondSelectedPosition == position){
                viewHolder.foreground.setVisibility(View.VISIBLE);
                viewHolder.check.setVisibility(View.VISIBLE);
            } else {
                viewHolder.foreground.setVisibility(View.GONE);
                viewHolder.check.setVisibility(View.GONE);
            }
        } else {
            viewHolder.foreground.setVisibility(View.GONE);
            viewHolder.check.setVisibility(View.GONE);
        }

    }
    public  class ViewHolder extends RecyclerView.ViewHolder {

        View indicator, foreground;
        TextView title1, title2;
        ImageView itemImage, check;
        LinearLayout linearLayout;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);

            indicator = itemLayoutView.findViewById(R.id.indicator);
            title1 = (TextView) itemLayoutView.findViewById(R.id.title1);
            title2 = (TextView) itemLayoutView.findViewById(R.id.title2);
            itemImage = (ImageView) itemLayoutView.findViewById(R.id.item_image);
            linearLayout = (LinearLayout) itemLayoutView.findViewById(R.id.layout);

            foreground= itemLayoutView.findViewById(R.id.foreground);
            check = (ImageView) itemLayoutView.findViewById(R.id.check);

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (firstSelected) {
                        secondSelected= true;
                        homeFragment.addItem2ToCoparison(parseObjects.get(getAdapterPosition()));
                        secondSelectedPosition = getAdapterPosition();
                        notifyItemChanged(getAdapterPosition());

                    } else {
                        //do intent stuff
                    }
                }
            });

            itemLayoutView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    firstSelected = true;
                    firstSelectedPosition = getAdapterPosition();
                    homeFragment.showComparsionView();
                    homeFragment.addItem1ToCoparison(parseObjects.get(getAdapterPosition()));
                    notifyItemChanged(getAdapterPosition());

                    return true;
                }
            });

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


    public int getDrawableForPollutionLevel(int number) {
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
                return Color.GREEN;
            case 2:
                return Color.parseColor("#FFA500");
            case 3:
                return Color.MAGENTA;
            case 4:
                return Color.RED;
            default:
                return Color.YELLOW;
        }
    }

    public void clearComparison() {
        firstSelected= false;
        secondSelected = false;
        firstSelectedPosition = -1;
        secondSelectedPosition = -1;
        notifyDataSetChanged();
    }

}
