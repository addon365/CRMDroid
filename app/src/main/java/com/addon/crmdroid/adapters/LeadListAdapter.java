package com.addon.crmdroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.addon.crmdroid.R;
import com.addon.crmdroid.models.Lead;
import com.addon.crmdroid.models.Profile;

import java.util.Arrays;
import java.util.List;

public class LeadListAdapter extends ArrayAdapter<Lead> {
    Context context;
    List<Lead> leads;

    public LeadListAdapter(Context context, Lead[] leads) {
        this(context, Arrays.asList(leads));
    }

    public LeadListAdapter(Context context, List<Lead> leads) {
        super(context, R.layout.item_lead, leads);
        this.leads = leads;
        ;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_lead, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.leadSource = (TextView) convertView.findViewById(R.id.item_lead_source);
            viewHolder.leadName = (TextView) convertView.findViewById(R.id.item_lead_name);
            viewHolder.leadCity = (TextView) convertView.findViewById(R.id.item_lead_city);
            viewHolder.leadDate = (TextView) convertView.findViewById(R.id.item_lead_date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Lead lead = leads.get(position);
        Profile profile = lead.getProfile();


        viewHolder.leadSource.setText(lead.getLeadSource().getCode());
        viewHolder.leadName.setText(profile.getName());
        String city = profile.getCity() + "," + profile.getState();
        viewHolder.leadCity.setText(city);
        viewHolder.leadDate.setText(lead.getDateAsString());


        return convertView;
    }

    static class ViewHolder {
        TextView leadSource;
        TextView leadName;
        TextView leadCity;
        TextView leadDate;
    }
}
