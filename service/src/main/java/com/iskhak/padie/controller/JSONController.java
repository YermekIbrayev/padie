package com.iskhak.padie.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iskhak.padie.config.Constants;
import com.iskhak.padie.dao.PackageModelDAO;
import com.iskhak.padie.dao.ProviderDAO;
import com.iskhak.padie.dao.ReviewDAO;
import com.iskhak.padie.dao.ServiceItemDAO;
import com.iskhak.padie.dao.UserDAO;
import com.iskhak.padie.model.Review;
import com.iskhak.padie.model.packagedata.PackageModel;
import com.iskhak.padie.model.packagedata.SetPackageModel;
import com.iskhak.padie.model.packagedata.ViewedPackage;
import com.iskhak.padie.model.security.User;
import com.iskhak.padie.security.JwtAuthenticationRequest;
import com.iskhak.padie.security.JwtTokenUtil;
import com.iskhak.padie.security.service.JwtAuthenticationResponse;

@RestController
@RequestMapping(value="json")
public class JSONController {
	@Autowired
	private ServiceItemDAO serviceItemDAO;
	@Autowired
	private PackageModelDAO packageModelDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ProviderDAO providerDAO;
	@Autowired
	private JwtTokenUtil jwtTokenUtil; 
    @Autowired
    private UserDetailsService userDetailsService;
	@Autowired
	private ReviewDAO reviewDAO;
	
