package exercise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import data.IntentResolver;
import workout.WorkoutBuilder;
import workout.WorkoutBuilderHelper;

import database.DatabaseHelper;

/**
 * Created by big1 on 7/20/2016.
 */
public class ExerciseList extends Activity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_exercises);

        final ExerciseListAdapter adapter = new ExerciseListAdapter(this);
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(adapter);
        resolveCallingPurpose();
    }

    private void resolveCallingPurpose() {
        final IntentResolver resolver = IntentResolver.getInstance();
        final WorkoutBuilderHelper helper = WorkoutBuilderHelper.getInstance();
        final DatabaseHelper db = new DatabaseHelper(this);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (resolver.getFrom()) {

                    case ("WorkoutBuilder"):
                        helper.addExercise(db.getExercise(id));
                        resolver.setIntent("ExerciseList", "WorkoutBuilder", -1);
                        startActivity(new Intent(ExerciseList.this, WorkoutBuilder.class));
                }
            }
        });
    }
}
