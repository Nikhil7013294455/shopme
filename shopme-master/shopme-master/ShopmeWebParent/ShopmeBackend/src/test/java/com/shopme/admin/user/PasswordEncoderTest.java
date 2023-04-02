package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
	
	@Test
	public void testEncodePassword() {
		BCryptPasswordEncoder pE = new BCryptPasswordEncoder();
		String rawPassword = "suppy2819";
		String encodedPassword = pE.encode(rawPassword);
		System.out.println(encodedPassword);
		boolean matches = pE.matches(rawPassword, encodedPassword);
		assertThat(matches).isTrue();
	}
}
