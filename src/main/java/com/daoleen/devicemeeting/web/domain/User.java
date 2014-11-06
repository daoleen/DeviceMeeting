package com.daoleen.devicemeeting.web.domain;

import com.daoleen.devicemeeting.web.converter.LocalDateTimePersistenceConverter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Created by alex on 17.6.14.
 */

@Entity
@Table(name = "users")
public class User implements Serializable, UserDetails {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Email
    @NotEmpty
    @Length(min = 3, max = 255)
    @Column(name = "email", unique = true, nullable = false, updatable = false)
    private String email;

    @NotEmpty
    @Length(min = 3, max = 128)
    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "is_enabled", nullable = false)
    private boolean enabled;

    @Column(name = "created_at", nullable = false)
    //@DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")	// I use a conversion service
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    //@DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime expiresAt;

    @Column(name = "is_locked", length = 1)
    private boolean locked;

    @Max(255)
    @Column(name = "locked_reason")
    private String lockedReason;

    @Column(name = "avatar")
    private String avatar;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role_refs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private com.daoleen.devicemeeting.web.domain.UserDetails userDetails;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy="owner")
    private List<Room> rooms;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy="user")
    private List<Invite> invite;

    public User() {
        enabled = true;
        createdAt = LocalDateTime.now();
        expiresAt = createdAt.plusYears(1L);
        locked = false;
    }

    public User(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Transient
	private static final long serialVersionUID = -1704493851101014483L;
    
    // Getters / Setters
    
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        LocalDateTime today = LocalDateTime.now();
        return (expiresAt.compareTo(today) >= 0);
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getLockedReason() {
        return lockedReason;
    }

    public void setLockedReason(String lockedReason) {
        this.lockedReason = lockedReason;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public com.daoleen.devicemeeting.web.domain.UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(com.daoleen.devicemeeting.web.domain.UserDetails userDetails) {
        this.userDetails = userDetails;
    }
    

    public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public List<Invite> getInvite() {
		return invite;
	}

	public void setInvite(List<Invite> invite) {
		this.invite = invite;
	}

	@Override
    public String toString() {
        return (getUserDetails().toString() != null) ? getUserDetails().toString() : email;
    }
}
