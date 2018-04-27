package com.addon.crmdroid.models;

import java.util.Calendar;
import java.util.UUID;

public class Lead {
    public UUID leadId;
    public Calendar CreatedDate;
    public UUID leadSourceId;
    public UUID profileId;
    public UUID userId;
    public String comments;

    public LeadSource leadSource;
    public Profile profile;

    public UUID getLeadId() {
        return leadId;
    }

    public void setLeadId(UUID leadId) {
        this.leadId = leadId;
    }

    public Calendar getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        CreatedDate = createdDate;
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
}
