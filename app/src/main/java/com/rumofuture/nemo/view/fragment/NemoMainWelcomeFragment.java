package com.rumofuture.nemo.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.view.activity.NemoLoginActivity;
import com.rumofuture.nemo.view.activity.NemoRegisterActivity;

public class NemoMainWelcomeFragment extends Fragment {

    public NemoMainWelcomeFragment() {

    }

    public static NemoMainWelcomeFragment newInstance() {
        return new NemoMainWelcomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_main_welcome, container, false);

        Button logInButton = view.findViewById(R.id.log_in_button);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NemoLoginActivity.actionStart(getActivity());
            }
        });

        Button signUpButton = view.findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NemoRegisterActivity.actionStart(getActivity());
            }
        });

        return view;
    }
}
