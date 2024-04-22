package com.nhom5.lafavor2024;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.nhom5.lafavor2024.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public static ArrayList<Cart> cartArrayList;

    HomeFragment homeFragment = new HomeFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, homeFragment).commit();

        addEvents();
    }



    private void addEvents() {
        binding.lnlHome.setOnClickListener(clickListener);
        binding.lnlFavorite.setOnClickListener(clickListener);
        binding.lnlCart.setOnClickListener(clickListener);
        binding.lnlProfile.setOnClickListener(clickListener);

        if (cartArrayList != null){

        }else {
            cartArrayList = new ArrayList<>();
        }

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            Fragment fragment = null;
            if (view.equals(binding.lnlHome)) {
                fragment = new HomeFragment();
                transaction.replace(R.id.containerLayout, fragment, "FragHome");
                transaction.addToBackStack("FHome");
            } else if (view.equals(binding.lnlFavorite)) {
                fragment = new FavoriteFragment();
                transaction.replace(R.id.containerLayout, fragment, "FragFavorite");
                transaction.addToBackStack("FFavorite");
            } else if (view.equals(binding.lnlCart)) {
                fragment = new EmptyCartFragment();
                transaction.replace(R.id.containerLayout, fragment, "FragCart");
                transaction.addToBackStack("FCart");
            } else if (view.equals(binding.lnlProfile)) {
                fragment = new ProfileMain();
                transaction.replace(R.id.containerLayout, fragment, "FragProfile");
                transaction.addToBackStack("FProfile");
            }
            transaction.commit();
        }
    };
}