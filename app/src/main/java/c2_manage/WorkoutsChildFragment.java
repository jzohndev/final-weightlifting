package c2_manage;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import data.IntentResolver;
import database.DatabaseHelper;
import database.Workout;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutsChildFragment extends Fragment implements WorkoutsAdapter.WorkoutOnItemClickListener{
    private View mView;
    private WorkoutsAdapter mAdapter;

    public WorkoutsChildFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_workouts_child, container, false);

        mAdapter = new WorkoutsAdapter(getContext());
        mAdapter.setOnItemClickListener(this);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        final RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        return mView;
    }

    @Override
    public void onItemClick(final Workout workout, View v) {
        if (v != null && v.getTag() == "overflow"){
            PopupMenu popupMenu = new PopupMenu(getContext(), v);
            popupMenu.getMenuInflater()
                    .inflate(R.menu.workout_card_overflow_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    final AlertDialog.Builder confirmation
                            = new AlertDialog.Builder(getContext());
                    confirmation.setMessage("Delete this workout?");
                    confirmation.setCancelable(true);
                    confirmation.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseHelper db = new DatabaseHelper(getContext());
                                    db.deleteWorkout(workout.getId());
                                    mAdapter.updateDataSet();
                                    dialog.cancel();
                                }
                            });
                    confirmation.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog popup = confirmation.create();
                    popup.show();
                    return true;
                }
            });
            popupMenu.show();
        } else {
            final IntentResolver resolver = IntentResolver.getInstance();
            resolver.setIntent("WorkoutsChildFragment", "WorkoutsChildFragment", -1, workout);
            startActivity(new Intent(getActivity(), WorkoutDetail.class));
        }
    }
}
