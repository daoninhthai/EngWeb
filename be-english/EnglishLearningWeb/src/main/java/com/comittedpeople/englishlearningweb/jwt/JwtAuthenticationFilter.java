package com.comittedpeople.englishlearningweb.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.comittedpeople.englishlearningweb.services.UserDetailsCustomServiceImpl;

public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserDetailsCustomServiceImpl userDetailsCustomService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		try {
			String jwt = getJwtFromRequest(request);
			
			if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				Long userID = tokenProvider.getUserIdFromJWT(jwt);
				
				UserDetails userDetails = userDetailsCustomService.loadUserById(userID);
				
				if (userDetails != null) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Authentication error" );
			e.printStackTrace();
		}
		
		
		//Cho phép HTTP Request đi qua DispatcherServlet của Spring và các Controller.
		filterChain.doFilter(request, response);
		
	}
	
	private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
	
}
