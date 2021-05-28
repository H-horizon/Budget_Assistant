package android.h.horizon.budget_assistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mExpenseButton;
    private static final int REQUEST_CODE_EXPENSES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setExpenseButton();
    }

    private void setExpenseButton() {
        mExpenseButton = (Button) findViewById(R.id.expense_button);
        mExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ExpenseActivity.class);
                startActivity(i);
            }
        });
    }

}