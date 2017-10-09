package com.kcht.parking.charge;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by shz on 04/10/2017.
 */
@RunWith(Parameterized.class)
public class TestGetNumericFromString {
    public TestGetNumericFromString(final String input, final Integer[] expected) {
        this.input = input;
        this.expected = expected;
    }
    private String input;
    private Integer[] expected;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[]{"maxHour[10,16]", new Integer[]{10, 16}},
                new Object[]{"<15", new Integer[]{15}}
        );
    }

    @Test
    public void test() throws Exception {
        assertThat(StringTool.getNumericFromString(input), is(Arrays.asList(expected)));
    }
}
