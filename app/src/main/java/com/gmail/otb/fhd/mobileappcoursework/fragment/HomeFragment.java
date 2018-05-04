package com.gmail.otb.fhd.mobileappcoursework.fragment;

/**
 * Created by fahadalms3odi on 4/14/18.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gmail.otb.fhd.mobileappcoursework.NetworkLayer.API;
import com.gmail.otb.fhd.mobileappcoursework.NetworkLayer.ApiClient;
import com.gmail.otb.fhd.mobileappcoursework.R;
import com.gmail.otb.fhd.mobileappcoursework.adapters.ProfileAdapter;
import com.gmail.otb.fhd.mobileappcoursework.model.Employee;
import com.gmail.otb.fhd.mobileappcoursework.model.EmployeeOffice;
import com.gmail.otb.fhd.mobileappcoursework.model.EmployeeRole;
import com.gmail.otb.fhd.mobileappcoursework.model.JsonResponse;
import com.gmail.otb.fhd.mobileappcoursework.utills.ActivityManager;
import com.gmail.otb.fhd.mobileappcoursework.utills.DividerItemDecoration;
import com.gmail.otb.fhd.mobileappcoursework.utills.HandlerInput;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.tapadoo.alerter.Alerter;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.app.Activity.RESULT_CANCELED;
import static android.support.constraint.Constraints.TAG;


public class HomeFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener , ProfileAdapter.MessageAdapterListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "userEmail";
    private static final String ARG_PARAM2 = "OfficeID";
    private static final String ARG_PARAM3 = "supervisor";

    static final int PICTURE_RESULT = 1;

    public ImageView employee_img;


    // TODO: Rename and change types of parameters


    private MaterialSearchView searchView;
    private FloatingActionButton fab;

    private EmployeeOffice[] offices;
    List<EmployeeOffice> listoffices;
    private RecyclerView recyclerView;
    private ProfileAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private  Uri downloadUrl;

    private Context context;
    private List<Employee> employeesInOneOffice =new ArrayList<Employee>();
    private List<Employee> EmployeesList = new ArrayList<Employee>();
    private Map<String, List<Employee> > employees = new HashMap<String, List<Employee> >();

    private String userEmail;
    private String OfficeID;
    private String supervisor;
    private String building;
    private FrameLayout layout_fab;

    private Employee em;
    private Map<String, List<Employee>> employeeIist;
    private DatabaseReference rootRef;






    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
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
        initGui();
    }



    private void initGui() {
        context = this.getActivity();
        layout_fab = getActivity().findViewById(R.id.fap_container);
        downloadUrl = null;

        if(supervisor.trim().equals("1"))
        {

            fab = (FloatingActionButton) layout_fab.findViewById(R.id.fab);
            layout_fab.setVisibility(View.VISIBLE);

            fab.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {


                    MaterialDialog dialog =
                            new MaterialDialog.Builder(context)
                                    .title("Add employee")
                                    .autoDismiss(false)
                                    .customView(R.layout.dialog_customview, true)
                                    .negativeText(android.R.string.cancel)
                                    .positiveText("Add")
                                    .build();


                    final EditText employee_firstName = (EditText) dialog.findViewById(R.id.employee_first_name);
                    final EditText employee_lastName = (EditText) dialog.findViewById(R.id.employee_last_name);
                    final EditText employee_phone = (EditText) dialog.findViewById(R.id.employee_phone);
                    final EditText employee_email = (EditText) dialog.findViewById(R.id.employee_email);
                    final EditText employee_job_title = (EditText) dialog.findViewById(R.id.employee_job_Title);
                    final EditText employee_password = (EditText) dialog.findViewById(R.id.employee_password);

                    final LinearLayout layout_prog = (LinearLayout) dialog.findViewById(R.id.layout_prog);
                    layout_prog.setVisibility(View.GONE);

                    final LinearLayout layout_add = (LinearLayout) dialog.findViewById(R.id.layout_adding);
                    layout_add.setVisibility(View.VISIBLE);


                    employee_img =(ImageView)dialog.findViewById(R.id.employee_img);

                    em = new Employee();
                    employeesInOneOffice =new ArrayList<Employee>();
                    dialog.getBuilder().onPositive(new MaterialDialog.SingleButtonCallback()
                    {
                        @Override
                        public void onClick(@NonNull final MaterialDialog dialog, @NonNull DialogAction which)
                        {


                            if (!HandlerInput.isEmpty(employee_firstName) &&
                                    !HandlerInput.isEmpty(employee_lastName) &&
                                    !HandlerInput.isEmpty(employee_phone) &&
                                    !HandlerInput.isEmpty(employee_email) &&
                                    !HandlerInput.isEmpty(employee_job_title) &&
                                    !HandlerInput.isEmpty(employee_password))
                            {

                                layout_add.setVisibility(View.GONE);
                                layout_prog.setVisibility(View.VISIBLE);



                                em.setEmail(employee_email.getText().toString());
                                em.setFirstName(employee_firstName.getText().toString());
                                em.setLastName(employee_lastName.getText().toString());
                                em.setMobileNamber(employee_phone.getText().toString());
                                em.setEmployeeID(gen_Employee_id());

                                EmployeeRole Role = new EmployeeRole("0", employee_job_title.getText().toString());
                                em.setRole(Role);
                                if(downloadUrl == null)
                                    em.setPhoto("");
                                em.setPassword(employee_password.getText().toString());


                                try {


                                    rootRef = FirebaseDatabase.getInstance().getReference();
                                    rootRef.child("offices");
                                    rootRef.child("0");
                                    rootRef.child("employees");
                                    rootRef.addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                            Log.d("Data onChildAdded", dataSnapshot.getValue().toString());

                                            employeeIist = new HashMap<String, List<Employee>>();
                                            String office;
                                            if (OfficeID.equals("1")) {
                                                office = "0";
                                            } else
                                                office = "1";

                                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                                                //Read all employees in the same office
                                                String officeiD = (String) userSnapshot.child("OfficeID").getValue();
                                                if (officeiD.equals(OfficeID)) {
                                                    String key = (String) userSnapshot.child("employees").getKey();
                                                    List<Employee> obj = new ArrayList<Employee>();
                                                    obj.addAll((Collection<? extends Employee>) userSnapshot.child("employees").child("employees").getValue());
                                                    obj.add(em); // this the nea employee

                                                    Log.d(TAG, "Key: " + key);
                                                    employeeIist.put(key, obj);

                                                    Log.d("employeeIist::", employeeIist.toString());
                                                    rootRef.child("offices")
                                                            .child(office)
                                                            .child("employees")
                                                            .setValue(employeeIist);
                                                    break;
                                                }
                                            }


                                        }

                                        @Override
                                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                                            Log.d("Data onChildChanged", dataSnapshot.getValue().toString());

                                            Alerter.create((Activity) context)
                                                    .setText("Employee has been added ..")
                                                    .setIcon(R.drawable.ic_note_add_black_24dp)
                                                    .setIconColorFilter(0)
                                                    .show();
                                            dialog.dismiss();

                                        }

                                        @Override
                                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                                            Log.d("Data onChildRemoved", dataSnapshot.getValue().toString());

                                        }

                                        @Override
                                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                                            Log.d("Data onChildMoved", dataSnapshot.getValue().toString());

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Alerter.create((Activity) context)
                                                    .setText("Firebase adding issue, please try again..")
                                                    .setIcon(R.drawable.ic_error_black_24dp)
                                                    .setIconColorFilter(0)
                                                    .show();

                                        }

                                    });
                                }//try
                                catch (Exception e) {
                                    System.err.println("Caught IOException: " + e.getMessage());
                                    Alerter.create((Activity) context)
                                            .setText("Firebase adding issue, please try again..")
                                            .setIcon(R.drawable.ic_note_add_black_24dp)
                                            .setIconColorFilter(0)
                                            .show();
                                }

                                }//if

                        }
                    });// Positive Click

                    dialog.getBuilder().onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            dialog.dismiss();
                                        }
                                    });






                    Button openCamera = (Button)dialog.findViewById(R.id.employee_add_photo);
                    openCamera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            // the intent is my camera
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            //getting uri of the file
                            file = Uri.fromFile(getFile());

                            //Setting the file Uri to my photo
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,file);

                            if(intent.resolveActivity(context.getPackageManager())!=null)
                            {
                                startActivityForResult(intent, PICTURE_RESULT);
                            }
                        }
                    });

                    if (help1 != null)
                        employee_img.setImageBitmap(thumbnail.extractThumbnail(help1, help1.getWidth(), help1.getHeight()));

                    dialog.show();


                }
            }); // Click in Fab
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
        API apiService =
                ApiClient.getClient().create(API.class);

        Call<JsonResponse> call = apiService.getDetails();
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {

                System.out.println("response.body()");
                System.out.println(response.body().getOffices().length);

                // clear the inbox
                offices = null;


                offices =  response.body().getOffices();
                listoffices = Arrays.asList(offices);
                buildAdapter();

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
            employees.put(element.getOfficeID(), element.getEmployees().getEmployees());

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

    @Override
    public void onPause() {
        super.onPause();

        if (swipeRefreshLayout!=null) {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.destroyDrawingCache();
            swipeRefreshLayout.clearAnimation();
        }
    }

    Bitmap help1;
    String mCurrentPhotoPath;
    ContentValues values;
    private Uri file;
    ThumbnailUtils thumbnail;



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.gc();
        if (requestCode == PICTURE_RESULT) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    help1 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), file);
                    employee_img.setImageBitmap(thumbnail.extractThumbnail(help1, help1.getWidth(), help1.getHeight()));
                  //  uploadFile(help1);
                    uploadImage(file);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }//if
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(context, "Picture was not taken", Toast.LENGTH_SHORT);
            }
        }
    }

    //this method will create and return the path to the image file
    private File getFile() {
        File folder = Environment.getExternalStoragePublicDirectory("/images");// the file path

        //if it doesn't exist the folder will be created
        if(!folder.exists())
        {folder.mkdir();}

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_"+ timeStamp + "_";
        File image_file = null;

        try {
            image_file = File.createTempFile(imageFileName,".jpg",folder);
        } catch (IOException e) {
            e.printStackTrace();
        }


        mCurrentPhotoPath = image_file.getAbsolutePath();
        Log.d("mCurrentPhotoPath::",mCurrentPhotoPath);

        return image_file;
    }


    public String gen_Employee_id() {
        Random r = new Random( System.currentTimeMillis() );
        return String.valueOf(((1 + r.nextInt(2)) * 10000 + r.nextInt(10000)));
    }




//    private void uploadFile(Bitmap bitmap) {
//
//        final ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setTitle("Uploading...");
//        progressDialog.show();
//        progressDialog.setCancelable(false);
//
//
//
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReferenceFromUrl("Your url for storage");
//        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
//        StorageReference mountainImagesRef = storageRef.child("images/" + em.getEmployeeID()+timeStamp+ ".jpg");
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
//        byte[] data = baos.toByteArray();
//        UploadTask uploadTask = mountainImagesRef.putBytes(data);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                progressDialog.dismiss();
//
//                Log.d("exception::",exception.toString());
//                Alerter.create((Activity) context)
//                        .setText("Try again, firebase issue.")
//                        .setIcon(R.drawable.ic_error_black_24dp)
//                        .setIconColorFilter(0)
//                        .show();
//
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
//                Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                em.setPhoto(downloadUrl.toString());
//                Log.d("downloadUrl-->", "" + downloadUrl);
//
//                Alerter.create((Activity) context)
//                        .setText("Picture has been Stored.")
//                        .setIcon(R.drawable.ic_error_black_24dp)
//                        .setIconColorFilter(0)
//                        .show();
//            }
//        });
//
//    }


    private void uploadImage( Uri filePath) {

        if(filePath != null)
        {

            FirebaseStorage storage = FirebaseStorage.getInstance();;
            StorageReference storageReference = storage.getReference();;

            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                             downloadUrl = taskSnapshot.getDownloadUrl();
                            Log.d("taskSnapshot::", taskSnapshot.toString());
                            em.setPhoto(downloadUrl.toString());
                            Alerter.create((Activity) context)
                                    .setText("Uploaded.")
                                    .setIcon(R.drawable.ic_error_black_24dp)
                                    .setIconColorFilter(0)
                                    .show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressDialog.dismiss();
                           // em.setPhoto("");
                            Log.d("exception::", e.toString());
                            Alerter.create((Activity) context)
                                    .setText("Try again, firebase issue.")
                                    .setIcon(R.drawable.ic_error_black_24dp)
                                    .setIconColorFilter(0)
                                    .show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }


    }


