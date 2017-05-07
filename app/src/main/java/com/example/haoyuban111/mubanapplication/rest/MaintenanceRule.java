package com.example.haoyuban111.mubanapplication.rest;

import android.text.TextUtils;

import com.example.haoyuban111.mubanapplication.help_class.LanguageHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class MaintenanceRule extends JsonEntry implements IMaintenanceRule {

    public enum EMaintananceButtonStyleV2 {
        HIDDEN(0),
        OPEN_BROWSER(1),
        OPEN_MARKET(2);

        public final int value;

        EMaintananceButtonStyleV2(int value) {
            this.value = value;
        }

        public static EMaintananceButtonStyleV2 fromInt(int value) {
            EMaintananceButtonStyleV2 result = null;
            for (EMaintananceButtonStyleV2 item : EMaintananceButtonStyleV2.values()) {
                if (item.value == value) {
                    result = item;
                    break;
                }
            }
            if (result == null) {
                throw new IllegalArgumentException("EMaintananceButtonStyleV2 - fromInt");
            }
            return result;
        }
    }

    public static final int RULE_INTERNET_NOT_AVAILABLE = -10;
    public static final int RULE_SESSION_NOT_AVAILABLE = -11;
    public static final int RULE_EMPTY = -1;
    public static final int RULE_MAINTENANCE = 0;
    public static final int RULE_UPGRADE = 1;
    public static final int RULE_PROTOCOL = 2;

    public static final MaintenanceRule EMPTY = new MaintenanceRule(RULE_EMPTY);

    private EntriesText _buttonTitleV2;
    private EntriesText _statusMessageV2;
    private EntriesText _statusHeaderV2;
    private EntriesText _urlV2;

    public MaintenanceRule(String json) throws JSONException {
        super(json);
    }

    public MaintenanceRule(int ruleType) {
        try {
            setRuleType(ruleType);
        } catch (JSONException ex) {
//            LogWriter.e(ex);
        }
    }

    public int getId() {
        return optInt("Id", -1);
    }

    public int getOsType() {
        return optInt("EosType");
    }

    public int getRuleType() {
        return optInt("RuleType");
    }

    public MaintenanceRule setRuleType(int type) throws JSONException {
        put("RuleType", type);
        return this;
    }

    public int getStatus() {
        return optInt("Status");
    }

    public int getButtonStyle() {
        return optInt("ButtonStyle");
    }

    public int getButtonStyleV2() {
        return optInt("ButtonStyleV2", EMaintananceButtonStyleV2.HIDDEN.value);
    }

    public String getUrlV2(String language) {
        if (_urlV2 == null) {
            String text = optString("UrlV2", "");
            if (!TextUtils.isEmpty(text)) {
                try {
                    JSONObject jsonObject = new JSONObject(text);
                    _urlV2 = new EntriesText(jsonObject);
                } catch (Exception exc) {
//                    LogWriter.e(exc);
                }
            }
        }
        return getCurrentText(_urlV2, language);
    }

    public String getVersion() {
        return optString("Version", "");
    }

    public MaintenanceRule setVersion(String version) {
        try {
            put("Version", version);
        } catch (Exception ex) {
            //nothing to do
        }
        return this;
    }

    public String getStatusHeader() {
        String statusHeader = optString("StatusHeader", "");
        if (statusHeader.equals("null")) {
            return "";
        }
        return statusHeader;
    }

    public String getStatusHeaderV2(String language) {
        if (_statusHeaderV2 == null) {
            String text = optString("StatusHeaderV2", "");
            if (text.equals("null")) {
                text = "";
            }
            if (!TextUtils.isEmpty(text)) {
                try {
                    JSONObject jsonObject = new JSONObject(text);
                    _statusHeaderV2 = new EntriesText(jsonObject);
                } catch (Exception exc) {
//                    LogWriter.e(exc);
                }
            }
        }
        return getCurrentText(_statusHeaderV2, language);
    }

    public String getStatusMessage() {
        return optString("StatusMessage", "");
    }

    public String getStatusMessageV2(String language) {
        if (_statusMessageV2 == null) {
            String text = optString("StatusMessageV2", "");
            if (!TextUtils.isEmpty(text)) {
                try {
                    JSONObject jsonObject = new JSONObject(text);
                    _statusMessageV2 = new EntriesText(jsonObject);
                } catch (Exception exc) {
//                    LogWriter.e(exc);
                }
            }
        }
        return getCurrentText(_statusMessageV2, language);
    }

    public String getStatusInfo() {
        return optString("StatusInfo", "");
    }

    public String getButtonTitle() {
        return optString("ButtonTitle", "");
    }

    public String getButtonTitleV2(String language) {
        if (_buttonTitleV2 == null) {
            String text = optString("ButtonTitleV2", "");
            if (!TextUtils.isEmpty(text)) {
                try {
                    JSONObject jsonObject = new JSONObject(text);
                    _buttonTitleV2 = new EntriesText(jsonObject);
                } catch (Exception exc) {
//                    LogWriter.e(exc);
                }
            }
        }
        return getCurrentText(_buttonTitleV2, language);
    }


    public String getURLDetails() {
        return optString("URLDetails", "");
    }

    public String getExcludeIPs() {
        return optString("ExcludeIPs", "");
    }

    public String getButtonStyleName() {
        return optString("ButtonStyleName", "");
    }

    public String getButtonStyleNameV2() {
        return optString("ButtonStyleNameV2", "");
    }

    public String getRuleName() {
        return optString("RuleName", "");
    }

    public boolean isEnabled() {
        return optBoolean("IsEnabled", false);
    }

    public MaintenanceRule setEnabled(boolean state) {
        try {
            put("IsEnabled", state);
        } catch (Exception ex) {
            //nothing to do
        }
        return this;
    }

    public boolean isUpgrade() {
        return getRuleType() == RULE_UPGRADE || getRuleType() == RULE_PROTOCOL;
    }

    public boolean isMaintenance() {
        return getRuleType() == RULE_MAINTENANCE;
    }

    public boolean isInternet() {
        return getRuleType() == RULE_INTERNET_NOT_AVAILABLE;
    }

    public boolean isEmpty() {
        return getRuleType() == RULE_EMPTY;
    }

    public String getCurrentText(EntriesText entriesText, String language) {
        String result = "";

        if (entriesText != null) {
            if (!language.equals(LanguageHelper.getDefaultLanguage())) {
                result = entriesText.get(language);
            }
            if (TextUtils.isEmpty(result)) {
                result = entriesText.getDefault();
            }
        }

        return result;
    }
}
