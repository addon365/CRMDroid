package com.addon.crmdroid.models;


import com.google.gson.Gson;

import java.util.Calendar;
import java.util.UUID;

import static com.addon.crmdroid.utils.AppConstants.getGson;

public class Lead {
    private UUID id;
    private Calendar createdDate;
    private UUID leadSourceId;
    private UUID profileId;
    private UUID userId;
    private String comments;

    private LeadSource leadSource;
    private Profile profile;

    public UUID getId() {
        return id;
    }

    public void setId(UUID leadId) {
        this.id = leadId;
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

    public static Lead[] fromJson(String message) {
        Gson gson = getGson();
        Lead[] leads = gson.fromJson(message, Lead[].class);
        return leads;
    }

    public static Lead fromJsonSingle(String message) {
        Gson gson = getGson();
        Lead lead = gson.fromJson(message, Lead.class);
        return lead;
    }

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }

    public String getDateAsString() {
        return createdDate.get(Calendar.DATE) + "/"
                + (createdDate.get(Calendar.MONTH) + 1) + "/"
                + createdDate.get(Calendar.YEAR);
    }
}
