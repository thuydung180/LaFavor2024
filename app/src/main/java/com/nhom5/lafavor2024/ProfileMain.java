package com.nhom5.lafavor2024;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhom5.lafavor2024.databinding.FragmentProfileMainBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileMain extends Fragment {

    FragmentProfileMainBinding binding;
    Intent intent;
    SharedPreferences sharedPreferences;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileMain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileMain.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileMain newInstance(String param1, String param2) {
        ProfileMain fragment = new ProfileMain();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileMainBinding.inflate(inflater, container, false);
        addEvent();

        return binding.getRoot();
    }

    private void addEvent() {
        binding.lnlProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ProfileMain.this.getActivity(), MyProfile.class);
                startActivity(intent);
            }
        });
        binding.lnlMyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ProfileMain.this.getActivity(), MyAddressEmpty.class);
                startActivity(intent);
            }
        });
        binding.lnlMyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ProfileMain.this.getActivity(), MyOrders.class);
                startActivity(intent);
            }
        });
        binding.lnlSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ProfileMain.this.getActivity(), Setting.class);
                startActivity(intent);
            }
        });
        binding.lnlAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ProfileMain.this.getActivity(), AboutUs.class);
                startActivity(intent);
            }
        });
//        binding.lnlLogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                intent = new Intent(ProfileMain.this.getActivity(), Login.class);
//                startActivity(intent);
//            }
//        });

        logOut();



    }

    private void logOut() {
        binding.lnlLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the context from the Fragment
                Context context = getContext();
                if (context != null) {
                    sharedPreferences = context.getSharedPreferences("checkbox", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();

                    intent = new Intent(ProfileMain.this.getActivity(), Login.class);
                    startActivity(intent);
                }
            }
        });
    }


}