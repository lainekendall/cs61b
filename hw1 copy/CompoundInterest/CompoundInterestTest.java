import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {

    @Test
    public void testNumYears() {
        /** Sample assert statement for comparing integers.

        assertEquals(0, 0); */
        assertEquals(1, CompoundInterest.numYears(2016));
        assertEquals(10, CompoundInterest.numYears(2025));
        assertEquals(0, CompoundInterest.numYears(2015));
    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;
        assertEquals(12.544, CompoundInterest.futureValue(10,12,2017), tolerance);
        assertEquals(4.5, CompoundInterest.futureValue(3,50,2016), tolerance);
        assertEquals(12.860624999999999, CompoundInterest.futureValue(15,-5,2018), tolerance);
        assertEquals(5500.0, CompoundInterest.futureValue(5000,10,2016), tolerance);

    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
        assertEquals(250.43747119999998, CompoundInterest.futureValueReal(100,40,2018,3), tolerance);
        assertEquals(8.36, CompoundInterest.futureValueReal(10,-12,2016,5), tolerance);
        assertEquals(559.77603359776, CompoundInterest.futureValueReal(560,1,2019,1), tolerance);
        assertEquals(10, CompoundInterest.futureValueReal(10,1,2015,1), tolerance);
    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
        assertEquals(10500, CompoundInterest.totalSavings(5000,2016,10), tolerance);
        assertEquals(16550, CompoundInterest.totalSavings(5000,2017,10), tolerance);
        assertEquals(236.0, CompoundInterest.totalSavings(100,2016,36), tolerance);
        assertEquals(672.5056000000001, CompoundInterest.totalSavings(100,2018,36), tolerance);
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
        assertEquals(10185, CompoundInterest.totalSavingsReal(5000,2016,10,3), tolerance);
        assertEquals(652.5315111744001, CompoundInterest.totalSavingsReal(100,2018,36,1), tolerance);

    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
