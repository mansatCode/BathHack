package com.android.bathhack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.bathhack.models.SummaryModel;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;

public class SummaryActivity extends AppCompatActivity implements View.OnClickListener{

    // Constants
    private static final String TAG = "SummaryActivity";

    // Global UI Components
    private FrameLayout shareBtn, homeBtn, replayBtn;
    private TextView timeTxt, heartsTxt, coinsTxt, hazardsTxt, distanceTxt;
    private ArrayList<ConstraintLayout> acoladeBtns;
    private ArrayList<ImageView> acoladeImgs;
    private ConstraintLayout popupCont, mainCont;

    // Variables
    private boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationClient;
    private SummaryModel summaryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Set to full screen (remove status bar)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        initUI();
        setListeners();
    }

    private void initUI() {
        shareBtn = findViewById(R.id.summary_share_button);
        replayBtn = findViewById(R.id.summary_replay_button);
        homeBtn = findViewById(R.id.summary_home_button);

        timeTxt = findViewById(R.id.summary_time_text);
        heartsTxt = findViewById(R.id.summary_hearts_text);
        coinsTxt = findViewById(R.id.coin_text);
        hazardsTxt = findViewById(R.id.summary_hazards_text);
        distanceTxt = findViewById(R.id.summary_distance_text);

        acoladeBtns = new ArrayList<ConstraintLayout>() {{
            add(findViewById(R.id.acolade_cont1));
            add(findViewById(R.id.acolade_cont2));
            add(findViewById(R.id.acolade_cont3));
            add(findViewById(R.id.acolade_cont4));
            add(findViewById(R.id.acolade_cont5));
            add(findViewById(R.id.acolade_cont6));
        }};

        acoladeImgs = new ArrayList<>();
        for (ConstraintLayout btn : acoladeBtns) {
            acoladeImgs.add((ImageView) btn.getChildAt(0));
        }

        popupCont = findViewById(R.id.summary_popup_container);
        mainCont = findViewById(R.id.summary_main_container);
    }

    private void setListeners() {
        for (ConstraintLayout btn : acoladeBtns) {
            btn.setOnClickListener(this);
        }

        shareBtn.setOnClickListener(this);
        homeBtn.setOnClickListener(this);
        replayBtn.setOnClickListener(this);

        popupCont.setOnClickListener(this);
    }

    public void setAcolades(SummaryModel summaryModel) {
        this.summaryModel = summaryModel;
        //TODO use method when calling this activity from playActivity
        //Speed
        acoladeImgs.get(0).setImageDrawable((summaryModel.getTime() > 30000) ?
                    getResources().getDrawable(R.drawable.acolade_rocket) :
                    getResources().getDrawable(R.drawable.acolade_snail));

        //Coins
        acoladeImgs.get(1).setImageDrawable((summaryModel.getCoins() > 15) ?
                getResources().getDrawable(R.drawable.acolade_money) :
                getResources().getDrawable(R.drawable.acolade_broke));

        //Hearts
        acoladeImgs.get(2).setImageDrawable((summaryModel.getHearts() > 3) ?
                getResources().getDrawable(R.drawable.acolade_money) :
                getResources().getDrawable(R.drawable.acolade_broke));
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.summary_share_button:
                //TODO share image
                break;
            case R.id.summary_home_button:
                intent = new Intent(SummaryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                SummaryActivity.this.finish();
                break;
            case R.id.summary_replay_button:
                intent = new Intent(SummaryActivity.this, PlayActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                SummaryActivity.this.finish();
                break;

            case R.id.acolade_cont1:
                //TODO show popup for acolade
                togglePopup();
                break;
            case R.id.acolade_cont2:
                //TODO show popup for acolade
                togglePopup();
                break;
            case R.id.acolade_cont3:
                //TODO show popup for acolade
                togglePopup();
                break;
            case R.id.acolade_cont4:
                //TODO show popup for acolade
                togglePopup();
                break;
            case R.id.acolade_cont5:
                //TODO show popup for acolade
                togglePopup();
                break;
            case R.id.acolade_cont6:
                //TODO show popup for acolade
                togglePopup();
                break;

            case R.id.summary_popup_container:
                togglePopup();
                break;
        }
    }

    private void togglePopup() {
        int popupVis = popupCont.getVisibility();
        int mainVis = mainCont.getVisibility();
        mainCont.setVisibility(popupVis);
        popupCont.setVisibility(mainVis);
    }

}