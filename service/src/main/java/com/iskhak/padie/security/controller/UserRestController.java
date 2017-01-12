package com.iskhak.padie.security.controller;

import com.iskhak.padie.security.JwtUser;

import javax.servlet.http.HttpServletRequest;

/*@RestController*/
public class UserRestController {

    /*@Value("${jwt.header}")*/
/*    private String tokenHeader = "Authorization";

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;*/

    /*@RequestMapping(value = "user", method = RequestMethod.GET)*/
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
/*        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;*/
    	return null;
    }

}
