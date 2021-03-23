package io.upg.data.network;

import io.upg.data.model.response.DateTransactionsItem;

public interface CheckTransactionListener {

    void transactionSuccess(DateTransactionsItem transactionsItem);

    void transactionFailed();

    void transactionNotFound();

    void onError(Throwable throwable);
}
