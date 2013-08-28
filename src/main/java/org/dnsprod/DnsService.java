package org.dnsprod;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DnsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String defaultDnsServer = "defaultDnsServer";

    public String lookupBestDns(String ip, String domain) {
        List<DomainEntry> entries = fetchDomainEntriesFromDB(domain);
        Long number = IPUtil.ipToNumber(ip);
        for (DomainEntry entry : entries) {
            if (entry.isInRange(number)) {
                return entry.getDnsServer();
            }
        }

        return defaultDnsServer;
    }
    
    public String lookupBestDns2(String ip, String domain) {
          Long ipNumber = IPUtil.ipToNumber(ip);
          String targetDomain = fetchDomainEntryFromDB(domain, ipNumber);
          return targetDomain == null ? defaultDnsServer : targetDomain;
      }

    public void createDomainEntry(DomainEntry entry) {
        jdbcTemplate
                .update("insert into domain_entry (`dns_server`, `domain`, `max_ip_number`, `min_ip_number`) values (?, ?, ?, ?)",
                        entry.getDnsServer(), entry.getDomain(), entry.getMaxIpNumber(), entry.getMinIpNumber());
    }

    public void createDomainEntries(List<DomainEntry> entries) {
        jdbcTemplate
                .batchUpdate(
                        "insert into domain_entry (`dns_server`, `domain`, `max_ip_number`, `min_ip_number`) values (?, ?, ?, ?)",
                        new BatchSetter(entries));
    }

    private List<DomainEntry> fetchDomainEntriesFromDB(String domain) {
        List<DomainEntry> entries = new ArrayList<DomainEntry>();
        List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from domain_entry t where t.domain = ?",
                domain);
        for (Map<String, Object> map : result) {
            entries.add(new DomainEntry(domain, (String) map.get("dns_server"), (Long) map.get("min_ip_number"),
                    (Long) map.get("max_ip_number")));
        }
        return entries;
    }

    private String fetchDomainEntryFromDB(String domain, Long ip) {
        return jdbcTemplate
                .queryForObject(
                        "select t.domain from domain_entry t where t.domain = ? and ? >= t.min_ip_number and ? <= t.max_ip_number limit 1",
                        String.class, domain, ip, ip);
    }

    public List<String> getDomains(int count) {
        List<String> domains= jdbcTemplate.queryForList("select distinct t.domain from domain_entry t limit 3000", String.class);
        Collections.shuffle(domains);
        return domains.subList(0, count);
    }
}

class BatchSetter implements BatchPreparedStatementSetter {
    private List<DomainEntry> entries;

    public BatchSetter(List<DomainEntry> entries) {
        this.entries = entries;
    }

    public int getBatchSize() {
        return entries.size();
    }

    public void setValues(PreparedStatement ps, int i) throws SQLException {
        try {

            ps.setString(1, entries.get(i).getDnsServer());
            ps.setString(2, entries.get(i).getDomain());
            ps.setLong(3, entries.get(i).getMaxIpNumber());
            ps.setLong(4, entries.get(i).getMinIpNumber());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}