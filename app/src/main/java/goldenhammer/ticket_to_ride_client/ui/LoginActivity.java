package goldenhammer.ticket_to_ride_client.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.content.Context;
import android.widget.Toast;

import goldenhammer.ticket_to_ride_client.R;

/**
 * Created by jon on 2/3/17.
 */

public class LoginActivity extends AppCompatActivity {

    Button loginButton = (Button) findViewById(R.id.loginButton);
    Button registerButton = (Button) findViewById(R.id.registerButton);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }

    private void toastMessage(String message) {
        Context context = this.getApplicationContext();
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
