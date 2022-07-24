package hellojpa.상속관계매핑.통합테이블전략;

import javax.persistence.Entity;

@Entity
public class Album extends Item {

    private String artist;

    public String getArtist() {
        return artist;
    }
}
