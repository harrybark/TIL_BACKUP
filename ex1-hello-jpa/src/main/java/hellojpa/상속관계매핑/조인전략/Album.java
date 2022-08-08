package hellojpa.상속관계매핑.조인전략;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "A")
public class Album extends Item {

    private String artist;
}
