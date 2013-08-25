package org.dnsprod;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.stereotype.Service;

@Service
public class DnsService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private String defaultDnsServer = "defaultDnsServer";
    
    @PostConstruct
    private void createCollections(){
        mongoTemplate.dropCollection(DomainEntry.class);
        mongoTemplate.createCollection(DomainEntry.class);
        mongoTemplate.indexOps(DomainEntry.class).ensureIndex(new Index().on("domain",Order.ASCENDING));   
    }
    
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
        mongoTemplate.insert(entry);
    }

    private List<DomainEntry> fetchDomainEntriesFromDB(String domain) {
        return mongoTemplate.find(query(where("domain").is(domain)), DomainEntry.class);
    }
}
