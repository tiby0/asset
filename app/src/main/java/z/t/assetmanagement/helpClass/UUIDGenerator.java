package z.t.assetmanagement.helpClass;

import java.util.UUID;

/**
 * Created by usi on 2016/9/10.
 */
public class UUIDGenerator {
public  static String getUUID(){
    String ss= UUID.randomUUID().toString().trim().replaceAll("-", "");
    return  ss;

}

}
