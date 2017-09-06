import org.junit.Test;

import java.math.BigDecimal;

public class Tests {

    @Test
    public void test1(){
        BigDecimal result = new BigDecimal("0.1");
        System.out.println("result = " + result.toPlainString());
        BigDecimal minval = new BigDecimal(0.25D);
        System.out.println("minval = " + minval.toPlainString());
        System.out.println(result.compareTo(minval));
        result = result.compareTo(minval) == 1 ? result : minval;
        System.out.println("result = " + result.toPlainString());
    }
}
