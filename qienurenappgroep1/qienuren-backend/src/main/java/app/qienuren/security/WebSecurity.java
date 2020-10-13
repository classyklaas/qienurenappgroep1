package app.qienuren.security;

import app.qienuren.controller.GebruikerRepository;
import app.qienuren.controller.GebruikerServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

//@EnableGlobalMethodSecurity(securedEnabled = true), optional, makes it possible to use @Secured in endpoints, annotation to grant authority per role/authority
//prepostenabled, optional, makes it possible to use @pre/post authorize, which is almost same as secured, only here you can use optional statements
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final GebruikerServiceInterface gebruikerServiceInterface;
    private  final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final GebruikerRepository gebruikerRepository;

    @Autowired
    public WebSecurity(GebruikerServiceInterface gebruikerServiceInterface, BCryptPasswordEncoder bCryptPasswordEncoder, GebruikerRepository gebruikerRepository){
        this.gebruikerServiceInterface = gebruikerServiceInterface;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.gebruikerRepository = gebruikerRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()    //enable cors, default disabled
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/medewerker/**").hasAnyRole("ADMIN", "MEDEWERKER")
                .antMatchers("/api/trainee/**").hasAnyRole("ADMIN", "TRAINEE")
                .antMatchers("/api/bedrijf/**").hasAnyRole("ADMIN", "BEDRIJF")
                .antMatchers("/api/login").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/**" ).hasAnyAuthority("READ:GEBRUIKER", "READ:URENFORMULIER")
//                    .antMatchers(HttpMethod.POST, "/api/**").hasAnyAuthority("CREATE:GEBRUIKER", "CREATE:URENFORMULIER")
//                    .antMatchers(HttpMethod.PUT, "/api/**").hasAnyAuthority("UPDATE:GEBRUIKER", "UPDATE:URENFORMULIER", "APPROVE:URENFORMULIER")
//                    .antMatchers(HttpMethod.DELETE, "/api/**").hasAnyAuthority("DELETE:GEBRUIKER", "DELETE:URENFORMULIER")
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(getAuthenticationFilter())
                .addFilter(new AuthorizationFilter(authenticationManager(), gebruikerRepository))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(gebruikerServiceInterface).passwordEncoder(bCryptPasswordEncoder);
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/api/login");
        return filter;
    }

    //configuration for cors from CorsConfiguration class
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setExposedHeaders(Arrays.asList("Authorization", "userId", "userRole", "Content-Disposition"));
        configuration.setAllowedOrigins(Arrays.asList("*"));    //allow all origins
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);   // to allow headers
        configuration.setAllowedHeaders(Arrays.asList("*"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}