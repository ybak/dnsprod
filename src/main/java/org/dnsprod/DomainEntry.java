package org.dnsprod;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Index;

@Entity
public class DomainEntry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "int(11)")
    private Long id;

    @Column(columnDefinition = "varchar(30)")
    @Index(name = "IDX_DOMAIN")
    private String domain;

    @Column(columnDefinition = "varchar(20)")
    private String dnsServer;

    @Column(columnDefinition = "bigint")
    private Long minIpNumber;

    @Column(columnDefinition = "bigint")
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
