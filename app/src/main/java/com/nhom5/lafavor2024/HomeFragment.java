package com.nhom5.lafavor2024;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhom5.adapters.BannerAdapter;
import com.nhom5.adapters.CategoryAdapter;
import com.nhom5.adapters.SearchAdapter;
import com.nhom5.adapters.TopPickAdapter;
import com.nhom5.lafavor2024.databinding.FragmentHomeBinding;
import com.nhom5.models.Category;
import com.nhom5.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kotlin.collections.ArrayDeque;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TopPickAdapter adapter;
    private SearchAdapter searchAdapter;
    private CategoryAdapter adapter1;
    private BannerAdapter bannerAdapter;
    private List<Product> productList;
    private List<Category> categoryList;
    private List<String> urls;
    private Timer timer;
    private TimerTask timerTask;
    int position;

    private FirebaseFirestore db;

    FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        bindView();
        addEvents();

        db = FirebaseFirestore.getInstance();
        fetchDataFromFirestore();
        return binding.getRoot();
    }

    public class CustomGridLayoutManager extends GridLayoutManager {
        public CustomGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }
        @Override
        public boolean canScrollVertically() {
            // Trả về false để ngăn không cho RecyclerView cuộn
            return false;
        }
    }

    private void bindView() {
        //Search Adapter
        searchAdapter = new SearchAdapter(this, R.layout.item_search_result, new ArrayList<>());
        binding.rcvSearchResult.setAdapter(searchAdapter);
        binding.rcvSearchResult.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvSearchResult.setVisibility(View.GONE); // Ẩn ban đầu
        // Top Pick
        productList = new ArrayList<>();
        adapter = new TopPickAdapter(this, R.layout.item_favorite, productList);
        binding.rcvTopPick.setAdapter(adapter);
        binding.rcvTopPick.setLayoutManager(new CustomGridLayoutManager(getContext(), 2));

        // Category
        categoryList = new ArrayList<>();
        adapter1 = new CategoryAdapter(this, R.layout.item_category_mini, categoryList);
        binding.rcvCategory.setAdapter(adapter1);
        binding.rcvCategory.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        // Banner
        getListUrl();
        bannerAdapter = new BannerAdapter(this.getContext(), urls);
        binding.rcvBanner.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        binding.rcvBanner.setAdapter(bannerAdapter);

        if(urls != null) {
            position = Integer.MAX_VALUE /2;
            binding.rcvBanner.scrollToPosition(position);
        }

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.rcvBanner);
        binding.rcvBanner.smoothScrollBy(5,0);
    }

    private void addEvents() {
        binding.txtViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductCategory.class);
                startActivity(intent);
            }
        });

        binding.btnCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyCoupon.class);
                startActivity(intent);
            }
        });
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return true;
            }
        });
    }

    private void performSearch(String query) {
        if (!query.isEmpty()) {
            // Ẩn các thành phần không cần thiết
            binding.rcvBanner.setVisibility(View.GONE);
            binding.rcvCategory.setVisibility(View.GONE);
            binding.rcvTopPick.setVisibility(View.GONE);
            binding.textView18.setVisibility(View.GONE);
            binding.txtViewAll.setVisibility(View.GONE);
            binding.textView19.setVisibility(View.GONE);

            // Thực hiện tìm kiếm
            db.collection("products")
                    .orderBy("productName")
                    .startAt(query)
                    .endAt(query + "\uf8ff")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Product> filteredProducts = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                filteredProducts.add(document.toObject(Product.class));
                            }
                            // Cập nhật dữ liệu và hiển thị RecyclerView
                            searchAdapter.updateData(filteredProducts);
                            binding.rcvSearchResult.setVisibility(View.VISIBLE);
                        } else {
                            Log.d("SearchError", "Error getting documents: ", task.getException());
                        }
                    });
        } else {
            // Hiển thị lại các thành phần và ẩn kết quả tìm kiếm
            binding.rcvBanner.setVisibility(View.VISIBLE);
            binding.rcvCategory.setVisibility(View.VISIBLE);
            binding.rcvTopPick.setVisibility(View.VISIBLE);
            binding.textView18.setVisibility(View.VISIBLE);
            binding.txtViewAll.setVisibility(View.VISIBLE);
            binding.textView19.setVisibility(View.VISIBLE);
            binding.rcvSearchResult.setVisibility(View.GONE);
        }
    }


    public void updateData(List<Product> newData) {
        productList.clear();
        productList.addAll(newData);
    }

    @Override
    public void onResume() {
        super.onResume();
        runAutoScrollBanner();
    }

    private void runAutoScrollBanner() {
        if(timer==null&&timerTask==null) {
            timer=new Timer();
            timerTask=new TimerTask() {
                @Override
                public void run() {
                    if(position==Integer.MAX_VALUE) {
                        position=Integer.MAX_VALUE / 2;
                        binding.rcvBanner.scrollToPosition(position);
                        binding.rcvBanner.smoothScrollBy(5,0);
                    } else {
                        position++;
                        binding.rcvBanner.smoothScrollToPosition(position);
                    }
                }
            };
            timer.schedule(timerTask, 4000, 4000);

        }
    }

    private void getListUrl() {
        urls = new ArrayList<>();
        urls.add("https://firebasestorage.googleapis.com/v0/b/lafavor2024.appspot" +
                ".com/o/banner%2Fbanner3.jpg?alt=media&token=bf798d8a-1eb1-4dde-acaf-94fed4aef6dd");
        urls.add("https://firebasestorage.googleapis.com/v0/b/lafavor2024.appspot" +
                ".com/o/banner%2Fbanner2.jpg?alt=media&token=88bc7538-b1fe-4b91-ad19-45c05f99d553");
        urls.add("https://firebasestorage.googleapis.com/v0/b/lafavor2024.appspot" +
                ".com/o/banner%2Fbanner1.jpg?alt=media&token=a9a0eabb-b64f-4dd7-b7ab-12582e7f73ea");
    }

    private void fetchDataFromFirestore() {
        db.collection("products")
                .limit(8)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Product product = document.toObject(Product.class);
                                productList.add(product);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(),
                                    "Error getting documents: " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        
        db.collection("categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Category category = document.toObject(Category.class);
                                categoryList.add(category);
                            }
                            adapter1.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(),
                                    "Error getting documents: " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}