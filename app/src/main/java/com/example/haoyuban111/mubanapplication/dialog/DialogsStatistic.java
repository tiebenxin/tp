package com.example.haoyuban111.mubanapplication.dialog;

import android.app.Dialog;

public class DialogsStatistic {

    private int _showDialogsCount;

    public static final DialogsStatistic INSTANCE = new DialogsStatistic();

    private DialogsStatistic() {

    }

    public int getShowDialogsCount() {
        return _showDialogsCount;
    }

    public void startShowDialog(Dialog dialog){
        _showDialogsCount++;
    }

    public void stopShowDialog(Dialog dialog){
        _showDialogsCount--;
    }
}
