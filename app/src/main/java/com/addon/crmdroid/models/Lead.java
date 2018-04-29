package com.addon.crmdroid.models;

import com.addon.crmdroid.adapters.DateConvertAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.UUID;

public class Lead {
    private UUID leadId;
    private Calendar createdDate;
    private UUID leadSourceId;
    private UUID profileId;
    private UUID userId;
    private String comments;

    private LeadSource leadSource;
    private Profile profile;

    public UUID getLeadId() {
        return leadId;
    }

    public void setLeadId(UUID leadId) {
        this.leadId = leadId;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public UUID getLeadSourceId() {
        return leadSourceId;
    }

    public void setLeadSourceId(UUID leadSourceId) {
        this.leadSourceId = leadSourceId;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LeadSource getLeadSource() {
        return leadSource;
    }

    public void setLeadSource(LeadSource leadSource) {
        this.leadSource = leadSource;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public static Lead[] fromJson(String messsage) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Calendar.class, new DateConvertAdapter())
                .create();
        Lead[] leads = gson.fromJson(messsage, Lead[].class);
        return leads;
    }

    public String getDateAsString() {
        return createdDate.get(Calendar.DATE) + "/"
                + (createdDate.get(Calendar.MONTH) + 1) + "/"
                + createdDate.get(Calendar.YEAR);
    }
}
