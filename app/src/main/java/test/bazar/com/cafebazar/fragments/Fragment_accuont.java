package test.bazar.com.cafebazar.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import test.bazar.com.cafebazar.R;


public class Fragment_accuont extends Fragment {
    Bundle bundle ;
    TextView txtUsername;
    TextView txtExit;
    View view;
    SharedPreferences sharedPreferences;
    String userName;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        bundle = getArguments();
        userName = bundle.getString("username");
        setUpView();
        txtExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getContext().getSharedPreferences("home", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", "");
                editor.apply();
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.rel_parent_allView,new FragmentHome());
                transaction.commit();
                Toast.makeText(getContext(), "شما از حساب کاربری خود خارج شدید", Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }

    private void setUpView() {
        txtUsername = view.findViewById(R.id.txt_fragmentAccount_userName);
        txtExit = view.findViewById(R.id.txt_fragmentAccount_exit);
        txtUsername.setText(userName);
    }
}
