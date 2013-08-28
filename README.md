dnsprod
=======

A simulation of the DNS service, with database powered by mysql,HSQLDB (memdb), mongodb, redis (data serilized by protobuf).<br>
A kind of DB performance test ignore disk storage.<br>

mysql result:<br>
    // 336544 millis sepend for generate db<br>
    // 100019 millis sepend for lookup dns<br>
    // 503428 lookups    <br>

mongodb results:<br>
    // 19375 millis spend for generate db<br>
    // 100074 millis spend for lookup dns<br>
    // 499985 lookups<br>
    
hsqldb result:<br>
    // 29348 millis spend for generate db<br>
    // 100043 millis spend for lookup dns<br>
    // 929903 lookups<br>

redis results:    <br>
    // 15163 millis sepend for generate db<br>
    // 100035 millis sepend for lookup dns<br>
    // 1177133 lookups<br>
    
the results show me<br>
1. reids with probobuf is really fast<br>
2. mogodb is not so fast as I thought.<br>
3. even hsqldb embeded in the same java test application process, it still be defeated by redis.<br>
