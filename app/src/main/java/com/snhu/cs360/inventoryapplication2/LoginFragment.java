package com.snhu.cs360.inventoryapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoginFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Login screen
        View view = inflater.inflate(R.layout.login, container, false);

        Button btnRegister = view.findViewById(R.id.btnRegister);

        //Register button
        btnRegister.setOnClickListener(l -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            //register screen
            View editView = inflater.inflate(R.layout.register, null);
            builder.setView(editView);
            builder.setTitle("Create Login");

            //Pull text from editTexts
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

        //Submit button
        Button btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(l -> {

            //Pull text from editTexts
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

        //Notification button
        AtomicBoolean notifStatus = new AtomicBoolean(false);

        Button btnNotifications = view.findViewById(R.id.btnNotifications);

        btnNotifications.setOnClickListener(lRegister -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            View editView = inflater.inflate(R.layout.fragment_notification_display, null);
            builder.setView(editView);
            builder.setTitle("Notification Preference");

            Button btnEnable = editView.findViewById(R.id.btnNotifEnable);
            Button btnDisable = editView.findViewById(R.id.btnNotifDisable);

            //Initial button status to reflect notification status
            if (notifStatus.get()){
                btnEnable.setEnabled(false);
            } else {
                btnEnable.setEnabled(true);
            }

            //Enable button
        // notifStatus is true when enabled. Enable button is disabled when true
            btnEnable.setOnClickListener(lEnable -> {
            notifStatus.set(true);
            btnEnable.setEnabled(false);
            btnDisable.setEnabled(true);

        });

            //Disable button
            // notifStatus is false when disabled. Disable button is disabled when false
            btnDisable.setOnClickListener(lDisable -> {
                notifStatus.set(false);
                btnDisable.setEnabled(false);
                btnEnable.setEnabled(true);

            });

            builder.create();

            builder.show();

        });
        return view;
    }
}