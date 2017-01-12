package com.iskhak.padie.security.controller;

import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;

/*@RestController*/
public class AuthenticationRestController {

    /*@Value("${jwt.header}")*/
/*    private String tokenHeader="Authorization";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;*/

    /*@RequestMapping(value = "/auth", method = RequestMethod.POST)*/
    public ResponseEntity<?> createAuthenticationToken(/*@RequestBody JwtAuthenticationRequest authenticationRequest, Device device*/) /*throws AuthenticationException*/ {
/*    	System.out.println("request: "+authenticationRequest.getUsername());
        // Perform the security
    	
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
       
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails, device);

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));*/
    	return null;
    }

    /*@RequestMapping(value = "refresh", method = RequestMethod.GET)*/
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
/*        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }*/
    	return null;
    }

}
