package com.rumofuture.nemo.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.contract.MyEmailBindContract;

import cn.bmob.v3.exception.BmobException;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyEmailBindFragment extends Fragment implements MyEmailBindContract.View {

    private MyEmailBindContract.Presenter mPresenter;

    private NemoProgressBarFragment mProgressBar;
    private EditText mEmailView;

    public MyEmailBindFragment() {
        
    }

    public static MyEmailBindFragment newInstance() {
        return new MyEmailBindFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mProgressBar = NemoProgressBarFragment.newInstance(getResources().getString(R.string.prompt_binding));
        View view = inflater.inflate(R.layout.fragment_nemo_email_bind, container, false);

        mEmailView = (EditText) view.findViewById(R.id.email_view);
        final Button emailBindButton = (Button) view.findViewById(R.id.email_bind_button);

        emailBindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.bindEmailRequest(mEmailView.getText().toString().trim());
            }
        });

        return view;
    }

    @Override
    public void setPresenter(MyEmailBindContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar(boolean show) {
        if (show) {
            mProgressBar.show(getFragmentManager(), null);
        } else {
            mProgressBar.dismiss();
        }
    }

    @Override
    public void showEmailError(Integer stringId) {
        if (null == stringId) {
            mEmailView.setError(null);
        } else {
            mEmailView.setError("邮箱地址格式错误");
            mEmailView.requestFocus();
        }
    }

    @Override
    public void showEmailBindRequestSuccess(String prompt) {
        Toast.makeText(getActivity(), prompt, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showEmailBindRequestFailed(BmobException e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
