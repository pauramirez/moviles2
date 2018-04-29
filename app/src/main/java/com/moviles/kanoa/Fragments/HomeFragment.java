package com.moviles.kanoa.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moviles.kanoa.Model.GenericPlace;
import com.moviles.kanoa.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private static final String TAG = "HomeRecyclerViewFragment";
    private RecyclerView recyclerView;
    private DatabaseReference myref;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        view.setTag(TAG);

        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myref= FirebaseDatabase.getInstance().getReference().child("/Top");
        FirebaseRecyclerAdapter<GenericPlace,HomeFragment.BlogViewHolder> recyclerAdapter=new FirebaseRecyclerAdapter<GenericPlace,HomeFragment.BlogViewHolder>(
                GenericPlace.class,
                R.layout.row_restaurant,
                HomeFragment.BlogViewHolder.class,
                myref
        ) {
            @Override
            protected void populateViewHolder(HomeFragment.BlogViewHolder viewHolder, GenericPlace model, int position) {
                viewHolder.setTitle(model.getName());
                viewHolder.setAddress(model.getDescription());
                viewHolder.setImage(model.getImgUrl());
            }

        };
        recyclerView.setAdapter(recyclerAdapter);

        return view;
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView textView_title;
        TextView textView_address;
        ImageView imageView;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            textView_title = (TextView)itemView.findViewById(R.id.textview_restaurant_name);
            textView_address= (TextView) itemView.findViewById(R.id.restaurant_address_textview);
            imageView=(ImageView)itemView.findViewById(R.id.imageview_restaurant);
        }
        public void setTitle(String title)
        {
            textView_title.setText(title+"");
        }
        public void setAddress(String address)
        {
            textView_address.setText(address);
        }
        public void setImage(String image)
        {
            Glide.with(mView.getContext())
                    .load(image)
                    .into(imageView);
        }
    }
}
