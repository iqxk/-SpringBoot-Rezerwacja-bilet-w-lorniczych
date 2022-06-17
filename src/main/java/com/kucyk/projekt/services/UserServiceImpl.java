package com.kucyk.projekt.services;

import com.kucyk.projekt.config.ProfileNames;
import com.kucyk.projekt.repositories.RoleRepository;
import com.kucyk.projekt.repositories.UserRepository;
import com.kucyk.projekt.models.Role;

import com.kucyk.projekt.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service("userDetailsService")
@Profile((ProfileNames.USERS_IN_DATABASE))
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return convertToUserDetails(user);
    }

    private UserDetails convertToUserDetails(com.kucyk.projekt.models.User user){
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getType().toString()));
        }
        return new User(user.getUsername(), user.getPassword(), true, true, true, true, grantedAuthorities);
    }

    public boolean checkIfUserExists(com.kucyk.projekt.models.User u)
    {
        var user = userRepository.findByUsername(u.getUsername());
        return user != null;
    }

    public com.kucyk.projekt.models.User saveUser(com.kucyk.projekt.models.User u)
    {
        u.setPassword(encyptPassword(u.getPassword()));

        Role roleUser = roleRepository.findByType(Role.Types.ROLE_USER);
        u.setRoles(new HashSet<>(Arrays.asList(roleUser)));

        userRepository.save(u);
        return u;
    }

    public String encyptPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public com.kucyk.projekt.models.User getUserById(Long id)
    {
        return userRepository.findById(id).get();
    }

    public com.kucyk.projekt.models.User findUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
