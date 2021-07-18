package android.h.horizon.budget_assistant;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.h.horizon.budget_assistant.dashboard.ExpensesActivity;
import android.h.horizon.budget_assistant.dashboard.IncomesActivity;
import android.h.horizon.budget_assistant.transaction.Transaction;
import android.h.horizon.budget_assistant.transaction.TransactionContainer;
import android.h.horizon.budget_assistant.transaction.Transactions;
import android.os.Build;
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
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ALL_TIME = 0;
    private static final int TODAY = 1;
    private static final int THIS_WEEK = 2;
    private static final int THIS_MONTH = 3;
    private static final int THIS_YEAR = 4;
    private int mTimeSpinnerPosition = ALL_TIME;//default value
    private int mTransactionSpinnerPosition = 0;
    String[] mTransactionCategory;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);
        setValuesField();
        setExpensesButton();
        setIncomeButton();
        Spinner spinner = findViewById(R.id.transactions_spinner);
        spinner.setOnItemSelectedListener(new TransactionCategorySpinnerClass());
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transactions_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        mTransactionCategory = getResources().getStringArray(R.array.transactions_array);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu(Menu menu) called");
        getMenuInflater().inflate(R.menu.activity_main, menu);
        MenuItem item = menu.findItem(R.id.time_spinner);
        Spinner spinner = (Spinner) item.getActionView();
        spinner.setOnItemSelectedListener(new TimePeriodSpinnerClass());
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_time_array, R.layout.spinner_time_list_main);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        return true;
    }

    class TimePeriodSpinnerClass implements AdapterView.OnItemSelectedListener {
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            Log.d(TAG, "onItemSelected() for Time period called");
            mTimeSpinnerPosition = position;
            setValuesField();
            //Toast.makeText(v.getContext(), "Your choose :"+ position,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    class TransactionCategorySpinnerClass implements AdapterView.OnItemSelectedListener {
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            Log.d(TAG, "onItemSelected() for Transaction Category called");
            mTransactionSpinnerPosition = position;
            setValuesField();
            Toast.makeText(v.getContext(), mTransactionCategory[position] + " chosen",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private DataPoint[] createDataPointsForBarChart() {
        DataPoint[] dataPoints = new DataPoint[4];
        Transactions transactionsValues = Transactions.get(MainActivity.this);
        TransactionContainer transactionContainer = TransactionContainer.get(MainActivity.this);
        List<Transaction> transactions = transactionContainer.
                getTransactions(mTransactionCategory[mTransactionSpinnerPosition]);
        int position = mTimeSpinnerPosition;
        if (mTransactionSpinnerPosition > 0 && mTransactionSpinnerPosition <= 7) {
            setDataPointsForExpenditure(transactionsValues, transactions, dataPoints, position);
        } else if (mTransactionSpinnerPosition > 7 && mTransactionSpinnerPosition <= 10) {
            setDataPointsForRevenue(transactionsValues, transactions, dataPoints, position);
        }
        return dataPoints;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private DataPoint[] createDataPointsForRevenue() {
        Transactions transactionsValues = Transactions.get(MainActivity.this);
        TransactionContainer transactionContainer = TransactionContainer.get(MainActivity.this);
        List<Transaction> transactions = transactionContainer.getTransactions();
        DataPoint[] dataPoints = new DataPoint[4];
        int position = mTimeSpinnerPosition;
        setDataPointsForRevenue(transactionsValues, transactions, dataPoints, position);
        return dataPoints;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDataPointsForRevenue(Transactions transactionsValues,
                                         List<Transaction> transactions,
                                         DataPoint[] dataPoints, int position) {
        switch (position) {
            case TODAY:
                Instant now = Instant.now();
                Date today = Date.from(now);
                Date oneDayAgo = Date.from(now.minus(1, ChronoUnit.DAYS));
                Date twoDaysAgo = Date.from(now.minus(2, ChronoUnit.DAYS));
                Date tomorrow = Date.from(now.plus(1, ChronoUnit.DAYS));
                dataPoints[0] = new DataPoint(twoDaysAgo, transactionsValues.getDayRevenue(twoDaysAgo, transactions));
                dataPoints[1] = new DataPoint(oneDayAgo, transactionsValues.getDayRevenue(oneDayAgo, transactions));
                dataPoints[2] = new DataPoint(today, transactionsValues.getDayRevenue(today, transactions));
                dataPoints[3] = new DataPoint(tomorrow, 0);
                break;
            case THIS_WEEK:
                Instant currentWeek = Instant.now();
                Date thisWeek = Date.from(currentWeek);
                Date oneWeekAgo = Date.from(currentWeek.minus(7, ChronoUnit.DAYS));
                Date twoWeeksAgo = Date.from(currentWeek.minus(14, ChronoUnit.DAYS));
                Date nextWeek = Date.from(currentWeek.plus(7, ChronoUnit.DAYS));
                dataPoints[0] = new DataPoint(twoWeeksAgo, transactionsValues.getWeekRevenue(twoWeeksAgo, transactions));
                dataPoints[1] = new DataPoint(oneWeekAgo, transactionsValues.getWeekRevenue(oneWeekAgo, transactions));
                dataPoints[2] = new DataPoint(thisWeek, transactionsValues.getWeekRevenue(thisWeek, transactions));
                dataPoints[3] = new DataPoint(nextWeek, 0);
                break;
            case THIS_MONTH:
                Instant currentMonth = Instant.now();
                Date thisMonth = Date.from(currentMonth);
                Date oneMonthAgo = Date.from(currentMonth.minus(30, ChronoUnit.DAYS));
                Date twoMonthsAgo = Date.from(currentMonth.minus(60, ChronoUnit.DAYS));
                Date nextMonth = Date.from(currentMonth.plus(1, ChronoUnit.DAYS));
                dataPoints[0] = new DataPoint(twoMonthsAgo, transactionsValues.getMonthRevenue(twoMonthsAgo, transactions));
                dataPoints[1] = new DataPoint(oneMonthAgo, transactionsValues.getMonthRevenue(oneMonthAgo, transactions));
                dataPoints[2] = new DataPoint(thisMonth, transactionsValues.getMonthRevenue(thisMonth, transactions));
                dataPoints[3] = new DataPoint(nextMonth, 0);
                break;

            case ALL_TIME:
            case THIS_YEAR:
                Instant currentYear = Instant.now();
                Date thisYear = Date.from(currentYear);
                Date oneYearAgo = Date.from(currentYear.minus(365, ChronoUnit.DAYS));
                Date twoYearsAgo = Date.from(currentYear.minus(730, ChronoUnit.DAYS));
                Date nextYear = Date.from(currentYear.plus(365, ChronoUnit.DAYS));
                dataPoints[0] = new DataPoint(twoYearsAgo, transactionsValues.getYearRevenue(twoYearsAgo, transactions));
                dataPoints[1] = new DataPoint(oneYearAgo, transactionsValues.getYearRevenue(oneYearAgo, transactions));
                dataPoints[2] = new DataPoint(thisYear, transactionsValues.getYearRevenue(thisYear, transactions));
                dataPoints[3] = new DataPoint(nextYear, 0);
                break;
            default:
                //Add log here
                break;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private DataPoint[] createDataPointsForExpenditure() {
        Transactions transactionsValues = Transactions.get(MainActivity.this);
        TransactionContainer transactionContainer = TransactionContainer.get(MainActivity.this);
        List<Transaction> transactions = transactionContainer.getTransactions();
        DataPoint[] dataPoints = new DataPoint[4];
        int position = mTimeSpinnerPosition;
        setDataPointsForExpenditure(transactionsValues, transactions, dataPoints, position);
        return dataPoints;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDataPointsForExpenditure(Transactions transactionsValues, List<Transaction> transactions, DataPoint[] dataPoints, int position) {
        switch (position) {
            case TODAY:
                Instant now = Instant.now();
                Date today = Date.from(now);
                Date oneDayAgo = Date.from(now.minus(1, ChronoUnit.DAYS));
                Date twoDaysAgo = Date.from(now.minus(2, ChronoUnit.DAYS));
                Date tomorrow = Date.from(now.plus(1, ChronoUnit.DAYS));
                dataPoints[0] = new DataPoint(twoDaysAgo, transactionsValues.getDayExpenditure(twoDaysAgo, transactions));
                dataPoints[1] = new DataPoint(oneDayAgo, transactionsValues.getDayExpenditure(oneDayAgo, transactions));
                dataPoints[2] = new DataPoint(today, transactionsValues.getDayExpenditure(today, transactions));
                dataPoints[3] = new DataPoint(tomorrow, 0);
                break;
            case THIS_WEEK:
                Instant currentWeek = Instant.now();
                Date thisWeek = Date.from(currentWeek);
                Date oneWeekAgo = Date.from(currentWeek.minus(7, ChronoUnit.DAYS));
                Date twoWeeksAgo = Date.from(currentWeek.minus(14, ChronoUnit.DAYS));
                Date nextWeek = Date.from(currentWeek.plus(7, ChronoUnit.DAYS));
                dataPoints[0] = new DataPoint(twoWeeksAgo, transactionsValues.getWeekExpenditure(twoWeeksAgo, transactions));
                dataPoints[1] = new DataPoint(oneWeekAgo, transactionsValues.getWeekExpenditure(oneWeekAgo, transactions));
                dataPoints[2] = new DataPoint(thisWeek, transactionsValues.getWeekExpenditure(thisWeek, transactions));
                dataPoints[3] = new DataPoint(nextWeek, 0);
                break;
            case THIS_MONTH:
                Instant currentMonth = Instant.now();
                Date thisMonth = Date.from(currentMonth);
                Date oneMonthAgo = Date.from(currentMonth.minus(30, ChronoUnit.DAYS));
                Date twoMonthsAgo = Date.from(currentMonth.minus(60, ChronoUnit.DAYS));
                Date nextMonth = Date.from(currentMonth.plus(1, ChronoUnit.DAYS));
                dataPoints[0] = new DataPoint(twoMonthsAgo, transactionsValues.getMonthExpenditure(twoMonthsAgo, transactions));
                dataPoints[1] = new DataPoint(oneMonthAgo, transactionsValues.getMonthExpenditure(oneMonthAgo, transactions));
                dataPoints[2] = new DataPoint(thisMonth, transactionsValues.getMonthExpenditure(thisMonth, transactions));
                dataPoints[3] = new DataPoint(nextMonth, 0);
                break;

            case ALL_TIME:
            case THIS_YEAR:
                Instant currentYear = Instant.now();
                Date thisYear = Date.from(currentYear);
                Date oneYearAgo = Date.from(currentYear.minus(365, ChronoUnit.DAYS));
                Date twoYearsAgo = Date.from(currentYear.minus(730, ChronoUnit.DAYS));
                Date nextYear = Date.from(currentYear.plus(365, ChronoUnit.DAYS));
                dataPoints[0] = new DataPoint(twoYearsAgo, transactionsValues.getYearExpenditure(twoYearsAgo, transactions));
                dataPoints[1] = new DataPoint(oneYearAgo, transactionsValues.getYearExpenditure(oneYearAgo, transactions));
                dataPoints[2] = new DataPoint(thisYear, transactionsValues.getYearExpenditure(thisYear, transactions));
                dataPoints[3] = new DataPoint(nextYear, 0);
                break;
            default:
                //Add log here
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setValuesField() {
        Log.d(TAG, "setValuesField() called");
        Transactions transactionsValues = Transactions.get(MainActivity.this);
        TextView revenueTextView = findViewById(R.id.total_incomes_value);
        TextView expenditureTextView = findViewById(R.id.total_expenses_value);
        TextView savingTextView = findViewById(R.id.savings_value);
        updateUI(transactionsValues, revenueTextView, expenditureTextView, savingTextView);
    }

    private void setExpensesButton() {
        Button expensesButton = findViewById(R.id.expense_button);
        Log.d(TAG, "Expenses button has been set");
        expensesButton.setOnClickListener(v -> {
            Log.d(TAG, "Expenses button has been clicked");
            Intent i = new Intent(MainActivity.this, ExpensesActivity.class);
            startActivity(i);
        });
    }

    private void setIncomeButton() {
        Button incomesButton = findViewById(R.id.income_button);
        Log.d(TAG, "Incomes button has been set");
        incomesButton.setOnClickListener(v -> {
            Log.d(TAG, "Incomes button has been clicked");
            Intent i = new Intent(MainActivity.this, IncomesActivity.class);
            startActivity(i);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateUI(Transactions transactionsValues,
                          TextView revenueTextView, TextView expenditureTextView,
                          TextView savingTextView) {
        Log.d(TAG, "updateUI() called");
        DecimalFormat df = new DecimalFormat("###.##");

        switch (mTimeSpinnerPosition) {
            case ALL_TIME:
                Log.d(TAG, "updateUI(): ALL_TIME");
                updateUiAllTime(transactionsValues, revenueTextView, expenditureTextView,
                        savingTextView, df);
                break;
            case TODAY:
                Log.d(TAG, "updateUI(): TODAY");
                updateUiToday(transactionsValues, revenueTextView, expenditureTextView,
                        savingTextView, df);
                break;
            case THIS_WEEK:
                Log.d(TAG, "updateUI(): THIS_WEEK");
                updateUiWeekly(transactionsValues, revenueTextView, expenditureTextView,
                        savingTextView, df);
                break;
            case THIS_MONTH:
                Log.d(TAG, "updateUI(): THIS_MONTH");
                updateUiMonthly(transactionsValues, revenueTextView, expenditureTextView,
                        savingTextView, df);
                break;
            case THIS_YEAR:
                Log.d(TAG, "updateUI(): THIS_YEAR");
                updateUiYearly(transactionsValues, revenueTextView, expenditureTextView,
                        savingTextView, df);
                break;
            default:
                Log.d(TAG, "updateUI(): UNKNOWN CONSTANT");
        }
        createGraph();
    }

    private void updateUiAllTime(Transactions transactionsValues, TextView revenueTextView,
                                 TextView expenditureTextView, TextView savingTextView,
                                 DecimalFormat df) {
        Log.d(TAG, "updateUiAllTime() called");
        double revenue;
        double expenditure;
        double savings;
        revenue = transactionsValues.getRevenue();
        expenditure = transactionsValues.getExpenditure();
        savings = transactionsValues.getSavings();
        setUI(revenueTextView, expenditureTextView, savingTextView, df, revenue, expenditure,
                savings);
    }

    private void updateUiToday(Transactions transactionsValues, TextView revenueTextView,
                               TextView expenditureTextView, TextView savingTextView,
                               DecimalFormat df) {
        Log.d(TAG, "updateUiToday() called");
        double revenue;
        double expenditure;
        double savings;
        revenue = transactionsValues.getTodayRevenue();
        expenditure = transactionsValues.getTodayExpenditure();
        savings = revenue - expenditure;
        setUI(revenueTextView, expenditureTextView, savingTextView, df, revenue, expenditure,
                savings);
    }

    private void updateUiWeekly(Transactions transactionsValues, TextView revenueTextView,
                                TextView expenditureTextView, TextView savingTextView,
                                DecimalFormat df) {
        Log.d(TAG, "updateUiWeekly() called");
        double revenue;
        double expenditure;
        double savings;
        revenue = transactionsValues.getThisWeekRevenue();
        expenditure = transactionsValues.getThisWeekExpenditure();
        savings = revenue - expenditure;
        setUI(revenueTextView, expenditureTextView, savingTextView, df, revenue, expenditure,
                savings);
    }

    private void updateUiMonthly(Transactions transactionsValues, TextView revenueTextView,
                                 TextView expenditureTextView, TextView savingTextView,
                                 DecimalFormat df) {
        Log.d(TAG, "updateUiMonthly() called");
        double revenue;
        double expenditure;
        double savings;
        revenue = transactionsValues.getThisMonthRevenue();
        expenditure = transactionsValues.getThisMonthExpenditure();
        savings = revenue - expenditure;
        setUI(revenueTextView, expenditureTextView, savingTextView, df, revenue, expenditure,
                savings);
    }

    private void updateUiYearly(Transactions transactionsValues, TextView revenueTextView,
                                TextView expenditureTextView, TextView savingTextView,
                                DecimalFormat df) {
        Log.d(TAG, "updateUiYearly() called");
        double revenue;
        double expenditure;
        double savings;
        revenue = transactionsValues.getThisYearRevenue();
        expenditure = transactionsValues.getThisYearExpenditure();
        savings = revenue - expenditure;
        setUI(revenueTextView, expenditureTextView, savingTextView, df, revenue, expenditure,
                savings);
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

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createGraph() {//Should be called from update ui
        GraphView transactionGraph = findViewById(R.id.transactions_graph);
        transactionGraph.removeAllSeries();//remove previous data
        SimpleDateFormat simpleDateFormat;
        switch (mTimeSpinnerPosition) {
            case TODAY:
            case THIS_WEEK:
            case THIS_MONTH:
                simpleDateFormat = new SimpleDateFormat("dd-MM");
                break;
            case ALL_TIME:
            case THIS_YEAR:
                simpleDateFormat = new SimpleDateFormat("yyyy");
                break;
            default:
                simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");//dummy format
        }
        transactionGraph.getGridLabelRenderer().setLabelFormatter(
                new DateAsXAxisLabelFormatter(MainActivity.this, simpleDateFormat));
        transactionGraph.getGridLabelRenderer().setNumHorizontalLabels(5);
        transactionGraph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        transactionGraph.getViewport().setDrawBorder(true);

        //set revenue line graph
        if (mTransactionSpinnerPosition == 0) {
            LineGraphSeries<DataPoint> revenueLineSeries = new LineGraphSeries<>(new DataPoint[0]);
            revenueLineSeries.resetData(createDataPointsForRevenue());
            revenueLineSeries.setColor(Color.GREEN);
            transactionGraph.addSeries(revenueLineSeries);

            // set Expenditure line graph
            LineGraphSeries<DataPoint> expenditureLineSeries = new LineGraphSeries<>(new DataPoint[0]);
            expenditureLineSeries.resetData(createDataPointsForExpenditure());
            expenditureLineSeries.setColor(Color.RED);
            transactionGraph.addSeries(expenditureLineSeries);
        } else if (mTransactionSpinnerPosition > 0 && mTransactionSpinnerPosition <= 10) {
            // set category bar chart
            BarGraphSeries<DataPoint> categoryBarChart = new BarGraphSeries<>(new DataPoint[0]);
            categoryBarChart.resetData(createDataPointsForBarChart());
            transactionGraph.addSeries(categoryBarChart);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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