package com.example.lab7_20212591.conf;

import com.example.lab7_20212591.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig {
    final UserRepository userRepository;
    //Instancia de la conexion con la db
    final DataSource dataSource;

    public WebSecurityConfig(DataSource dataSource, UserRepository userRepository) {
        this.dataSource = dataSource;
        this.userRepository = userRepository;
    }
    //password encoder para obtener las contraseñas sin cifrar
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin()
                //ruta en el controlador
                .loginPage("/Teatrologin")
                //action en la vista del login
                .loginProcessingUrl("/submitLoginForm")
                //Esto indica a donde se irá luego de loguearse
                .successHandler((request, response, authentication) -> {
                    //En caso de ir al login desde una pagina determinada, al loguearse debe volver a la misma página
                    DefaultSavedRequest defaultSavedRequest =
                            (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");

                    HttpSession session = request.getSession();
                    session.setAttribute("user", userRepository.findByEmail(authentication.getName()));


                    //si vengo por url defaultSR existe
                    if (defaultSavedRequest != null) {
                        String targetURl = defaultSavedRequest.getRequestURL();
                        new DefaultRedirectStrategy().sendRedirect(request, response, targetURl);
                    } else { //estoy viniendo del botón de login
                        String rol = "";
                        //.getAuthorities obtiene el rol
                        for (GrantedAuthority role : authentication.getAuthorities()) {
                            rol = role.getAuthority();
                            break;
                        }

                        if (rol.equals("ADMIN")) {
                            response.sendRedirect("/shipper");
                        }else if(rol.equals("CLIENTE")) {
                            response.sendRedirect("/employee");
                        }else if(rol.equals("GERENTE")){

                        }
                    }
                });

        http.authorizeHttpRequests()
                .requestMatchers("/Sala", "/Sala/**").hasAnyAuthority("GERENTE")
                .requestMatchers("/Obra", "/Obra/**").hasAnyAuthority("GERENTE","CLIENTE")
                .requestMatchers("/Funcion", "/Funcion/**").hasAnyAuthority("GERENTE","ADMIN")
                .requestMatchers("/Reserva/allReservas").hasAnyAuthority("ADMIN")
                .requestMatchers("/Reserva/misReservas").hasAnyAuthority("CLIENTE")
                //Las demás rutas que no son protegidas
                .anyRequest().permitAll();

        http.logout()
                .logoutSuccessUrl("/loginTeatro")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);

        return http.build();
    }
    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        String sqlAuth = "SELECT email,password FROM users where email = ?";

        String sqlAuto = "SELECT u.email, r.nombre FROM users u " +
                "inner join roles r on u.roleId = r.id " +
                "where u.email = ?";

        users.setUsersByUsernameQuery(sqlAuth);
        users.setAuthoritiesByUsernameQuery(sqlAuto);

        return users;
    }
}
