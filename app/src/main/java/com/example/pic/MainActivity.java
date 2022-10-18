package com.example.pic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    OfficeFragment officeFragment = new OfficeFragment();
    ActivoFragment activoFragment = new ActivoFragment();
    EnviarFragment enviarFragment = new EnviarFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, officeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.oficina:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, officeFragment).commit();
                        return true;
                    case R.id.pdf:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, activoFragment).commit();
                        return true;
                    case R.id.send:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, enviarFragment).commit();
                        return true;
                }
                return false;
            }
        });









    }
}