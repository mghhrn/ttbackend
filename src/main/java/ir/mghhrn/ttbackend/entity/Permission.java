package ir.mghhrn.ttbackend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(schema = "public", name = "permission")
public class Permission implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = -8469642395920948580L;

    @Id
    @SequenceGenerator(name = "permissionSeq", sequenceName="permission_sequence", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissionSeq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "farsi_name", nullable = false)
    private String farsiName;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();


    @Override
    public String getAuthority() {
        return name;
    }
}
