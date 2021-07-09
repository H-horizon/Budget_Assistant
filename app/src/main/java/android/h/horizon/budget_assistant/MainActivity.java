package android.h.horizon.budget_assistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.h.horizon.budget_assistant.dashboard.ExpensesActivity;
import android.h.horizon.budget_assistant.dashboard.IncomesActivity;
import android.h.horizon.budget_assistant.transaction.Transactions;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);
        setValuesField();
        setExpensesButton();
        setIncomeButton();
    }

    private void setValuesField() {
        Log.d(TAG, "setValuesField() called");
        Transactions transactionsValues = Transactions.get(MainActivity.this);
        TextView revenueTextView = (TextView) findViewById(R.id.total_incomes_value);
        TextView expenditureTextView = (TextView) findViewById(R.id.total_expenses_value);
        TextView savingTextView = (TextView) findViewById(R.id.savings_value);
        updateUI(transactionsValues, revenueTextView, expenditureTextView, savingTextView);
    }

    private void setExpensesButton() {
        Button expensesButton = (Button) findViewById(R.id.expense_button);
        Log.d(TAG, "Expenses button has been set");
        expensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Expenses button has been clicked");
                Intent i = new Intent(MainActivity.this, ExpensesActivity.class);
                startActivity(i);
            }
        });
    }

    private void setIncomeButton() {
        Button incomesButton = (Button) findViewById(R.id.income_button);
        Log.d(TAG, "Incomes button has been set");
        incomesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Incomes button has been clicked");
                Intent i = new Intent(MainActivity.this, IncomesActivity.class);
                startActivity(i);
            }
        });
    }

    private void updateUI(Transactions transactionsValues,
                          TextView revenueTextView, TextView expenditureTextView,
                          TextView savingTextView) {
        Log.d(TAG, "updateUI(Transactions transactionsValues, TextView revenueTextView, " +
                "TextView expenditureTextView, TextView savingTextView) called");
        DecimalFormat df = new DecimalFormat("###.##");
        revenueTextView.setText(df.format(transactionsValues.getRevenue()));
        expenditureTextView.setText(df.format(transactionsValues.getExpenditure()));
        if (transactionsValues.getSavings() > 0) {
            Log.d(TAG, "updateUI():Green");
            savingTextView.setTextColor(Color.parseColor("#01DEFA"));
        } else {
            savingTextView.setTextColor(Color.parseColor("#DC0000"));
            Log.d(TAG, "updateUI():Red");
        }
        savingTextView.setText(df.format(transactionsValues.getSavings()));
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
        setValuesField();
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