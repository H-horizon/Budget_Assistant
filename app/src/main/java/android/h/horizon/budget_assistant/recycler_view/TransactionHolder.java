package android.h.horizon.budget_assistant.recycler_view;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TransactionHolder extends RecyclerView.ViewHolder {
    public TextView mTitleTextView;
    public TransactionHolder(View itemView) {
        super(itemView);
        mTitleTextView = (TextView) itemView;
    }
}
