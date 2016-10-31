package c4_progression;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import exercise.ExerciseListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExercisesChildFragment extends Fragment {
    private View mView;
    private SearchView mSearchView;
    private ListView mListView;
    private SearchView.OnQueryTextListener mSearchListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String searchQuery) {
            mAdapter.filterSearch(searchQuery.trim());
            return true;
        }
    };
    private AdapterView.OnItemSelectedListener mMuscleGroupListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selection = (String) mMuscleGroupSpinner.getSelectedItem();
            mAdapter.filterMuscleGroup(selection);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private ExerciseListAdapter mAdapter;
    private Spinner mMuscleGroupSpinner;

    public ExercisesChildFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_exercises_child, container, false);
        mListView = (ListView) mView.findViewById(R.id.list_view);
        mAdapter = new ExerciseListAdapter(getParentFragment().getActivity());
        mListView.setAdapter(mAdapter);
        searchView();
        spinner();
        return mView;
    }

    private void searchView() {
        mSearchView = (SearchView) mView.findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(mSearchListener);
    }

    private void spinner() {
        ArrayAdapter<CharSequence> spinnerAdapter =
                ArrayAdapter.createFromResource(getContext(),
                        R.array.workout_muscle_groups,
                        R.layout.builder_spinner_selection);

        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mMuscleGroupSpinner = (Spinner) mView.findViewById(R.id.muscle_group_spinner);
        mMuscleGroupSpinner.setOnItemSelectedListener(mMuscleGroupListener);
        mMuscleGroupSpinner.setAdapter(spinnerAdapter);
    }
}
