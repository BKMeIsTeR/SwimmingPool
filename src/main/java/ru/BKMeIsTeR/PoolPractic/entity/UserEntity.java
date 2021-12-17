package ru.BKMeIsTeR.PoolPractic.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
@Data
//@ToString(exclude = {"userRole"})
@NoArgsConstructor
@AllArgsConstructor
//Для того, чтобы в дальнейшим использовать класс Userв Spring Security,
//он должен реализовывать интерфейс UserDetails
public class UserEntity implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Size(min=5, message = "Не меньше 5 знаков")
    @Column(name = "user_name")
    private String username;

    @Size(min=5, message = "Не меньше 5 знаков")
    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private RoleEntity userRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //new AuthorityUtils().createAuthorityList(getUserRole().getName());
        return Collections.singleton(getUserRole());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
