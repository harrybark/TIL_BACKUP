package hellojpa.상속관계매핑.서브타입전략;

import javax.persistence.Entity;

@Entity
public class Book extends Item {

    private String author;
    private String isbn;

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }
}
