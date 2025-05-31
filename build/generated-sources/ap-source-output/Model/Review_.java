package Model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-11T21:21:56")
@StaticMetamodel(Review.class)
public class Review_ { 

    public static volatile SingularAttribute<Review, Date> date;
    public static volatile SingularAttribute<Review, Integer> accountid;
    public static volatile SingularAttribute<Review, Integer> productid;
    public static volatile SingularAttribute<Review, Integer> rate;
    public static volatile SingularAttribute<Review, String> details;
    public static volatile SingularAttribute<Review, Integer> id;

}