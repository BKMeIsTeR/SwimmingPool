package ru.BKMeIsTeR.PoolPractic.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_role")
public class RoleEntity implements GrantedAuthority {

    @Id
    private Long id;

    private String name;

//    @JsonIgnore
//    @OneToMany(cascade = CascadeType.ALL
//            , mappedBy = "userRole")
//    private Set<UserEntity> usersEntity;

    public RoleEntity() {
    }

    public RoleEntity(Long id) {
        this.id = id;
    }

    public RoleEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Set<UserEntity> getUsersEntity() {
//        return usersEntity;
//    }
//
//    public void setUsersEntity(Set<UserEntity> usersEntity) {
//        this.usersEntity = usersEntity;
//    }

    @Override
    public String getAuthority() {
        return getName();
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
//                ", usersEntity=" + usersEntity +
                '}';
    }
}
