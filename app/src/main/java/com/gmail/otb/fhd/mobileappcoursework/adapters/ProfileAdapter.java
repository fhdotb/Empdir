package com.gmail.otb.fhd.mobileappcoursework.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gmail.otb.fhd.mobileappcoursework.R;
import com.gmail.otb.fhd.mobileappcoursework.model.Employee;
import com.gmail.otb.fhd.mobileappcoursework.utills.CircleTransform;
import com.gmail.otb.fhd.mobileappcoursework.utills.FlipAnimator;
import java.util.ArrayList;
import java.util.List;



public class ProfileAdapter  extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> implements Filterable {
    private Context mContext;
    private List<Employee> EmployeesList = new ArrayList<Employee>() ;
    private List<Employee> EmployeesList_fi = new ArrayList<Employee>() ;

    private MessageAdapterListener listener;
    private SparseBooleanArray selectedItems;

    // array used to perform multiple animation at once
    private SparseBooleanArray animationItemsIndex;
    private boolean reverseAllAnimations = false;

    // index is used to animate only the selected row
    // dirty fix, find a better solution
    private static int currentSelectedIndex = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView from, subject, message, iconText, timestamp;
        public ImageView iconImp, imgProfile;
        public LinearLayout messageContainer;
        public RelativeLayout iconContainer, iconBack, iconFront;


        public MyViewHolder(View view) {
            super(view);
            from = (TextView) view.findViewById(R.id.from);
            subject = (TextView) view.findViewById(R.id.txt_primary);
            message = (TextView) view.findViewById(R.id.txt_secondary);
            iconText = (TextView) view.findViewById(R.id.icon_text);
            timestamp = (TextView) view.findViewById(R.id.timestamp);
            iconBack = (RelativeLayout) view.findViewById(R.id.icon_back);
            iconFront = (RelativeLayout) view.findViewById(R.id.icon_front);
            iconImp = (ImageView) view.findViewById(R.id.icon_star);
            imgProfile = (ImageView) view.findViewById(R.id.icon_profile);
            messageContainer = (LinearLayout) view.findViewById(R.id.message_container);
            iconContainer = (RelativeLayout) view.findViewById(R.id.icon_container);
            this.setIsRecyclable(false);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onRowLongClicked(getAdapterPosition());
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }


