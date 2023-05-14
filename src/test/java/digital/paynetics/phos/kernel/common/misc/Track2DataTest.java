package digital.paynetics.phos.kernel.common.misc;

import org.junit.Test;

import java.util.Arrays;

import digital.paynetics.phos.exceptions.EmvException;

import static org.junit.Assert.assertTrue;


public class Track2DataTest {
    private static final String PAN = "5413339000001513";
    private static final String MONTH = "12";
    private static final String YEAR = "49";
    private static final String SERVICE_CODE = "101";
    private static final String DISCRETIONARY_DATA = "9000990000000";


    @Test
    public void test_constructor() throws EmvException {
        Track2Data t2d = new Track2Data(ByteUtils.fromString("5413339000001513D49121019000990000000F"));

        assertTrue(Arrays.equals(t2d.getPan(), PAN.toCharArray()));
        assertTrue(Arrays.equals(t2d.getExpirationDateYear(), YEAR.toCharArray()));
        assertTrue(Arrays.equals(t2d.getExpirationDateMonth(), MONTH.toCharArray()));
        assertTrue(Arrays.equals(t2d.getServiceCode(), SERVICE_CODE.toCharArray()));
        assertTrue(Arrays.equals(t2d.getDiscretionaryData(), DISCRETIONARY_DATA.toCharArray()));
    }


    @Test
    public void test_toBytes() throws EmvException {
        byte[] ba1 = ByteUtils.fromString("5413339000001513D49121019000990000000F");
        Track2Data t2d = new Track2Data(ba1);
        byte[] ba2 = t2d.toBytes();
        Track2Data t2d2 = new Track2Data(ba2);

        assertTrue(Arrays.equals(t2d.getPan(), t2d2.getPan()));
        assertTrue(Arrays.equals(t2d.getExpirationDateYear(), t2d2.getExpirationDateYear()));
        assertTrue(Arrays.equals(t2d.getExpirationDateMonth(), t2d2.getExpirationDateMonth()));
        assertTrue(Arrays.equals(t2d.getServiceCode(), t2d2.getServiceCode()));
        assertTrue(Arrays.equals(t2d.getDiscretionaryData(), t2d2.getDiscretionaryData()));
    }
}
