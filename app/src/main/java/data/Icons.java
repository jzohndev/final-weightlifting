package data;

import android.support.v4.util.ArrayMap;

import com.example.jzohndev.no_bullshit_weightlifting_new.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import database.Exercise;

/**
 * Created by big1 on 9/8/2016.
 */
public class Icons {
    public static final int ABS = R.drawable.ic_muscle_groups_abs_brown;
    public static final int BACK = R.drawable.ic_muscle_group_back_lime;
    public static final int BICEPS = R.drawable.ic_muscle_group_biceps_teal;
    public static final int CARDIO = R.drawable.ic_muscle_group_cardio_pink;
    public static final int CHEST = R.drawable.ic_muscle_group_chest_yellow;
    public static final int FOREARMS = R.drawable.ic_muscle_groups_forearms_turqoise;
    public static final int LEGS = R.drawable.ic_muscle_groups_legs_turqoise;
    public static final int NECK = R.drawable.ic_muscle_groups_neck_green;
    public static final int SHOULDERS = R.drawable.ic_muscle_groups_shoulders_purple;
    public static final int TRICEPS = R.drawable.ic_muscle_groups_triceps_blue;

    public static final int ABS_DOT = R.drawable.ic_workout_dots_abs_brown;
    public static final int BACK_DOT = R.drawable.ic_workout_dots_back_lime;
    public static final int BICEPS_DOT = R.drawable.ic_workout_dots_biceps_teal;
    public static final int CARDIO_DOT = R.drawable.ic_workout_dots_cardio_pink;
    public static final int CHEST_DOT = R.drawable.ic_workout_dots_chest_yellow;
    public static final int FOREARMS_DOT = R.drawable.ic_workout_dots_forearms_torquise;
    public static final int LEGS_DOT = R.drawable.ic_workout_dots_legs_turqoise;
    public static final int NECK_DOT = R.drawable.ic_workout_dots_neck_green;
    public static final int SHOULDERS_DOT = R.drawable.ic_workout_dots_shoulders_purple;
    public static final int TRICEPS_DOT = R.drawable.ic_workout_dots_triceps_blue;

    public static int getMuscleGroupIcon(String muscleGroup) {
        muscleGroup = muscleGroup.toLowerCase();
        switch (muscleGroup) {
            case ("abs"):
                return ABS;
            case ("back"):
                return BACK;
            case ("biceps"):
                return BICEPS;
            case ("cardio"):
                return CARDIO;
            case ("chest"):
                return CHEST;
            case ("forearms"):
                return FOREARMS;
            case ("legs"):
                return LEGS;
            case ("neck"):
                return NECK;
            case ("shoulders"):
                return SHOULDERS;
            case ("triceps"):
                return TRICEPS;
            default:
                return -1;
        }
    }

    public static List<Integer> getWorkoutDots(List<Exercise> exercises) {
        Map<String, Integer> mExercises = new HashMap<>();
        mExercises.put("abs", 0);
        mExercises.put("back", 0);
        mExercises.put("biceps", 0);
        mExercises.put("cardio", 0);
        mExercises.put("chest", 0);
        mExercises.put("forearms", 0);
        mExercises.put("legs", 0);
        mExercises.put("neck", 0);
        mExercises.put("shoulders", 0);
        mExercises.put("triceps", 0);

        for (Exercise currentExercise : exercises) {
            switch (currentExercise.getMuscleGroup().toLowerCase()) {
                case ("abs"):
                    mExercises.put("abs", mExercises.get("abs") + 1);
                    break;
                case ("back"):
                    mExercises.put("back", mExercises.get("back") + 1);
                    break;
                case ("biceps"):
                    mExercises.put("biceps", mExercises.get("biceps") + 1);
                    break;
                case ("cardio"):
                    mExercises.put("cardio", mExercises.get("cardio") + 1);
                    break;
                case ("chest"):
                    mExercises.put("chest", mExercises.get("chest") + 1);
                    break;
                case ("forearms"):
                    mExercises.put("forearms", mExercises.get("forearms") + 1);
                    break;
                case ("legs"):
                    mExercises.put("legs", mExercises.get("legs") + 1);
                    break;
                case ("neck"):
                    mExercises.put("neck", mExercises.get("neck") + 1);
                    break;
                case ("shoulders"):
                    mExercises.put("shoulders", mExercises.get("shoulders") + 1);
                    break;
                case ("triceps"):
                    mExercises.put("triceps", mExercises.get("triceps") + 1);
                    break;
            }
        }

        return sortByValue(mExercises);

    }

    private static List<Integer> sortByValue(Map<String, Integer> originalMap) {
        List<Map.Entry<String, Integer>> sortedMapEntries =
                new ArrayList<>(originalMap.entrySet());

        Collections.sort(sortedMapEntries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });


        List<Integer> dotResourceIds = new LinkedList<>();
        int iterator = 0;
        for (Map.Entry<String, Integer> entry : sortedMapEntries) {
            if (iterator < 4) {
                switch (entry.getKey()) {
                    case ("abs"):
                        if (entry.getValue() == 0) {
                            dotResourceIds.add(0);
                        } else {
                            dotResourceIds.add(R.drawable.ic_workout_dots_abs_brown);
                        }
                        break;
                    case ("back"):
                        if (entry.getValue() == 0) {
                            dotResourceIds.add(0);
                        } else {
                            dotResourceIds.add(R.drawable.ic_workout_dots_back_lime);
                        }
                        break;
                    case ("biceps"):
                        if (entry.getValue() == 0) {
                            dotResourceIds.add(0);
                        } else {
                            dotResourceIds.add(R.drawable.ic_workout_dots_biceps_teal);
                        }
                        break;
                    case ("cardio"):
                        if (entry.getValue() == 0) {
                            dotResourceIds.add(0);
                        } else {
                            dotResourceIds.add(R.drawable.ic_workout_dots_cardio_pink);
                        }
                        break;
                    case ("chest"):
                        if (entry.getValue() == 0) {
                            dotResourceIds.add(0);
                        } else {
                            dotResourceIds.add(R.drawable.ic_workout_dots_chest_yellow);
                        }
                        break;
                    case ("forearms"):
                        if (entry.getValue() == 0) {
                            dotResourceIds.add(0);
                        } else {
                            dotResourceIds.add(R.drawable.ic_workout_dots_forearms_torquise);
                        }
                        break;
                    case ("legs"):
                        if (entry.getValue() == 0) {
                            dotResourceIds.add(0);
                        } else {
                            dotResourceIds.add(R.drawable.ic_workout_dots_legs_turqoise);
                        }
                        break;
                    case ("neck"):
                        if (entry.getValue() == 0) {
                            dotResourceIds.add(0);
                        } else {
                            dotResourceIds.add(R.drawable.ic_workout_dots_neck_green);
                        }
                        break;
                    case ("shoulders"):
                        if (entry.getValue() == 0) {
                            dotResourceIds.add(0);
                        } else {
                            dotResourceIds.add(R.drawable.ic_workout_dots_shoulders_purple);
                        }
                        break;
                    case ("triceps"):
                        if (entry.getValue() == 0) {
                            dotResourceIds.add(0);
                        } else {
                            dotResourceIds.add(R.drawable.ic_workout_dots_triceps_blue);
                        }
                        break;
                }
                iterator++;
            }
        }
        return dotResourceIds;
    }
}
