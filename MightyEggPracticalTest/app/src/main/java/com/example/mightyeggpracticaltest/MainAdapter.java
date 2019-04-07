package com.example.mightyeggpracticaltest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/*
    it is a custom adapter for showing list in the recycler view
    it has a static view holder inner class

 */


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private ArrayList<User> usersList ;

    public MainAdapter(ArrayList<User> usersList) {
        this.usersList = usersList;
    }

    public MainAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        /*
            creating view holder
         */

        View view = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_user_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        /*
            setting values in each row of recycler view

         */


        User user = usersList.get(i);
        viewHolder.setUserName(user.getUsername()); // this will set user name
        viewHolder.setMoney(user.getMoney()); // this will set money amount
        viewHolder.setRankText(); // this will set rank
        viewHolder.setImage(user); // this will set image
    }

    @Override
    public int getItemCount() {
        if(usersList!=null){
            return usersList.size();
        }
        else {
            return 0;
        }
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final View view;
        private final TextView userNameText;
        private final TextView moneyAmountText;
        private final TextView rankText;
        private final ImageView imageOfUser;
        private static int rank = 1 ;


        public ViewHolder(View view){
            super(view);

            /*
                initializing views of each row in recyclerview

             */


            this.view = view ;
            userNameText = (TextView) view.findViewById(R.id.userNameText);
            moneyAmountText = (TextView) view.findViewById(R.id.moneyAmountText);
            rankText = (TextView) view.findViewById(R.id.rankText);
            imageOfUser = (ImageView) view.findViewById(R.id.imageOfUser);
        }

        public void setUserName(String name){
            userNameText.setText(name);
        }

        public void setMoney(int money){
            moneyAmountText.setText(User.insertCommaInMoney(money+""));
        }

        public void setRankText(){
            /*
                setting rank
                if rank is 4 then start it again from 1

             */

            rankText.setText(rank+"");
            rank++;
            if(rank==4){
                rank = 1 ;
            }
        }

        public void setImage(User user){
            /*
                setting user images from resources according to their name

             */

            if(user.getUsername().equals("User 1")){
                imageOfUser.setImageDrawable(view.getResources().getDrawable(R.drawable.user_1));
            }
            else if(user.getUsername().equals("User 2")){
                imageOfUser.setImageDrawable(view.getResources().getDrawable(R.drawable.user_2));
            }
            else if(user.getUsername().equals("User 3")){
                imageOfUser.setImageDrawable(view.getResources().getDrawable(R.drawable.user_3));
            }

        }

    }

}
