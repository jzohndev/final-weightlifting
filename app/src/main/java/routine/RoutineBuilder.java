package routine;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import c0_all.MenuPager;
import workout.WorkoutBuilderHelper;

import java.text.SimpleDateFormat;
import java.util.List;

import database.DatabaseHelper;
import database.Workout;
import workout.WorkoutList;

/**
 * Created by big1 on 7/21/2016.
 */
public class RoutineBuilder extends Activity {
    private WorkoutBuilderHelper mHelper;

    private ActionBar actionBar;
    private FloatingActionButton fab;
    private Bundle objective;
    // Recycler View stuff
    private RecyclerView recyclerV;
    private LinearLayoutManager lManager;
    private RoutineAdapter adapter;
    private EditText editRoutineName;
    private View defaultBar, editBar;
    private ImageView cancelImageV, editImageV, finishImageV;
    private TextView routineNameTextV, notesTextV, cancelTextV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = WorkoutBuilderHelper.getInstance();

        setContentView(R.layout.layout_manage_routine_builder);
        actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        RoutineBuilderHelper.setDefaultRoutineName(getApplicationContext());
        setActionBarDefault();
        updateFabIcon();
        //Recycler View stuff
        recyclerV = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerV.setHasFixedSize(true);
        lManager = new LinearLayoutManager(this);
        lManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerV.setLayoutManager(lManager);
        adapter = new RoutineAdapter();
        recyclerV.setAdapter(adapter);
    }


    public void updateFabIcon() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        if (RoutineBuilderHelper.getWorkoutList().size() < 1) {
            fab.setImageDrawable(getResources().getDrawable(android.R.drawable.checkbox_off_background));
            fab.setClickable(false);
            return;
        }
        fab.setImageDrawable(getResources().getDrawable(android.R.drawable.checkbox_on_background));
        fab.setClickable(true);
    }

    private void setActionBarDefault() {
        defaultBar = getLayoutInflater().inflate(R.layout.view_workout_builder_default_bar, null);
        actionBar.setCustomView(defaultBar);
        Toolbar parent = (Toolbar) defaultBar.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        parent.setPadding(0, 0, 0, 0);
        cancelImageV = (ImageView) actionBar.getCustomView().findViewById(R.id.cancel_image_view);
        cancelImageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //TODO
            }
        });
        routineNameTextV = (TextView) actionBar.getCustomView().findViewById(R.id.name_text_view);
        routineNameTextV.setText(RoutineBuilderHelper.getRoutineName());

        editImageV = (ImageView) actionBar.getCustomView().findViewById(R.id.edit_image_view);
        editImageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActionBarEdit();
            }
        });
        notesTextV = (TextView) actionBar.getCustomView().findViewById(R.id.notes_text_view);
        notesTextV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView finishTextV, cancelTextV, deleteTextV;
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final View notesView = inflater.inflate(R.layout.view_popup_description, null);
                final EditText descriptionEditT = (EditText) notesView.findViewById(R.id.description_edit_text);
                descriptionEditT.setFocusable(true);
                final PopupWindow popup = new PopupWindow(notesView,
                        RecyclerView.LayoutParams.WRAP_CONTENT,
                        RecyclerView.LayoutParams.WRAP_CONTENT);
                if (mHelper.getWorkoutDescription() != null) {
                    String description = RoutineBuilderHelper.getRoutineDescription();
                    descriptionEditT.setText(description);
                    int textLength = description.length();
                    descriptionEditT.setSelection(textLength, textLength);
                }
               /* deleteTextV = (TextView) notesView.findViewById(R.id.delete_textv);
                deleteTextV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        descriptionEditT.setText("");
                    }
                });*/
                /*finishTextV = (TextView) notesView.findViewById(R.id.finish_textv);
                finishTextV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RoutineBuilderHelper.setRoutineDescription(descriptionEditT.getText().toString());
                        popup.dismiss();
                    }
                });
                cancelTextV = (TextView) notesView.findViewById(R.id.cancel_textv);
                cancelTextV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popup.dismiss();
                    }
                });*/
                CoordinatorLayout coord = (CoordinatorLayout) findViewById(R.id.coordinator);
                popup.setFocusable(true);
                popup.showAtLocation(coord, Gravity.CENTER, 0, 0);
            }
        });
    }

    private void setActionBarEdit() {
        editBar = getLayoutInflater().inflate(R.layout.view_workout_builder_edit_bar, null);
        actionBar.setCustomView(editBar);
        Toolbar parent = (Toolbar) editBar.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        parent.setPadding(0, 0, 0, 0);
        finishImageV = (ImageView) actionBar.getCustomView().findViewById(R.id.accept_image_view);
        editRoutineName = (EditText) actionBar.getCustomView().findViewById(R.id.workout_name_edit_text);
        editRoutineName.setText(mHelper.getWorkoutName());
        editRoutineName.setFocusable(true);
        finishImageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager =
                        (InputMethodManager) getApplicationContext().
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(
                        getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                RoutineBuilderHelper.setRoutineName(editRoutineName.getText().toString());
                setActionBarDefault();
            }
        });/*
        cancelTextV = (TextView) actionBar.getCustomView().findViewById(R.id.cancel_textv);*/
        cancelTextV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActionBarDefault();
            }
        });
    }

    public void finishPressed(View v) {
        boolean routineCreated = RoutineBuilderHelper.createRoutine(this);
        if (routineCreated) {
            Intent i = new Intent(RoutineBuilder.this, MenuPager.class);
            objective = new Bundle();
            objective.putString("from", "RoutineBuilder");
            objective.putString("orgFrom", "TODO");
            objective.putLong("purpose", -1);
            i.putExtras(objective);
            startActivity(i);
        }
    }

    public class RoutineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int FOOTER_VIEW = 1;
        public List<Workout> workouts;
        private DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        public RoutineAdapter() {
            updateDataSet();
        }

        private void updateDataSet() {
            updateFabIcon();
            workouts = RoutineBuilderHelper.getWorkoutList();
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if (workouts.size() == 0) return 1;
            return workouts.size() + 1;
        }

        @Override
        public long getItemId(int position) {
            return workouts.get(position).getId();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v;
            if (viewType == FOOTER_VIEW) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_footer_add_workout, parent, false);
                return new FooterViewHolder(v);
            }
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout_card, parent, false);
            return new WorkoutViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            try {
                if (holder instanceof WorkoutViewHolder) {
                    Workout workout = workouts.get(position);
                    WorkoutViewHolder wHolder = (WorkoutViewHolder) holder;
                    wHolder.name.setText(workout.getName());
                    wHolder.exercises.setText(String.valueOf(db.getWorkoutExerciseNumberOf(workout.getId())));
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
                    String createdDate = formatter.format(workouts.get(position).getCreatedDate());
                    wHolder.date.setText(createdDate);
                    int delete = (int) workouts.get(position).getId();
                    wHolder.delete.setId(delete);
                } else if (holder instanceof FooterViewHolder) {
                    FooterViewHolder vh = (FooterViewHolder) holder;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemViewType(int position) {
            if (position == workouts.size()){
                return FOOTER_VIEW;
            }
            return super.getItemViewType(position);
        }

        public class WorkoutViewHolder extends RecyclerView.ViewHolder {
            protected TextView name, exercises, date;
            protected ImageView delete;

            public WorkoutViewHolder(View v) {
                super(v);
                name = (TextView) v.findViewById(R.id.workout_name_textview);
                exercises = (TextView) v.findViewById(R.id.exercises_number_textview);
                delete = (ImageView) v.findViewById(R.id.overflow_menu_image_view);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RoutineBuilderHelper.removeWorkout(delete.getId());
                        updateDataSet();
                    }
                });
            }
        }

        public class FooterViewHolder extends RecyclerView.ViewHolder {

            public FooterViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(RoutineBuilder.this, WorkoutList.class);
                        objective = new Bundle();
                        objective.putString("from", "RoutineBuilder");
                        objective.putString("orgFrom", "RoutineBuilder");
                        objective.putLong("purpose", -1);
                        i.putExtras(objective);
                        startActivity(i);
                    }
                });
            }
        }
    }
}
