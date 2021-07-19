package android.h.horizon.budget_assistant;

import androidx.annotation.NonNull;
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
    public static final int NONE_INDEX = 0;
    public static final int LAST_EXPENSE_INDEX = 8;
    public static final int LAST_INCOME_INDEX = 11;
    public static final int NUM_HORIZONTAL_LABELS = 5;
    public static final int NUMBER_OF_DATA_POINTS = 4;
    public static final int ONE_DAY = 1;
    public static final int ONE_WEEK = 7;
    public static final int ONE_MONTH = 30;
    public static final int ONE_YEAR = 365;
    private int mTimeSpinnerPosition = ALL_TIME;//default value
    private int mTransactionSpinnerPosition = NONE_INDEX;
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
        setTransactionSpinner();
        setTransactionCategory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu(Menu menu) called");
        getMenuInflater().inflate(R.menu.activity_main, menu);
        MenuItem item = menu.findItem(R.id.time_spinner);
        setTimePeriodSpinner(item);
        return true;
    }

    class TimePeriodSpinnerClass implements AdapterView.OnItemSelectedListener {
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            Log.d(TAG, "onItemSelected() for Time period called");
            mTimeSpinnerPosition = position;
            setValuesField();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            //Intentionally left blank
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
            //Intentionally left blank
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

    private void setTransactionSpinner() {
        Log.d(TAG, "setTransactionSpinner() called");
        Spinner spinner = findViewById(R.id.transactions_spinner);
        spinner.setOnItemSelectedListener(new TransactionCategorySpinnerClass());
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transactions_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void setTransactionCategory() {
        Log.d(TAG, "setTransactionCategory() called");
        mTransactionCategory = getResources().getStringArray(R.array.transactions_array);
    }

    private void setTimePeriodSpinner(MenuItem item) {
        Log.d(TAG, "setTimePeriodSpinner(MenuItem item) called");
        Spinner spinner = (Spinner) item.getActionView();
        spinner.setOnItemSelectedListener(new TimePeriodSpinnerClass());
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_time_array, R.layout.spinner_time_list_main);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
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
    private void createGraph() {
        Log.d(TAG, "createGraph() called");
        GraphView transactionGraph = findViewById(R.id.transactions_graph);
        transactionGraph.removeAllSeries();//remove previous data
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = getSimpleDateFormat();
        initialiseGraphView(transactionGraph, simpleDateFormat);
        setGraphs(transactionGraph);

    }

    @SuppressLint("SimpleDateFormat")
    @NonNull
    private SimpleDateFormat getSimpleDateFormat() {
        Log.d(TAG, "getSimpleDateFormat() called");
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
        return simpleDateFormat;
    }

    private void initialiseGraphView(GraphView transactionGraph,
                                     SimpleDateFormat simpleDateFormat) {
        Log.d(TAG, "initialiseGraphView() called");
        transactionGraph.getGridLabelRenderer().setLabelFormatter(
                new DateAsXAxisLabelFormatter(MainActivity.this, simpleDateFormat));
        transactionGraph.getGridLabelRenderer().setNumHorizontalLabels(NUM_HORIZONTAL_LABELS);
        transactionGraph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        transactionGraph.getViewport().setDrawBorder(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setGraphs(GraphView transactionGraph) {
        Log.d(TAG, "setGraphs() called");
        if (mTransactionSpinnerPosition == NONE_INDEX) {
            setRevenueLineGraph(transactionGraph);
            setExpenditureLineGraph(transactionGraph);
        } else if (mTransactionSpinnerPosition > NONE_INDEX
                && mTransactionSpinnerPosition <= LAST_INCOME_INDEX) {
            setCategoryBarChart(transactionGraph);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setRevenueLineGraph(GraphView transactionGraph) {
        Log.d(TAG, "setRevenueLineGraph() called");
        LineGraphSeries<DataPoint> revenueLineSeries = new LineGraphSeries<>(new DataPoint[0]);
        revenueLineSeries.resetData(createDataPointsForRevenue());
        revenueLineSeries.setColor(Color.GREEN);
        transactionGraph.addSeries(revenueLineSeries);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setExpenditureLineGraph(GraphView transactionGraph) {
        Log.d(TAG, "setExpenditureLineGraph() called");
        LineGraphSeries<DataPoint> expenditureLineSeries = new LineGraphSeries<>(new DataPoint[0]);
        expenditureLineSeries.resetData(createDataPointsForExpenditure());
        expenditureLineSeries.setColor(Color.RED);
        transactionGraph.addSeries(expenditureLineSeries);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setCategoryBarChart(GraphView transactionGraph) {
        Log.d(TAG, "setCategoryBarChart() called");
        BarGraphSeries<DataPoint> categoryBarChart = new BarGraphSeries<>(new DataPoint[0]);
        categoryBarChart.resetData(createDataPointsForBarChart());
        transactionGraph.addSeries(categoryBarChart);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private DataPoint[] createDataPointsForRevenue() {
        Log.d(TAG, "createDataPointsForRevenue() called");
        Transactions transactionsValues = Transactions.get(MainActivity.this);
        TransactionContainer transactionContainer = TransactionContainer.get(MainActivity.this);
        List<Transaction> transactions = transactionContainer.getTransactions();
        DataPoint[] dataPoints = new DataPoint[NUMBER_OF_DATA_POINTS];
        int position = mTimeSpinnerPosition;
        setDataPointsForRevenue(transactionsValues, transactions, dataPoints, position);
        return dataPoints;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private DataPoint[] createDataPointsForExpenditure() {
        Log.d(TAG, "createDataPointsForExpenditure() called");
        Transactions transactionsValues = Transactions.get(MainActivity.this);
        TransactionContainer transactionContainer = TransactionContainer.get(MainActivity.this);
        List<Transaction> transactions = transactionContainer.getTransactions();
        DataPoint[] dataPoints = new DataPoint[NUMBER_OF_DATA_POINTS];
        int position = mTimeSpinnerPosition;
        setDataPointsForExpenditure(transactionsValues, transactions, dataPoints, position);
        return dataPoints;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private DataPoint[] createDataPointsForBarChart() {
        Log.d(TAG, "createDataPointsForBarChart() called");
        DataPoint[] dataPoints = new DataPoint[NUMBER_OF_DATA_POINTS];
        Transactions transactionsValues = Transactions.get(MainActivity.this);
        TransactionContainer transactionContainer = TransactionContainer.get(MainActivity.this);
        List<Transaction> transactions = transactionContainer.
                getTransactions(mTransactionCategory[mTransactionSpinnerPosition]);
        int position = mTimeSpinnerPosition;
        setDataPointsForBarChart(dataPoints, transactionsValues, transactions, position);
        return dataPoints;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDataPointsForBarChart(DataPoint[] dataPoints, Transactions transactionsValues,
                                          List<Transaction> transactions, int position) {
        Log.d(TAG, "setDataPointsForBarChart() called");
        if (mTransactionSpinnerPosition > NONE_INDEX && mTransactionSpinnerPosition <=
                LAST_EXPENSE_INDEX) {
            setDataPointsForExpenditure(transactionsValues, transactions, dataPoints, position);
        } else if (mTransactionSpinnerPosition > LAST_EXPENSE_INDEX
                && mTransactionSpinnerPosition <= LAST_INCOME_INDEX) {
            setDataPointsForRevenue(transactionsValues, transactions, dataPoints, position);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDataPointsForRevenue(Transactions transactionsValues,
                                         List<Transaction> transactions,
                                         DataPoint[] dataPoints, int position) {
        Log.d(TAG, "setDataPointsForRevenue() called");
        switch (position) {
            case TODAY:
                setDataPointsForTodayRevenue(transactionsValues, transactions, dataPoints);
                break;
            case THIS_WEEK:
                setDataPointsForThisWeekRevenue(transactionsValues, transactions, dataPoints);
                break;
            case THIS_MONTH:
                setDataPointsForThisMonthRevenue(transactionsValues, transactions, dataPoints);
                break;
            case ALL_TIME:
            case THIS_YEAR:
                setDataPointsForThisYearRevenue(transactionsValues, transactions, dataPoints);
                break;
            default:
                Log.d(TAG, "Invalid TimePeriodSpinner index");
                break;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDataPointsForTodayRevenue(Transactions transactionsValues,
                                              List<Transaction> transactions,
                                              DataPoint[] dataPoints) {
        Log.d(TAG, "setDataPointsForTodayRevenue() called");
        Instant now = Instant.now();
        Date today = Date.from(now);
        Date oneDayAgo = Date.from(now.minus(ONE_DAY, ChronoUnit.DAYS));
        Date twoDaysAgo = Date.from(now.minus(2 * ONE_DAY, ChronoUnit.DAYS));
        Date tomorrow = Date.from(now.plus(ONE_DAY, ChronoUnit.DAYS));
        dataPoints[0] = new DataPoint(twoDaysAgo, transactionsValues.getDayRevenue(twoDaysAgo,
                transactions));
        dataPoints[1] = new DataPoint(oneDayAgo, transactionsValues.getDayRevenue(oneDayAgo,
                transactions));
        dataPoints[2] = new DataPoint(today, transactionsValues.getDayRevenue(today,
                transactions));
        dataPoints[3] = new DataPoint(tomorrow, 0);//No value will be associated
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDataPointsForThisWeekRevenue(Transactions transactionsValues,
                                                 List<Transaction> transactions,
                                                 DataPoint[] dataPoints) {
        Log.d(TAG, "setDataPointsForThisWeekRevenue() called");
        Instant currentWeek = Instant.now();
        Date thisWeek = Date.from(currentWeek);
        Date oneWeekAgo = Date.from(currentWeek.minus(ONE_WEEK, ChronoUnit.DAYS));
        Date twoWeeksAgo = Date.from(currentWeek.minus(2 * ONE_WEEK,
                ChronoUnit.DAYS));
        Date nextWeek = Date.from(currentWeek.plus(ONE_WEEK, ChronoUnit.DAYS));
        dataPoints[0] = new DataPoint(twoWeeksAgo, transactionsValues.getWeekRevenue(twoWeeksAgo,
                transactions));
        dataPoints[1] = new DataPoint(oneWeekAgo, transactionsValues.getWeekRevenue(oneWeekAgo,
                transactions));
        dataPoints[2] = new DataPoint(thisWeek, transactionsValues.getWeekRevenue(thisWeek,
                transactions));
        dataPoints[3] = new DataPoint(nextWeek, 0);//No value will be associated
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDataPointsForThisMonthRevenue(Transactions transactionsValues,
                                                  List<Transaction> transactions,
                                                  DataPoint[] dataPoints) {
        Log.d(TAG, "setDataPointsForThisMonthRevenue() called");
        Instant currentMonth = Instant.now();
        Date thisMonth = Date.from(currentMonth);
        Date oneMonthAgo = Date.from(currentMonth.minus(ONE_MONTH, ChronoUnit.DAYS));
        Date twoMonthsAgo = Date.from(currentMonth.minus(2 * ONE_MONTH,
                ChronoUnit.DAYS));
        Date nextMonth = Date.from(currentMonth.plus(ONE_MONTH, ChronoUnit.DAYS));
        dataPoints[0] = new DataPoint(twoMonthsAgo, transactionsValues.getMonthRevenue(twoMonthsAgo,
                transactions));
        dataPoints[1] = new DataPoint(oneMonthAgo, transactionsValues.getMonthRevenue(oneMonthAgo,
                transactions));
        dataPoints[2] = new DataPoint(thisMonth, transactionsValues.getMonthRevenue(thisMonth,
                transactions));
        dataPoints[3] = new DataPoint(nextMonth, 0);//No value will be associated
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDataPointsForThisYearRevenue(Transactions transactionsValues,
                                                 List<Transaction> transactions,
                                                 DataPoint[] dataPoints) {
        Log.d(TAG, "setDataPointsForThisYearRevenue() called");
        Instant currentYear = Instant.now();
        Date thisYear = Date.from(currentYear);
        Date oneYearAgo = Date.from(currentYear.minus(ONE_YEAR, ChronoUnit.DAYS));
        Date twoYearsAgo = Date.from(currentYear.minus(2 * ONE_YEAR,
                ChronoUnit.DAYS));
        Date nextYear = Date.from(currentYear.plus(ONE_YEAR, ChronoUnit.DAYS));
        dataPoints[0] = new DataPoint(twoYearsAgo, transactionsValues.getYearRevenue(twoYearsAgo,
                transactions));
        dataPoints[1] = new DataPoint(oneYearAgo, transactionsValues.getYearRevenue(oneYearAgo,
                transactions));
        dataPoints[2] = new DataPoint(thisYear, transactionsValues.getYearRevenue(thisYear,
                transactions));
        dataPoints[3] = new DataPoint(nextYear, 0);//No value will be associated
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDataPointsForExpenditure(Transactions transactionsValues,
                                             List<Transaction> transactions,
                                             DataPoint[] dataPoints, int position) {
        Log.d(TAG, "setDataPointsForExpenditure() called");
        switch (position) {
            case TODAY:
                setDataPointForTodayExpenditure(transactionsValues, transactions, dataPoints);
                break;
            case THIS_WEEK:
                setDataPointsForThisWeekExpenditure(transactionsValues, transactions, dataPoints);
                break;
            case THIS_MONTH:
                setDataPointsForThisMonthExpenditure(transactionsValues, transactions, dataPoints);
                break;
            case ALL_TIME:
            case THIS_YEAR:
                setDataPointsForThisYearExpenditure(transactionsValues, transactions, dataPoints);
                break;
            default:
                Log.d(TAG, "Invalid TransactionCategorySpinner index");
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDataPointForTodayExpenditure(Transactions transactionsValues,
                                                 List<Transaction> transactions,
                                                 DataPoint[] dataPoints) {
        Log.d(TAG, "setDataPointForTodayExpenditure() called");
        Instant now = Instant.now();
        Date today = Date.from(now);
        Date oneDayAgo = Date.from(now.minus(ONE_DAY, ChronoUnit.DAYS));
        Date twoDaysAgo = Date.from(now.minus(2 * ONE_DAY, ChronoUnit.DAYS));
        Date tomorrow = Date.from(now.plus(ONE_DAY, ChronoUnit.DAYS));
        dataPoints[0] = new DataPoint(twoDaysAgo, transactionsValues.getDayExpenditure(twoDaysAgo,
                transactions));
        dataPoints[1] = new DataPoint(oneDayAgo, transactionsValues.getDayExpenditure(oneDayAgo,
                transactions));
        dataPoints[2] = new DataPoint(today, transactionsValues.getDayExpenditure(today,
                transactions));
        dataPoints[3] = new DataPoint(tomorrow, 0);//No value will be associated
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDataPointsForThisWeekExpenditure(Transactions transactionsValues,
                                                     List<Transaction> transactions,
                                                     DataPoint[] dataPoints) {
        Log.d(TAG, "setDataPointsForThisWeekExpenditure() called");
        Instant currentWeek = Instant.now();
        Date thisWeek = Date.from(currentWeek);
        Date oneWeekAgo = Date.from(currentWeek.minus(ONE_WEEK, ChronoUnit.DAYS));
        Date twoWeeksAgo = Date.from(currentWeek.minus(2 * ONE_WEEK,
                ChronoUnit.DAYS));
        Date nextWeek = Date.from(currentWeek.plus(ONE_WEEK, ChronoUnit.DAYS));
        dataPoints[0] = new DataPoint(twoWeeksAgo, transactionsValues.getWeekExpenditure(
                twoWeeksAgo, transactions));
        dataPoints[1] = new DataPoint(oneWeekAgo, transactionsValues.getWeekExpenditure(oneWeekAgo,
                transactions));
        dataPoints[2] = new DataPoint(thisWeek, transactionsValues.getWeekExpenditure(thisWeek,
                transactions));
        dataPoints[3] = new DataPoint(nextWeek, 0);//No value will be associated
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDataPointsForThisMonthExpenditure(Transactions transactionsValues,
                                                      List<Transaction> transactions,
                                                      DataPoint[] dataPoints) {
        Log.d(TAG, "setDataPointsForThisMonthExpenditure() called");
        Instant currentMonth = Instant.now();
        Date thisMonth = Date.from(currentMonth);
        Date oneMonthAgo = Date.from(currentMonth.minus(ONE_MONTH, ChronoUnit.DAYS));
        Date twoMonthsAgo = Date.from(currentMonth.minus(2 * ONE_MONTH,
                ChronoUnit.DAYS));
        Date nextMonth = Date.from(currentMonth.plus(ONE_MONTH, ChronoUnit.DAYS));
        dataPoints[0] = new DataPoint(twoMonthsAgo, transactionsValues.getMonthExpenditure(
                twoMonthsAgo, transactions));
        dataPoints[1] = new DataPoint(oneMonthAgo, transactionsValues.getMonthExpenditure(
                oneMonthAgo, transactions));
        dataPoints[2] = new DataPoint(thisMonth, transactionsValues.getMonthExpenditure(
                thisMonth, transactions));
        dataPoints[3] = new DataPoint(nextMonth, 0);//No value will be associated
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setDataPointsForThisYearExpenditure(Transactions transactionsValues,
                                                     List<Transaction> transactions,
                                                     DataPoint[] dataPoints) {
        Log.d(TAG, "setDataPointsForThisYearExpenditure() called");
        Instant currentYear = Instant.now();
        Date thisYear = Date.from(currentYear);
        Date oneYearAgo = Date.from(currentYear.minus(ONE_YEAR, ChronoUnit.DAYS));
        Date twoYearsAgo = Date.from(currentYear.minus(2 * ONE_YEAR,
                ChronoUnit.DAYS));
        Date nextYear = Date.from(currentYear.plus(ONE_YEAR, ChronoUnit.DAYS));
        dataPoints[0] = new DataPoint(twoYearsAgo, transactionsValues.getYearExpenditure(twoYearsAgo,
                transactions));
        dataPoints[1] = new DataPoint(oneYearAgo, transactionsValues.getYearExpenditure(oneYearAgo,
                transactions));
        dataPoints[2] = new DataPoint(thisYear, transactionsValues.getYearExpenditure(thisYear,
                transactions));
        dataPoints[3] = new DataPoint(nextYear, 0);//No value will be associated
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