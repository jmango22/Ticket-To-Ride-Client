package goldenhammer.ticket_to_ride_client.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.widget.Toast;

import goldenhammer.ticket_to_ride_client.R;

/**
 * Created by jon on 2/3/17.
 */

public class LoginActivity extends AppCompatActivity {

    private LoginPresenter loginPreseter = new LoginPresenter(this);

    private String username;
    private String password;
    private String host;
    private String port;
    
    public void toastMessage(String message) {
        Context context = this.getApplicationContext();
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onLogin(String uname) {
        toastMessage("Welcome user: "+uname);
        Intent intent = new Intent(getBaseContext(), GameSelectorActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                register();
            }
        });

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login();
            }
        });
    }

    private void register() {
        setUsername();
        setPassword();
        setURL();
        toastMessage("Sending registration with username: "+username);

        loginPreseter.sendRegistration(username, password, host, port);
    }

    private void login() {
        setUsername();
        setPassword();
        setURL();
        toastMessage("Sending login with username: "+username);


        loginPreseter.sendLogin(username, password, host, port);
    }

    private void setUsername() {
        EditText usernameText = (EditText) findViewById(R.id.usernameText);
        username = usernameText.getText().toString();
    }

    private void setPassword() {
        EditText passwordText = (EditText) findViewById(R.id.passwordText);
        password = passwordText.getText().toString();
    }

    private void setURL() {
        EditText hostText = (EditText) findViewById(R.id.hostText);
        EditText portText = (EditText) findViewById(R.id.portText);

        host = hostText.getText().toString();
        port = portText.getText().toString();

    }
}
