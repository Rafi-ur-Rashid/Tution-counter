package com.example.rf.tutioncounter.presenter;

import android.content.Context;

import com.example.rf.tutioncounter.interfaces.consumerRequest;

/**
 * Created by RF on 7/16/2017.
 */

public class FirstActivityPresenter {
    private consumerRequest cons;
    private Context context;
    DataEmptyCheckPresenter tuttut;

    public FirstActivityPresenter(consumerRequest cons, Context context) {
        this.cons = cons;
        this.context = context;
        tuttut=new DataEmptyCheckPresenter(context);
    }

    public void gotoNext() {
        if (tuttut.chekckEmptiness())
            cons.goToNextActivity(true);
        else {
            cons.goToNextActivity(false);
        }

    }
}

