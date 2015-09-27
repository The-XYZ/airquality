package xyz.airquality.Fragments;


import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.db.chart.Tools;
import com.db.chart.listener.OnEntryClickListener;
import com.db.chart.model.Bar;
import com.db.chart.model.BarSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.BarChartView;
import com.db.chart.view.ChartView;
import com.db.chart.view.Tooltip;
import com.db.chart.view.XController;
import com.db.chart.view.YController;
import com.db.chart.view.animation.Animation;
import com.db.chart.view.animation.easing.ElasticEase;

import java.util.ArrayList;
import java.util.Random;

import xyz.airquality.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ComparisonDaily extends Fragment {



    public  static  int PERMCO = 4;
    public  static  int PERMNO = 12;
    public  static  int PERMOZONE = 180;
    public  static  int PERMNO2 = 120;


    private BarChartView mChartOne;
    private ImageButton mPlayOne;
    private boolean mUpdateOne;
    private final String[] mLabelsOne= {"1 pm", "2 pm", "3 pm", "4 pm", "5 pm"};
    private final float [][] mValuesOne = {{Float.parseFloat(String.valueOf(gen())+".0f"), Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f")}, {Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f")}};


    /** Second chart */
    private BarChartView mChartTwo;
    private ImageButton mPlayTwo;
    private boolean mUpdateTwo;
    private final String[] mLabelsTwo= {"1 pm", "2 pm", "3 pm", "4 pm", "5 pm"};
    private final float [][] mValuesTwo =  {{Float.parseFloat(String.valueOf(gen())+".0f"), Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f")}, {Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f")}};

    private TextView mTextViewTwo;


    /** Third chart */
    private BarChartView mChartThree;
    private ImageButton mPlayThree;
    private boolean mUpdateThree;
    private final String[] mLabelsThree= {"1 pm", "2 pm", "3 pm", "4 pm", "5 pm"};
    private final float[][] mValuesThree =  {{Float.parseFloat(String.valueOf(gen())+".0f"), Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f")}, {Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f"),Float.parseFloat(String.valueOf(gen())+".0f")}};







    public ComparisonDaily() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_comparison_daily, container, false);

        // Init first chart
        mUpdateOne = true;
        mChartOne = (BarChartView) layout.findViewById(R.id.barchartNO2);
        mPlayOne = (ImageButton) layout.findViewById(R.id.play1);
        mPlayOne.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                /*if(mUpdateOne)
                    updateChart(0, mChartOne, mPlayOne);
                else {
                    dismissChart(0, mChartOne, mPlayOne);
                }
                mUpdateOne = !mUpdateOne;*/
                dismissChart(0, mChartOne, mPlayOne);
            }
        });

        // Init second chart
        mUpdateTwo = true;
        mChartTwo = (BarChartView) layout.findViewById(R.id.barchartSO2);
        mPlayTwo = (ImageButton) layout.findViewById(R.id.play2);
        mPlayTwo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(mUpdateTwo)
                    updateChart(1, mChartTwo, mPlayTwo);
                else
                    dismissChart(1, mChartTwo, mPlayTwo);
                mUpdateTwo = !mUpdateTwo;
            }
        });
        mTextViewTwo = (TextView) layout.findViewById(R.id.value);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {


        }else{


        }

        // Init third chart
        mUpdateThree = true;
        mChartThree = (BarChartView) layout.findViewById(R.id.barchartCO);
        mPlayThree = (ImageButton) layout.findViewById(R.id.play3);
        mPlayThree.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(mUpdateThree)
                    updateChart(2, mChartThree, mPlayThree);
                else
                    dismissChart(2, mChartThree, mPlayThree);
                mUpdateThree = !mUpdateThree;
            }
        });

        showChart(0, mChartOne, mPlayOne);
        showChart(1, mChartTwo, mPlayTwo);
        showChart(2, mChartThree, mPlayThree);

        return layout;
    }

    /**
     * Show a CardView chart
     * @param tag   Tag specifying which chart should be dismissed
     * @param chart   Chart view
     * @param btn    Play button
     */
    private void showChart(final int tag, final ChartView chart, final ImageButton btn){
        dismissPlay(btn);
        Runnable action =  new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        showPlay(btn);
                    }
                }, 500);
            }
        };

        switch(tag) {
            case 0:
                produceOne(chart, action); break;
            case 1:
                produceTwo(chart, action); break;
            case 2:
                produceThree(chart, action); break;
            default:
        }
    }


    /**
     * Update the values of a CardView chart
     * @param tag   Tag specifying which chart should be dismissed
     * @param chart   Chart view
     * @param btn    Play button
     */
    private void updateChart(final int tag, final ChartView chart, ImageButton btn){

        dismissPlay(btn);

        switch(tag){
            case 0:
                updateOne(chart); break;
            case 1:
                updateTwo(chart); break;
            case 2:
                updateThree(chart); break;
            default:
        }
    }


    /**
     * Dismiss a CardView chart
     * @param tag   Tag specifying which chart should be dismissed
     * @param chart   Chart view
     * @param btn    Play button
     */
    private void dismissChart(final int tag, final ChartView chart, final ImageButton btn){

        dismissPlay(btn);

        Runnable action =  new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        showPlay(btn);
                        showChart(tag, chart, btn);
                    }
                }, 500);
            }
        };

        switch(tag){
            case 0:
                dismissOne(chart, action); break;
            case 1:
                dismissTwo(chart, action); break;
            case 2:
                dismissThree(chart, action); break;
            default:
        }
    }


    /**
     * Show CardView play button
     * @param btn    Play button
     */
    private void showPlay(ImageButton btn){
        btn.setEnabled(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            btn.animate().alpha(1).scaleX(1).scaleY(1);
        else
            btn.setVisibility(View.VISIBLE);
    }


    /**
     * Dismiss CardView play button
     * @param btn    Play button
     */
    private void dismissPlay(ImageButton btn){
        btn.setEnabled(false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            btn.animate().alpha(0).scaleX(0).scaleY(0);
        else
            btn.setVisibility(View.INVISIBLE);
    }



    /**
     *
     * Chart 1
     *
     */

    public void produceOne(ChartView chart, Runnable action){
        BarChartView barChart = (BarChartView) chart;

        barChart.setOnEntryClickListener(new OnEntryClickListener() {
            @Override
            public void onClick(int setIndex, int entryIndex, Rect rect) {
                System.out.println("OnClick "+rect.left);
            }
        });

        Tooltip tooltip = new Tooltip(getActivity(), R.layout.barchart_one_tooltip);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            tooltip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1));
            tooltip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0));
        }
        barChart.setTooltips(tooltip);

        BarSet barSet = new BarSet(mLabelsOne, mValuesOne[0]);
        barSet.setColor(Color.parseColor("#a8896c"));
        barChart.addData(barSet);

        barSet = new BarSet(mLabelsOne, mValuesOne[1]);
        barSet.setColor(Color.parseColor("#c33232"));
        barChart.addData(barSet);

        barChart.setSetSpacing(Tools.fromDpToPx(-15));
        barChart.setBarSpacing(Tools.fromDpToPx(35));
        barChart.setRoundCorners(Tools.fromDpToPx(2));

        Paint gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#8986705C"));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setAntiAlias(true);
        gridPaint.setStrokeWidth(Tools.fromDpToPx(.75f));

        barChart.setBorderSpacing(5)
                .setAxisBorderValues(0, 10, 2)
                .setGrid(BarChartView.GridType.FULL, gridPaint)
                .setYAxis(false)
                .setXLabels(XController.LabelPosition.OUTSIDE)
                .setYLabels(YController.LabelPosition.NONE)
                .setLabelsColor(Color.parseColor("#86705c"))
                .setAxisColor(Color.parseColor("#86705c"));

        int[] order = {2, 1, 3, 0, 4};
        final Runnable auxAction = action;
        Runnable chartOneAction = new Runnable() {
            @Override
            public void run() {
                showTooltipOne();
                auxAction.run();
            }
        };
        barChart.show(new Animation()
                .setOverlap(.5f, order)
                .setEndAction(chartOneAction))
        //.show()
        ;
    }

    public void updateOne(ChartView chart){

        dismissTooltipOne();
        float [][]newValues = {{8.5f, 6.5f, 4.5f, 3.5f, 9f}, {5.5f, 3.0f, 3.0f, 2.5f, 7.5f}};
        chart.updateValues(0, newValues[0]);
        chart.updateValues(1, newValues[1]);
        chart.notifyDataUpdate();
    }

    public void dismissOne(ChartView chart, Runnable action){

        dismissTooltipOne();
        int[] order = {0, 4, 1, 3, 2};
        chart.dismiss(new Animation()
                .setOverlap(.5f, order)
                .setEndAction(action));
    }


    private void showTooltipOne(){

        ArrayList<ArrayList<Rect>> areas = new ArrayList<>();
        areas.add(mChartOne.getEntriesArea(0));
        areas.add(mChartOne.getEntriesArea(1));

        for(int i = 0; i < areas.size(); i++) {
            for (int j = 0; j < areas.get(i).size(); j++) {

                Tooltip tooltip = new Tooltip(getActivity(), R.layout.barchart_one_tooltip, R.id.value);
                tooltip.prepare(areas.get(i).get(j), mValuesOne[i][j]);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    tooltip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1));
                    tooltip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0));
                }
                mChartOne.showTooltip(tooltip, true);
            }
        }

    }


    private void dismissTooltipOne(){
        mChartOne.dismissAllTooltips();
    }


    /**
     *
     * Chart 2
     *
     */

    public void produceTwo(ChartView chart, Runnable action){
        BarChartView barChart = (BarChartView) chart;

        barChart.setOnEntryClickListener(new OnEntryClickListener() {
            @Override
            public void onClick(int setIndex, int entryIndex, Rect rect) {
                System.out.println("OnClick "+rect.left);
            }
        });

        Tooltip tooltip = new Tooltip(getActivity(), R.layout.barchart_one_tooltip);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            tooltip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1));
            tooltip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0));
        }
        barChart.setTooltips(tooltip);

        BarSet barSet = new BarSet(mLabelsTwo, mValuesTwo[0]);
        barSet.setColor(Color.parseColor("#a8896c"));
        barChart.addData(barSet);

        barSet = new BarSet(mLabelsTwo, mValuesTwo[1]);
        barSet.setColor(Color.parseColor("#c33232"));
        barChart.addData(barSet);

        barChart.setSetSpacing(Tools.fromDpToPx(-15));
        barChart.setBarSpacing(Tools.fromDpToPx(35));
        barChart.setRoundCorners(Tools.fromDpToPx(2));

        Paint gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#8986705C"));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setAntiAlias(true);
        gridPaint.setStrokeWidth(Tools.fromDpToPx(.75f));

        barChart.setBorderSpacing(5)
                .setAxisBorderValues(0, 10, 2)
                .setGrid(BarChartView.GridType.FULL, gridPaint)
                .setYAxis(false)
                .setXLabels(XController.LabelPosition.OUTSIDE)
                .setYLabels(YController.LabelPosition.NONE)
                .setLabelsColor(Color.parseColor("#86705c"))
                .setAxisColor(Color.parseColor("#86705c"));

        int[] order = {2, 1, 3, 0, 4};
        final Runnable auxAction = action;
        Runnable chartOneAction = new Runnable() {
            @Override
            public void run() {
                showTooltipOne();
                auxAction.run();
            }
        };
        barChart.show(new Animation()
                .setOverlap(.5f, order)
                .setEndAction(chartOneAction))
        //.show()
        ;
    }

    public void updateTwo(ChartView chart){

        chart.dismissAllTooltips();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            mTextViewTwo.animate().alpha(0).setDuration(100);

        }else{
            mTextViewTwo.setVisibility(View.INVISIBLE);

        }

        float [][]newValues = {{8.5f, 6.5f, 4.5f, 3.5f, 9f}, {5.5f, 3.0f, 3.0f, 2.5f, 7.5f}};
        chart.updateValues(0, newValues[0]);
        chart.updateValues(1, newValues[1]);
        chart.notifyDataUpdate();
    }

    public void dismissTwo(ChartView chart, Runnable action){

        chart.dismissAllTooltips();

        int[] order = {0, 4, 1, 3, 2};
        chart.dismiss(new Animation()
                .setOverlap(.5f, order)
                .setEndAction(action));
    }



    /**
     *
     * Chart 3
     *
     */

    public void produceThree(ChartView chart, Runnable action){
        BarChartView barChart = (BarChartView) chart;

        barChart.setOnEntryClickListener(new OnEntryClickListener() {
            @Override
            public void onClick(int setIndex, int entryIndex, Rect rect) {
                System.out.println("OnClick "+rect.left);
            }
        });

        Tooltip tooltip = new Tooltip(getActivity(), R.layout.barchart_one_tooltip);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            tooltip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1));
            tooltip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0));
        }
        barChart.setTooltips(tooltip);

        BarSet barSet = new BarSet(mLabelsThree, mValuesThree[0]);
        barSet.setColor(Color.parseColor("#a8896c"));
        barChart.addData(barSet);

        barSet = new BarSet(mLabelsThree, mValuesThree[1]);
        barSet.setColor(Color.parseColor("#c33232"));
        barChart.addData(barSet);

        barChart.setSetSpacing(Tools.fromDpToPx(-15));
        barChart.setBarSpacing(Tools.fromDpToPx(35));
        barChart.setRoundCorners(Tools.fromDpToPx(2));

        Paint gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#8986705C"));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setAntiAlias(true);
        gridPaint.setStrokeWidth(Tools.fromDpToPx(.75f));

        barChart.setBorderSpacing(5)
                .setAxisBorderValues(0, 10, 2)
                .setGrid(BarChartView.GridType.FULL, gridPaint)
                .setYAxis(false)
                .setXLabels(XController.LabelPosition.OUTSIDE)
                .setYLabels(YController.LabelPosition.NONE)
                .setLabelsColor(Color.parseColor("#86705c"))
                .setAxisColor(Color.parseColor("#86705c"));

        int[] order = {2, 1, 3, 0, 4};
        final Runnable auxAction = action;
        Runnable chartOneAction = new Runnable() {
            @Override
            public void run() {
                showTooltipOne();
                auxAction.run();
            }
        };
        barChart.show(new Animation()
                .setOverlap(.5f, order)
                .setEndAction(chartOneAction))
        //.show()
        ;
    }

    public void updateThree(ChartView chart){

        chart.dismissAllTooltips();

        float [][]newValues = {{8.5f, 6.5f, 4.5f, 3.5f, 9f}, {5.5f, 3.0f, 3.0f, 2.5f, 7.5f}};
        chart.updateValues(0, newValues[0]);
        chart.updateValues(1, newValues[1]);
        chart.notifyDataUpdate();
    }

    public void dismissThree(ChartView chart, Runnable action){

        chart.dismissAllTooltips();
        int[] order = {0, 4, 1, 3, 2};
        chart.dismiss(new Animation()
                .setOverlap(.5f, order)
                .setEndAction(action));
    }


    public int NO() {
        Random r = new Random();
        int Low = 357;
        int High = 450;
        int R = r.nextInt(High - Low) + Low;
        return R;
    }

    public int CO() {
        Random r = new Random();
        int Low = 1;
        int High = 8;
        int R = r.nextInt(High - Low) + Low;
        return R;
    }

    public int Ozone() {
        Random r = new Random();
        int Low = 12;
        int High = 250;
        int R = r.nextInt(High - Low) + Low;
        return R;
    }

    public int N02() {
        Random r = new Random();
        int Low = 12;
        int High = 200;
        int R = r.nextInt(High - Low) + Low;
        return R*10;
    }


    public int gen() {
        Random r = new Random();
        int Low = 1;
        int High = 10;
        int R = r.nextInt(High - Low) + Low;
        return R;
    }

}
