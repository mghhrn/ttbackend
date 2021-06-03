package ir.mghhrn.ttbackend.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(schema = "public", name = "user")
public class User implements UserDetails, Serializable {

    private static final long serialVersionUID = -9101923856026857402L;

    @Id
    @SequenceGenerator(name = "userSeq", sequenceName="user_sequence", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "hashed_password")
    private String hashedPassword;

    @Column(name = "cellphone")
    private String cellphone;

    @Column(name = "is_cellphone_validated")
    private Boolean cellphoneValidated;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ROLE",
            joinColumns =
            @JoinColumn(name = "user_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "user_role_fk_user_id")
            ),
            inverseJoinColumns =
            @JoinColumn(name = "role_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "user_role_fk_role_id")
            )
    )
    private List<Role> roles;

    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY)
    private List<TherapySession> therapySessions;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "is_updated_at")
    private Date updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    @Override
    public String getUsername() {
        return cellphone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
