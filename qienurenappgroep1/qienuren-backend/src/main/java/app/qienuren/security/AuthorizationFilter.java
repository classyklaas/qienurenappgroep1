package app.qienuren.security;

import app.qienuren.controller.GebruikerRepository;
import app.qienuren.model.Gebruiker;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    private final GebruikerRepository gebruikerRepository;

    public AuthorizationFilter(AuthenticationManager authManager,
                               GebruikerRepository gebruikerRepository) {
        super(authManager);
        this.gebruikerRepository = gebruikerRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(SecurityConstants.HEADER_STRING);
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
        String token = req.getHeader(SecurityConstants.HEADER_STRING);

        if (token != null) {
            token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
            String user = Jwts.parser()
                    .setSigningKey(SecurityConstants.getTokenSecret())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            if (user != null) {
                Gebruiker gebruiker = gebruikerRepository.findByEmail(user);
                if (gebruiker == null) return null;
                GebruikerPrincipal gebruikerPrincipal = new GebruikerPrincipal(gebruiker);
                return new UsernamePasswordAuthenticationToken(gebruikerPrincipal, null, gebruikerPrincipal.getAuthorities());
                //changed the return from (user,null,userPrincipal.getauth..) to (userPrincipal,null,userPr...) and created a userId in principal to add a statement in @preauthorize for userId
            }
            return null;
        }
        return null;
    }
}