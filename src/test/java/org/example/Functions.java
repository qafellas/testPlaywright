package org.example;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Functions {
    @Test
    void addition(){
        Assert.assertEquals(1+1, 2);
    }

    @Test
    void subtraction(){
        Assert.assertEquals(1-1, 2);
    }
}
