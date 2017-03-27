package com.yo1000.sq.libra.util;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author yo1000
 */
@RunWith(JUnit4.class)
public class StackTraceTreeTest {
    @Test
    public void stackTraceTreeTest() {
        String actual1 = StackTraceTree.getStackTraceString(2, "|_", null, null);
        System.out.println(actual1);

        Assert.assertThat(actual1, CoreMatchers.containsString(StackTraceTree.class.getName()));
        Assert.assertThat(actual1, CoreMatchers.containsString(StackTraceTreeTest.class.getName()));

        String actual2 = StackTraceTree.getStackTraceString();
        System.out.println(actual2);

        Assert.assertThat(actual2, CoreMatchers.not(CoreMatchers.containsString(StackTraceTree.class.getName() + ".")));
        Assert.assertThat(actual2, CoreMatchers.containsString(StackTraceTreeTest.class.getName()));
    }
}
