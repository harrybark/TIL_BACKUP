package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Member {

    @Id //@GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;

    public Member() {
    }

}
