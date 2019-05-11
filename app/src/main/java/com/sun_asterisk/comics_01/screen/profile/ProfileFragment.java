package com.sun_asterisk.comics_01.screen.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.sun_asterisk.comics_01.R;
import com.sun_asterisk.comics_01.data.model.Cache;
import com.sun_asterisk.comics_01.data.repository.UserRepository;
import com.sun_asterisk.comics_01.data.source.remote.UserRemoteDataSource;
import com.sun_asterisk.comics_01.screen.login.LoginActivity;
import com.sun_asterisk.comics_01.screen.signup.SignUpActivity;
import com.sun_asterisk.comics_01.utils.Constant;
import com.sun_asterisk.comics_01.utils.StringUtils;
import java.util.Calendar;
import java.util.Objects;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment
        implements View.OnClickListener, ProfileContract.View {
    private static ProfileFragment sInstance;
    private static final int PROFILE_REQUEST_CODE = 8888;
    private static final String LOG_OUT_ERROR = "Log out fail!";
    private static final String EMPTY_ERROR = "Empty!";
    private static final String FORMAT_ERROR = "At least 8 character include character and number";
    private CardView mCvSignIn;
    private CardView mCvInformationNotChange;
    private CardView mCvInformationCanBeChange;
    private CardView mCvLogout;
    private TextView mTvUserName;
    private TextView mTvEmail;
    private TextView mTvName;
    private EditText mEdName;
    private TextView mTvGender;
    private RadioGroup mRadioGroup;
    private RadioButton mRbMale;
    private RadioButton mRbFemale;
    private TextView mTvBirthday;
    private DatePicker mDatePicker;
    private ConstraintLayout mViewInputPassword;
    private EditText mEdOldPassword;
    private EditText mEdNewPassword;
    private EditText mEdConfirmPassword;
    private TextView mTvEdit;
    private TextView mTvSave;
    private String mName;
    private String mBirthday;
    private Boolean mGender;
    private Boolean mIsEdit = false;
    private Boolean mIsChangePassword = false;
    private ProfilePresenter mPresenter;
    private LinearLayout mLoadingContainer;

    private Cache mCache;

    public static ProfileFragment newInstance() {
        if (sInstance == null) sInstance = new ProfileFragment();
        return sInstance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initView(view);
        bindData();
        return view;
    }

    private void bindData() {
        mCache = Cache.getInstance();
        if (mCache.isLogin) {
            setViewLogin();
        } else {
            setViewNotLogin();
        }
    }

    private void initView(View view) {

        mCvSignIn = view.findViewById(R.id.cvSignIn);
        TextView tvSignIn = view.findViewById(R.id.tvLogin);
        tvSignIn.setOnClickListener(this);

        mCvInformationNotChange = view.findViewById(R.id.cvInformationNotChange);
        mTvUserName = view.findViewById(R.id.tvUserName);
        mTvEmail = view.findViewById(R.id.tvEmail);

        mCvInformationCanBeChange = view.findViewById(R.id.cvInformationCanBeChange);
        mTvName = view.findViewById(R.id.tvName);
        mEdName = view.findViewById(R.id.edName);
        mTvGender = view.findViewById(R.id.tvGender);
        mRadioGroup = view.findViewById(R.id.radioGroup);
        mRbMale = view.findViewById(R.id.rdMaleProfile);
        mRbMale.setOnClickListener(this);
        mRbFemale = view.findViewById(R.id.rdFemaleProfile);
        mRbFemale.setOnClickListener(this);
        mTvBirthday = view.findViewById(R.id.tvBirthday);
        mDatePicker = view.findViewById(R.id.dateOfBirthInput);
        TextView tvChangePassword = view.findViewById(R.id.tvChangePassWord);
        tvChangePassword.setOnClickListener(this);
        mViewInputPassword = view.findViewById(R.id.viewInputPassword);
        mEdOldPassword = view.findViewById(R.id.edOldPassword);
        mEdNewPassword = view.findViewById(R.id.edNewPassword);
        mEdConfirmPassword = view.findViewById(R.id.edConfirmPassword);
        mTvEdit = view.findViewById(R.id.tvEdit);
        mTvEdit.setOnClickListener(this);

        mTvSave = view.findViewById(R.id.tvSave);
        mTvSave.setOnClickListener(this);

        mCvLogout = view.findViewById(R.id.cvLogout);
        TextView tvLogout = view.findViewById(R.id.tvLogout);
        tvLogout.setOnClickListener(this);

        mLoadingContainer = view.findViewById(R.id.loadingContainer);

        mPresenter = new ProfilePresenter(
                UserRepository.getInstance(null, UserRemoteDataSource.getInstance()));
        mPresenter.setView(this);
    }

    public void setViewLogin() {
        mCvSignIn.setVisibility(View.GONE);
        mCvInformationNotChange.setVisibility(View.VISIBLE);
        mCvInformationCanBeChange.setVisibility(View.VISIBLE);
        mCvLogout.setVisibility(View.VISIBLE);
        mCache = Cache.getInstance();
        if (mCache.isLogin) {
            mTvUserName.setText(mCache.getUserName());
            mTvEmail.setText(mCache.getEmail());
            mTvName.setText(mCache.getName());
            mEdName.setText(mCache.getName());
            mTvBirthday.setText(StringUtils.formatDate(mCache.getBirthDay()));
            mGender = mCache.getGender();
            mTvGender.setText(mGender ? getString(R.string.male) : getString(R.string.female));
            if (mGender) {
                mRbMale.setChecked(true);
            } else {
                mRbFemale.setChecked(true);
            }
            Calendar calendar = StringUtils.parseStringToCalendar(mCache.getBirthDay());
            mDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH), null);
        }
    }

    public void setViewNotLogin() {
        mCvSignIn.setVisibility(View.VISIBLE);
        mCvInformationNotChange.setVisibility(View.GONE);
        mCvInformationCanBeChange.setVisibility(View.GONE);
        mCvLogout.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PROFILE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                updateUi();
            }
        }
    }

    private void updateUi() {
        setViewLogin();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLogin:
                startActivityForResult(LoginActivity.getLoginIntent(getContext()),
                        PROFILE_REQUEST_CODE);
                break;
            case R.id.tvEdit:
                updateUiEdit();
                break;
            case R.id.tvChangePassWord:
                updateUiChangePassword();
                break;
            case R.id.tvLogout:
                showAlertDialogLogOut();
                break;
            case R.id.rdMale:
                setMale();
                break;
            case R.id.rdFemale:
                setFemale();
                break;
            case R.id.tvSave:
                changeInform();
                break;
            default:
                break;
        }
    }

    private void changeInform() {
        mName = mEdName.getText().toString().trim();
        mBirthday = mDatePicker.getYear()
                + "-"
                + (mDatePicker.getMonth() + 1)
                + "-"
                + mDatePicker.getDayOfMonth();
        String oldPassword = mEdOldPassword.getText().toString().trim();
        String newPassword = mEdNewPassword.getText().toString().trim();
        String confirmPassword = mEdConfirmPassword.getText().toString().trim();
        Boolean isChangePassword = false;
        if (!oldPassword.isEmpty() || !newPassword.isEmpty() || !confirmPassword.isEmpty()) {
            isChangePassword = true;
        }
        if (mName.isEmpty()) {
            mEdName.requestFocus();
            mEdName.setError(EMPTY_ERROR);
        } else {
            if (isChangePassword) {
                if (oldPassword.isEmpty()) {
                    mEdOldPassword.requestFocus();
                    mEdOldPassword.setError(EMPTY_ERROR);
                } else if (newPassword.isEmpty()) {
                    mEdNewPassword.requestFocus();
                    mEdNewPassword.setError(EMPTY_ERROR);
                } else if (!StringUtils.isCorrectFormatPassword(newPassword)) {
                    mEdNewPassword.requestFocus();
                    mEdNewPassword.setError(FORMAT_ERROR);
                } else if (confirmPassword.isEmpty()) {
                    mEdConfirmPassword.requestFocus();
                    mEdConfirmPassword.setError(EMPTY_ERROR);
                } else if (!newPassword.equals(confirmPassword)) {
                    mEdConfirmPassword.setText("");
                    mEdConfirmPassword.requestFocus();
                    Toast.makeText(getContext(), SignUpActivity.INFO_CONFIRM_PASSWORD_ERROR,
                            Toast.LENGTH_SHORT).show();
                } else {
                    mLoadingContainer.setVisibility(View.VISIBLE);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(Constant.NAME, mName);
                        jsonObject.put(Constant.BIRTHDAY, mBirthday);
                        jsonObject.put(Constant.GENDER, mGender);
                        jsonObject.put(Constant.NEW_PASSWORD, confirmPassword);
                        jsonObject.put(Constant.OLD_PASSWORD, oldPassword);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mPresenter.changeInformation(jsonObject);
                    mEdOldPassword.setText("");
                    mEdNewPassword.setText("");
                    mEdConfirmPassword.setText("");
                }
            } else {
                mLoadingContainer.setVisibility(View.VISIBLE);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(Constant.NAME, mName);
                    jsonObject.put(Constant.BIRTHDAY, mBirthday);
                    jsonObject.put(Constant.GENDER, mGender);
                    jsonObject.put(Constant.NEW_PASSWORD, "");
                    jsonObject.put(Constant.OLD_PASSWORD, "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mPresenter.changeInformation(jsonObject);
            }
        }
        mTvBirthday.setText(mBirthday);
    }

    private void setFemale() {
        mGender = false;
    }

    private void setMale() {
        mGender = true;
    }

    private void showAlertDialogLogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.log_out));
        builder.setMessage(getString(R.string.confirm_log_out));
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        builder.setNegativeButton(getString(R.string.agree), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                mLoadingContainer.setVisibility(View.VISIBLE);
                mPresenter.logOut();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateUiChangePassword() {
        if (!mIsChangePassword) {
            mIsChangePassword = true;
            mViewInputPassword.setVisibility(View.VISIBLE);
            mTvSave.setVisibility(View.VISIBLE);
        } else {
            mIsChangePassword = false;
            mViewInputPassword.setVisibility(View.GONE);
            mTvSave.setVisibility(View.GONE);
        }
    }

    private void updateUiEdit() {
        if (!mIsEdit) {
            onViewEdit();
        } else {
            offViewEdit();
        }
    }

    private void onViewEdit() {
        mTvEdit.setText(getString(R.string.cancel));
        mIsEdit = true;
        mEdName.setVisibility(View.VISIBLE);
        mDatePicker.setVisibility(View.VISIBLE);
        mRadioGroup.setVisibility(View.VISIBLE);
        mTvSave.setVisibility(View.VISIBLE);
    }

    private void offViewEdit() {
        mTvEdit.setText(getString(R.string.edit));
        mIsEdit = false;
        mEdName.setVisibility(View.GONE);
        mDatePicker.setVisibility(View.GONE);
        mRadioGroup.setVisibility(View.GONE);
        mViewInputPassword.setVisibility(View.GONE);
        mTvSave.setVisibility(View.GONE);
    }

    @Override
    public void onLogOutSuccess() {
        SharedPreferences.Editor editor = Objects.requireNonNull(this.getActivity())
                .getSharedPreferences(Constant.SHARED, Context.MODE_PRIVATE)
                .edit();
        editor.remove(Constant.TOKEN);
        editor.apply();
        mCache = null;
        mLoadingContainer.setVisibility(View.GONE);
        setViewNotLogin();
    }

    @Override
    public void onLogOutFailure() {
        mLoadingContainer.setVisibility(View.GONE);
        Toast.makeText(getContext(), LOG_OUT_ERROR, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChangeInformationSuccess() {
        mTvName.setText(mName);
        mTvBirthday.setText(mBirthday);
        offViewEdit();
        mLoadingContainer.setVisibility(View.GONE);
    }

    @Override
    public void onChangeInformationFailure(Exception exception) {
        mLoadingContainer.setVisibility(View.GONE);
        Snackbar.make(mCvInformationCanBeChange, exception.getMessage(), Snackbar.LENGTH_SHORT)
                .show();
    }
}
