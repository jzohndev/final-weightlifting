package exercise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import data.Icons;
import database.DatabaseHelper;
import database.Exercise;

/**
 * Created by big1 on 7/20/2016.
 */
public class ExerciseListAdapter extends BaseAdapter {
    private List<Exercise> exercises;
    private List<Exercise> muscleGroupExercises;
    private DatabaseHelper db;
    private Context context;

    public ExerciseListAdapter(Context context) {
        this.context = context;
        db = new DatabaseHelper(context);
        updateDataSet();
    }

    private void updateDataSet() {
        exercises = new ArrayList<>();
        muscleGroupExercises = new ArrayList<>();
        exercises.addAll(db.getAllExercises());
        muscleGroupExercises.addAll(muscleGroupExercises);
    }

    @Override
    public int getCount() {
        if (exercises == null) {
            return 0;
        } else if (exercises.size() == 0) {
            return 0;
        } else return exercises.size();
    }

    @Override
    public Object getItem(int position) {
        return exercises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return exercises.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exercise_list, null);
        }
        final TextView exerciseName = (TextView) convertView.findViewById(R.id.exercise_name_text_view);
        exerciseName.setText(exercises.get(position).getName());
        final ImageView icon = (ImageView) convertView.findViewById(R.id.icon_image_view);
        icon.setImageResource(Icons.getMuscleGroupIcon(exercises.get(position).getMuscleGroup()));
        final TextView muscleGroup = (TextView) convertView.findViewById(R.id.muscle_group_text_view);
        muscleGroup.setText(exercises.get(position).getMuscleGroup());
        return convertView;
    }

    public void filterMuscleGroup(String muscleGroup){
        if (muscleGroup.contains("All Muscles")){
            muscleGroupExercises.clear();
            exercises.clear();
            muscleGroupExercises.addAll(db.getAllExercises());
            exercises.addAll(muscleGroupExercises);
        } else {
            muscleGroupExercises.clear();
            exercises.clear();
            muscleGroupExercises = db.getExercisesMuscleGroup(muscleGroup);
            exercises.addAll(muscleGroupExercises);

        }
        notifyDataSetChanged();
    }

    public void filterSearch(String searchQuery){
        searchQuery = searchQuery.toLowerCase(Locale.getDefault());
        exercises.clear();
        if (searchQuery.length() == 0) {
            exercises.addAll(muscleGroupExercises);
        } else {
            for (Exercise postDetail : muscleGroupExercises) {
                if (searchQuery.length() != 0 && postDetail.getName().toLowerCase().contains(searchQuery)) {
                    exercises.add(postDetail);
                }
            }
        }
        notifyDataSetChanged();
    }
}
