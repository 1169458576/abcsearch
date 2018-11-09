package com.zhzh.lib.abcsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private PopAbcView pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.btn);
        pop=new PopAbcView(MainActivity.this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
                pop.showPopupWindow(btn,(int)(Math.random()*100),(int)(Math.random()*100),String.valueOf((int)(Math.random()*10)));
            }
        });
    }
}
