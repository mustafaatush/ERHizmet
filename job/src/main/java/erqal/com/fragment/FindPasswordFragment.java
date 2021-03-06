package erqal.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import erqal.job.R;
import erqal.job.act.ControlAct;
import erqal.job.net.Constants;
import erqal.job.util.Config;
import erqal.job.util.PreferenceUtil;
import erqal.mylibrary.widget.UButton;
import erqal.mylibrary.widget.UEditText;
import erqal.mylibrary.widget.UTextView;

/**
 * Created by Administrator on 2015/12/6 0006.
 */
public class FindPasswordFragment extends Fragment {

    View mainView;

    @Bind(R.id.top_back)
    ImageView back;
    @Bind(R.id.top_title)
    UTextView title;
    @Bind(R.id.phone_number)
    UEditText phoneNumber;
    @Bind(R.id.password)
    UEditText password;
    @Bind(R.id.password_again)
    UEditText passwordAgain;
    @Bind(R.id.verifyication_code_edit)
    UEditText verifyicationCodeEdit;
    @Bind(R.id.verifyication_code_btn)
    UButton verifyicationCodeBtn;
    @Bind(R.id.register)
    UButton findPassword;
    @Bind(R.id.message)
    UTextView message;
    @Bind(R.id.phone_number_cancel)
    ImageView phoneNumberCancel;
    @Bind(R.id.password_cancel)
    ImageView passwordCancel;
    @Bind(R.id.password_again_cancel)
    ImageView passwordAgainCancel;

    private FragmentManager manager;
    private FragmentTransaction transaction;
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainView = inflater.inflate(R.layout.register, null);
        ButterKnife.bind(this, mainView);
        mainView.setOnClickListener(null);
        bindListenr();
        setTitle();
        return mainView;
    }

    //打开Fragment
    private void openFragment(Fragment fragment) {
        manager = getActivity().getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.activity_exchange_right_in, R.anim.activity_exchange_no_anim, R.anim.activity_exchange_no_anim, R.anim.activity_exchange_right_out);
        transaction.add(R.id.main_act_fram, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void bindListenr() {
        findPassword.setOnClickListener(new EventListenet());
        phoneNumberCancel.setOnClickListener(new EventListenet());
        passwordCancel.setOnClickListener(new EventListenet());
        passwordAgainCancel.setOnClickListener(new EventListenet());
        phoneNumber.addTextChangedListener(new TextWatcherListener());
        password.addTextChangedListener(new TextWatcherListener());
        passwordAgain.addTextChangedListener(new TextWatcherListener());
        back.setOnClickListener(new EventListenet());
    }

    public static Fragment getInstance() {
        FindPasswordFragment fragment = new FindPasswordFragment();
        return fragment;
    }

    private class TextWatcherListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (getActivity().getCurrentFocus().getId() == R.id.phone_number) {
                passwordCancel.setVisibility(View.INVISIBLE);
                passwordAgainCancel.setVisibility(View.INVISIBLE);
                if (!phoneNumber.getText().toString().isEmpty()) {
                    phoneNumberCancel.setVisibility(View.VISIBLE);
                } else {
                    phoneNumberCancel.setVisibility(View.INVISIBLE);
                }
            } else if (getActivity().getCurrentFocus().getId() == R.id.password) {
                phoneNumberCancel.setVisibility(View.INVISIBLE);
                passwordAgainCancel.setVisibility(View.INVISIBLE);
                if (!password.getText().toString().isEmpty()) {
                    passwordCancel.setVisibility(View.VISIBLE);
                } else {
                    passwordCancel.setVisibility(View.INVISIBLE);
                }
            } else if (getActivity().getCurrentFocus().getId() == R.id.password_again) {
                phoneNumberCancel.setVisibility(View.INVISIBLE);
                passwordCancel.setVisibility(View.INVISIBLE);
                if (!passwordAgain.getText().toString().isEmpty()) {
                    passwordAgainCancel.setVisibility(View.VISIBLE);
                } else {
                    passwordAgainCancel.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void setTitle() {
        title.setText(R.string.find_password);
        findPassword.setText(R.string.reset_);
    }

    //处理点击事件
    private class EventListenet implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.register:
                    if (localValidate()) {
                        PreferenceUtil.commitString("login_status", Config.userName);
                        ControlAct.startUp(getActivity(), Constants.MeFragment, null);
                    }
                    break;
                case R.id.phone_number_cancel:
                    phoneNumber.setText("");
                    break;
                case R.id.password_cancel:
                    password.setText("");
                    break;
                case R.id.password_again_cancel:
                    passwordAgain.setText("");
                    break;
                case R.id.top_back:
                    getActivity().onBackPressed();
                    break;
                default:
                    break;
            }
        }
    }

    public boolean localValidate() {
        boolean result = false;

        if (phoneNumber.getText().toString().equals("") || phoneNumber.equals(null)) {
            message.setText(R.string.message_text_login_empty_username);
            return result;
        } else if (!isMobile(phoneNumber.getText().toString())) {
            message.setText(R.string.login_message_text_login_error_username_format);
            return result;
        } else if (password.getText().toString().equals("") || password.equals(null)) {
            message.setText(R.string.message_text_login_empty_password);
            return result;
        } else if (password.getText().length() < 6) {
            message.setText(R.string.message_text_login_error_password_length);
            return result;
        } else if (!passwordAgain.getText().toString().equals(password.getText().toString())) {
            message.setText(R.string.message_text_login_error_password_again);
            return result;
        } else if (verifyicationCodeEdit.getText().toString().equals("")) {
            message.setText(R.string.varify_code_not_empty);
            return result;
        } else if (verifyicationCodeEdit.getText().length() < 4) {
            message.setText(R.string.register_message_text_login_error_verifycode);
            return result;
        }

        return true;
    }

    public boolean serviceValidate(int messageCode) {
        if (messageCode == Constants.NETWORK_ERROR) {
            message.setText(R.string.network_error);
            return false;
        } else if (messageCode == Constants.VARIFY_CODE_ERROR) {
            message.setText(R.string.register_message_text_login_error_verifycode);
            return false;
        } else if (messageCode == Constants.USER_NOT_EXIT) {
            message.setText(R.string.login_message_text_login_error_username_not_exist);
            return false;
        } else if (messageCode == Constants.USER_LOCKED) {
            message.setText(R.string.user_be_locked);
            return false;
        } else if (messageCode == Constants.SUCCESS) {
            message.setText(R.string.reset_password_succeed_please_login_again);
            return true;
        } else {
            message.setText(R.string.errcode_unknown);
            return false;
        }
    }

    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }


}
