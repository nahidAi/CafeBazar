package test.bazar.com.cafebazar;

import android.graphics.Typeface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ss.bottomnavigation.BottomNavigation;
import com.ss.bottomnavigation.events.OnSelectedItemChangeListener;

import test.bazar.com.cafebazar.fragments.FragmentApps;
import test.bazar.com.cafebazar.fragments.FragmentBests;
import test.bazar.com.cafebazar.fragments.FragmentCats;
import test.bazar.com.cafebazar.fragments.FragmentHome;
import test.bazar.com.cafebazar.fragments.FragmentSearch;

public class MainActivity extends AppCompatActivity {
    Typeface myTypeface;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();

    }





    public  void  setupViews(){
        BottomNavigation bottomNavigation=(BottomNavigation)findViewById(R.id.bottom_navigation);
        // این مال آیتم پیش فرض هست که خودکار روی این آیتم باشه موقع اجرا
        bottomNavigation.setDefaultItem(4);
        myTypeface = Typeface.createFromAsset(getAssets(),"irsans_font.ttf");
        bottomNavigation.setTypeface(myTypeface);
        bottomNavigation.setOnSelectedItemChangeListener(new OnSelectedItemChangeListener() {
            @Override
            public void onSelectedItemChanged(int itemId) {
                switch (itemId){
                    case R.id.tab_home:
                        transaction=getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.rel_fragment_containers,new FragmentHome());
                        break;
                    case R.id.tab_bests:
                        transaction=getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.rel_fragment_containers,new FragmentBests());
                        break;
                    case R.id.tab_cats:
                        transaction=getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.rel_fragment_containers,new FragmentCats());
                        break;
                    case R.id.tab_search:
                        transaction=getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.rel_fragment_containers,new FragmentSearch());
                        break;
                    case R.id.tab_apps:
                        transaction=getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.rel_fragment_containers,new FragmentApps());
                        break;
                }
                transaction.commit();


            }
        });
    }
}
