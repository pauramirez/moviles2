package com.moviles.kanoa.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.moviles.kanoa.Activities.Home2;
import com.moviles.kanoa.Model.GenericPlaceInterface;
import com.moviles.kanoa.Model.Museum;
import com.moviles.kanoa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MuseumFragment extends Fragment {


    private static final String TAG = "MuseumsRecyclerViewFragment";
    private RecyclerView recyclerView;
    private DatabaseReference myref;
    private Museum museum;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;



    public MuseumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_museum, container, false);
        view.setTag(TAG);

        museum= new Museum();
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myref= FirebaseDatabase.getInstance().getReference().child("/Museums");

        FirebaseRecyclerAdapter<Museum,MuseumFragment.BlogViewHolder> recyclerAdapter=new FirebaseRecyclerAdapter<Museum,MuseumFragment.BlogViewHolder>(
                Museum.class,
                R.layout.row_restaurant,
                MuseumFragment.BlogViewHolder.class,
                myref
        ) {
            @Override
            protected void populateViewHolder(MuseumFragment.BlogViewHolder viewHolder, Museum model, int position) {
                museum=model;
                viewHolder.setTitle(model.getName());
                viewHolder.setAddress(model.getDescription());
                viewHolder.setImage(model.getImgUrl());
            }

            @Override
            public BlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final BlogViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new BlogViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                        editor = sharedPref.edit();

                        Gson gson = new Gson();
                        String json = gson.toJson(museum);
                        editor.putString(String.valueOf(R.string.current_object_to_show_persola_view), json);
                        editor.commit();

                        String mkey = String.valueOf(R.string.actual_Class_bind);
                        String mObj = String.valueOf(R.string.ConstantMuseums);
                        editor.putString(mkey,mObj);
                        editor.commit();

                        Toast.makeText(getActivity(), "Item clicked at " + position + "name: "+museum.getName(), Toast.LENGTH_SHORT).show();
                        Home2 a = (Home2)getActivity();
                        a.doFragmentUnity();
                    }
                });
                return viewHolder;
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
        private String name ;
        private BlogViewHolder.ClickListener mClickListener;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            textView_title = (TextView)itemView.findViewById(R.id.textview_restaurant_name);
            textView_address= (TextView) itemView.findViewById(R.id.restaurant_address_textview);
            imageView=(ImageView)itemView.findViewById(R.id.imageview_restaurant);

            //listener set on ENTIRE ROW, you may set on individual components within a row.
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(v, getAdapterPosition());

                }
            });
        }

        //Interface to send callbacks...
        public interface ClickListener{
            public void onItemClick(View view, int position);
        }

        public void setOnClickListener(BlogViewHolder.ClickListener clickListener){
            mClickListener = clickListener;
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
