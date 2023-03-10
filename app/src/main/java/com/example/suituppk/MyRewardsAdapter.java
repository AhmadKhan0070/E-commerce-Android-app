package com.example.suituppk;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyRewardsAdapter extends RecyclerView.Adapter<MyRewardsAdapter.Viewholder> {

    private List<RewardModel> rewardModelList;
    private Boolean userMiniLayout = false ;
    private RecyclerView coupensRecyclerview;
    private LinearLayout selectedCoupen;
    private String productorignalPrice;
    private TextView selectedCoupenTitle ;
    private TextView selectedCoupenExpiryDate;
    private TextView selectedCoupenBody;
    private TextView discountedPrice;
    private int cartItemPosition = -1;
    private List<CartItemModel> cartItemModelList;


    public MyRewardsAdapter(List<RewardModel> rewardModelList , Boolean userMiniLayout ) {
        this.rewardModelList = rewardModelList;
        this.userMiniLayout = userMiniLayout;
    }

    public MyRewardsAdapter(List<RewardModel> rewardModelList , Boolean userMiniLayout , RecyclerView coupensRecyclerview , LinearLayout selectedCoupen, String productorignalPrice , TextView coupenTitle , TextView coupenExpiryDate , TextView coupenBody , TextView discountedPrice) {
        this.rewardModelList = rewardModelList;
        this.userMiniLayout = userMiniLayout;
        this.coupensRecyclerview = coupensRecyclerview;
        this.selectedCoupen = selectedCoupen;
        this.productorignalPrice = productorignalPrice;
        this.selectedCoupenTitle = coupenTitle;
        this.selectedCoupenExpiryDate = coupenExpiryDate;
        this.selectedCoupenBody = coupenBody;
        this.discountedPrice = discountedPrice;
    }



    public MyRewardsAdapter(int cartItemPosition , List<RewardModel> rewardModelList , Boolean userMiniLayout , RecyclerView coupensRecyclerview , LinearLayout selectedCoupen, String productorignalPrice , TextView coupenTitle , TextView coupenExpiryDate , TextView coupenBody , TextView discountedPrice , List<CartItemModel> cartItemModelList) {
        this.rewardModelList = rewardModelList;
        this.userMiniLayout = userMiniLayout;
        this.coupensRecyclerview = coupensRecyclerview;
        this.selectedCoupen = selectedCoupen;
        this.productorignalPrice = productorignalPrice;
        this.selectedCoupenTitle = coupenTitle;
        this.selectedCoupenExpiryDate = coupenExpiryDate;
        this.selectedCoupenBody = coupenBody;
        this.discountedPrice = discountedPrice;
        this.cartItemPosition = cartItemPosition;
        this.cartItemModelList = cartItemModelList;
    }

    @NonNull
    @Override
    public MyRewardsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view;

        if (userMiniLayout) {
             view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mini_rewards_item_layout, viewGroup, false);


        } else{

            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rewards_item_layout, viewGroup, false);
    }
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRewardsAdapter.Viewholder holder, int position) {

        String coupenId = rewardModelList.get(position).getCoupenId();
        String type = rewardModelList.get(position).getType();
        Date validity = rewardModelList.get(position).getTimestamp();
        String body = rewardModelList.get(position).getCoupenBody();
        String lowerLimit = rewardModelList.get(position).getLowerLimit();
        String upperLimit = rewardModelList.get(position).getUpperLimit();
        String discORamt = rewardModelList.get(position).getdiscORamt();
        Boolean alreadyUsed = rewardModelList.get(position).getAlreadyUsed();
         holder.setData(coupenId ,type , validity , body , lowerLimit , upperLimit , discORamt , alreadyUsed);
    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder{

        private TextView coupenTitle;
        private TextView coupenExpiryDate;
        private TextView coupenBody;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            coupenTitle = itemView.findViewById(R.id.coupen_title);
            coupenExpiryDate = itemView.findViewById(R.id.coupen_validity);
            coupenBody = itemView.findViewById(R.id.coupen_body);
        }
        private void setData(final String coupenId ,final String type , final Date validity , final String body , final String lowerLimit , final String upperLimit , final String discORamt , final boolean alreadyUsed){
            if (type.equals("Discount")){
                coupenTitle.setText(type);
            }else {
                coupenTitle.setText("FLAT RS." + discORamt+ " OFF");
            }


            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/YYYY");

            if (alreadyUsed){
                coupenExpiryDate.setText("Already used");
                coupenExpiryDate.setTextColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
                coupenBody.setTextColor(Color.parseColor("#50ffffff"));
                coupenTitle.setTextColor(Color.parseColor("#50ffffff"));
            }else {
                coupenBody.setTextColor(Color.parseColor("#ffffff"));
                coupenTitle.setTextColor(Color.parseColor("#ffffff"));
                coupenExpiryDate.setTextColor(itemView.getContext().getResources().getColor(R.color.coupenPurple));
                coupenExpiryDate.setText("till " +simpleDateFormat.format(validity));

            }
            coupenBody.setText(body);

            if (userMiniLayout){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!alreadyUsed){

                            selectedCoupenTitle.setText(type);
                            selectedCoupenExpiryDate.setText(simpleDateFormat.format(validity));
                            selectedCoupenBody.setText(body);

                            if (Long.valueOf(productorignalPrice) >= Long.valueOf(lowerLimit) && Long.valueOf(productorignalPrice) <= Long.valueOf(upperLimit)) {

                                if (type.equals("Discount")) {
                                    Long discountAmount = Long.valueOf(productorignalPrice) * Long.valueOf(discORamt) / 100;
                                    discountedPrice.setText("Rs." + (Long.valueOf(productorignalPrice) - discountAmount) + "/-");
                                } else {

                                    discountedPrice.setText("Rs." + (Long.valueOf(productorignalPrice) - Long.valueOf(discORamt)) + "/-");
                                }
                                if (cartItemPosition != -1) {
                                    cartItemModelList.get(cartItemPosition).setSelectedCoupenId(coupenId);
                                }
                            } else {

                                if (cartItemPosition != -1) {
                                    cartItemModelList.get(cartItemPosition).setSelectedCoupenId(null);
                                }
                                discountedPrice.setText("Invalid");
                                Toast.makeText(itemView.getContext(), "Sorry ! product does not matches the coupen terms.", Toast.LENGTH_SHORT).show();
                            }

                            if (coupensRecyclerview.getVisibility() == View.GONE) {
                                coupensRecyclerview.setVisibility(View.VISIBLE);
                                selectedCoupen.setVisibility(View.GONE);

                            } else {
                                coupensRecyclerview.setVisibility(View.GONE);
                                selectedCoupen.setVisibility(View.VISIBLE);

                            }

                        }
                    }
                });
            }

        }
    }


}
