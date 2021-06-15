package android.h.horizon.budget_assistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.h.horizon.budget_assistant.dashboard.ExpensesActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button mExpensesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);
        setExpensesButton();
    }

    private void setExpensesButton() {
        mExpensesButton = (Button) findViewById(R.id.expense_button);
        Log.d(TAG, "Expenses button has been set");
        mExpensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Expenses button has been clicked");
                Intent i = new Intent(MainActivity.this, ExpensesActivity.class);
                startActivity(i);
            }
        });
    }

}