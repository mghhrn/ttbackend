package ir.mghhrn.ttbackend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(schema = "public", name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 8314767180986460245L;

    @Id
    @SequenceGenerator(name = "roleSeq", sequenceName="role_sequence", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(name = "ROLE_PERMISSION",
            joinColumns =
            @JoinColumn(name = "role_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "role_permission_fk_role_id")
            ),
            inverseJoinColumns =
            @JoinColumn(name = "permission_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "role_permission_fk_permission_id")
            )
    )
    private List<Permission> permissions;
}
