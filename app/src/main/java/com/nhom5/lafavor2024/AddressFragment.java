package com.nhom5.lafavor2024;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhom5.adapters.AddressAdapter;
import com.nhom5.lafavor2024.databinding.FragmentAddressBinding;
import com.nhom5.models.Address;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddressFragment extends Fragment {

    FragmentAddressBinding binding;
    RecyclerView addressRecycler;
    AddressAdapter addressAdapter;
    ArrayList<Address> listAddress;

    Address selectedAddress;

    //Menu/////

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddressFragment newInstance(String param1, String param2) {
        AddressFragment fragment = new AddressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddressBinding.inflate(inflater, container, false);


        getAddressFromFirebase();
        getAddress();
        addEvent();
        registerForContextMenu(binding.rcvMyAddress);

        return binding.getRoot();
    }

    public void getAddressFromFirebase() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String currentUserId = currentUser.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Addresses").child(currentUserId);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                listAddress.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Address address = dataSnapshot.getValue(Address.class);
//                    listAddress.add(address);
//                }
//                addressAdapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(AddressFragment.this.getContext(), "Get address fail", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        );
//        if (listAddress == null) {
//            listAddress = new ArrayList<>();
//        } else {
//            // Clear the listAddress ArrayList before adding new addresses
//            listAddress.clear();
//        }
        databaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Address address = snapshot.getValue(Address.class);
                listAddress.add(address);
                addressAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                listAddress.clear();
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Address address = dataSnapshot.getValue(Address.class);
//                    if (address != null) {
//                        // Check if the list is empty before adding new addresses
//                        if (listAddress.isEmpty() || !listAddress.contains(address)) {
//                            listAddress.add(address);
//                        }
//                    }
//                }
//                // Notify adapter after adding addresses
//                addressAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    private void getAddress() {
        addressRecycler = binding.rcvMyAddress;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        addressRecycler.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
        addressRecycler.addItemDecoration(dividerItemDecoration);
        listAddress = new ArrayList<>();
        addressAdapter = new AddressAdapter(this.getContext(), listAddress, new AddressAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Address address) {
                //Code here
                selectedAddress = address;
            }
        });
        addressRecycler.setAdapter(addressAdapter);
    }

    private void addEvent() {
        binding.imvAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(AddressFragment.this.getContext(), MyAddressAdd.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
//       getActivity().getMenuInflater().inflate(R.menu.option_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String province = selectedAddress.getProvince();
        if (item.getItemId() == R.id.mnSetDefault) {
            //Code Set Default
//            setDefaultAddress(selectedAddress);
        }
        if (item.getItemId() == R.id.mnEdit) {
            Intent intent = new Intent(this.getContext(), EditAddress.class);
            //Code edit
            if (province != null) {
                intent.putExtra("data", province);

                startActivity(intent);
            }
        }
        if (item.getItemId() == R.id.mnDelete) {
            if (selectedAddress != null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle("Confirm Detele?");
            builder.setIcon(android.R.drawable.ic_input_delete);
            builder.setMessage("Do you want to delete this address?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Code delete
                    FirebaseDatabase.getInstance().getReference("Addresses").child(userId)
                            .child(province).removeValue().addOnSuccessListener(aVoid -> {
                                Toast.makeText(getActivity(), "Successfully deleted", Toast.LENGTH_SHORT).show();
                                getAddressFromFirebase();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getActivity(), "Fail!", Toast.LENGTH_SHORT).show();
                            });
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }
        return super.onContextItemSelected(item);
    }

//    private void setDefaultAddress(Address selectedAddress) {
//        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Addresses").child(userId);
//
//        // Get all addresses
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Address address = snapshot.getValue(Address.class);
//                    if (address != null) {
//                        // Update the default status
//                        address.setDefault(address.getProvince().equals(selectedAddress.getProvince()));
//                        databaseReference.child(snapshot.getKey()).setValue(address);
//                    }
//                }
//                Toast.makeText(getActivity(), "Default address set successfully", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "Failed to set default address", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}



