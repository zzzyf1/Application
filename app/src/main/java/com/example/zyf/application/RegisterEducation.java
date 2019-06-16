package com.example.zyf.application;

import com.example.zyf.application.Db.*;
import com.example.zyf.application.Utils.*;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterEducation extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mTradeMarkView;//注册商标
    private EditText mPhoneView;
    private EditText mAddressView;
    private EditText mDomainView;
    private EditText minAge;
    private EditText maxAge;
    private EditText mConfirmPasswordView;
    private EditText mDescribeView;
    private View mProgressView;
    private View mLoginFormView;
    private String TradeMark;
    private String email;
    private String phone;
    private String Address;
    private String Domain;
    private String Min;
    private String Max;
    private String password;
    private String Confirm;
    private String Describe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_education);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        // Show the Up button in the action bar.
        /*ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        */
        // Set up the login form.
        mTradeMarkView=findViewById(R.id.tradeMark);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPhoneView=findViewById(R.id.phone);
        mAddressView=findViewById(R.id.address1);
        mDomainView=findViewById(R.id.domain1);
        minAge=findViewById(R.id.minAge);
        maxAge=findViewById(R.id.maxAge);
        mConfirmPasswordView=findViewById(R.id.confirmPwd);
        mDescribeView=findViewById(R.id.describe);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mTradeMarkView.setError(null);
        mPhoneView.setError(null);
        mAddressView.setError(null);
        mDomainView.setError(null);
        minAge.setError(null);
        maxAge.setError(null);
        mConfirmPasswordView.setError(null);
        mDescribeView.setError(null);
        // Store values at the time of the login attempt.
        TradeMark=mTradeMarkView.getText().toString();
        email = mEmailView.getText().toString();
        phone=mPhoneView.getText().toString();
        Address=mAddressView.getText().toString();
        Domain=mDomainView.getText().toString();
        Min=minAge.getText().toString();
        Max=maxAge.getText().toString();
        password = mPasswordView.getText().toString();
        Confirm=mConfirmPasswordView.getText().toString();
        Describe=mDescribeView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        //检查密码和确认密码
        if (TextUtils.isEmpty(password)||TextUtils.isEmpty(Confirm)) {
            if(TextUtils.isEmpty(password)){
                mPasswordView.setError(getString(R.string.error_field_required));
                focusView = mPasswordView;
                cancel = true;
            }
            if(TextUtils.isEmpty(Confirm)){
                mConfirmPasswordView.setError(getString(R.string.error_field_required));
                focusView = mConfirmPasswordView;
                cancel = true;
            }
        }else if(!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }else if (!isConfirmPasswordValid(password,Confirm)) {
            mConfirmPasswordView.setError(getString(R.string.error_invalid_confirm));
            focusView = mConfirmPasswordView;
            cancel = true;
        }
        // 检查商标
        if (TextUtils.isEmpty(TradeMark)) {
            mTradeMarkView.setError(getString(R.string.error_field_required));
            focusView = mTradeMarkView;
            cancel = true;
        } else if (!isTradeMarkValid(TradeMark)) {
            mTradeMarkView.setError(getString(R.string.error_invalid_TM));
            focusView = mTradeMarkView;
            cancel = true;
        }
        // 检查电话
        if (TextUtils.isEmpty(phone)) {
            mPhoneView.setError(getString(R.string.error_field_required));
            focusView = mPhoneView;
            cancel = true;
        } else if (!isPhoneValid(phone)) {
            mPhoneView.setError(getString(R.string.error_invalid_phone));
            focusView = mPhoneView;
            cancel = true;
        }
        //检查地址
        if (TextUtils.isEmpty(Address)) {
            mAddressView.setError(getString(R.string.error_field_required));
            focusView = mAddressView;
            cancel = true;
        }
        //检查教育领域
        if (TextUtils.isEmpty(Domain)) {
            mDomainView.setError(getString(R.string.error_field_required));
            focusView = mDomainView;
            cancel = true;
        }
        // 检查年龄
        if (TextUtils.isEmpty(Min)||TextUtils.isEmpty(Max)) {
            if(TextUtils.isEmpty(Min)){
                minAge.setError(getString(R.string.error_field_required));
                focusView = minAge;
                cancel = true;
            }
            if(TextUtils.isEmpty(Max)){
                maxAge.setError(getString(R.string.error_field_required));
                focusView = maxAge;
                cancel = true;
            }
        } else if (!isAgeValid(Min,Max)) {
            minAge.setError(getString(R.string.error_invalid_age));
            focusView = minAge;
            cancel = true;
        }
        // 检查简短介绍
        if (TextUtils.isEmpty(Describe)) {
            mDescribeView.setError(getString(R.string.error_field_required));
            focusView = mDescribeView;
            cancel = true;
        } else if (!isDescribeValid(Describe)) {
            mDescribeView.setError(getString(R.string.error_invalid_describe));
            focusView = mDescribeView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    private boolean isTradeMarkValid(String TradeMark){
        return TradeMark.length()==6;
    }
    private boolean isPhoneValid(String phone){
        return phone.length()==11;
    }
    private boolean isAgeValid(String min,String max){
        int MinAge=Integer.parseInt(min);
        int MaxAge=Integer.parseInt(max);
        return MinAge>=3&&MaxAge<=80&&MinAge<MaxAge;
    }
    private boolean isConfirmPasswordValid(String pwd1,String pwd2){
        return pwd1.equals(pwd2);
    }
    private boolean isDescribeValid(String des){
        return des.length()<20;
    }
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(RegisterEducation.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private String returnCode;
        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                /**
                 * -1 更新失败
                 * -2 邮箱已被占用
                 * -3 教育机构已通过审核，无法注册
                 * -4 不能修改邮箱信息
                 * 0 注册成功
                 * 1 更新成功*/
                education medu =new education();
                email memail= new email();
                phone mphone=new phone();
                edu_Information information=new edu_Information();
                //教育机构
                medu.setEducation_id(TradeMark);
                medu.setDomain(Domain);
                medu.setAddress(Address);
                medu.setMaxAge(Integer.parseInt(Min));
                medu.setMinAge(Integer.parseInt(Max));
                medu.setDescribe(Describe);
                medu.setStar(0);
                //邮箱
                memail.setuEmail(email);
                memail.setuPassword(password);
                memail.setuType("1");//教育机构邮箱标识码
                //电话
                mphone.setPhoneNumber(phone);
                //申请表单
                information.setmEducation(medu);
                information.setmEmail(memail);
                List<phone> list=new ArrayList<>();
                list.add(mphone);
                information.setmPhone(list);
                //将表单转换为json字符串
                String str=transformJson.edu_InformationToJson(information);
                returnCode=SyncHttpUtil.sendOkHttpRequest("http://49.140.124.219:8081/myApplication/RegisterEducation",str);

            } catch (Exception e) {
                returnCode="-6";
                return false;
            }
            if(returnCode!=null&&!(returnCode.equals("0")||returnCode.equals("1"))){
                return false;
            }else{
                return true;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            AlertDialog.Builder dialog=new AlertDialog.Builder(RegisterEducation.this);
            if (success) {
                if(returnCode.equals("0")){
                    dialog.setTitle("申请成功");
                }else{
                    dialog.setTitle("更改成功");
                }
                dialog.setMessage("等待审核中，审核成功前你可以修改申请信息");
            } else {
                if(returnCode.equals("-1")||returnCode.equals("-6")||returnCode.equals("请求超时")){
                    dialog.setTitle("失败");
                    dialog.setMessage("服务器忙请稍后再提交信息");
                }
                if(returnCode.equals("-2")){
                    dialog.setTitle("失败");
                    dialog.setMessage("邮箱已被占用");
                }
                if(returnCode.equals("-3")){
                    dialog.setTitle("失败");
                    dialog.setMessage("教育机构已通过审核，无法注册");
                }
                if(returnCode.equals("-4")){
                    dialog.setTitle("失败");
                    dialog.setMessage("审核通过前不能修改邮箱信息");
                }

            }
            dialog.setCancelable(false);
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.show();
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

