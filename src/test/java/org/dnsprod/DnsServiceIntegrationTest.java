package org.dnsprod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.Assert;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
public class DnsServiceIntegrationTest {

    private static final Long minIpNumber = IPUtil.ipToNumber("1.0.0.0");
    private static final Long maxIpNumber = IPUtil.ipToNumber("255.255.255.255");
    private static final Long ipCount = maxIpNumber - minIpNumber;

    private List<String> domains = new ArrayList<String>();
    private List<String> ips = new ArrayList<String>();

    private Random random = new Random();

    @Autowired
    private DnsService dnsService;

    @BeforeClass
    public static void prepare() {
    }

    @Test
    public void insertDomainEntries() {
        generate10DomainEntries(random, "name-server", "mail.google.com");
        generate10DomainEntries(random, "name-server", "weibo.com");

        // int count = 3 * 1000 * 1000;
        int count = 3 * 10;
        for (int i = 0; i < count; i++) {
            String dnsServer = randomLowerAlphabetic(6) + "-" + randomLowerAlphabetic(6);
            String domain = randomLowerAlphabetic(4) + "." + randomLowerAlphabetic(6) + ".com";
            generate10DomainEntries(random, dnsServer, domain);
        }
    }

    @Test
    public void testLookupBestDns() {
        String dns = dnsService.lookupBestDns("58.16.128.1", "mail.google.com");
        Assert.assertNotNull(dns);
        // TODO do more assert
    }

    // 19375 millis sepend for generate db
    // 100074 millis sepend for lookup dns
    // 499985 lookups
    @Test
    public void testLookupBestDnsPerformance() throws InterruptedException {
        for (int i = 0; i < 3000; i++) {
            ips.add(IPUtil.numberToIp(minIpNumber + nextLong(random, ipCount)));
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 3 * 10000; i++) {
            String dnsServer = randomLowerAlphabetic(6) + "-" + randomLowerAlphabetic(6);
            String domain = randomLowerAlphabetic(4) + "." + randomLowerAlphabetic(6) + ".com";
            generate10DomainEntries(random, dnsServer, domain);
        }

        System.out.println(System.currentTimeMillis() - startTime + " millis sepend for generate db ");

        ExecutorService executorService = Executors.newFixedThreadPool(100, new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });

        final AtomicInteger counter = new AtomicInteger();
        startTime = System.currentTimeMillis();
        Runnable task = new Runnable() {
            public void run() {
                while (true) {
                    String domain = domains.get(random.nextInt(domains.size()));
                    String ip = ips.get(random.nextInt(ips.size()));
                    String dns = dnsService.lookupBestDns(ip, domain);
                    Assert.assertNotNull(dns);
                    counter.incrementAndGet();
                }
            }
        };
        for (int i = 0; i < 100; i++) {
            executorService.execute(task);
        }
        TimeUnit.SECONDS.sleep(100);
        System.out.println(System.currentTimeMillis() - startTime + " millis sepend for lookup dns");
        System.out.println(counter + " lookups");
    }

    private void generate10DomainEntries(Random random, String dnsServer, String domain) {
        domains.add(domain);
        List<Long> ipNumbers = new ArrayList<Long>();
        ipNumbers.add(minIpNumber);
        ipNumbers.add(maxIpNumber);
        for (int i = 0; i < 9; i++) {
            ipNumbers.add(minIpNumber + nextLong(random, ipCount));
        }
        Collections.sort(ipNumbers);

        for (int i = 0; i < 10; i++) {
            createDomainEntry(i, ipNumbers.get(i), ipNumbers.get(i + 1), dnsServer, domain);
        }
    }

    private void createDomainEntry(int i, Long minIpNumber, Long maxIpNumber, String dnsServer, String domain) {
        DomainEntry entry = new DomainEntry();
        entry.setMinIpNumber(minIpNumber);
        entry.setMaxIpNumber(maxIpNumber);
        entry.setDnsServer(dnsServer + "-" + (i + 1));
        entry.setDomain(domain);
        dnsService.createDomainEntry(entry);
    }

    private long nextLong(Random rng, long n) {
        long bits, val;
        do {
            bits = (rng.nextLong() << 1) >>> 1;
            val = bits % n;
        } while (bits - val + (n - 1) < 0L);
        return val;
    }

    private String randomLowerAlphabetic(int n) {
        return RandomStringUtils.randomAlphabetic(n).toLowerCase();
    }
}
