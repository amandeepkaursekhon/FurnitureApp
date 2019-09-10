package com.example.capstone.furniturestore.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.capstone.furniturestore.EditAddressActivity;
import com.example.capstone.furniturestore.R;
import com.example.capstone.furniturestore.Models.Address;


import java.util.ArrayList;


/**
 * Created by mankirankaur on 2018-03-31.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    Context ctx;
    ArrayList<Address> arrayListaddress = new ArrayList<Address>();
    Address addressuser;



    public AddressAdapter(ArrayList<Address> arrayListaddress, Context ctx) {

        this.arrayListaddress.clear();
        this.arrayListaddress = (ArrayList<Address>) arrayListaddress;
        notifyDataSetChanged();
        this.ctx = ctx;

    }


    @Override
    public AddressAdapter.AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_layout, parent, false);
        AddressAdapter.AddressViewHolder addressViewHolder = new AddressAdapter.AddressViewHolder(view, ctx, arrayListaddress);
        return addressViewHolder;
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, int position) {

        addressuser = arrayListaddress.get(position);
        //iid = addressuser.getAddressId();

        holder.tvFullName.setText(addressuser.getUfullname());
        holder.tvAddress.setText(addressuser.getUaddress());
        holder.tvCity.setText(addressuser.getUcity());
        holder.tvPostalcode.setText(addressuser.getUpostalcode());
        holder.tvState.setText(addressuser.getUstate());

    }

    @Override
    public int getItemCount() {
        return arrayListaddress.size();
    }



    public static class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvFullName;
        public TextView tvAddress;
        public TextView tvCity;
        public TextView tvState;
        public TextView tvPostalcode;
        //public TextView tvEditaddress;
        public ImageView tvImageEditaddress;


        ArrayList<Address> arrayListaddress = new ArrayList<Address>();
        Intent intt;
        Context ctx;


        public AddressViewHolder(View itemView, final Context ctx, final ArrayList<Address> arrayListaddress)
        {

            super(itemView);
            this.arrayListaddress = arrayListaddress;
            this.ctx = ctx;

            tvFullName = (TextView) itemView.findViewById(R.id.tvFullName);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvCity = (TextView)itemView.findViewById(R.id.tvCity);
            tvState = (TextView)itemView.findViewById(R.id.tvState);
            tvPostalcode = (TextView)itemView.findViewById(R.id.tvPostalcode);

            tvImageEditaddress = (ImageView)itemView.findViewById(R.id.image_Editaddress);
            tvImageEditaddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    Address addressuser = arrayListaddress.get(position);


                    intt = new Intent(ctx,EditAddressActivity.class);

                    intt.putExtra("addressid",addressuser.getAddressId());
                    intt.putExtra("fullname",addressuser.getUfullname());
                    intt.putExtra("useraddress",addressuser.getUaddress());
                    intt.putExtra("usercity",addressuser.getUcity());
                    intt.putExtra("userstate",addressuser.getUstate());
                    intt.putExtra("userpostalcode",addressuser.getUpostalcode());
                    intt.putExtra("userphonenumber",addressuser.getUphone());

                    ctx.startActivity(intt);

                }
            });




            //tvEditaddress = (TextView)itemView.findViewById(R.id.text_Editaddress);
            /*tvEditaddress.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)

                {
                    int position = getAdapterPosition();
                    Address addressuser = arrayListaddress.get(position);


                    intt = new Intent(ctx,EditAddressActivity.class);

                    intt.putExtra("addressid",addressuser.getAddressId());
                    intt.putExtra("fullname",addressuser.getUfullname());
                    intt.putExtra("useraddress",addressuser.getUaddress());
                    intt.putExtra("usercity",addressuser.getUcity());
                    intt.putExtra("userstate",addressuser.getUstate());
                    intt.putExtra("userpostalcode",addressuser.getUpostalcode());
                    intt.putExtra("userphonenumber",addressuser.getUphone());

                    ctx.startActivity(intt);

                }
            });*/


        }


        @Override
        public void onClick(View v) {



        }
    }



}
