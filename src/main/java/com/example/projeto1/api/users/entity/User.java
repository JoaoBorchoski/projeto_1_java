// package com.example.projeto1.api.users.entity;

// import jakarta.persistence.*;
// import java.time.Instant;
// import java.util.UUID;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import lombok.AllArgsConstructor;
// import org.hibernate.annotations.CreationTimestamp;
// import org.hibernate.annotations.UpdateTimestamp;

// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Entity
// @Table(name = "users", schema = "public")
// public class User {
//     @Id
//     @GeneratedValue(strategy = GenerationType.UUID)
//     @Column(name = "id", updatable = false, nullable = false)
//     private UUID id;

//     @Column(name = "name", nullable = false)
//     private String name;

//     @Column(name = "login", unique = true, nullable = false)
//     private String login;

//     @Column(name = "password")
//     private String password;

//     @Column(name = "is_admin", nullable = false)
//     private boolean isAdmin;

//     @Column(name = "is_super_user", nullable = false)
//     private boolean isSuperUser;

//     @Column(name = "is_disabled", nullable = false)
//     private boolean isDisabled;

//     @CreationTimestamp
//     @Column(name = "created_at", updatable = false, nullable = false)
//     private Instant createdAt;

//     @UpdateTimestamp
//     @Column(name = "updated_at", nullable = false)
//     private Instant updatedAt;
// }


package com.example.projeto1.api.users.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; 

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", schema = "public")
public class User implements UserDetails { 
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;

    @Column(name = "is_super_user", nullable = false)
    private boolean isSuperUser;

    @Column(name = "is_disabled", nullable = false)
    private boolean isDisabled;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna as permissões/perfis do usuário.
        // Neste exemplo, estamos usando isAdmin e isSuperUser para definir os papéis.
        // Você pode adaptar isso à sua lógica de perfis.
        List<GrantedAuthority> authorities = new java.util.ArrayList<>();
        if (this.isAdmin) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); 
        }
        if (this.isSuperUser) {
            authorities.add(new SimpleGrantedAuthority("ROLE_SUPER_USER"));
        }
        if (!this.isAdmin && !this.isSuperUser) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.login; 
    }

    @Override
    public String getPassword() {
        return this.password; 
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
        return !this.isDisabled;
    }
}