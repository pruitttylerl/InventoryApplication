package com.snhu.cs360.inventoryapplication2;

import android.content.Intent;
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

        Button btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(l -> {
            //pull text from editViews
            EditText txtUsername = view.findViewById(R.id.usernameEditText);
            EditText txtPassword = view.findViewById(R.id.passwordEditText);
            String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();

            if (LoginDatabase.getInstance(getContext()).validateLogin(username, password)) {
                Intent intent = new Intent(getActivity(), DisplayAllActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(getContext(), "Invalid Login", Toast.LENGTH_SHORT).show();
            }
        });

        /*Button btnNotifications = view.findViewById(R.id.btnNotifications);

        btnRegister.setOnClickListener(lRegister -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            View editView = inflater.inflate(R.layout.fragment_notification_display, null);
            builder.setView(editView);
            builder.setTitle("Notification Preference");

            Button btnEnable = editView.findViewById(R.id.btnNotifDisable);
            Button btnDisable = editView.findViewById(R.id.btnNotifEnable);

            btnEnable.setOnClickListener(lEnable -> {

        });
            btnDisable.setOnClickListener(lDisable -> {

            });

        }); */
        return view;
    }
}