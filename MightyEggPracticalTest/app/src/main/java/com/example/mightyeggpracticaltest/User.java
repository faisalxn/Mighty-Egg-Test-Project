package com.example.mightyeggpracticaltest;

/*
    this is a model class of a user
    this class implements comparable for sorting

 */

public class User  implements Comparable {
    private String username;
    private int money;

    public User(String username, int money) {
        this.username = username;
        this.money = money;
    }


    public User() {

    }

    /*
        gettters and setters for member variables

     */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    /*
        compareTo method is for comparing objects when they will be sorted in an array list

     */


    @Override
    public int compareTo(Object o) {
        int compareMoney = ((User)o).getMoney();
        return this.money-compareMoney;
    }


    /*
        insertCommaInMoney method is for converting a money amount with comma


     */

    public static String insertCommaInMoney(String money){
        String temp = "";
        int count = 1 ;
        for(int i = money.length()-1 ; i>=0 ; i-- , count++){
            char ch = money.charAt(i);
            temp = ch + temp;

            if(count==3){
                temp = "," + temp;
            }

        }
        return temp ;
    }



}
