package android.h.horizon.budget_assistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.h.horizon.budget_assistant.dashboard.ExpensesActivity;
import android.h.horizon.budget_assistant.dashboard.IncomesActivity;
import android.h.horizon.budget_assistant.transaction.Transactions;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivity";
    public static final int ALL_TIME = 0;
    public static final int THIS_WEEK = 1;
    public static final int THIS_MONTH = 2;
    public static final int THIS_YEAR = 3;
    private int mPosition = ALL_TIME;//default value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);
        setValuesField();
        setExpensesButton();
        setIncomeButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu(Menu menu) called");
        getMenuInflater().inflate(R.menu.activity_main, menu);
        MenuItem item = menu.findItem(R.id.time_spinner);
        Spinner spinner = (Spinner) item.getActionView();
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_time_array, R.layout.spinner_items_list_main);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemSelected() called");
        mPosition = position;
        setValuesField();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(TAG, "onNothingSelected() called");
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
        Log.d(TAG, "updateUI() called");
        DecimalFormat df = new DecimalFormat("###.##");
        double revenue;
        double expenditure;
        double savings;
        if (mPosition == ALL_TIME) {
            Log.d(TAG, "updateUI(): ALL_TIME");
            revenue = transactionsValues.getRevenue();
            expenditure = transactionsValues.getExpenditure();
            savings = transactionsValues.getSavings();
            setUI(revenueTextView, expenditureTextView, savingTextView, df, revenue, expenditure,
                    savings);
        } else if (mPosition == THIS_WEEK) {
            Log.d(TAG, "updateUI(): THIS_WEEK");
            revenue = transactionsValues.getWeeklyRevenue();
            expenditure = transactionsValues.getWeeklyExpenditure();
            savings = revenue - expenditure;
            setUI(revenueTextView, expenditureTextView, savingTextView, df, revenue, expenditure,
                    savings);
        } else if (mPosition == THIS_MONTH) {
            Log.d(TAG, "updateUI(): THIS_MONTH");
            revenue = transactionsValues.getMonthlyRevenue();
            expenditure = transactionsValues.getMonthlyExpenditure();
            savings = revenue - expenditure;
            setUI(revenueTextView, expenditureTextView, savingTextView, df, revenue, expenditure,
                    savings);
        } else if (mPosition == THIS_YEAR) {
            Log.d(TAG, "updateUI(): THIS_YEAR");
            revenue = transactionsValues.getYearlyRevenue();
            expenditure = transactionsValues.getYearlyExpenditure();
            savings = revenue - expenditure;
            setUI(revenueTextView, expenditureTextView, savingTextView, df, revenue, expenditure,
                    savings);
        } else {
            Log.d(TAG, "updateUI(): UNKNOWN CONSTANT");
        }
    }

    private void setUI(TextView revenueTextView, TextView expenditureTextView,
                       TextView savingTextView, DecimalFormat df, double revenue,
                       double expenditure, double savings) {
        Log.d(TAG, "setUI() called");
        setUiText(revenueTextView, expenditureTextView, savingTextView, df, revenue, expenditure,
                savings);
        savingTextView.setText(df.format(savings));
    }

    private void setUiText(TextView revenueTextView, TextView expenditureTextView,
                           TextView savingTextView, DecimalFormat df, double revenue,
                           double expenditure, double savings) {
        Log.d(TAG, "setUiText() called");
        revenueTextView.setText(df.format(revenue));
        expenditureTextView.setText(df.format(expenditure));
        if (savings > 0) {
            Log.d(TAG, "setUiText():Green");
            savingTextView.setTextColor(Color.parseColor("#01DEFA"));
        } else {
            savingTextView.setTextColor(Color.parseColor("#DC0000"));
            Log.d(TAG, "setUiText():Red");
        }
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