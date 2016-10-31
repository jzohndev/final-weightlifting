package c2_manage;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import java.util.List;

import data.IntentResolver;
import database.DatabaseHelper;
import database.Exercise;
import database.Workout;

/**
 * Created by big1 on 8/11/2016.
 */
public class WorkoutDetail extends Activity {
    private Workout mWorkout;
    private WorkoutDetailsAdapter mAdapter;
    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveCallingPurpose();
        setContentView(R.layout.activity_workout_detail);
        mFrameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        mFrameLayout.getForeground().setAlpha(0);
        setTitle();
        backButton();
        initializeRecyclerView();
    }

    private void resolveCallingPurpose() {
        final IntentResolver resolver = IntentResolver.getInstance();
        mWorkout = resolver.getWorkout();
        //TODO if/and
    }

    private void setTitle() {
        final TextView title = (TextView) findViewById(R.id.workout_name_text_view);
        title.setText(mWorkout.getName());
    }

    private void backButton() {
        final ImageButton backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                /*startActivity(new Intent(WorkoutDetail.this, ));*/
            }
        });
    }

    private void initializeRecyclerView() {
        mAdapter = new WorkoutDetailsAdapter();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }


    // Class
    public class WorkoutDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Exercise> mExercises;
        private final int DESCRIPTION_SUBHEADING = 1;
        private final int DESCRIPTION_TEXT_VIEW = 2;
        private final int DIVIDER = 3;
        private final int EXERCISES_SUBHEADING = 4;
        private final int EXERCISE_LIST_ITEM = 5;

        private final int ADDITIONAL_VIEWS = 4;

        public WorkoutDetailsAdapter() {
            updateDataSet();
        }

        private void updateDataSet() {
            final DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            mExercises = db.getWorkoutExercises(mWorkout.getId());
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v;
            switch (viewType) {
                case (DESCRIPTION_SUBHEADING):
                    v = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.description_text_view, parent, false);
                    return new SubheadingViewHolder(v);
                case (DESCRIPTION_TEXT_VIEW):
                    v = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.workout_description_text_view, parent, false);
                    return new DescriptionViewHolder(v);
                case (DIVIDER):
                    v = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.divider, parent, false);
                    return new SubheadingViewHolder(v);
                case (EXERCISES_SUBHEADING):
                    v = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.exercises_text_view, parent, false);
                    return new SubheadingViewHolder(v);
            }
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.exercise_list, parent, false);
            return new ExercisesViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof DescriptionViewHolder) {
                final DescriptionViewHolder mHolder = (DescriptionViewHolder) holder;
                if (mWorkout.getDescription() != null && mWorkout.getDescription().contains("")) {
                    mHolder.description.setText(mWorkout.getDescription());
                }
            } else if (holder instanceof ExercisesViewHolder) {
                final ExercisesViewHolder mHolder = (ExercisesViewHolder) holder;
                final int exercisePosition = position - ADDITIONAL_VIEWS;
                final Exercise currentExercise = mExercises.get(exercisePosition);
                // TODO mHolder.icon
                mHolder.name.setText(currentExercise.getName());
                mHolder.muscleGroup.setText(currentExercise.getMuscleGroup());
            }
        }

        @Override
        public int getItemCount() {
            final int totalExercises = mExercises.size();

            return (ADDITIONAL_VIEWS + totalExercises);
        }

        @Override
        public int getItemViewType(int position) {
            switch (position) {
                case (0):
                    return DESCRIPTION_SUBHEADING;
                case (1):
                    return DESCRIPTION_TEXT_VIEW;
                case (2):
                    return DIVIDER;
                case (3):
                    return EXERCISES_SUBHEADING;
                default:
                    return EXERCISE_LIST_ITEM;
            }
        }
    }

    // Class
    public class SubheadingViewHolder extends RecyclerView.ViewHolder {

        public SubheadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    // Class
    public class DescriptionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView description;

        public DescriptionViewHolder(View v) {
            super(v);
            description = (TextView) v.findViewById(R.id.description_edit_text);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            final View popupLayout = getLayoutInflater().inflate(R.layout.popup_description, null);
            final PopupWindow popup = new PopupWindow(popupLayout,
                    RecyclerView.LayoutParams.WRAP_CONTENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT);
            popup.setFocusable(true);
            popup.setElevation(24);

            final EditText descriptionEditText =
                    (EditText) popupLayout.findViewById(R.id.description_edit_text);

            if (mWorkout.getDescription() != null) {
                final String description = mWorkout.getDescription();
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
                    mWorkout.setDescription(descriptionEditText.getText().toString());
                    db.updateWorkout(mWorkout);
                    mAdapter.notifyDataSetChanged();
                    mFrameLayout.getForeground().setAlpha(0);
                    popup.dismiss();
                }
            });
            mFrameLayout.getForeground().setAlpha(120);
            popup.showAtLocation(mFrameLayout, Gravity.CENTER, 0, 0);
        }
    }

    // Class
    public class ExercisesViewHolder extends RecyclerView.ViewHolder {
        protected ImageView icon;
        protected TextView name;
        protected TextView muscleGroup;

        public ExercisesViewHolder(View v) {
            super(v);
            icon = (ImageView) v.findViewById(R.id.icon_image_view);
            name = (TextView) v.findViewById(R.id.exercise_name_text_view);
            muscleGroup = (TextView) v.findViewById(R.id.muscle_group_text_view);
        }
    }
}
