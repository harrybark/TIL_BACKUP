package hellojpa.상속관계매핑.조인전략;

import javax.persistence.*;

@Entity
@Inheritance(
        strategy = InheritanceType.JOINED // Join 전략
)
// DTYPE을 만들어주며, 엔티티명을 default로 사용한다.
@DiscriminatorColumn(name = "DIS_TYPE") // DTYPE -> DIS_TYPE
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
