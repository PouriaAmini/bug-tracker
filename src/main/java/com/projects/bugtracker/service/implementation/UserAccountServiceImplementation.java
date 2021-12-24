package com.projects.bugtracker.service.implementation;

import com.projects.bugtracker.model.UserAccount;
import com.projects.bugtracker.repository.UserAccountRepository;
import com.projects.bugtracker.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserAccountServiceImplementation implements UserAccountService, UserDetailsService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserAccount> userAccount = Optional.ofNullable(userAccountRepository.findUserAccountByEmail(email));
        if(userAccount.isEmpty()) {
            log.error("USER WITH THIS EMAIL ADDRESS WAS NOT FOUND");
            throw new UsernameNotFoundException("USER WITH THIS EMAIL ADDRESS WAS NOT FOUND");
        } else {
            log.info("USER WITH THIS EMAIL ADDRESS WAS FOUND");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userAccount
                .get()
                .getPosition()
                .getUserRole()
                )
        );
        return new User(
                userAccount.get().getEmail(),
                userAccount.get().getPassword(),
                authorities
        );
    }

    @Override
    public Optional<UserAccount> create(UserAccount userAccount) {
        log.info("UserAccount: CREATE USER_ACCOUNT {}", userAccount.getEmail());
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        return Optional.of(userAccountRepository.save(userAccount));
    }

    @Override
    public Optional<UserAccount> get(String email) {
        log.info("UserAccount: FETCHING email {}", email);
        return Optional.ofNullable(userAccountRepository.findUserAccountByEmail(email));
    }

    @Override
    public List<UserAccount> getUserAccounts() {
        return userAccountRepository.findAll();
    }
}