    public ProfileAdapter(Context mContext, List<Employee> re, MessageAdapterListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        this.EmployeesList = re;
        this.EmployeesList_fi = EmployeesList;
        setHasStableIds(true);
        selectedItems = new SparseBooleanArray();
        animationItemsIndex = new SparseBooleanArray();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Employee message = EmployeesList.get(position);
        Log.d("employees in position:", message.toString());
        message.setFirstName(message.getFirstName());
       // message = employeesInOneOffice.get(position);
        // displaying text view data
        holder.from.setText(message.getFirstName());
        holder.subject.setText(message.getEmail());
        holder.message.setText(message.getMobileNamber());
        holder.timestamp.setText(message.getRole().getJobTitle());

        // displaying the first letter of From in icon text
        holder.iconText.setText(message.getLastName().substring(0, 1));

        // change the row state to activated
        holder.itemView.setActivated(selectedItems.get(position, false));

        // change the font style depending on message read status
        applyReadStatus(holder, message);

        // handle message star
        applyImportant(holder, message);

        // handle icon animation
        applyIconAnimation(holder, position);

        // display profile image
        applyProfilePicture(holder, message);

        // apply click events
        applyClickEvents(holder, position);
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {
        holder.iconContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIconClicked(position);
            }
        });

        holder.iconImp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIconImportantClicked(position);
            }
        });

        holder.messageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMessageRowClicked(position);
            }
        });

        holder.messageContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onRowLongClicked(position);
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });
    }

    private void applyProfilePicture(MyViewHolder holder, Employee message) {
        if(message.getPhoto() != null)
        {
            Glide.with(mContext).load(message.getPhoto())
                    .thumbnail(0.5f)
                    .crossFade()
                    .transform(new CircleTransform(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imgProfile);
            holder.imgProfile.setColorFilter(null);
            holder.iconText.setVisibility(View.GONE);

        }
       else {
            holder.imgProfile.setImageResource(R.drawable.bg_circle);
            holder.imgProfile.setColorFilter(Color.GREEN);
            holder.iconText.setVisibility(View.VISIBLE);
        }
    }

    private void applyIconAnimation(MyViewHolder holder, int position) {
        if (selectedItems.get(position, false)) {
            holder.iconFront.setVisibility(View.GONE);
            resetIconYAxis(holder.iconBack);
            holder.iconBack.setVisibility(View.VISIBLE);
            holder.iconBack.setAlpha(1);

            if (currentSelectedIndex == position) {
                FlipAnimator.flipView(mContext, holder.iconBack, holder.iconFront, true);
                resetCurrentIndex();
            }
        } else {
            holder.iconBack.setVisibility(View.GONE);
            resetIconYAxis(holder.iconFront);
            holder.iconFront.setVisibility(View.VISIBLE);
            holder.iconFront.setAlpha(1);
            if ((reverseAllAnimations && animationItemsIndex.get(position, false)) || currentSelectedIndex == position) {
                FlipAnimator.flipView(mContext, holder.iconBack, holder.iconFront, false);
                resetCurrentIndex();
            }
        }
    }




    // As the views will be reused, sometimes the icon appears as
    // flipped because older view is reused. Reset the Y-axis to 0
    private void resetIconYAxis(View view) {
        if (view.getRotationY() != 0) {
            view.setRotationY(0);
        }
    }

    public void resetAnimationIndex() {
        reverseAllAnimations = false;
        animationItemsIndex.clear();
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(EmployeesList.get(position).getEmployeeID());
    }

    private void applyImportant(MyViewHolder holder, Employee message) {
        if (message.getRole().getSupervisor().equals("1")) {
            holder.iconImp.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_star_black_24dp));
            holder.iconImp.setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_selected));
       } else {
            holder.iconImp.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_star_border_black_24dp));
          holder.iconImp.setColorFilter(ContextCompat.getColor(mContext, R.color.icon_tint_normal));
       }
    }

    private void applyReadStatus(MyViewHolder holder, Employee message) {
        if (!message.getRole().getSupervisor().trim().equals("1")) {
            holder.from.setTypeface(null, Typeface.NORMAL);
            holder.subject.setTypeface(null, Typeface.NORMAL);
            holder.from.setTextColor(ContextCompat.getColor(mContext, R.color.subject));
            holder.subject.setTextColor(ContextCompat.getColor(mContext, R.color.message));
        } else {
              holder.from.setTypeface(null, Typeface.BOLD);
              holder.subject.setTypeface(null, Typeface.BOLD);
              holder.from.setTextColor(ContextCompat.getColor(mContext, R.color.from));
              holder.subject.setTextColor(ContextCompat.getColor(mContext, R.color.subject));
        }
    }

    @Override
    public int getItemCount() {
        return EmployeesList_fi.size();
    }

    public void toggleSelection(int pos) {
        currentSelectedIndex = pos;
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
            animationItemsIndex.delete(pos);
        } else {
            selectedItems.put(pos, true);
            animationItemsIndex.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        reverseAllAnimations = true;
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public void removeData(int position) {
        EmployeesList.remove(position);
        resetCurrentIndex();
    }

    public void clearData()
    {

        EmployeesList.clear();
    }



    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                Log.d("performFiltering",charSequence.toString());
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    Log.d("I'm here in if:: ", charString);
                   EmployeesList_fi = EmployeesList;
                } else {


                    List<Employee>  filteredList = new  ArrayList<Employee>();
                    List<Employee>  not_filteredList = new  ArrayList<Employee>();

                    for (Employee e : EmployeesList_fi) {
                        Log.d("chart:: ",charSequence.toString());
                        Log.d("Employee e ::",e.toString());


                           if( e.getFirstName().substring(0,1).equals(charString) ||
                             e.getFirstName().toLowerCase().substring(0,1).equals(charString)){

                            filteredList.add(e);

                        }
                        else
                            not_filteredList.add(e);
                    }

                    Log.d("filteredList:: ",filteredList.toString());
                    EmployeesList_fi = filteredList;
                   // EmployeesList_fi.addAll(not_filteredList);
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = EmployeesList_fi;
                notifyDataSetChanged();

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                EmployeesList_fi = (ArrayList<Employee>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();

            }
        };
    }


//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                   EmployeesList_fi = EmployeesList;
//                } else {
//                    List<Employee> filteredList = new ArrayList<>();
//                    for (Employee row : EmployeesList_fi) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (row.getFirstName().toLowerCase().contains(charString.toLowerCase()) ||
//                                row.getLastName().contains(charSequence)) {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    EmployeesList_fi = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = EmployeesList_fi;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                EmployeesList_fi = (ArrayList<Employee>) filterResults.values;
//                // refresh the list with filtered data
//                notifyDataSetChanged();
//            }
//        };
//    }



    private void resetCurrentIndex() {
        currentSelectedIndex = -1;
    }

    public interface MessageAdapterListener {
        void onIconClicked(int position);

        void onIconImportantClicked(int position);

        void onMessageRowClicked(int position);

        void onRowLongClicked(int position);
    }



}