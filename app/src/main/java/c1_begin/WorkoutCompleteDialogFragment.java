package c1_begin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.view.ContextThemeWrapper;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import c0_all.MenuPager;
import c0_all.OnRequestNavigationToFragmentListener;
import data.IntentResolver;

/**
 * Created by jzohn on 12/16/2016.
 */

public class WorkoutCompleteDialogFragment extends DialogFragment {
    OnRequestNavigationToFragmentListener mCallback;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mCallback = (OnRequestNavigationToFragmentListener) getContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(
                new ContextThemeWrapper(getActivity(), R.style.WorkoutCompleteCustomDialog));
        builder.setMessage(R.string.workout_complete_dialog_message)
                .setPositiveButton(R.string.workout_complete_dialog_positive_button,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mCallback.onFragmentNavigationRequested(3);
                            }
                        })
                .setNegativeButton(R.string.workout_complete_dialog_negative_button,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
        return builder.create();
    }
}