	// client 
	@RequestMapping(value="/serviceList", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getServiceListJSON(){

		return ResponseEntity.ok(serviceItemDAO.list());
	} 
	
	// not using
	@RequestMapping(value="/OrderList", method = RequestMethod.GET, produces = "application/json")
	public List<PackageModel> getOrderListJSON(@RequestHeader(Constants.TOKEN_HEADER) String token){
		return packageModelDAO.list();
	}
	
	// client
	@RequestMapping(value="/sendOrder", method = RequestMethod.POST)
	public ResponseEntity<?> sendOrder(@RequestHeader(Constants.TOKEN_HEADER) String token, @RequestBody SetPackageModel order){
		System.out.println(order.getId());
		System.out.println(order.getProviderID());
		Long clientId = validateByToken(token);
		if(clientId ==-1){
			return new ResponseEntity<String>(Constants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
		}
		return  ResponseEntity.ok(packageModelDAO.setOrder(order, clientId));
	}
	
	// client
	// Order creation date = null. Why? Should be fixed
	@RequestMapping(value="/getOrderPrice", method = RequestMethod.POST)
	public ResponseEntity<?> getOrderPrice(@RequestHeader(Constants.TOKEN_HEADER) String token, @RequestBody SetPackageModel order){
		System.out.println(order.getId());
		Long clientId = validateByToken(token);
		if(clientId ==-1){
			return new ResponseEntity<String>(Constants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
		}
		return ResponseEntity.ok(packageModelDAO.getOrderPrice(order));
	}
	
/*	@RequestMapping(value="/testSetPackageList", method = RequestMethod.GET, produces = "application/json")
	public List<SetPackageModel> testSetPackageList(){
		return packageModelDAO.testSetPackageModel();
	}*/
	
	// provider
	@RequestMapping(value="/getNewOrders/{deviceId}/{date}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getNewOrders(@RequestHeader(Constants.TOKEN_HEADER) String token, 
			@PathVariable("deviceId") String deviceId, 
			@PathVariable("date") @DateTimeFormat(pattern=Constants.DATE_TIME_FORMAT) Date date){
		Long providerId = validateByToken(token);
		if(providerId ==-1){
			return new ResponseEntity<String>(Constants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
		}
		
		return ResponseEntity.ok(packageModelDAO.getNewOrders(deviceId, date, providerId));
	}
	
	// provider
	@RequestMapping(value="/setViewedOrders", method = RequestMethod.POST)
	public ResponseEntity<?> viewedOrder(@RequestHeader(Constants.TOKEN_HEADER) String token, @RequestBody ViewedPackage viewedPackage){
		System.out.println("Viewed package:"+viewedPackage.getId()+" viewed"+viewedPackage.getViewed());
		Long providerId = validateByToken(token);
		if(providerId ==-1){
			return new ResponseEntity<String>(Constants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
		}
		packageModelDAO.setViewedPackage(viewedPackage, providerId);
		return ResponseEntity.ok("");
	}
	
	// provider
	@RequestMapping(value="/setAccepted/{pkgId}", method=RequestMethod.GET)
	public ResponseEntity<?> acceptOrder(@RequestHeader(Constants.TOKEN_HEADER) String token, @PathVariable("pkgId") int pkgId){
		Long providerId = validateByToken(token);
		if(providerId ==-1){
			return new ResponseEntity<String>(Constants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
		}
		boolean response = packageModelDAO.acceptOrder(pkgId, providerId);
		ResponseEntity<Void> result;
		if(response)
			result = new ResponseEntity<Void>(HttpStatus.OK);
		else
			result = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		return result;
	}
	
	// provider
	@RequestMapping(value="/setDone/{pkgId}", method=RequestMethod.GET)
	public ResponseEntity<?> finishOrder(@RequestHeader(Constants.TOKEN_HEADER) String token, 
			@PathVariable("pkgId") int pkgId){
		Long providerId = validateByToken(token);
		if(providerId ==-1){
			return new ResponseEntity<String>(Constants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
		}
		boolean response = packageModelDAO.finishOrder(pkgId, providerId);
		ResponseEntity<Void> result;
		if(response)
			result = new ResponseEntity<Void>(HttpStatus.OK);
		else
			result = new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		return result;
	}
	
	
	/// Test  function
	@RequestMapping(value="/getUser", method=RequestMethod.GET, produces = "application/json")
	public User getUser(){ 
		return userDAO.get(1);
	}
	
	// should be client and provider
	@RequestMapping(value="/login", method=RequestMethod.POST, produces = "application/json")
	ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
        // Perform the security    	
        final String token = userDAO.login(authenticationRequest);

        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        }

	// should be client and provider
	@RequestMapping(value="/register", method=RequestMethod.POST, produces="application/json")
	ResponseEntity<?> register(@RequestBody User user, Device device){
		String password = user.getPassword();
		String result = userDAO.register(user);
		if(result.equals(Constants.EMAIL_ALREADY_IN_USE))
			return (ResponseEntity<?>) ResponseEntity.badRequest();
		JwtAuthenticationRequest authenticationRequest = new JwtAuthenticationRequest(user.getEmail(), password);
		final String token = userDAO.login(authenticationRequest);
		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}
	
	//client
	@RequestMapping(value="/getProviders", method=RequestMethod.GET, produces="application/json")
	ResponseEntity<?> getProviders(@RequestHeader(Constants.TOKEN_HEADER) String token){
		Long clientId = validateByToken(token);
		if(clientId ==-1){
			return new ResponseEntity<String>(Constants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
		}
		return ResponseEntity.ok(providerDAO.list());
	}
	
	//client
	@RequestMapping(value="/sendReview", method=RequestMethod.POST, produces="application/json")
	ResponseEntity<?> sendReview(@RequestHeader(Constants.TOKEN_HEADER) String token,
			@RequestBody Review review){
		
		if(validateByToken(token) ==-1){
			return new ResponseEntity<String>(Constants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
		}
		
		if(!reviewDAO.addReview(review)){
			return (ResponseEntity<?>)ResponseEntity.badRequest();
		}
		
		return ResponseEntity.ok("");
	}
	
	@RequestMapping(value="/getReview/{pid}", method=RequestMethod.GET, produces="application/json")
	ResponseEntity<?> getReviews(@RequestHeader(Constants.TOKEN_HEADER) String token,
			@PathVariable int pid){
		
		if(validateByToken(token) ==-1){
			return new ResponseEntity<String>(Constants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
		}
		
		return ResponseEntity.ok(reviewDAO.getReviews(pid));
	}
	
	///----------------- helper functions -------------------------
	private Long validateByToken(String token){
		try{
			String email = jwtTokenUtil.getEmailFromToken(token);
			UserDetails user = userDetailsService.loadUserByUsername(email);
			if(!jwtTokenUtil.validateToken(token, user))
				return -1L;
			return jwtTokenUtil.getUserIdFromToken(token);
		} catch(Exception e){
			e.printStackTrace();
			return -1L;
		}
	}
}
