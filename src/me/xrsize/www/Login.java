package me.xrsize.www;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Login extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Querys q = new Querys();
        String result = q.login("pontus", "peppar");
        Log.d("LOGIN", result);
        setContentView(R.layout.login);
        
    }
}