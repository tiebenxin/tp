package com.example.haoyuban111.mubanapplication.ui.fragment;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.example.haoyuban111.mubanapplication.R;
import com.example.haoyuban111.mubanapplication.dialog.DialogContainer;
import com.example.haoyuban111.mubanapplication.dialog.DialogExtended;
import com.example.haoyuban111.mubanapplication.interfac.IProgressLoad;

/**
 * Created by haoyuban111 on 2017/3/14.
 */

public class FragmentBase extends Fragment implements IProgressLoad {

    private DialogContainer dialogContainer;

    @Override
    public void show() {
        if (dialogContainer == null) {
            dialogContainer = showProcess();
        }
    }

    @Override
    public void dismiss() {
        if (dialogContainer != null && dialogContainer.isShowing()) {
            dialogContainer.dismiss();
        }
    }

    protected DialogContainer showProcess() {
        DialogExtended dialog = new DialogExtended(getActivity(), R.style.dialogBackWhiteTransparency);
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.layout_progress_sprite, null);
        View view = layout.findViewById(R.id.view);
        RotateAnimation animation = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lir = new LinearInterpolator();
        animation.setInterpolator(lir);
        animation.setRepeatCount(-1);
        animation.setDuration(1500);
        view.setAnimation(animation);
        dialog.setContentView(layout);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        DialogContainer result = new DialogContainer(dialog);
        result.show();
        return result;
    }
}
