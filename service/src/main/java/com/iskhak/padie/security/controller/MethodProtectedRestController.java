package com.iskhak.padie.security.controller;

import org.springframework.http.ResponseEntity;

/*@RestController
@RequestMapping("protected")*/
public class MethodProtectedRestController {
/*	@Autowired
    private UserDetailsService userDetailsService;*/
    /**
     * This is an example of some different kinds of granular restriction for endpoints. You can use the built-in SPEL expressions
     * in @PreAuthorize such as 'hasRole()' to determine if a user has access. Remember that the hasRole expression assumes a
     * 'ROLE_' prefix on all role names. So 'ADMIN' here is actually stored as 'ROLE_ADMIN' in database!
     **/
    /*@RequestMapping(method = RequestMethod.GET)*/
    /*@PreAuthorize("hasRole('ROLE_ADMIN')") -- not working don't know why*/
    public ResponseEntity<?> getProtectedGreeting() {
        /*return ResponseEntity.ok("Greetings from admin protected method! For: " );*/
    	return null;
    }

}