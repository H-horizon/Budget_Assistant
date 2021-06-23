package android.h.horizon.budget_assistant.transactions;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionContainer {

    private static TransactionContainer sTransactionList;
    private List<Transaction> mTransactions;

    public static TransactionContainer get(Context context) {
        if (sTransactionList == null) {
            sTransactionList = new TransactionContainer(context);
        }
        return sTransactionList;
    }

    private TransactionContainer(Context context) {
        mTransactions = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            Transaction transaction = new Transaction("Expense "+i,10.1*i);
//            mTransactions.add(transaction);
//        }
    }
    public List<Transaction> getTransactions() {
        return mTransactions;
    }
    public Transaction getTransaction(UUID id) {
        for (Transaction transaction : mTransactions) {
            if (transaction.getId().equals(id)) {
                return transaction;
            }
        }
        return null;
    }

    public void addTransaction(Transaction transaction) {
        mTransactions.add(transaction);
    }
}
