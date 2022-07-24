package hellojpa.상속관계매핑.서브타입전략;

import javax.persistence.Entity;

@Entity
public class Album extends Item {

    private String artist;

    public String getArtist() {
        return artist;
    }
}
