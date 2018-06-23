package com.addon.crmdroid;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.addon.crmdroid.models.Lead;
import com.addon.crmdroid.models.Profile;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.addon.crmdroid.models.Lead.fromJsonSingle;
import static com.addon.crmdroid.utils.AppConstants.INTENT_AUDIO_FILE;
import static com.addon.crmdroid.utils.AppConstants.INTENT_LEAD_INFO;
import static com.addon.crmdroid.utils.AppConstants.TAG_PLAY;
import static com.addon.crmdroid.utils.AppConstants.TAG_STOP;
import static com.addon.crmdroid.utils.AppConstants.getTimeString;
import static com.addon.crmdroid.utils.AppConstants.showSnackBar;

public class LeadStatusActivity extends AppCompatActivity {

    private static final String TAG = "Lead Status Activity";

    @BindView(R.id.btn_lead_play)
    ImageView btnPlay;

    @BindView(R.id.tv_time)
    TextView playerTime;

    @BindView(R.id.status_lead_name)
    TextView tvName;

    @BindView(R.id.status_lead_address)
    TextView tvAddress;

    @BindView(R.id.status_mobile)
    TextView tvMobile;

    @BindView(R.id.sb_lead_progress)
    SeekBar seekBar;

    Lead lead;
    String audioFileName;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_status);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        lead = fromJsonSingle(intent.getStringExtra(INTENT_LEAD_INFO));
        audioFileName = intent.getStringExtra(INTENT_AUDIO_FILE);

        tvName.setText(lead.getProfile().getName());
        tvAddress.setText(getFullAddress());
        tvMobile.setText(lead.getProfile().getMobileNumber());
        btnPlay.setTag(TAG_PLAY);

        initAudioControls();

    }

    private String getFullAddress() {
        Profile profile = lead.getProfile();
        StringBuilder fullAddress = new StringBuilder();
        fullAddress.append(profile.getAddress1() + "\n");
        fullAddress.append(profile.getAddress2() + "\n");
        fullAddress.append(profile.getArea() + "\n");
        fullAddress.append(profile.getCity() + "\n");
        fullAddress.append(profile.getState() + "\n");
        return fullAddress.toString();
    }

    public void btnPlayStop_onClick(View view) {
        //Integer integer = (Integer) btnPlay.getTag();
        if (true){//integer == TAG_PLAY) {

            startPlayer();
        } else {

            stopPlayer();
        }
    }

    private void stopPlayer() {
        if (mediaPlayer == null)
            return;
        btnPlay.setBackgroundResource(R.drawable.ic_action_play);
        btnPlay.setTag(TAG_PLAY);
        mediaPlayer.release();
        mediaPlayer = null;
        seekBar.setMax(0);
        seekbarUpdateHandler.removeCallbacks(updateSeekbar);

    }

    private void startPlayer() {
        if (!initAudioControls()) {
            showSnackBar(playerTime, "Unable to Start Player");
            return;
        }

        mediaPlayer.start();
        seekbarUpdateHandler.postDelayed(updateSeekbar, 0);
        btnPlay.setBackgroundResource(R.drawable.ic_action_stop);
        btnPlay.setTag(TAG_STOP);

    }

    private boolean initAudioControls() {
        if (mediaPlayer != null)
            return true;

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(audioFileName);
            mediaPlayer.prepare();
            seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
            seekBar.setMax(mediaPlayer.getDuration());
            mediaPlayer.setOnCompletionListener(onCompletionListener);
            return true;
        } catch (IOException exception) {
            Log.e(TAG, exception.getMessage(), exception);
            mediaPlayer = null;
            return false;
        }
    }

    private Handler seekbarUpdateHandler = new Handler();
    private Runnable updateSeekbar = new Runnable() {
        @Override
        public void run() {

            int progress = mediaPlayer.getCurrentPosition();
            seekBar.setProgress(progress);
            playerTime.setText(getTimeString(progress));
            seekbarUpdateHandler.postDelayed(this, 50);
        }
    };
    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (mediaPlayer != null && fromUser) {
                mediaPlayer.seekTo(progress * 1000);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            stopPlayer();
        }
    };

}