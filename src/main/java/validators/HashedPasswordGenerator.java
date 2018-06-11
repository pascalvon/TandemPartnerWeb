package Validators;

import com.google.common.hash.Hashing;
import com.google.common.base.Charsets;

public class HashedPasswordGenerator {
    public static String generateHash(String password){
        return Hashing.sha1().hashString(password, Charsets.UTF_8).toString();
    }
}
