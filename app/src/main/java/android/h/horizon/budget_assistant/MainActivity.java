package android.h.horizon.budget_assistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.h.horizon.budget_assistant.dashboard.ExpensesActivity;
import android.h.horizon.budget_assistant.dashboard.IncomesActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button mExpensesButton;
    private Button mIncomesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);
        setExpensesButton();
        setIncomeButton();
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

    private void setIncomeButton() {
        mIncomesButton = (Button) findViewById(R.id.income_button);
        Log.d(TAG, "Incomes button has been set");
        mIncomesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Incomes button has been clicked");
                Intent i = new Intent(MainActivity.this, IncomesActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

}