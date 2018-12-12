package test.bazar.com.cafebazar.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import test.bazar.com.cafebazar.R;

public class FragmentDescription extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.description,container,false);
        TextView txtDesc;
        txtDesc = view.findViewById(R.id.txt_description);
        Bundle bundle = getArguments();
        String desc = bundle.getString("desc");
        txtDesc.setText(desc);
        return  view;
    }
}
