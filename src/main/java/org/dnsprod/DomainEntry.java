package org.dnsprod;




public class DomainEntry {
    
    private String domain;

    private String dnsServer;

    private Long minIpNumber;

    private Long maxIpNumber;

    public DomainEntry() {
    }

    public DomainEntry(String domain, String dnsServer, Long minIpNumber, Long maxIpNumber) {
        super();
        this.domain = domain;
        this.dnsServer = dnsServer;
        this.minIpNumber = minIpNumber;
        this.maxIpNumber = maxIpNumber;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDnsServer() {
        return dnsServer;
    }

    public void setDnsServer(String dnsServer) {
        this.dnsServer = dnsServer;
    }

    public Long getMinIpNumber() {
        return minIpNumber;
    }

    public void setMinIpNumber(Long minIpNumber) {
        this.minIpNumber = minIpNumber;
    }

    public Long getMaxIpNumber() {
        return maxIpNumber;
    }

    public void setMaxIpNumber(Long maxIpNumber) {
        this.maxIpNumber = maxIpNumber;
    }

    public boolean isInRange(Long number) {
        return number >= minIpNumber && number <= maxIpNumber;
    }

}
