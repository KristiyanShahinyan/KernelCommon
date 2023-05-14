package digital.paynetics.phos.kernel.common.misc;

import org.junit.Test;

import digital.paynetics.phos.exceptions.EmvException;

import static org.junit.Assert.assertTrue;


public class Track1DataTest {
    private static final String PAN = "5581123456781323";
    private static final String DATE = "1607";
    private static final String NAME = "SMITH/JOHN";
    private static final String SERVICE_CODE = "102";
    private static final String FORMAT_CODE = "B";
    private static final String DISCRETIONARY_DATA = "1473810559010203";


    @Test
    public void test() throws EmvException {
        Track1Data t1d = new Track1Data("%B5581123456781323^SMITH/JOHN^16071021473810559010203?\n" +
                ";5581123456781323=160710212423468?");

        assertTrue(t1d.getPan().equals(PAN));
        assertTrue(t1d.getExpirationDate().equals(DATE));
        assertTrue(t1d.getName().equals(NAME));
        assertTrue(t1d.getServiceCode().equals(SERVICE_CODE));
        assertTrue(t1d.getFormatCode().equals(FORMAT_CODE));
        assertTrue(t1d.getDiscretionaryData().equals(DISCRETIONARY_DATA));
    }


}
