package com.nhom5.lafavor2024;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import com.nhom5.lafavor2024.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    BottomNavigationView bottomNavigationView;
    public static ArrayList<Cart> cartArrayList;
    public static final int NAV_HOME = R.id.nav_home;
    public static final int NAV_FAV = R.id.nav_fav;
    public static final int NAV_CART = R.id.nav_cart;
    public static final int NAV_PROF = R.id.nav_prof;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
//        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, homeFragment).commit();

        binding.bottomNavigationBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == NAV_HOME) {
                    replaceFragment(new HomeFragment());
                } else if (itemId == NAV_FAV) {
                    replaceFragment(new FavoriteFragment());
                } else if (itemId == NAV_CART) {
                    replaceFragment(new EmptyAddressFragment());
                } else if (itemId == NAV_PROF) {
                    replaceFragment(new ProfileMain());
                } else {
                    // Handle default case if needed
                }
                return true;
            }
        });
//                item -> {
//            int itemId = item.getItemId();
//            switch (itemId) {
//                case NAV_HOME:
//                    replaceFragment(new HomeFragment());
//                    break;
//                case NAV_FAV:
//                    replaceFragment(new FavoriteFragment());
//                    break;
//                case NAV_CART:
//                    replaceFragment(new EmptyAddressFragment());
//                    break;
//                case NAV_PROF:
//                    replaceFragment(new ProfileMain());
//                    break;
////                default:
////                    return new HomeFragment();
//            }
//            return true;
//        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerLayout, fragment);
        fragmentTransaction.commit();
    }


//    private void addEvents() {
//        binding.lnlHome.setOnClickListener(clickListener);
//        binding.lnlFavorite.setOnClickListener(clickListener);
//        binding.lnlCart.setOnClickListener(clickListener);
//        binding.lnlProfile.setOnClickListener(clickListener);
//
//        if (cartArrayList != null){
//
//        }else {
//            cartArrayList = new ArrayList<>();
//        }
//
//    }
//
//    View.OnClickListener clickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            FragmentManager manager = getSupportFragmentManager();
//            FragmentTransaction transaction = manager.beginTransaction();
//
//            Fragment fragment = null;
//            if (view.equals(binding.lnlHome)) {
//                fragment = new HomeFragment();
//                transaction.replace(R.id.containerLayout, fragment, "FragHome");
//                transaction.addToBackStack("FHome");
//            } else if (view.equals(binding.lnlFavorite)) {
//                fragment = new FavoriteFragment();
//                transaction.replace(R.id.containerLayout, fragment, "FragFavorite");
//                transaction.addToBackStack("FFavorite");
//            } else if (view.equals(binding.lnlCart)) {
//                fragment = new EmptyCartFragment();
//                transaction.replace(R.id.containerLayout, fragment, "FragCart");
//                transaction.addToBackStack("FCart");
//            } else if (view.equals(binding.lnlProfile)) {
//                fragment = new ProfileMain();
//                transaction.replace(R.id.containerLayout, fragment, "FragProfile");
//                transaction.addToBackStack("FProfile");
//            }
//            transaction.commit();
//        }
//    };
//    public void onClickTextView(View view) {
//        TextView textView = (TextView) view;
//        textView.setTextColor(getResources().getColorStateList(R.color.text_color));
//    }

//    public void onClickImageView(View view) {
//        ImageView imageView = (ImageView) view;
//        imageView.setColorFilter(getResources().getColorStateList(R.color.vector_fill_color));
//    }
}