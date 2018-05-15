package com.enflo.interfaces;

import android.view.View;

public interface OnDataPass  {
    void onDataPass(Boolean data);

    void firstSet(String username, String email, String pass);

    void secondSet(int selector);

    void thirdSetAttend(String [] types);

    void thirdSetPromo(String comp, String location, String [] tags);

    void onClickActivity(View view);
}