package com.androidapp.tobeacontinue;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidapp.tobeacontinue.etc.Settings;
import com.androidapp.tobeacontinue.fragment.BeaconFragment;
import com.androidapp.tobeacontinue.fragment.CalendarFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback {
    //메인화면
    //메인화면은 두 부분으로 나누었다 -> 비콘을 활용한 비콘 프레그먼트 & 날짜로 저장하는 캘린더 프레그먼트

    BeaconFragment beaconFragment;          //비콘 프레그먼트 선언
    CalendarFragment calendarFragment;      //캘린더 프레그먼트 선언

    DrawerLayout drawer;                    //메뉴액티비티는 왼쪽 상단에 네비게이션 드로어를 이용했기 때문에 선언
    Toolbar toolbar;                        //액션바

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toolbar = findViewById(R.id.toolbar);
          setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        beaconFragment = new BeaconFragment();
        calendarFragment = new CalendarFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.container, beaconFragment).commit();        //기본 프레그먼트는 비콘프레그먼트임

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {           //네비게이션 드로어 열었을 때 두 가지 메뉴
        int id = item.getItemId();

        if(id==R.id.menu1){
            Toast.makeText(this,"장소로 추가", Toast.LENGTH_SHORT).show();
            OnFragmentSelected(0, null);
        }
        else if(id ==R.id.menu2){
            Toast.makeText(this,"캘린더로 추가",Toast.LENGTH_SHORT).show();
            OnFragmentSelected(1, null);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void OnFragmentSelected(int position, Bundle bundle) {               //프레그먼트 설정, 인터페이스 구현
        Fragment curFragment = null;

        if(position == 0){
            curFragment = beaconFragment;
        }
        else if(position ==1 ){
            curFragment = calendarFragment;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, curFragment).commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {                             //액션바에 오른쪽에 위치한 검색 메뉴
        getMenuInflater().inflate(R.menu.menu_main, menu);

        View v = menu.findItem(R.id.menu_search).getActionView();
        if(v!=null){
            EditText editText = v.findViewById(R.id.editText);

            if(editText != null){
                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                        Toast.makeText(getApplicationContext(),"입력됨",Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();
        switch (curId){
            case R.id.menu_search:
                Toast.makeText(this,"검색 메뉴가 선택되었습니다.",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_settings:                //검색 메뉴와 다르게 설정 메뉴는 클릭할 경우 새로운 액티비티로 전환되게 하였음
                Toast.makeText(this,"설정 메뉴가 검색되었습니다.",Toast.LENGTH_SHORT).show();
                Intent settingIntent = new Intent(this, Settings.class);
                startActivity(settingIntent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
