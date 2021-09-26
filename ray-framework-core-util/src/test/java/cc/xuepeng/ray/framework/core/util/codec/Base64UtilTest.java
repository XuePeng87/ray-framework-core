package cc.xuepeng.ray.framework.core.util.codec;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class Base64UtilTest {

    private static final String CONTENT = "ABCDEFG";

    private static final byte[] RESULT = {0, 16, -125, 16, 81};

    @Test
    public void test_encode_success() {
        Assert.assertArrayEquals(RESULT, Base64Util.decode(CONTENT));
        Assert.assertArrayEquals(RESULT, Base64Util.decode(CONTENT.getBytes()));
    }

    @Test
    public void test_encode_content_null_exception() {
        try {
            Base64Util.decode(StringUtils.EMPTY);
        } catch (Error e) {
            Assert.assertTrue(e instanceof AssertionError);
        }
    }

}
