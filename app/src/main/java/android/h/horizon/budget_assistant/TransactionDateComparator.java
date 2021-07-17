package android.h.horizon.budget_assistant;

import android.h.horizon.budget_assistant.transaction.Transaction;

import java.util.Comparator;

public class TransactionDateComparator implements Comparator<Transaction> {
    @Override
    public int compare(Transaction o1, Transaction o2) {
        return o1.getDate().compareTo(o2.getDate());

    }
}
