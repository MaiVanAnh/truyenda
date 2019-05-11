package com.sun_asterisk.comics_01.screen.signup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;
import com.sun_asterisk.comics_01.R;
import com.sun_asterisk.comics_01.data.repository.UserRepository;
import com.sun_asterisk.comics_01.data.source.remote.UserRemoteDataSource;
import com.sun_asterisk.comics_01.utils.Constant;
import com.sun_asterisk.comics_01.utils.EmailValidator;
import com.sun_asterisk.comics_01.utils.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity
        implements SignUpContract.View, View.OnClickListener {
    private static final String sMyFormat = "yyyy-MM-dd";
    public static final String INFO_EMAIL_ERROR = "Email format incorrect!";
    private static final String INFO_SIZE_ERROR = "At least 8 character!";
    public static final String INFO_CONFIRM_PASSWORD_ERROR = "Confirm password incorrect!";
    private static final String INFO_FORMAT_PASSWORD_ERROR =
            "At least 8 character include character and number!";
    private static final String INFO_BIRTHDAY_ERROR = "Something was wrong!";
    private static final String INFO_BIRTHDAY_EMPTY = "Select your birthday!";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    private static final String SUCCESS = "Sign up successfully!";
    private EditText mEdUserName;
    private EditText mEdName;
    private Boolean mSex = true;
    private EditText mEdBirthDay;
    private EditText mEdEmail;
    private EditText mEdPassword;
    private EditText mEdConFirmPassword;
    private Button mBtnSignUp;
    private ImageView imgPassword;
    private LinearLayout mLoadingContainer;
    private SignUpPresenter mPresenter;
    private DatePickerDialog.OnDateSetListener mDate;
    private int mYearCurrent = Calendar.getInstance().get(Calendar.YEAR);
    private int mYearBirthday;
    private String mUserName;
    private String mPassword;
    private Boolean mIsPasswordVisible = false;
    final private Calendar mCalendar = Calendar.getInstance();

    public static Intent getSignUpIntent(Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
    }

    private void initView() {
        mEdUserName = findViewById(R.id.edUserName);
        mEdName = findViewById(R.id.edName);
        mDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYearBirthday = year;
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateBirthDay();
            }
        };
        imgPassword = findViewById(R.id.imgHintOrVisible);
        imgPassword.setOnClickListener(this);
        mEdBirthDay = findViewById(R.id.edBirthday);
        mEdBirthDay.setOnClickListener(this);
        mEdEmail = findViewById(R.id.edEmail);
        mEdPassword = findViewById(R.id.edPassword);
        mEdConFirmPassword = findViewById(R.id.edConfirmPassword);
        RadioButton rdMale = findViewById(R.id.rdMale);
        rdMale.setOnClickListener(this);
        RadioButton rdFemale = findViewById(R.id.rdFemale);
        rdFemale.setOnClickListener(this);
        mBtnSignUp = findViewById(R.id.btnSignUp);
        mBtnSignUp.setOnClickListener(this);
        ImageView imgCancel = findViewById(R.id.imgCancelSignUp);
        imgCancel.setOnClickListener(this);
        mLoadingContainer = findViewById(R.id.loadingContainer);
        mLoadingContainer.setVisibility(View.GONE);

        mPresenter = new SignUpPresenter(
                UserRepository.getInstance(null, UserRemoteDataSource.getInstance()));
        mPresenter.setView(this);
    }

    private void updateBirthDay() {
        SimpleDateFormat sdf = new SimpleDateFormat(sMyFormat, Locale.US);
        mEdBirthDay.setText(sdf.format(mCalendar.getTime()));
    }

    @Override
    public void onSignUpSuccess() {
        Snackbar.make(mBtnSignUp, SUCCESS, Snackbar.LENGTH_SHORT).show();
        mLoadingContainer.setVisibility(View.GONE);
        if (mUserName != null) {
            Intent intent = new Intent();
            intent.putExtra(USERNAME, mUserName);
            intent.putExtra(PASSWORD, mPassword);
            setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public void onSignUpFailure(Exception exception) {
        Snackbar.make(mBtnSignUp, exception.getMessage(), Snackbar.LENGTH_SHORT).show();
        mLoadingContainer.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rdMale:
                mSex = true;
                break;
            case R.id.rdFemale:
                mSex = false;
                break;
            case R.id.edBirthday:
                setBirthDay();
                break;
            case R.id.btnSignUp:
                signUp();
                break;
            case R.id.imgHintOrVisible:
                if (mIsPasswordVisible) {
                    showPassword();
                } else {
                    hintPassword();
                }
                break;
            case R.id.imgCancelSignUp:
                finish();
        }
    }

    private void hintPassword() {
        imgPassword.setImageResource(R.drawable.ic_hide);
        mEdPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mIsPasswordVisible = true;
    }

    private void showPassword() {
        imgPassword.setImageResource(R.drawable.ic_visible);
        mEdPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        mIsPasswordVisible = false;
    }

    private void setBirthDay() {
        mEdBirthDay.setError(null);
        new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDate,
                mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void signUp() {
        String userName = mEdUserName.getText().toString().trim();
        String name = mEdName.getText().toString().trim();
        String email = mEdEmail.getText().toString().trim();
        String birthday = mEdBirthDay.getText().toString().trim();
        String password = mEdPassword.getText().toString().trim();
        String confirmPassword = mEdConFirmPassword.getText().toString().trim();

        if (userName.isEmpty()) {
            mEdUserName.requestFocus();
        } else if (!StringUtils.isCorrectSize(userName)) {
            mEdUserName.requestFocus();
            mEdUserName.setError(INFO_SIZE_ERROR);
        } else if (name.isEmpty()) {
            mEdName.requestFocus();
        } else if (birthday.isEmpty()) {
            mEdBirthDay.setError(INFO_BIRTHDAY_EMPTY);
            mEdBirthDay.requestFocus();
        } else if (mYearBirthday > (mYearCurrent - 2) || mYearBirthday < (mYearCurrent - 100)) {
            mEdBirthDay.requestFocus();
            mEdBirthDay.setError(INFO_BIRTHDAY_ERROR);
        } else if (email.isEmpty()) {
            mEdEmail.requestFocus();
        } else if (!EmailValidator.isValidEmail(email)) {
            mEdEmail.requestFocus();
            mEdEmail.setError(INFO_EMAIL_ERROR);
        } else if (password.isEmpty()) {
            mEdPassword.requestFocus();
        } else if (!StringUtils.isCorrectFormatPassword(password)) {
            mEdPassword.requestFocus();
            mEdPassword.setError(INFO_FORMAT_PASSWORD_ERROR);
        } else if (confirmPassword.isEmpty()) {
            mEdConFirmPassword.requestFocus();
        } else if (!confirmPassword.equals(password)) {
            mEdConFirmPassword.setError("");
            mEdConFirmPassword.setText("");
            mEdConFirmPassword.requestFocus();
            Toast.makeText(getApplicationContext(), INFO_CONFIRM_PASSWORD_ERROR, Toast.LENGTH_LONG)
                    .show();
        } else {
            mUserName = userName;
            mPassword = password;
            mLoadingContainer.setVisibility(View.VISIBLE);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(Constant.USER_NAME, userName);
                jsonObject.put(Constant.NAME, name);
                jsonObject.put(Constant.GENDER, mSex);
                jsonObject.put(Constant.BIRTHDAY, birthday);
                jsonObject.put(Constant.EMAIL, email);
                jsonObject.put(Constant.PASSWORD, password);
                jsonObject.put(Constant.CONFIRM_PASSWORD, confirmPassword);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mPresenter.signUp(jsonObject);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
}
