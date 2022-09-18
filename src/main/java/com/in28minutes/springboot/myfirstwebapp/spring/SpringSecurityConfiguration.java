package com.in28minutes.springboot.myfirstwebapp.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.function.Function;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * SpringSecurityConfiguration handles ...
 * <p>
 * $LastChangedDate$<br>
 * $LastChangedBy$<br>
 * $Revision$<br>
 * </p>
 *
 * @author Mark Lordan
 */
@Configuration public class SpringSecurityConfiguration {

	@Bean public InMemoryUserDetailsManager createUserDetailsManager() {

		UserDetails userDetails = createNewUser("in28minutes", "dummy");
		UserDetails userDetails2 = createNewUser("mark", "dummydummy");

		return new InMemoryUserDetailsManager(userDetails, userDetails2);
	}

	private UserDetails createNewUser(final String username, final String password) {
		Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);

		return User.builder()
				.passwordEncoder(passwordEncoder)
				.username(username)
				.password(password)
				.roles("USER", "ADMIN")
				.build();
	}

	@Bean public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
		http.formLogin(withDefaults());
		http.csrf().disable();
		http.headers().frameOptions().disable();
		return http.build();
	}
}
