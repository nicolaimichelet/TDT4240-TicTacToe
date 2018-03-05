package com.mygdx.game.Singleton;

/**
 * Created by eiriksandberg on 06.02.2018.
 */

public class Singleton {
    private static Singleton mySingletonInstance = null;
    private int mySingletonValue;

    private Singleton(int i) {
        mySingletonValue = i;
    }

    public static Singleton getInstance(){
        if(mySingletonInstance == null){
            mySingletonInstance = new Singleton(10);
        }
        return mySingletonInstance;
    }

    public int getSingletonValue(){
        return this.mySingletonValue;
    }

    public void setSingletonValue(int i){
        mySingletonValue = i;
    }
}
