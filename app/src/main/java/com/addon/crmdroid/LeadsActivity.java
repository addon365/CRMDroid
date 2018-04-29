package com.addon.crmdroid;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ListView;

import com.addon.crmdroid.adapters.LeadListAdapter;
import com.addon.crmdroid.models.Lead;
import com.addon.crmdroid.utils.http.HttpCaller;
import com.addon.crmdroid.utils.http.HttpRequest;
import com.addon.crmdroid.utils.http.HttpResponse;

import static com.addon.crmdroid.utils.AppConstants.getLeadsAction;

public class LeadsActivity extends ListActivity {

    ListView leadsListView;
    LeadListAdapter leadListAdapter;

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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Lead lead=leadListAdapter.getItem(position);
        lead.getProfile().getMobileNumber();
    }

    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(
                leadsListView
                , message
                , Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}
