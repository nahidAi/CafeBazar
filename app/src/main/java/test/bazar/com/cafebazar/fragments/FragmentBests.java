package test.bazar.com.cafebazar.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.bazar.com.cafebazar.R;
import test.bazar.com.cafebazar.adapters.AppViewPagerAdapter;


public class FragmentBests extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bests,container,false);
        tabLayout = view.findViewById(R.id.tab_fragmentBest_tabLayout);
        viewPager = view.findViewById(R.id.vp_fragmentBest_viewpager);
        AppViewPagerAdapter adapter = new AppViewPagerAdapter(getChildFragmentManager());


        adapter.addFragment(new FragmentPersian(),"فارسی ها");
        adapter.addFragment(new FragmentMostPopular(),"محبوب ترین ها");
        adapter.addFragment(new FragmentMostSell(),"پرفروش ها");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(2);
        tabLayout.setupWithViewPager(viewPager);
        return  view;
    }
}
