package com.study.bookstore.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.study.bookstore.dto.response.ResponseCode;
import com.study.bookstore.dto.response.ResponseMessage;
import com.study.bookstore.filter.JwtAuthenticationFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

//* Spring Web 보안 설정 */

@Configurable
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	protected SecurityFilterChain configure(HttpSecurity security) throws Exception {

		security
			.httpBasic(HttpBasicConfigurer::disable)
			.sessionManagement(sessionManagement -> sessionManagement
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.csrf(CsrfConfigurer::disable)
			.cors(cors -> cors.configurationSource(configurationSource()))
			.authorizeHttpRequests(request -> request
				.requestMatchers("/api/v1/auth/**", 
				"/api/v1/books/categories-filter", 
				"/api/v1/books/discount-filter",
				"/api/v1/orders/shopping-cart"
				).permitAll()
				.anyRequest().authenticated()
			)

			.exceptionHandling(exception -> exception
				.authenticationEntryPoint(new AuthenticationFailEntryPoint())
			)

			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return security.build(); 

	};

	// CORS 설정
	@Bean
	protected CorsConfigurationSource configurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;

	};

	// 예외 설정
	class AuthenticationFailEntryPoint implements AuthenticationEntryPoint {

		@Override
		public void commence(
			HttpServletRequest reqeust, 
			HttpServletResponse response,
			AuthenticationException authException
			) throws IOException, ServletException {

				authException.printStackTrace();

				response.setContentType("application/json");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter()
					.write("{\"code\": \""+ ResponseCode.AUTHENTICATION_FAIL +"\" , \"message\": \""+ ResponseMessage.AUTHENTICATION_FAIL +"\"}");

			};

	};

}
