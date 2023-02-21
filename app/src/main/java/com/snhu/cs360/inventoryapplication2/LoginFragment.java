package com.snhu.cs360.inventoryapplication2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment {
    private static final List<LoginFragment.LoginListener> listeners = new ArrayList<>();

    public interface LoginListener {

    }

/*    private Login login;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    public static void register(LoginFragment.LoginListener listener) { listeners.add(listener); }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);

        Button btnRegister = view.findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(l -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            View editView = inflater.inflate(R.layout.register, null);
            builder.setView(editView);
            builder.setTitle("Create Login");

            EditText txtUsername = editView.findViewById(R.id.txtCreateUsername);
            EditText txtPassword = editView.findViewById(R.id.txtCreatePassword);

            builder.setPositiveButton("Create", (dialogInterface, i) -> {

                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                Login login = new Login(username, password);
                long result = LoginDatabase.getInstance(getContext()).addLogin(login);

                if (result == -1) {
                    Toast.makeText(getContext(), "Login already exists", Toast.LENGTH_SHORT).show();}
            });

            builder.setNegativeButton("Cancel", null);
            builder.create();

            builder.show();
        });

/*        Button btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(l -> {
            EditText txtUsername = view.findViewById(R.id.usernameEditText);
            EditText txtPassword = view.findViewById(R.id.passwordEditText);
            String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();

            if (LoginDatabase.validateLogin(username, password)) {

//                getActivity().onBackPressed();
            }
        });                          */
        return view;
    }
}