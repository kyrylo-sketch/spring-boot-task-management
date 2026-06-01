package com.karol.tastManagement.security;


import com.karol.tastManagement.model.User;
import com.karol.tastManagement.model.UserPrincipal;
import com.karol.tastManagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user details for user={}", username);
        User customer = repo.findByName(username).orElse(null);

        if (customer == null) {
            log.warn("User details not found for user={}", username);
            throw new UsernameNotFoundException("User not found");
        }

        log.info("User details found for user={}", username);
        return new UserPrincipal(customer);
    }
}
