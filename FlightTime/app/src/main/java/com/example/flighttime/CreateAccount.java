package com.example.flighttime;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flighttime.model.FlightDao;
import com.example.flighttime.model.FlightRoom;
import com.example.flighttime.model.LogRecord;
import com.example.flighttime.model.User;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CreateAccount extends AppCompatActivity {
    //Check if password & account meet the requirements
    private static String tester = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$";
    private Pattern pattern = Pattern.compile(tester);
    private Matcher matcher;

    int wrongFormat = 0;

    public boolean validate(final String password) {

        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("CrateAccountActivity", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creataccount);

        Button create_button = findViewById(R.id.create_account_button);
        create_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                EditText username = findViewById(R.id.user_name);
                EditText password = findViewById(R.id.password);

                //*LAB* Checking user and pw
                String error = " ";
                String pwFormatError = "Password does not meet the requirements.\n";
                String nameFormatError = "Username does not meet the requirements.\n";

                boolean userCheck = !validate(username.getText().toString());
                boolean passCheck = !validate(password.getText().toString());

                if(userCheck || passCheck){
                    if(userCheck){
                        error += nameFormatError;
                    }
                    if(passCheck){
                        error += pwFormatError;
                    }
                    TextView msg = findViewById(R.id.message);
                    msg.setText(error);

                    wrongFormat++;
                    if(wrongFormat == 2){
                        // inform user that he used to many tries -> return to main menu
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccount.this);
                        builder.setTitle("To many fails. Try again.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    return;
                }


                if (username.getText().toString().equals("!admin2")) {
                    // username already exists.
                    TextView msg = findViewById(R.id.message);
                    msg.setText("Username not available.");
                    return;
                }

                User user = FlightRoom.getFlightRoom(CreateAccount.this).
                        dao().getUserByName(username.getText().toString());

                if (user == null) {
                    // username does not exist, so add the new account
                    String name = username.getText().toString();
                    String pw = password.getText().toString();

                    User newUser = new User(name, pw);
                    FlightDao dao = FlightRoom.getFlightRoom(CreateAccount.this).dao();
                    dao.addUser(newUser);

                    //**LAB**
                    //DONE write a record to Log table with message that new Account has been created.
                    //  include username (but not password) in the message.
                    Date now = new Date();
                    LogRecord rec = new LogRecord(now, LogRecord.TYPE_NEW_ACCOUNT,
                            name, "");
                    dao.addLogRecord(rec);


                    // inform user that new account has been created
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccount.this);
                    builder.setTitle("Account successfully created.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    // username already exists.
                    TextView msg = findViewById(R.id.message);
                    msg.setText("Username not available.\n Please enter a new " +
                            "username and password.");

                    wrongFormat += 1;
                    if(wrongFormat == 2){
                        wrongFormat = 0;

                        // inform user that he used to many tries -> return to main menu
                        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAccount.this);
                        builder.setTitle("To many fails. Try again.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }

            }
        });
    }
}
