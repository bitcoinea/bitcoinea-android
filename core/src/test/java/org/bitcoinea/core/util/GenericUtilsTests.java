package org.bitcoinea.core.util;

import org.bitcoinea.core.coins.BitcoinMain;
import org.bitcoinea.core.coins.BlackcoinMain;
import org.bitcoinea.core.coins.CoinType;
import org.bitcoinea.core.coins.DigitalcoinMain;
import org.bitcoinea.core.coins.FeathercoinMain;
import org.bitcoinea.core.coins.LitecoinMain;
import org.bitcoinea.core.coins.NuBitsMain;
import org.bitcoinea.core.coins.PeercoinMain;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Coin;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author John L. Jegutanis
 */
public class GenericUtilsTests {

    @Test
    public void getPossibleTypes() throws AddressFormatException {
        List<CoinType> types = GenericUtils.getPossibleTypes("BPa5FmbZRGpmNfy4qaUzarXwSSFbJKFRMQ");
        assertTrue(types.contains(BlackcoinMain.get()));
        assertTrue(types.contains(NuBitsMain.get()));
        assertTrue(GenericUtils.hasMultipleTypes("BPa5FmbZRGpmNfy4qaUzarXwSSFbJKFRMQ"));

        // Many coins share Bitcoin's multisig addresses...
        types = GenericUtils.getPossibleTypes("3Lp1ZbdoDfZF21BLMBpctM6CrM6j4t2JyU");
        assertTrue(types.contains(BitcoinMain.get()));
        assertTrue(types.contains(LitecoinMain.get()));
        assertTrue(types.contains(FeathercoinMain.get()));
        assertTrue(types.contains(DigitalcoinMain.get()));
        assertTrue(GenericUtils.hasMultipleTypes("3Lp1ZbdoDfZF21BLMBpctM6CrM6j4t2JyU"));

        // Address method
        Address address = new Address(BlackcoinMain.get(), "BPa5FmbZRGpmNfy4qaUzarXwSSFbJKFRMQ");
        types = GenericUtils.getPossibleTypes(address);
        assertTrue(types.contains(BlackcoinMain.get()));
        assertTrue(types.contains(NuBitsMain.get()));
        assertTrue(GenericUtils.hasMultipleTypes(address));

        // Classic Bitcoin addresses should have only one type
        types = GenericUtils.getPossibleTypes("1AjnxP4frz7Nb4v2soLnhN2uV9UocqvaGH");
        assertTrue(types.contains(BitcoinMain.get()));
        assertTrue(types.size() == 1);
        assertFalse(GenericUtils.hasMultipleTypes("1AjnxP4frz7Nb4v2soLnhN2uV9UocqvaGH"));
    }

    @Test(expected = AddressFormatException.class)
    public void getPossibleTypesInvalid() throws AddressFormatException {
        GenericUtils.getPossibleTypes("");
    }

    @Test(expected = AddressFormatException.class)
    public void getPossibleTypesUnsupported() throws AddressFormatException {
        GenericUtils.getPossibleTypes("2mwJoik9pimQHUN2zU56J7h8tCTWYoUhpCM"); // version byte 0xFF
    }

    @Test
    public void formatValue() {
        // Bitcoin family
        assertEquals("1.3370", GenericUtils.formatCoinValue(BitcoinMain.get(), Coin.valueOf(133700000), 6, 0));
        assertEquals("0.001337", GenericUtils.formatCoinValue(BitcoinMain.get(), Coin.valueOf(133700), 6, 0));
        assertEquals("1.3370", GenericUtils.formatCoinValue(BitcoinMain.get(), Coin.valueOf(133700000), 4, 0));
        assertEquals("0.0013", GenericUtils.formatCoinValue(BitcoinMain.get(), Coin.valueOf(133700), 4, 0));
        assertEquals("1.34", GenericUtils.formatCoinValue(BitcoinMain.get(), Coin.valueOf(133700000), 2, 0));
        assertEquals("1.34", GenericUtils.formatCoinValue(BitcoinMain.get(), Coin.valueOf(133700000), 2, 0));
        assertEquals("0.0013", GenericUtils.formatCoinValue(BitcoinMain.get(), Coin.valueOf(133700), 2, 0));

        assertEquals("1.00", GenericUtils.formatCoinValue(BitcoinMain.get(), Coin.valueOf(100000001), 6, 0));
        assertEquals("1.00000001", GenericUtils.formatCoinValue(BitcoinMain.get(), Coin.valueOf(100000001), 8, 0));
        assertEquals("1.00", GenericUtils.formatCoinValue(BitcoinMain.get(), Coin.valueOf(100000010), 6, 0));
        assertEquals("1.00000010", GenericUtils.formatCoinValue(BitcoinMain.get(), Coin.valueOf(100000010), 8, 0));
        assertEquals("1.000001", GenericUtils.formatCoinValue(BitcoinMain.get(), Coin.valueOf(100000100), 6, 0));
        assertEquals("1.000001", GenericUtils.formatCoinValue(BitcoinMain.get(), Coin.valueOf(100000100), 8, 0));
        assertEquals("1.000010", GenericUtils.formatCoinValue(BitcoinMain.get(), Coin.valueOf(100001000), 6, 0));
        assertEquals("1.000010", GenericUtils.formatCoinValue(BitcoinMain.get(), Coin.valueOf(100001000), 8, 0));
        assertEquals("1.0010", GenericUtils.formatCoinValue(BitcoinMain.get(), Coin.valueOf(100100001), 6, 0));
        assertEquals("1.00100001", GenericUtils.formatCoinValue(BitcoinMain.get(), Coin.valueOf(100100001), 8, 0));

        // Peercoin family
        assertEquals("3.141592", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(3141592), 6, 0));
        assertEquals("0.027182", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(27182), 6, 0));
        assertEquals("3.1416", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(3141592), 4, 0));
        assertEquals("0.0272", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(27182), 4, 0));
        assertEquals("3.14", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(3141592), 2, 0));
        assertEquals("0.03", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(27182), 2, 0));

        assertEquals("1.00", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(1000001), 4, 0));
        assertEquals("1.000001", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(1000001), 6, 0));
        assertEquals("1.000001", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(1000001), 8, 0));
        assertEquals("1.00", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(1000010), 4, 0));
        assertEquals("1.000010", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(1000010), 6, 0));
        assertEquals("1.000010", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(1000010), 8, 0));
        assertEquals("1.0001", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(1000100), 4, 0));
        assertEquals("1.0001", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(1000100), 6, 0));
        assertEquals("1.0001", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(1000100), 8, 0));
        assertEquals("1.0010", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(1001000), 4, 0));
        assertEquals("1.0010", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(1001000), 6, 0));
        assertEquals("1.0010", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(1001000), 8, 0));
        assertEquals("1.0010", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(1001001), 4, 0));
        assertEquals("1.001001", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(1001001), 6, 0));
        assertEquals("1.001001", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(1001001), 8, 0));

        assertEquals("1.00", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(1000049), 4, 0));
        assertEquals("1.0001", GenericUtils.formatCoinValue(PeercoinMain.get(), Coin.valueOf(1000050), 4, 0));
    }


}
