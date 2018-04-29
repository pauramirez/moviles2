package com.moviles.kanoa.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.moviles.kanoa.Activities.Home2;
import com.moviles.kanoa.Model.GenericPlace;
import com.moviles.kanoa.Model.GenericPlaceInterface;
import com.moviles.kanoa.Model.Museum;
import com.moviles.kanoa.Model.Restaurant;
import com.moviles.kanoa.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */

public class PlaceFragment extends Fragment {
    private static final String TAG = "PlaceUnityViewFragment";
    private SharedPreferences sharedPref ;
    private SharedPreferences.Editor editor;
    GenericPlaceInterface obj ;
    ImageView topImage;
    TextView txtOpenHours;
    TextView txtKilometers;
    TextView txtTimeToPlace;
    TextView txtCurrency ;
    TextView txtLoveThisPlace;
    TextView txt_name;
    TextView txt_description;
    FloatingActionButton flButton;

    public PlaceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_place, container, false);
        view.setTag(TAG);
        //get All the things to fill
        topImage = view.findViewById(R.id.imageview_place_fragment_top);
        txtOpenHours = view.findViewById(R.id.txtOpenHours);
        txtKilometers = view.findViewById(R.id.txtKilometers);
        txtTimeToPlace = view.findViewById(R.id.txtTimeToPlace);
        txtCurrency = view.findViewById(R.id.txtCurrency_place_unit);
        txtLoveThisPlace = view.findViewById(R.id.txtLoveThisPlace_place_unit);
        txt_name = view.findViewById(R.id.txt_Name_Title_fragment_unit);
        txt_description = view.findViewById(R.id.txt_extended_description_fragment_unit);
        flButton = view.findViewById(R.id.btn_backToMuseums);
        //get preferences
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        Gson gson = new Gson();
        String json = sharedPref.getString(String.valueOf(R.string.current_object_to_show_persola_view), "");
        String currentObjName = sharedPref.getString(String.valueOf(R.string.actual_Class_bind), "");
        if(currentObjName.equals(String.valueOf(R.string.ConstantMuseums))){
            obj = gson.fromJson(json, Museum.class);
            getActivity().setTitle(obj.getName());

            //start filling up
            Glide.with(view.getContext())
                    .load(obj.getImgUrl())
                    .into(topImage);
            txtOpenHours.setText(obj.getHoursOpen());
            String dist = ""+ obj.getDistanceToPlace()+ " KM";
            txtKilometers.setText(dist);
            txtTimeToPlace.setText(obj.getTimeToPlace());
            String crr = ""+obj.getCost()+ " "+ obj.getCurrency();
            txtCurrency.setText(crr);
            txtLoveThisPlace.setText(String.valueOf(obj.getNumberOfFollowers()));
            txt_name.setText(obj.getName());
            txt_description.setText(obj.getDescription());

        }else if(currentObjName.equals(String.valueOf(R.string.ConstantRestaurants))){
           //TODo
        }else if(currentObjName.equals(String.valueOf(R.string.ConstantPlaces))){
            //TODo
        }
        flButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home2 a = (Home2)getActivity();
                a.backFragmentUnity("Museums");
            }
        });

        return view;
    }


}
