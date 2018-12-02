package cs240.trevash.familymap;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

import cs240.trevash.familymap.Models.DataModel;
import cs240.trevash.familymap.shared.Objects.EventsResult;
import cs240.trevash.familymap.shared.Objects.LoginRequest;
import cs240.trevash.familymap.shared.Objects.PersonsResult;
import cs240.trevash.familymap.shared.Objects.RegisterLoginResult;
import cs240.trevash.familymap.shared.Objects.RegisterRequest;
import cs240.trevash.familymap.WebAccess.EventsDataTask;
import cs240.trevash.familymap.WebAccess.LoginTask;
import cs240.trevash.familymap.WebAccess.PersonsDataTask;
import cs240.trevash.familymap.WebAccess.RegisterTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements LoginTask.Context, RegisterTask.Context, PersonsDataTask.Context, EventsDataTask.Context {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String request = "request";
    private static final String contextParam = "context";
    private static final String LOGIN_TAG = "LOGIN FRAGMENT";

    // TODO: Rename and change types of parameters
    private EditText mHost;
    private EditText mPort;
    private EditText mUserName;
    private EditText mPassword;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private String mGender;
    private RegisterLoginResult mResult;
    private ContextInfo mContextInfo;
    private RegisterLoginResult mLoginResult;
    private DataModel mDataModel = DataModel.getDataModel();


    private RadioButton mMaleRadioButton;
    private Button mSignInButton;
    private Button mRegisterButton;
    private RadioGroup mRadioGroup;

    private OnFragmentInteractionListener mListener;

    public interface ContextInfo {
        void personInfoReturn(PersonsResult ancestors);
        void eventInfoReturn(EventsResult events);
    }

    public LoginFragment() {}

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mHost = (EditText) v.findViewById(R.id.host_editText);
        mHost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkTextSet();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mPort = (EditText) v.findViewById(R.id.port_editText);
        mPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkTextSet();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mUserName = (EditText) v.findViewById(R.id.userName_editText);
        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkTextSet();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mPassword = (EditText) v.findViewById(R.id.password_editText);
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkTextSet();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mFirstName = (EditText) v.findViewById(R.id.firstName_editText);
        mFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkTextSet();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mLastName = (EditText) v.findViewById(R.id.lastName_editText);
        mLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkTextSet();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mEmail = (EditText) v.findViewById(R.id.email_editText);
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkTextSet();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mMaleRadioButton = (RadioButton) v.findViewById(R.id.male_radioButton);
        mMaleRadioButton.setChecked(true);

        mRadioGroup = (RadioGroup) v.findViewById(R.id.radioGroup_radioGroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male_radioButton:
                        mGender = "M";
                        break;
                    case R.id.female_radioButton:
                        mGender = "F";
                        break;
                    default:
                        mGender = "M";
                        break;
                }
            }
        });

        mSignInButton = (Button) v.findViewById(R.id.signIn_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mSignInButton.setEnabled(false);
                mRegisterButton.setEnabled(false);
                String mHostText = mHost.getText().toString();
                String mPortText = mPort.getText().toString();
                String mUserNameText = mUserName.getText().toString();
                String mPasswordText = mPassword.getText().toString();

                LoginClicked(new LoginRequest(mUserNameText, mPasswordText), mHostText, mPortText);
            }
        });
        mSignInButton.setEnabled(false);

        mRegisterButton = (Button) v.findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignInButton.setEnabled(false);
                mRegisterButton.setEnabled(false);
                String mHostText = mHost.getText().toString();
                String mPortText = mPort.getText().toString();
                String mUserNameText = mUserName.getText().toString();
                String mPasswordText = mPassword.getText().toString();
                String mFirstNameText = mFirstName.getText().toString();
                String mLastNameText = mLastName.getText().toString();
                String mEmailText = mEmail.getText().toString();

                RegisterClicked(new RegisterRequest(mUserNameText, mPasswordText, mEmailText, mFirstNameText, mLastNameText, mGender), mHostText, mPortText);
            }
        });
        mRegisterButton.setEnabled(false);
        return v;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void LoginClicked(LoginRequest request, String host, String port) {
        try {
            LoginTask login = new LoginTask(this, request);
            login.execute(new URL("http://" + host + ":" + port + "/user/login"));
        }
        catch (MalformedURLException e) {
            Log.e(LOGIN_TAG, e.getMessage(), e);
            Toast.makeText(getActivity(), R.string.invalid_url, Toast.LENGTH_SHORT).show();
        }
    }

    public void RegisterClicked(RegisterRequest request, String host, String port) {
        try {
            RegisterTask register = new RegisterTask(this, request);
            register.execute(new URL("http://" + host + ":" + port + "/user/register"));
        }
        catch (MalformedURLException e) {
            Log.e(LOGIN_TAG, e.getMessage(), e);
            Toast.makeText(getActivity(), R.string.invalid_url, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loginInfo(RegisterLoginResult result) {
        checkTextSet();

        if (result.getMessage() != null) {
            Toast.makeText(getActivity(), R.string.failed_login + result.getMessage(), Toast.LENGTH_SHORT).show();
        }
        else {
            mLoginResult = result;
            mDataModel.setAuthToken(result.getCurrAuth());
            mDataModel.setHost(mHost.getText().toString());
            mDataModel.setPort(mPort.getText().toString());


            PersonsDataTask personsDataTask = new PersonsDataTask(this, result.getCurrAuth().toString());
            EventsDataTask eventsDataTask = new EventsDataTask(this, result.getCurrAuth().toString());

            try {
                personsDataTask.execute(new URL("http://" + mHost.getText() + ":" + mPort.getText() + "/person"));
                eventsDataTask.execute(new URL("http://" + mHost.getText() + ":" + mPort.getText() + "/event"));
            }
            catch (MalformedURLException e) {
                Log.e(LOGIN_TAG, e.getMessage(), e);
                Toast.makeText(getActivity(), R.string.invalid_url, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void registerInfo(RegisterLoginResult result) {
        checkTextSet();

        if (result.getMessage() != null) {
            Toast.makeText(getActivity(), R.string.failed_register + result.getMessage(), Toast.LENGTH_SHORT).show();
        }
        else {
            mLoginResult = result;
            mDataModel.setAuthToken(result.getCurrAuth());

            PersonsDataTask personsDataTask = new PersonsDataTask(this, result.getCurrAuth().toString());
            EventsDataTask eventsDataTask = new EventsDataTask(this, result.getCurrAuth().toString());

            try {
                personsDataTask.execute(new URL("http://" + mHost.getText() + ":" + mPort.getText() + "/person"));
                eventsDataTask.execute(new URL("http://" + mHost.getText() + ":" + mPort.getText() + "/event"));
            }
            catch (MalformedURLException e) {
                Log.e(LOGIN_TAG, e.getMessage(), e);
                Toast.makeText(getActivity(), R.string.invalid_url, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void personInfo(PersonsResult result) {
        for (int i = 0; i < result.getData().size(); i++) {
            if (mLoginResult.getPersonId().equals(result.getData().get(i).getPersonId())) {
                mDataModel.setUser(result.getData().get(i));
                mDataModel.setUserID(mLoginResult.getPersonId());
            }
        }
        Toast.makeText(getActivity(), "Welcome " + mDataModel.getUser().getFirstName() + " " + mDataModel.getUser().getLastName(), Toast.LENGTH_SHORT).show();
        mContextInfo = (ContextInfo) getActivity();
        mContextInfo.personInfoReturn(result);
    }

    @Override
    public void eventInfo(EventsResult result) {
        mContextInfo = (ContextInfo) getActivity();
        mContextInfo.eventInfoReturn(result);
    }

    private void checkTextSet() {
        if (!TextUtils.isEmpty(mHost.getText()) && !TextUtils.isEmpty(mPort.getText()) && !TextUtils.isEmpty(mUserName.getText()) && !TextUtils.isEmpty(mPassword.getText())) {
            mSignInButton.setEnabled(true);
            if (!TextUtils.isEmpty(mFirstName.getText()) && !TextUtils.isEmpty(mLastName.getText()) && !TextUtils.isEmpty(mEmail.getText())) mRegisterButton.setEnabled(true);
            else mRegisterButton.setEnabled(false);
        }
        else mSignInButton.setEnabled(false);
    }
}
