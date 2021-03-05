package js.technology.session.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import js.technology.session.ui.session.SessionsActivity;

public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(ActivitySplashScreen.this, SessionsActivity.class);
        startActivity(intent);
        finish();
    }
}
