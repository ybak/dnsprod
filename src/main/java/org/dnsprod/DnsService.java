package org.dnsprod;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.dnsprod.Commons.DomainEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.google.protobuf.InvalidProtocolBufferException;

@Service
public class DnsService {

    @Autowired
    private RedisTemplate<String, byte[]> template;

    private String defaultDnsServer = "defaultDnsServer";

    public String lookupBestDns(String ip, String domain) {
        List<DomainEntry> entries = fetchDomainEntriesFromDB(domain);
        Long number = IPUtil.ipToNumber(ip);
        for (DomainEntry entry : entries) {
            if (isInRange(number, entry)) {
                return entry.getDnsServer();
            }
        }

        return defaultDnsServer;
    }

    public void createDomainEntry(DomainEntry entry) {
        template.opsForSet().add(entry.getDomain(), entry.toByteArray());
    }

    private List<DomainEntry> fetchDomainEntriesFromDB(String domain) {
        List<DomainEntry> results = new ArrayList<Commons.DomainEntry>();
        Set<byte[]> members = template.opsForSet().members(domain);
        for (byte[] bs : members) {
            try {
                results.add(DomainEntry.parseFrom(bs));
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException(e);
            }
        }
        return results;
    }

    public boolean isInRange(Long number, DomainEntry domainEntry) {
        return number >= domainEntry.getMinIpNumber() && number <= domainEntry.getMaxIpNumber();
    }
}
