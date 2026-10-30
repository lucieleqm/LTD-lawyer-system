package com.advocacia.estacio.infra.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfigurations {

	private final SecurityFilter securityFilter;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;


	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.cors(Customizer.withDefaults())
				.csrf(csrf -> csrf.disable())
				.headers(headers -> headers.frameOptions(frame -> frame.disable()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.exceptionHandling(exception -> exception
						.authenticationEntryPoint(customAuthenticationEntryPoint)
						.accessDeniedHandler(customAccessDeniedHandler)
				)

				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers("/h2-console/**").permitAll()
						.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/docs/**").permitAll()

						// AUTH
						.requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()


						// DEMANDAS
						//.requestMatchers(HttpMethod.GET, "/demandas/**").hasRole("ESTAGIARIO")
						.requestMatchers(HttpMethod.PATCH, "/demandas/{demandaId}/change").hasRole("PROFESSOR")
						.requestMatchers(HttpMethod.POST, "/demandas/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/demandas/role/{role}").hasAnyRole("ADMIN", "ADVOGADO", "PROFESSOR")

						.requestMatchers(HttpMethod.GET, "/demandas/me").hasAnyRole("ESTAGIARIO", "PROFESSOR", "ADVOGADO")
						.requestMatchers(HttpMethod.GET, "/demandas/pessoa/{pessoaId}").hasAnyRole("ADMIN")

//						.requestMatchers(HttpMethod.GET, "/demandas/estagiario/{estagiarioId}").hasRole("ESTAGIARIO")
//						.requestMatchers(HttpMethod.GET, "/demandas/advogado/{advogadoId}").hasRole("ADVOGADO")
//						.requestMatchers(HttpMethod.GET, "/demandas/professor/{professorId}").hasRole("PROFESSOR")
						.requestMatchers(HttpMethod.GET, "/demandas/status/{demandaStatus}").hasRole("ESTAGIARIO")
						.requestMatchers("/demandas/responde/**").hasRole("PROFESSOR")

						// ESTAGIÁRIOS
						.requestMatchers(HttpMethod.POST, "/estagiarios").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/estagiarios/{id}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/estagiarios/periodos").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/estagiarios/buscar/{nome}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/estagiarios/buscarId/email/{email}").hasRole("ESTAGIARIO")
						.requestMatchers(HttpMethod.PATCH, "/estagiarios/desativar/usuarios").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PATCH, "/estagiarios/desativar/desativarPorData").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PATCH, "/estagiarios/data/{id}/ativarDesativar/").hasRole("ADMIN")

						// MOVIMENTOS
						.requestMatchers(HttpMethod.POST, "/movimentos/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/movimentos/buscar/{numeroDoProcesso}").hasRole("ADMIN")

						// PROCESSOS
						.requestMatchers(HttpMethod.POST, "/processos/").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/processos/{id}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/processos/areasDoDireito").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/processos/tribunais").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/processos/processoStatus").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/processos/statusDoProcesso/{processoStatus}").permitAll()
						.requestMatchers(HttpMethod.GET, "/processos/buscar/{numeroDoProcesso}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/processos/numeroDoProcesso/{numeroDoProcesso}").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/processos/{id}").hasRole("ADMIN")
						.anyRequest().authenticated())
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	WebMvcConfigurer corsConfig() {
//		return new WebMvcConfigurer() {
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**")
//				.allowedOrigins("http://localhost:3000")
//				.allowedMethods("*")
//				.allowCredentials(true);
//			}
//		};
//	}
}
