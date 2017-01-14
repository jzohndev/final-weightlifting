package workout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import java.util.List;

import data.IntentResolver;
import database.Exercise;
import exercise.ExerciseList;
import c0_all.MenuPager;

/**
 * Created by big1 on 7/19/2016.
 */
public class WorkoutBuilder extends Activity {
    private WorkoutBuilderHelper mHelper;

    private FrameLayout mFrameLayout;

    private Toolbar mToolbar;
    private View mDefaultToolbar;
    private View mEditToolbar;

    private Bundle objective;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_manage_workout_builder);

        initializeResources();
        initializeToolbar();
        updateFab();
        initializeRecyclerView();
    }

    private void initializeResources() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mFrameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        mFrameLayout.getForeground().setAlpha(0);
        mHelper = WorkoutBuilderHelper.getInstance();
        mHelper.initializeDefaultWorkoutName(getApplicationContext());
    }

    private void initializeToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mDefaultToolbar = getLayoutInflater().inflate(R.layout.view_workout_builder_default_bar, null);
        mEditToolbar = getLayoutInflater().inflate(R.layout.view_workout_builder_edit_bar, null);
        setActionBarDefault();
    }

    public void updateFab() {
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if (mHelper.getExerciseList().size() < 1) {
            fab.setAlpha((float) 0.3);
            fab.setClickable(false);
            return;
        }
        fab.setAlpha((float) 1);
        fab.setClickable(true);
    }

    private void initializeRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        final ExerciseAdapter adapter = new ExerciseAdapter();

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    private void setActionBarDefault() {
        getWindow().setStatusBarColor(getResources().getColor(R.color.green));
        mToolbar.removeAllViews();
        mToolbar.addView(mDefaultToolbar);
        final ImageView ivCancel = (ImageView) mDefaultToolbar.findViewById(R.id.cancel_image_view);
        final TextView tvWorkoutName = (TextView) mDefaultToolbar.findViewById(R.id.name_text_view);
        final ImageView ivEdit = (ImageView) mDefaultToolbar.findViewById(R.id.edit_image_view);
        final TextView tvNotes = (TextView) mDefaultToolbar.findViewById(R.id.notes_text_view);

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentResolver resolver = IntentResolver.getInstance();
                mHelper.finish();
                resolver.setIntent("WorkoutBuilder", "WorkoutBuilder", -1);
                startActivity(new Intent(getApplicationContext(), MenuPager.class));
            }
        });
        tvWorkoutName.setText(mHelper.getWorkoutName());
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActionBarEdit();
            }
        });
        tvNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNotesPopup();
            }
        });
    }

    private void setActionBarEdit() {
        getWindow().setStatusBarColor(getResources().getColor(R.color.darkGrey));
        mToolbar.removeAllViews();
        mToolbar.addView(mEditToolbar);
        final ImageView ivAccept = (ImageView) mEditToolbar.findViewById(R.id.accept_image_view);
        final EditText etWorkoutName = (EditText) mEditToolbar.findViewById(R.id.workout_name_edit_text);
        final Button btnCancel = (Button) mEditToolbar.findViewById(R.id.cancel_button);

        ivAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager =
                        (InputMethodManager) getApplicationContext().
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(
                        getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                mHelper.setWorkoutName(etWorkoutName.getText().toString());
                setActionBarDefault();
            }
        });
        etWorkoutName.setText(mHelper.getWorkoutName());
        etWorkoutName.setFocusable(true);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActionBarDefault();
            }
        });
    }

    private void createNotesPopup() {
        final View popupLayout = getLayoutInflater().inflate(R.layout.popup_description, null);
        final PopupWindow popup = new PopupWindow(popupLayout,
                RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);
        popup.setElevation(24);

        final EditText descriptionEditText =
                (EditText) popupLayout.findViewById(R.id.description_edit_text);

        if (mHelper.getWorkoutDescription() != null) {
            final String description = mHelper.getWorkoutDescription();
            descriptionEditText.setText(description);
            final int descriptionTextLength = description.length();
            descriptionEditText.setSelection(descriptionTextLength,
                    descriptionTextLength);
        }

        final Button cancelButton = (Button) popupLayout.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFrameLayout.getForeground().setAlpha(0);
                popup.dismiss();
            }
        });
        final Button clearButton = (Button) popupLayout.findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descriptionEditText.setText("");
            }
        });
        final Button acceptButton = (Button) popupLayout.findViewById(R.id.accept_button);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.setWorkoutDescription(descriptionEditText.getText().toString());
                mFrameLayout.getForeground().setAlpha(0);
                popup.dismiss();
            }
        });
        mFrameLayout.getForeground().setAlpha(120);
        popup.showAtLocation(mFrameLayout, Gravity.CENTER, 0, 0);
    }


    public void finishPressed(View v) {
        boolean workoutCreated = mHelper.createWorkout(this);
        if (workoutCreated) {
            mHelper.finish();
            IntentResolver resolver = IntentResolver.getInstance();
            resolver.setIntent("WorkoutBuilder", "WorkoutsChildFragment", -1);
            startActivity(new Intent(WorkoutBuilder.this, MenuPager.class));
        }
    }

    // Class
    public class ExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int FOOTER_VIEW = 1;
        public List<Exercise> exercises;

        public ExerciseAdapter() {
            updateDataSet();
        }

        private void updateDataSet() {
            updateFab();
            exercises = mHelper.getExerciseList();
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            if (exercises.size() == 0) return 1;
            return exercises.size() + 1;
        }

        @Override
        public long getItemId(int position) {
            return exercises.get(position).getId();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v;
            if (viewType == FOOTER_VIEW) {
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.footer_add_exercise, parent, false);
                return new FooterViewHolder(v);
            }
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.exercise_card_reps_sets, parent, false);
            return new ExerciseViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            try {
                if (holder instanceof ExerciseViewHolder) {
                    final Exercise currentExercise = exercises.get(position);
                    final ExerciseViewHolder eHolder = (ExerciseViewHolder) holder;

                    eHolder.name.setText(exercises.get(position).getName());
                    if (currentExercise.getDefaultSets() != -1) {
                        eHolder.spnSets.setSelection((int) currentExercise.getDefaultSets());
                    }
                    eHolder.spnSets.setOnItemSelectedListener(
                            new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                    if (position != 0 && position != currentExercise.getDefaultSets()) {
                                        mHelper.updateSets(currentExercise.getId(), position);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                    if (currentExercise.getDefaultReps() != -1) {
                        eHolder.spnReps.setSelection((int) currentExercise.getDefaultReps());
                    }
                    eHolder.spnReps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            if (position != 0 && position != currentExercise.getDefaultReps()) {
                                mHelper.updateReps(currentExercise.getId(), position);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    int delete = (int) exercises.get(position).getId();
                    eHolder.delete.setId(delete);

                } else if (holder instanceof FooterViewHolder) {
                    FooterViewHolder vh = (FooterViewHolder) holder;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == exercises.size()) {
                return FOOTER_VIEW;
            }
            return super.getItemViewType(position);
        }


        // Class
        public class ExerciseViewHolder extends RecyclerView.ViewHolder {
            protected TextView name;
            protected Spinner spnSets;
            protected Spinner spnReps;
            protected ImageView delete;

            public ExerciseViewHolder(View v) {
                super(v);

                name = (TextView) v.findViewById(R.id.exercise_name_text_view);

                spnSets = (Spinner) v.findViewById(R.id.sets_spinner);
                final ArrayAdapter<CharSequence> setAdapter =
                        ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.sets_array,
                                R.layout.view_spinner_selection);
                setAdapter.setDropDownViewResource(R.layout.view_spinner_text_view);
                spnSets.setAdapter(setAdapter);

                spnReps = (Spinner) v.findViewById(R.id.reps_spinner);
                ArrayAdapter<CharSequence> repAdapter =
                        ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.reps_array,
                                R.layout.view_spinner_selection);
                repAdapter.setDropDownViewResource(R.layout.view_spinner_text_view);
                spnReps.setAdapter(repAdapter);

                delete = (ImageView) v.findViewById(R.id.overflow_menu_image_view);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mHelper.removeExercise(delete.getId());
                        updateDataSet();
                    }
                });
            }
        }

        // Class
        public class FooterViewHolder extends RecyclerView.ViewHolder {

            public FooterViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        IntentResolver resolver = IntentResolver.getInstance();
                        resolver.setIntent("WorkoutBuilder", "WorkoutBuilder", -1);
                        startActivity(new Intent(WorkoutBuilder.this, ExerciseList.class));
                    }
                });
            }
        }
    }
}
