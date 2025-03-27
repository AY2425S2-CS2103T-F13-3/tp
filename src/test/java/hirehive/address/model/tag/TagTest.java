package hirehive.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import hirehive.address.testutil.Assert;

public class TagTest {

    @Test
    public void test_getDefaultTag_returnsTrue() {
        assertEquals(Tag.getDefaultTag(), Tag.APPLICANT);
    }

    @Test
    public void test_getDefaultTag_returnsFalse() {
        assertFalse(Tag.getDefaultTag().equals(Tag.CANDIDATE));
    }
}
