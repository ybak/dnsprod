package org.dnsprod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

    public void createDomainEntry(DomainEntry entry) {
        jdbcTemplate
                .update("insert into domain_entry (`dns_server`, `domain`, `max_ip_number`, `min_ip_number`) values (?, ?, ?, ?)",
                        entry.getDnsServer(), entry.getDomain(), entry.getMaxIpNumber(), entry.getMinIpNumber());
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
}
