package hellojpa.값타입;

import javax.persistence.*;

@Entity
@Table(name = "EMBEDDED_MEMBER")
public class embeddedMember {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    private Period period;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "WORK_STREET")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "WORK_ZIPCODE"))
    }
    )
    private Address address;
}
