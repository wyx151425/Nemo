package com.rumofuture.nemo.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.contract.NemoLoginContract;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.view.activity.NemoPasswordUpdateActivity;
import com.rumofuture.nemo.view.activity.NemoRegisterActivity;

public class NemoLoginFragment extends Fragment implements NemoLoginContract.View {

    private NemoLoginContract.Presenter mPresenter;

    private NemoProgressBarFragment mProgressBar;

    private EditText mMobilePhoneNumberView;
    private EditText mPasswordView;

    public NemoLoginFragment() {

    }

    public static NemoLoginFragment newInstance() {
        return new NemoLoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mProgressBar = NemoProgressBarFragment.newInstance(getString(R.string.prompt_logging_in));
        View view = inflater.inflate(R.layout.fragment_nemo_log_in, container, false);

        mMobilePhoneNumberView = view.findViewById(R.id.user_mobile_phone_number_view);
        mPasswordView = view.findViewById(R.id.user_password_view);

        Button mMobilePhoneNumberLogInButton = view.findViewById(R.id.log_in_button);
        mMobilePhoneNumberLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.login(
                        mMobilePhoneNumberView.getText().toString().trim(),
                        mPasswordView.getText().toString().trim()
                );
            }
        });

        TextView passwordUpdateButton = view.findViewById(R.id.password_update_button);
        passwordUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NemoPasswordUpdateActivity.actionStart(getActivity());
            }
        });

        TextView signUpButton = view.findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NemoRegisterActivity.actionStart(getActivity());
            }
        });

        return view;
    }

    @Override
    public void setPresenter(NemoLoginContract.Presenter presenter) {
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
    public void showMobilePhoneNumberError(Integer stringId) {
        if (null == stringId) {
            mMobilePhoneNumberView.setError(null);
        } else {
            mMobilePhoneNumberView.setError(getString(stringId));
            mMobilePhoneNumberView.requestFocus();
        }
    }

    @Override
    public void showPasswordError(Integer stringId) {
        if (null == stringId) {
            mPasswordView.setError(null);
        } else {
            mPasswordView.setError(getString(stringId));
            mPasswordView.requestFocus();
        }
    }

    @Override
    public void showLoginSuccess(User user) {
        Toast.makeText(getActivity(), R.string.prompt_log_in_success, Toast.LENGTH_LONG).show();
        getActivity().finish();
    }

    @Override
    public void showLoginFailed(String message) {
        Toast.makeText(getActivity(), R.string.prompt_log_in_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
