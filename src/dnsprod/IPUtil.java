package dnsprod;


public class IPUtil {

    public static long ipToNumber(String addr) {
        String[] addrArray = addr.split("\\.");
        long num = 0;
        for(int i = 0; i < addrArray.length; i++) {
            int segment = 3 - i;
            num += Long.parseLong(addrArray[i]) << (segment * 8);
        }
        return num;
    }

    public static String numberToIp(long num) {
        return ((num >> 24) & 0xFF) + "." + ((num >> 16) & 0xFF) + "." + ((num >> 8) & 0xFF) +
                "." + (num & 0xFF);
    }

}
