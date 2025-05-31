package Model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-11T21:21:56")
@StaticMetamodel(Account.class)
public class Account_ { 

    public static volatile SingularAttribute<Account, Date> datereg;
    public static volatile SingularAttribute<Account, String> password;
    public static volatile SingularAttribute<Account, Character> role;
    public static volatile SingularAttribute<Account, String> imgpath;
    public static volatile SingularAttribute<Account, Integer> personid;
    public static volatile SingularAttribute<Account, Integer> id;
    public static volatile SingularAttribute<Account, String> username;
    public static volatile SingularAttribute<Account, Boolean> status;

}