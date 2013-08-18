package dnsprod;

import java.util.List;

public class DnsService {

    private String defaultDnsServer;

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

    private List<DomainEntry> fetchDomainEntriesFromDB(String domain) {
        //TODO
        return null;
    }

}
