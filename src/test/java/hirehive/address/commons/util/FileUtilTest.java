package hirehive.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static hirehive.address.testutil.Assert.assertThrows;

import hirehive.address.testutil.Assert;
import org.junit.jupiter.api.Test;

public class FileUtilTest {

    @Test
    public void isValidPath() {
        // valid path
        assertTrue(FileUtil.isValidPath("valid/file/path"));

        // invalid path
        assertFalse(FileUtil.isValidPath("a\0"));

        // null path -> throws NullPointerException
        Assert.assertThrows(NullPointerException.class, () -> FileUtil.isValidPath(null));
    }

}
