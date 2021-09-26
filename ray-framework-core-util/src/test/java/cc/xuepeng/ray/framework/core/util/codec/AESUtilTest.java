package cc.xuepeng.ray.framework.core.util.codec;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class AESUtilTest {

    private static final String SECRET = "YESWAY1234567890";

    private static final String CONTENT = "ABC";

    private static final String RESULT = "R+UNGdZgXrJ2CbX3nRltHw==";

    @Test
    public void test_encode_suceess() {
        final String actual = AES128Util.encode(CONTENT, SECRET);
        Assert.assertEquals(RESULT, actual);
    }

    @Test
    public void test_encode_secret_null_exception() {
        try {
            AES128Util.encode(CONTENT, StringUtils.EMPTY);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void test_encode_secret_short_exception() {
        try {
            AES128Util.encode(CONTENT, StringUtils.substring(SECRET, 5));
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void test_decode_success() {
        final String actual = AES128Util.decode(RESULT, SECRET);
        Assert.assertEquals(CONTENT, actual);
    }

    @Test
    public void test_decode_secret_null_exception() {
        try {
            AES128Util.decode(CONTENT, StringUtils.EMPTY);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void test_decode_secret_short_exception() {
        try {
            AES128Util.decode(CONTENT, StringUtils.substring(SECRET, 5));
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

}
