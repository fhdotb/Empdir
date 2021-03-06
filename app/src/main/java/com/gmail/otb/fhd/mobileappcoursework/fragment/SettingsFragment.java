package com.gmail.otb.fhd.mobileappcoursework.fragment;

/**
 * Created by fahadalms3odi on 4/14/18.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gmail.otb.fhd.mobileappcoursework.R;
import com.tapadoo.alerter.Alerter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public View logout;
    public View update;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // if (savedInstanceState != null)
         //   numberOfPager = savedInstanceState.getInt(KEY_PAGER_PAGE);

        initGui();
    }

    private void initGui() {

        logout = getView().findViewById(R.id.layout_logout);
        logout.setOnClickListener(this);

        update = getView().findViewById(R.id.layout_update);
        update.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.layout_logout:
                Alerter.create(getActivity())
                        .setText("Logout")
                        .setIcon(R.drawable.ic_lock_outline_black_24dp)
                        .setIconColorFilter(0)
                        .show();

//                PreferenceManager.getDefaultSharedPreferences().
//                        edit().clear().apply();

                    break;
                    case R.id.layout_update:
                        Alerter.create(getActivity())
                                .setText("update")
                                .setIcon(R.drawable.ic_lock_outline_black_24dp)
                                .setIconColorFilter(0)
                                .show();
                        break;

                }


        }



}

