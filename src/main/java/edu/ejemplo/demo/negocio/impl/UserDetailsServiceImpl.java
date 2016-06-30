package edu.ejemplo.demo.negocio.impl;

import edu.ejemplo.demo.model.Role;
import edu.ejemplo.demo.model.User;
import edu.ejemplo.demo.repositorios.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, rollbackFor = Exception.class, timeout = 30)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null || username.trim().isEmpty()){
            throw new IllegalArgumentException("Empty username");
        }

        User appUser = userRepository.findOneByEmail(username);
        if(appUser == null || !appUser.getEmailVerificado()){
            throw new UsernameNotFoundException("User not found or inactive");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for(Role role : appUser.getRoles()){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getCode());
            grantedAuthorities.add(grantedAuthority);
        }
        return new org.springframework.security.core.userdetails.User(username, appUser.getPassword(), grantedAuthorities);
    }
}
