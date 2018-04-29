package com.addon.crmdroid.models;

import java.util.UUID;

public class LeadSource {
    private UUID leadSourceId;
    private String name;
    private String code;
    private String progId;

    public UUID getLeadSourceId() {
        return leadSourceId;
    }

    public void setLeadSourceId(UUID leadSourceId) {
        this.leadSourceId = leadSourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProgId() {
        return progId;
    }

    public void setProgId(String progId) {
        this.progId = progId;
    }
}
