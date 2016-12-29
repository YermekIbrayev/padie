package com.iskhak.padie.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.mobile.device.DeviceWebArgumentResolver;
import org.springframework.mobile.device.site.SitePreferenceHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

@Configuration 
public class MobileConfig extends WebMvcConfigurerAdapter {
	   @Override
	   public void addArgumentResolvers(
	       List<HandlerMethodArgumentResolver> argumentResolvers) {
	     argumentResolvers.add(new ServletWebArgumentResolverAdapter(
	         new DeviceWebArgumentResolver()));
	     argumentResolvers
	         .add(new SitePreferenceHandlerMethodArgumentResolver());
	   }
}
