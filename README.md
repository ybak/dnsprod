dnsprod
=======

A simulation of the DNS service, with database powered by mysql,HSQLDB (memdb), mongodb, redis (data serilized by protobuf).
A kind of DB performance test ignore disk storage.

mysql result:
    // 336544 millis sepend for generate db
    // 100019 millis sepend for lookup dns
    // 503428 lookups    

mongodb results:
    // 19375 millis spend for generate db
    // 100074 millis spend for lookup dns
    // 499985 lookups
    
hsqldb result:
    // 29348 millis spend for generate db
    // 100043 millis spend for lookup dns
    // 929903 lookups

redis results:    
    // 15163 millis sepend for generate db
    // 100035 millis sepend for lookup dns
    // 1177133 lookups
    
the results show me
1. reids with probobuf is really fast
2. mogodb is not so fast as I thought.
3. even hsqldb embeded in the same java test application process, it still be defeated by redis.
