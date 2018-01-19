package org.bitcoinea.core.coins;

import org.bitcoinea.core.coins.families.PeerFamily;

public class BitcoinEAMain extends CoinType {
    private BitcoinEAMain() {
        id = "bitcoinea.main";

        addressHeader = 33;
        p2shHeader = 85;
        acceptableAddressCodes = new int[] { addressHeader, p2shHeader };
        spendableCoinbaseDepth = 1;

        family = PeerFamily.get();
        name = "BitcoinEA";
        symbol = "BEA";
        uriScheme = "bitcoinea";
        bip44Index = 1;
        unitExponent = 8;
        feePerKb = value(10000); // 0.001 NVC
        minNonDust = value(1);
        softDustLimit = value(100000); // 0.01 NVC 10000
        softDustPolicy = SoftDustPolicy.BASE_FEE_FOR_EACH_SOFT_DUST_TXO;
    }

    private static BitcoinEAMain instance = new BitcoinEAMain();
    public static synchronized BitcoinEAMain get() {
        return instance;
    }
}
