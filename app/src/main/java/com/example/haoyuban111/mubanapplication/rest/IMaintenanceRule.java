package com.example.haoyuban111.mubanapplication.rest;

public interface IMaintenanceRule {

    public int getId();
    public int getOsType();
    public int getRuleType();
    public int getStatus();
    public int getButtonStyle();
    public String getVersion();
    public String getStatusHeader();
    public String getStatusMessage();
    public String getStatusInfo();
    public String getButtonTitle();
    public String getURLDetails();
    public String getExcludeIPs();
    public String getButtonStyleName();
    public String getRuleName();
    public boolean isEnabled();
}
