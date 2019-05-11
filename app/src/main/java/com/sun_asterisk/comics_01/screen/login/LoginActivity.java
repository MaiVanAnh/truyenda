package com.sun_asterisk.comics_01.screen.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.sun_asterisk.comics_01.R;
import com.sun_asterisk.comics_01.data.model.Cache;
import com.sun_asterisk.comics_01.data.model.User;
import com.sun_asterisk.comics_01.data.repository.UserRepository;
import com.sun_asterisk.comics_01.data.source.remote.UserRemoteDataSource;
import com.sun_asterisk.comics_01.screen.signup.SignUpActivity;
import com.sun_asterisk.comics_01.utils.Constant;
import com.sun_asterisk.comics_01.utils.EmailValidator;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener, LoginContract.View {
    private static final int SIGN_UP_REQUEST_CODE = 7979;
    private static final String LOGIN_SUCCESS = "Login success!";
    private TextInputEditText mEdUserName;
    private TextInputEditText mEdPassword;
    private LinearLayout mLoadingContainer;
    private ConstraintLayout mLoginContainer;
    private LoginContract.Presenter mPresenter;
    private ImageView mImgEye;
    private Boolean mIsPasswordVisible = false;

    public static Intent getLoginIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mEdUserName = findViewById(R.id.edUserName);
        mEdPassword = findViewById(R.id.edPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        ImageView imgCancel = findViewById(R.id.imgCancel);
        imgCancel.setOnClickListener(this);
        TextView tvForgot = findViewById(R.id.tvForgotPassword);
        tvForgot.setOnClickListener(this);
        TextView tvSignUp = findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(this);
        mImgEye = findViewById(R.id.imgEye);
        mImgEye.setOnClickListener(this);
        mLoginContainer = findViewById(R.id.loginContainer);
        mLoadingContainer = findViewById(R.id.loadingContainer);
        mLoadingContainer.setVisibility(View.GONE);
        TextView tvLoadingTitle = findViewById(R.id.tvLoadingTitle);
        tvLoadingTitle.setText(getString(R.string.login_loading));

        mPresenter = new LoginPresenter(
                UserRepository.getInstance(null, UserRemoteDataSource.getInstance()));
        mPresenter.setView(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                login();
                break;
            case R.id.tvForgotPassword:
                forgotPassword();
                break;
            case R.id.tvSignUp:
                gotoSignUp();
                break;
            case R.id.imgEye:
                if (mIsPasswordVisible) {
                    showPassword();
                } else {
                    hintPassword();
                }
                break;
            case R.id.imgCancel:
                finish();
            default:
                break;
        }
    }

    private void forgotPassword() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Reset your password");
        alertDialog.setMessage("Enter email");
        alertDialog.setCancelable(false);
        final EditText tvEmail = new EditText(getApplicationContext());
        tvEmail.setPadding(16, 8, 16, 24);
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
        tvEmail.setLayoutParams(lp);
        alertDialog.setView(tvEmail);

        alertDialog.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog alert = alertDialog.create();
        alert.show();

        alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tvEmail.getText().toString().trim();
                if (email.isEmpty()) {
                    tvEmail.requestFocus();
                    tvEmail.setError("Input your email!");
                } else if (!EmailValidator.isValidEmail(email)) {
                    tvEmail.requestFocus();
                    tvEmail.setError(SignUpActivity.INFO_EMAIL_ERROR);
                } else {
                    mPresenter.sendEmailResetPassword(email);
                    alert.dismiss();
                }
            }
        });
    }

    private void gotoSignUp() {
        startActivityForResult(SignUpActivity.getSignUpIntent(getApplicationContext()),
                SIGN_UP_REQUEST_CODE);
    }

    private void hintPassword() {
        mImgEye.setImageResource(R.drawable.ic_hide);
        mEdPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mIsPasswordVisible = true;
    }

    private void showPassword() {
        mImgEye.setImageResource(R.drawable.ic_visible);
        mEdPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        mIsPasswordVisible = false;
    }

    private void login() {
        String userName = Objects.requireNonNull(mEdUserName.getText()).toString().trim();
        String password = Objects.requireNonNull(mEdPassword.getText()).toString().trim();

        if (userName.isEmpty()) {
            mEdUserName.requestFocus();
        } else if (password.isEmpty()) {
            mEdPassword.requestFocus();
        } else {
            mLoadingContainer.setVisibility(View.VISIBLE);
            mPresenter.login(userName, password);
        }
    }

    @Override
    public void onLoginSuccess(User user) {
        Cache cache = Cache.getInstance();
        cache.cacheUserName(user.getUserName());
        cache.cachedName(user.getName());
        cache.cachedGender(user.getGender());
        cache.cachedBirthDay(user.getDate());
        cache.cachedEmail(user.getEmail());
        cache.cachedToken(user.getToken());
        cache.isLogin = true;

        SharedPreferences.Editor editor =
                getSharedPreferences(Constant.SHARED, MODE_PRIVATE).edit();
        editor.putString(Constant.TOKEN, user.getToken());
        editor.apply();

        Toast.makeText(getApplicationContext(), LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onLoginFail(Exception exception) {
        Snackbar.make(mLoginContainer, exception.getMessage(), Snackbar.LENGTH_SHORT).show();
        mLoadingContainer.setVisibility(View.GONE);
    }

    @Override
    public void onSendEmailSuccess(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSendEmailFail(Exception exception) {
        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_UP_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                mEdUserName.setText(data.getStringExtra(SignUpActivity.USERNAME));
                mEdPassword.setText(data.getStringExtra(SignUpActivity.PASSWORD));
            }
        }
    }
}
