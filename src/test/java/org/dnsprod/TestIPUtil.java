package org.dnsprod;


import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author yijun.sun
 */
public class TestIPUtil implements NumberAndIP {

    @Test
    public void test_ip_0_0_0_0_to_number_0() {
        long number = IPUtil.ipToNumber(IP_0_0_0_0);
        assertEquals(NUMBER_0_0_0_0, number);
    }

    @Test
    public void test_number_0_to_ip_0_0_0_0() {
        String ip = IPUtil.numberToIp(NUMBER_0_0_0_0);
        assertEquals(IP_0_0_0_0, ip);
    }

    @Test
    public void test_ip_0_0_0_255_to_number_255() {
        long number = IPUtil.ipToNumber(IP_0_0_0_255);
        assertEquals(NUMBER_0_0_0_255, number);
    }

    @Test
    public void test_number_255_to_ip_0_0_0_255() {
        String ip = IPUtil.numberToIp(NUMBER_0_0_0_255);
        assertEquals(IP_0_0_0_255, ip);
    }

    @Test
    public void test_ip_0_0_1_0_to_number_256() {
        long number = IPUtil.ipToNumber(IP_0_0_1_0);
        assertEquals(NUMBER_0_0_1_0, number);
    }

    @Test
    public void test_number_256_to_ip_0_0_1_0() {
        String ip = IPUtil.numberToIp(NUMBER_0_0_1_0);
        assertEquals(IP_0_0_1_0, ip);
    }

    @Test
    public void test_ip_1_0_0_0_to_number_16777216() {
        long number = IPUtil.ipToNumber(IP_1_0_0_0);
        assertEquals(NUMBER_1_0_0_0, number);
    }

    @Test
    public void test_number_16777216_to_ip_1_0_0_0() {
        String ip = IPUtil.numberToIp(NUMBER_1_0_0_0);
        assertEquals(IP_1_0_0_0, ip);
    }

    @Test
    public void test_ip_255_255_255_255_to_number_4294967295() {
        long number = IPUtil.ipToNumber(IP_255_255_255_255);
        assertEquals(NUMBER_255_255_255_255, number);
    }

    @Test
    public void test_number_4294967295_to_ip_255_255_255_255() {
        String ip = IPUtil.numberToIp(NUMBER_255_255_255_255);
        assertEquals(IP_255_255_255_255, ip);
    }

}
