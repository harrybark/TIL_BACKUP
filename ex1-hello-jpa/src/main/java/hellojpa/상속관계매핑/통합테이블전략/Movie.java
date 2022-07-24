package hellojpa.상속관계매핑.통합테이블전략;

import javax.persistence.Entity;

@Entity
public class Movie extends Item {

    private String director;
    private String actor;

    public String getDirector() {
        return director;
    }

    public String getActor() {
        return actor;
    }
}
