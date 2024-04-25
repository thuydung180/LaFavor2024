package com.nhom5.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nhom5.lafavor2024.R;
import com.nhom5.models.Address;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    Context context;
    ArrayList<Address> listAddress;

    public AddressAdapter(Context context, ArrayList<Address> listAddress) {
        this.context = context;
        this.listAddress = listAddress;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Address address = listAddress.get(position);
        if (address == null ) {
            return;
        }
        holder.fullName.setText(address.getFullName());
        holder.phoneNumb.setText(address.getPhoneNumber());
        holder.fullAddress.setText(address.getStreet() + ", " + address.getWard() +", " + address.getDistrict() +", "+ address.getProvince());
    }

    @Override
    public int getItemCount() {
        return listAddress.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView fullName, phoneNumb, fullAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.txtAddressName);
            phoneNumb = itemView.findViewById(R.id.txtPhoneNumb);
            fullAddress = itemView.findViewById(R.id.txtAddress);
        }
    }
}
