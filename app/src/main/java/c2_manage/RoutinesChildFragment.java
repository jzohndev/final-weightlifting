package c2_manage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoutinesChildFragment extends Fragment {


    public RoutinesChildFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_routines_child, container, false);
    }

}
