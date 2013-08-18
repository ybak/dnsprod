package org.dnsprod;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DnsService {

    @PersistenceContext
    private EntityManager em;

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

    @Transactional
    public void createDomainEntry(DomainEntry entry) {
        em.persist(entry);
    }

    private List<DomainEntry> fetchDomainEntriesFromDB(String domain) {
        TypedQuery<DomainEntry> query = em.createQuery("select o from DomainEntry o where o.domain = :domain",
                DomainEntry.class);
        query.setParameter("domain", domain);
        return query.getResultList();
    }

}
