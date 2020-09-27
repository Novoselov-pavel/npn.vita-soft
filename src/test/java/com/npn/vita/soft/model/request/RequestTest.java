package com.npn.vita.soft.model.request;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class RequestTest {

    @Test
    void getFormattedHeader() {
        Request request = new Request();
        request.setHeader("asdasd");
        Assert.assertEquals("Request format doesn't valid","a-s-d-a-s-d", request.getFormattedHeader());
    }
}