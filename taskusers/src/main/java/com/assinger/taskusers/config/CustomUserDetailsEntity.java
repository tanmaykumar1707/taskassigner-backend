package com.assinger.taskusers.config;

import com.assinger.taskusers.entity.UsersEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
public class CustomUserDetailsEntity implements UserDetails {

    private Long userId;
    private String emailId;
    private String password;
    private boolean isEnabled;
    private List<GrantedAuthority> authorities;

    CustomUserDetailsEntity(UsersEntity usersEntity){
        this.userId = usersEntity.getUserId();
        this.emailId = usersEntity.getEmail();
        this.password = usersEntity.getPassword();
        this.isEnabled = usersEntity.isEnabled();
        this.authorities = Arrays.stream(usersEntity.getRole().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        log.info("authoriteis=====>"+authorities.toString());
    }

    public Long getUserId(){
        return this.userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.emailId;
    }

    @Override
    public boolean isAccountNonExpired() {
        //return UserDetails.super.isAccountNonExpired();
        return  this.isEnabled;
    }

    @Override
    public boolean isAccountNonLocked() {
       // return UserDetails.super.isAccountNonLocked();
        return this.isEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
       // return UserDetails.super.isCredentialsNonExpired();
       return  this.isEnabled;
    }

    @Override
    public boolean isEnabled() {
       // return UserDetails.super.isEnabled();
        return this.isEnabled;
    }
}
