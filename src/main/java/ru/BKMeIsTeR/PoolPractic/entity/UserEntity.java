package ru.BKMeIsTeR.PoolPractic.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "t_user")
//Для того, чтобы в дальнейшим использовать класс Userв Spring Security,
// он должен реализовывать интерфейс UserDetails
public class UserEntity implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

//    @Size(min=2, message = "Не меньше 5 знаков")
    private String username;

//    @Size(min=2, message = "Не меньше 5 знаков")
    private String password;
    
    @Transient
    private String passwordConfirm;

    //FetchType.EAGER – «жадная» загрузка, т.е. список ролей загружается вместе с
    // пользователем сразу (не ждет пока к нему обратятся).
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id"/*,
            referencedColumnName = "id",
              insertable = false,
            updatable = false*/)
    private RoleEntity userRole;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                ", userRole=" + userRole.getName() +
                //", getAuthorities=" + this.getAuthorities() +
                '}';
    }

    public UserEntity() {

    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public RoleEntity getUserRole() {
        return userRole;
    }

    public void setUserRole(RoleEntity userRole) {
        this.userRole = userRole;
    }

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
