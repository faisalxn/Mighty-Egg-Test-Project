package com.example.mightyeggpracticaltest;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import jxl.Cell;
import jxl.Image;
import jxl.Sheet;
import jxl.Workbook;

/*
    This is main activity, here in this app only one activity is needed

 */

public class MainActivity extends AppCompatActivity {

    /*
        Here I declared all views like button, recyclerview, textview, imageview
        Two array list also declared for weekly scores and all time scores

     */

    private ArrayList<User> thisWeekList, allTimeList ; // these two list will store two different type of scores
    private Button thisWeekButton, allTimeButton; // by pressing these buttons user can see weekly or all time leader board
    private RecyclerView userList ; // In this view all scores will be shown
    private MainAdapter adapterForWeek,adapterForAll; // here two custom adapter is taken for two different type of scores
    private TextView firstAmountText, secondAmountText, thirdAmountText; // textview for showing money of 1st, 2nd, 3rd position player
    private ImageView firstPersonImage,secondPersonImage,thirdPersonImage ; // these three imageview will contain images of 1st, 2nd , 3rd player's image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        createDividerForList();
        initializeList();
        fetchDataFromFileToList();
        sortList();
        settingAdapters();
        initializeViewByValue(thisWeekList);
        setEventListenersForButton();
        setImage();
    }

    /*
        initializeViews method will initialize our all views from resources and also set neccessery properties in our views

     */

    private void initializeViews(){
        thisWeekButton = (Button) findViewById(R.id.thisWeekButton);
        allTimeButton = (Button) findViewById(R.id.allTimeButton);
        firstAmountText = (TextView) findViewById(R.id.firstAmountText);
        secondAmountText = (TextView) findViewById(R.id.secondAmountText);
        thirdAmountText = (TextView) findViewById(R.id.thirdAmountText);
        firstPersonImage = (ImageView) findViewById(R.id.firstPersonImage);
        secondPersonImage = (ImageView) findViewById(R.id.secondPersonImage);
        thirdPersonImage = (ImageView) findViewById(R.id.thirdPersonImage);
        userList = (RecyclerView) findViewById(R.id.userList);
        userList.setHasFixedSize(true);
        userList.setLayoutManager(new LinearLayoutManager(this));
        thisWeekButton.setSelected(true);
        thisWeekButton.setTextColor(Color.parseColor("#FFFFFF"));
        allTimeButton.setTextColor(Color.parseColor("#ed1e79"));

    }

    /*
        createDividerForList method will add a divider in usersList recyclerview by a svg file
     */

    private void createDividerForList(){
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.ic_divider_2));
        userList.addItemDecoration(dividerItemDecoration);
    }

    /*
        initializeList method will initialize two different types of list

     */

    private void initializeList(){
        thisWeekList = new ArrayList<>();
        allTimeList = new ArrayList<>();
    }

    /*
        fetchDataFromFileToList method will extract data from excel file using jexcel library
        after extracting data these data will be inserted into two array list
        this method also calculates total scores by weekly and all time

     */

    private void fetchDataFromFileToList(){
        try{
            /*
                Reading excel file using jexcel library
             */
            AssetManager assetManager = getAssets();
            InputStream is = assetManager.open("scores.xls");
            Workbook wb = Workbook.getWorkbook(is);
            Sheet s = wb.getSheet(0);
            int row = s.getRows();
            int col = s.getColumns();

            /*
                reading all users score for all time and inserting into allTimeList

             */

            for(int i = 1 ; i<row ; i++){
                int sum = 0 ;
                for(int j  = 1 ; j<col ; j++){
                    Cell z = s.getCell(j,i);
                    String amountInString = z.getContents();
                    int amount = Integer.parseInt(amountInString);
                    sum += amount;

                }

                if(i==1){
                    User user = new User("User 1",sum);
                    allTimeList.add(user);
                }
                else if(i==2){
                    User user = new User("User 2",sum);
                    allTimeList.add(user);

                }
                else if(i==3){
                    User user = new User("User 3",sum);
                    allTimeList.add(user);

                }

            }

            /*
                reading all users score for this week and inserting into thisWeekList

             */

            for(int i = 1 ; i<row ; i++){
                int sum = 0 ;
                for(int j  = col-1 ; j>=col-7 ; j--){
                    Cell z = s.getCell(j,i);
                    String amountInString = z.getContents();
                    int amount = Integer.parseInt(amountInString);
                    sum += amount;

                }
                if(i==1){
                    User user = new User("User 1",sum);
                    thisWeekList.add(user);

                }
                else if(i==2){
                    User user = new User("User 2",sum);
                    thisWeekList.add(user);

                }
                else if(i==3){
                    User user = new User("User 3",sum);
                    thisWeekList.add(user);

                }
            }
        }
        catch(Exception e){

        }
    }

    /*
        After inserting data into list sortList method will sort them in descending order

     */

    private void sortList(){
        Collections.sort(thisWeekList,Collections.<User>reverseOrder());
        Collections.sort(allTimeList,Collections.<User>reverseOrder());
    }

    /*
        Now both two types of list is ready for showing
        settingAdapters method will create two adapters for two lists
        and this method also set default adapter in recyclerview

     */

    private void settingAdapters(){
        adapterForWeek = new MainAdapter(thisWeekList);
        adapterForAll = new MainAdapter(allTimeList);
        userList.setAdapter(adapterForWeek);
    }

    /*
        initializeViewByValue method will set default scores
        that means when user first time open the app it will show this week list
        money will be also shown using "comma"

        this method is also used in two button's on click listeners

     */

    private void initializeViewByValue(ArrayList<User> arrayList){

        firstAmountText.setText(User.insertCommaInMoney(arrayList.get(0).getMoney()+""));
        secondAmountText.setText(User.insertCommaInMoney(arrayList.get(1).getMoney()+""));
        thirdAmountText.setText(User.insertCommaInMoney(arrayList.get(2).getMoney()+""));

    }

    /*
        setEventListenersForButton method will set on click listener into two buttons
        and also setting neccessery properties like select, text color into two buttons

        each button on click listeners will also perform changing the 1st, 2nd, 3rd person money

     */


    private void setEventListenersForButton(){
        thisWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList.swapAdapter(adapterForWeek,false);

                allTimeButton.setSelected(false);
                thisWeekButton.setSelected(true);

                thisWeekButton.setTextColor(Color.parseColor("#FFFFFF"));
                allTimeButton.setTextColor(Color.parseColor("#ed1e79"));

                initializeViewByValue(thisWeekList);
            }
        });

        allTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList.swapAdapter(adapterForAll,false);

                thisWeekButton.setSelected(false);
                allTimeButton.setSelected(true);

                allTimeButton.setTextColor(Color.parseColor("#FFFFFF"));
                thisWeekButton.setTextColor(Color.parseColor("#ed1e79"));


                initializeViewByValue(allTimeList);
            }
        });
    }

    /*
        setImage method will set image according to button's selecting or clicking

     */

    private void setImage(){
        if(thisWeekButton.isSelected()) {
            setImageFromList(thisWeekList);
        }
        else{
            setImageFromList(allTimeList);
        }
    }


    /*
        setImageFromList method will show image of 1st, 2nd , 3rd person's image according to their name
        images are available in resources

     */

    private void setImageFromList(ArrayList<User> arrayList){
        if(arrayList.get(0).getUsername().equals("User 1")){
            firstPersonImage.setImageDrawable(getResources().getDrawable(R.drawable.user_1));
        }
        else if(arrayList.get(0).getUsername().equals("User 2")){
            firstPersonImage.setImageDrawable(getResources().getDrawable(R.drawable.user_2));
        }
        else if(arrayList.get(0).getUsername().equals("User 3")){
            firstPersonImage.setImageDrawable(getResources().getDrawable(R.drawable.user_3));
        }



        if(arrayList.get(1).getUsername().equals("User 1")){
            secondPersonImage.setImageDrawable(getResources().getDrawable(R.drawable.user_1));
        }
        else if(arrayList.get(1).getUsername().equals("User 2")){
            secondPersonImage.setImageDrawable(getResources().getDrawable(R.drawable.user_2));
        }
        else if(arrayList.get(1).getUsername().equals("User 3")){
            secondPersonImage.setImageDrawable(getResources().getDrawable(R.drawable.user_3));
        }



        if(arrayList.get(2).getUsername().equals("User 1")){
            thirdPersonImage.setImageDrawable(getResources().getDrawable(R.drawable.user_1));
        }
        else if(arrayList.get(2).getUsername().equals("User 2")){
            thirdPersonImage.setImageDrawable(getResources().getDrawable(R.drawable.user_2));
        }
        else if(arrayList.get(2).getUsername().equals("User 3")){
            thirdPersonImage.setImageDrawable(getResources().getDrawable(R.drawable.user_3));
        }


    }



}
