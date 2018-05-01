package com.gmail.otb.fhd.mobileappcoursework.fragment;

/**
 * Created by fahadalms3odi on 4/14/18.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.gmail.otb.fhd.mobileappcoursework.NetworkLayer.API;
import com.gmail.otb.fhd.mobileappcoursework.NetworkLayer.ApiClient;
import com.gmail.otb.fhd.mobileappcoursework.R;
import com.gmail.otb.fhd.mobileappcoursework.adapters.ProfileAdapter;
import com.gmail.otb.fhd.mobileappcoursework.model.Employee;
import com.gmail.otb.fhd.mobileappcoursework.model.EmployeeOffice;
import com.gmail.otb.fhd.mobileappcoursework.model.JsonResponse;
import com.gmail.otb.fhd.mobileappcoursework.utills.ActivityManager;
import com.gmail.otb.fhd.mobileappcoursework.utills.DividerItemDecoration;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener , ProfileAdapter.MessageAdapterListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "userEmail";
    private static final String ARG_PARAM2 = "OfficeID";
    private static final String ARG_PARAM3 = "supervisor";


    // TODO: Rename and change types of parameters


    private MaterialSearchView searchView;
    private FloatingActionButton fab;

    private EmployeeOffice[] offices;
    List<EmployeeOffice> listoffices;
    private RecyclerView recyclerView;
    private ProfileAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Context context;
    private List<Employee> employeesInOneOffice =new ArrayList<Employee>();
    private List<Employee> EmployeesList = new ArrayList<Employee>();
    private Map<String, List<Employee> > employees = new HashMap<String, List<Employee> >();

    private String userEmail;
    private String OfficeID;
    private String supervisor;
    private String building;
    private FrameLayout layout_fab;



    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            userEmail = getArguments().getString(ARG_PARAM1);
            OfficeID = getArguments().getString(ARG_PARAM2);
            supervisor = getArguments().getString(ARG_PARAM3);
            building = getArguments().getString("building");
            Log.d("userEmail===",userEmail);
            Log.d("OfficeID===",OfficeID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // if (savedInstanceState != null)
        //   numberOfPager = savedInstanceState.getInt(KEY_PAGER_PAGE);
        initGui();
    }

    private void initGui() {

        context = this.getActivity();
        layout_fab = getActivity().findViewById(R.id.fap_container);

        if(supervisor.trim().equals("1"))
        {

            fab = (FloatingActionButton) layout_fab.findViewById(R.id.fab);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Alerter.create(getActivity())
                            .setText("Add employee")
                            .setIcon(R.drawable.ic_note_add_black_24dp)
                            .setIconColorFilter(Color.DKGRAY)
                            .show();
                }
            });

            layout_fab.setVisibility(View.VISIBLE);
        }
        else
            layout_fab.setVisibility(View.GONE);




        int top_to_padding=500;
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setProgressViewOffset(false, 0,top_to_padding);



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), LinearLayoutManager.VERTICAL));

        // show loader and fetch messages



        swipeRefreshLayout.measure(View.MEASURED_SIZE_MASK,View.MEASURED_HEIGHT_STATE_SHIFT);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                getEmployeeProfile();
            }
        }, 1000);



        FrameLayout layout = getActivity().findViewById(R.id.toolbar_container);
        searchView = (MaterialSearchView) layout.findViewById(R.id.search_view);


    }


    private void search(MaterialSearchView searchView) {


        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.color_cursor_white);
        //searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    @Override
    public void onRefresh() {
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                getEmployeeProfile();
            }
        }, 1000);



    }


    @Override
    public void onIconClicked(int position) {
        }

    @Override
    public void onIconImportantClicked(int position) {

    }

    @SuppressLint("LongLogTag")
    @Override
    public void onMessageRowClicked(int position) {


        String userEmail  = EmployeesList.get(position).getEmail();
        String  OfficeID = this.OfficeID;
        String photo = EmployeesList.get(position).getPhoto();
        String jobTitle = EmployeesList.get(position).getRole().getJobTitle();
        String supervisor = this.supervisor;
        String name = EmployeesList.get(position).getFirstName() +" "+ EmployeesList.get(position).getLastName();
        String manager = "F";
        String phone = EmployeesList.get(position).getMobileNamber();
        String building = this.building;

        for (Employee e :EmployeesList )
        {
            if (e.getRole().getSupervisor().trim().equals("1"))
                manager = e.getFirstName() +" "+ e.getLastName();
        }

        ActivityManager.goEmployeeProfile(
                        context, userEmail,OfficeID, photo,jobTitle, supervisor, name,manager,phone,building,EmployeesList);

    }

    @Override
    public void onRowLongClicked(int position) {

    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                searchView.setMenuItem(item);
                search(searchView);
                break;
        }

        return false;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }




    /**
     * Fetches mail messages by making HTTP request
     * url: http://api.androidhive.info/json/inbox.json
     */
    private void getEmployeeProfile() {
//        swipeRefreshLayout.setRefreshing(true);

        API apiService =
                ApiClient.getClient().create(API.class);

        Call<JsonResponse> call = apiService.getDetails();
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {



                // Show it.


                System.out.println("response.body()");
                System.out.println(response.body().getOffices().length);

                // clear the inbox
                offices = null;


                offices =  response.body().getOffices();
                listoffices = Arrays.asList(offices);
                buildAdapter();



//                mAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }



            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {
                Alerter.create((Activity) context)
                        .setText("No internet connection")
                        .setIcon(R.drawable.ic_error_black_24dp)
                        .setIconColorFilter(0)
                        .show();

                swipeRefreshLayout.setRefreshing(false);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }

                System.out.println("Error message:");
                System.out.println(t.getMessage());

            }




        });
    }

    private void buildAdapter()
    {
        List<Employee> employeesInOneOffice =new ArrayList<Employee>();
        EmployeesList = new ArrayList<Employee>();

        for ( EmployeeOffice element : listoffices)
        {
            Log.d(element.getOfficeID(),element.getEmployees().toString());
            employees.put(element.getOfficeID(), Arrays.asList(element.getEmployees()));

        }


        Log.d("Map of employees: ", employees.toString());



        // i still need to check id of user to ensure obtaining their matching
        employeesInOneOffice = employees.get(OfficeID);

        for ( Employee e : employeesInOneOffice)
        {
            if (e != null)
                EmployeesList.add(e);
        }
        if (mAdapter != null) {
            Log.d("Case adapter not null::", String.valueOf(mAdapter));
            mAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter = new ProfileAdapter(getActivity(), EmployeesList, this);
            recyclerView.setAdapter(mAdapter);
        }
    }


    /**
     * chooses a random color from array.xml
     */
    private int getRandomMaterialColor(String typeColor) {
        int returnColor = Color.GRAY;
        int arrayId = getResources().getIdentifier("mdcolor_" + typeColor, "array", getActivity().getPackageName());

        if (arrayId != 0) {
            TypedArray colors = getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.GRAY);
            colors.recycle();
        }
        return returnColor;
    }


    @Override
    public void onPause() {
        super.onPause();

        if (swipeRefreshLayout!=null) {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.destroyDrawingCache();
            swipeRefreshLayout.clearAnimation();
        }
    }




    }


