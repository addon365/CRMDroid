package com.addon.crmdroid;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.addon.crmdroid.adapters.LeadListAdapter;
import com.addon.crmdroid.models.Lead;
import com.addon.crmdroid.utils.http.HttpCaller;
import com.addon.crmdroid.utils.http.HttpRequest;
import com.addon.crmdroid.utils.http.HttpResponse;

import java.io.File;
import java.io.IOException;

import static com.addon.crmdroid.utils.AppConstants.INTENT_AUDIO_FILE;
import static com.addon.crmdroid.utils.AppConstants.INTENT_LEAD_INFO;
import static com.addon.crmdroid.utils.AppConstants.REQUEST_LEAD_STATUS;
import static com.addon.crmdroid.utils.AppConstants.getLeadsAction;

public class LeadsActivity extends ListActivity {

    private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";

    private static final String TAG = "LeadsActivity";
    ListView leadsListView;
    LeadListAdapter leadListAdapter;

    private MediaRecorder recorder = null;
    private int currentFormat = 0;
    private int output_formats[] = {MediaRecorder.OutputFormat.MPEG_4,
            MediaRecorder.OutputFormat.THREE_GPP};
    private String file_exts[] = {AUDIO_RECORDER_FILE_EXT_MP4,
            AUDIO_RECORDER_FILE_EXT_3GP};
    private String fileName;

    private Lead selectedLead;
    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leads);
        leadsListView = getListView();

        HttpRequest httpRequest = new HttpRequest(getLeadsAction());
        httpRequest.setMethodtype(HttpRequest.GET);

        new HttpCaller(this, "Loading Leads") {
            @Override
            public void onResponse(HttpResponse response) {
                super.onResponse(response);
                if (response.getStatus() == HttpResponse.SUCCESS) {
                    leadListAdapter = new LeadListAdapter(getApplicationContext(),
                            Lead.fromJson(response.getMesssage()));

                    leadsListView.setAdapter(leadListAdapter);
                } else {
                    showSnackbar(response.getMesssage());
                }
            }
        }.execute(httpRequest);

    }

    private String getFilename() {
        String filepath = getExternalCacheDir().getAbsolutePath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);

        if (!file.exists()) {
            file.mkdirs();
        }
        fileName = (filepath + "/" + System.currentTimeMillis() + file_exts[currentFormat]);

        return fileName;
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        selectedLead = leadListAdapter.getItem(position);
        String mobileNumber = selectedLead.getProfile().getMobileNumber();

        telephonyManager =
                (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);


        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + mobileNumber));
            startActivity(intent);
        } catch (SecurityException securityException) {
            Log.e("LeadsActivity", securityException.getMessage(), securityException);
        }

    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(output_formats[currentFormat]);
        //recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(getFilename());
        recorder.setOnErrorListener(errorListener);
        recorder.setOnInfoListener(infoListener);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException e) {
            Log.e("REDORDING :: ", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("REDORDING :: ", e.getMessage());
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        try {
            if (null != recorder) {
                recorder.stop();
                recorder.reset();
                recorder.release();

                recorder = null;
                telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_NONE);
                Intent intent = new Intent(getApplicationContext(), LeadStatusActivity.class);
                intent.putExtra(INTENT_AUDIO_FILE, fileName);
                intent.putExtra(INTENT_LEAD_INFO, selectedLead.toJson());
                startActivityForResult(intent, REQUEST_LEAD_STATUS);
            }
        } catch (RuntimeException stopException) {

        }
    }

    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(
                leadsListView
                , message
                , Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int what, int extra) {
            Toast.makeText(LeadsActivity.this,
                    "Error: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
        }
    };

    private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {
            Toast.makeText(LeadsActivity.this,
                    "Warning: " + what + ", " + extra, Toast.LENGTH_SHORT)
                    .show();
        }
    };

    PhoneStateListener callStateListener = new PhoneStateListener() {
        public void onCallStateChanged(int state, String incomingNumber) {
            if (state == TelephonyManager.CALL_STATE_RINGING) {

            }
            if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                //Phone is currently in a Call.
                startRecording();
            }
            if (state == TelephonyManager.CALL_STATE_IDLE) {
                //Phone is neither ringing nor in a call.
                stopRecording();

            }
        }
    };
}
