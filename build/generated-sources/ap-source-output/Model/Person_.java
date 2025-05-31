package Model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-11T21:21:56")
@StaticMetamodel(Person.class)
public class Person_ { 

    public static volatile SingularAttribute<Person, String> address;
    public static volatile SingularAttribute<Person, Date> birthdate;
    public static volatile SingularAttribute<Person, Character> gender;
    public static volatile SingularAttribute<Person, String> contact;
    public static volatile SingularAttribute<Person, String> name;
    public static volatile SingularAttribute<Person, String> ic;
    public static volatile SingularAttribute<Person, Integer> id;
    public static volatile SingularAttribute<Person, String> email;

}