package hellojpa.상속관계매핑.조인전략;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "B")
public class Book extends Item {

    private String author;
    private String isbn;
}
