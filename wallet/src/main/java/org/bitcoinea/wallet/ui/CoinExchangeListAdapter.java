package wallet.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.bitcoinea.core.coins.CoinType;

import wallet.ui.widget.CoinListItem;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import wallet.ExchangeRatesProvider;

/**
 * @author John L. Jegutanis
 */
public class CoinExchangeListAdapter extends BaseAdapter {
    private final Context context;
    private final List<CoinType> coins;
    private final HashMap<String, ExchangeRatesProvider.ExchangeRate> rates;

    public CoinExchangeListAdapter(final Context context, List<CoinType> coins,
                                   @Nullable List<ExchangeRatesProvider.ExchangeRate> rates) {
        this.context = context;
        this.coins = coins;
        this.rates = new HashMap<String, ExchangeRatesProvider.ExchangeRate>(coins.size());
        setExchangeRates(rates);
    }

    public CoinExchangeListAdapter(final Context context, List<CoinType> coins) {
        this(context, coins, null);
    }

    @Override
    public int getCount() {
        return coins.size();
    }

    @Override
    public CoinType getItem(int position) {
        return coins.get(position);
    }

    public void setExchangeRates(@Nullable List<ExchangeRatesProvider.ExchangeRate> newRates) {
        if (newRates != null) {
            for (ExchangeRatesProvider.ExchangeRate rate : newRates) {
                if (isRateRelative(rate)) {
                    this.rates.put(rate.currencyCodeId, rate);
                }
            }
        } else {
            rates.clear();
        }
        notifyDataSetChanged();
    }

    private boolean isRateRelative(ExchangeRatesProvider.ExchangeRate rate) {
        if (rate.rate.value1.type instanceof CoinType && coins.contains(rate.rate.value1.type)) {
            return true;
        } else if (rate.rate.value2.type instanceof CoinType && coins.contains(rate.rate.value2.type)) {
            return true;
        }
        return false;
    }

    @Nullable
    public ExchangeRatesProvider.ExchangeRate getExchangeRate(String code) {
        return rates.get(code);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View row, ViewGroup parent) {
        if (row == null) {
            row = new CoinListItem(context);
        }

        CoinType coinType = getItem(position);
        if (coinType != null) {
            ((CoinListItem) row).setCoin(coinType);

            ExchangeRatesProvider.ExchangeRate rate = getExchangeRate(coinType.getSymbol());
            if (rate != null) ((CoinListItem) row).setExchangeRate(rate);
        }


        return row;
    }


}