package utilities;

import com.google.common.hash.Hashing;
import com.google.common.base.Charsets;

/**
 * Hashed das Passwort des Nutzers bei der Registrierung und Bearbeitung seines Profils.
 */
public class HashedPasswordGenerator {

    /**
     * Erzeugt aus einem {@code String} einen Hashwert und gibt diesen zur&uuml;ck.
     *
     * @param password Der {@code String}, welcher gehashed werden soll.
     * @return Gibt einen gehasheden {@code String} zur&uuml;ck.
     */
    public static String generateHash(String password){
        return Hashing.sha1().hashString(password, Charsets.UTF_8).toString();
    }
}
