package cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Builder
public class Player implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long player_id;
    @Column
    private String name;
    @Column
    @CreationTimestamp
    private LocalDateTime dateOfRegister;
    @OneToOne
    @JoinColumn(name = "game_id")
    private Game game;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String password;


    /**
     *
     * @return List<Roles>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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
