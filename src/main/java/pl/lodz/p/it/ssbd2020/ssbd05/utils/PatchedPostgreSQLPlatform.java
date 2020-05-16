package pl.lodz.p.it.ssbd2020.ssbd05.utils;

import org.eclipse.persistence.internal.helper.ClassConstants;
import org.eclipse.persistence.platform.database.PostgreSQLPlatform;

import java.sql.Types;

public class PatchedPostgreSQLPlatform extends PostgreSQLPlatform {

    @Override
    public int getJDBCType(Class javaType) {
        if (javaType == ClassConstants.TIME_LDATE) {
            return Types.DATE;
        } else if (javaType == ClassConstants.TIME_LTIME) {
            return Types.TIMESTAMP;
        } else if (javaType == ClassConstants.TIME_LDATETIME) {
            return Types.TIMESTAMP;
        } else if (javaType == ClassConstants.TIME_ODATETIME) {
            return Types.TIMESTAMP;
        } else if (javaType == ClassConstants.TIME_OTIME) {
            return Types.TIMESTAMP;
        } else {
            return super.getJDBCType(javaType);
        }
    }
}
