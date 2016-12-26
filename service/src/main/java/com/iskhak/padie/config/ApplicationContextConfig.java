package com.iskhak.padie.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.iskhak.padie.model.listdata.ExtraSelection;
import com.iskhak.padie.model.listdata.GetServiceItem;
import com.iskhak.padie.model.listdata.MainSelection;
import com.iskhak.padie.model.listdata.SetServiceItem;
import com.iskhak.padie.model.packagedata.PackageModel;
import com.iskhak.padie.model.packagedata.SelectedItems;
import com.iskhak.padie.model.packagedata.SelectedItemsAdd;
import com.iskhak.padie.model.packagedata.SelectedItemsAddExtra;
import com.iskhak.padie.model.packagedata.SetPackageModel;
import com.iskhak.padie.model.packagedata.SetSelectedItems;
import com.iskhak.padie.model.packagedata.SetSelectedItemsAdd;
import com.iskhak.padie.model.packagedata.SetSelectedItemsAddExtra;
import com.iskhak.padie.model.packagedata.ViewedPackage;
import com.iskhak.padie.model.security.User;
import com.iskhak.padie.security.JwtAuthenticationEntryPoint;
import com.iskhak.padie.security.JwtAuthenticationTokenFilter;

@Configuration
@ComponentScan("com.iskhak.padie")
@EnableTransactionManagement
@EnableWebMvc
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationContextConfig extends WebSecurityConfigurerAdapter  {
    @Bean(name = "viewResolver")
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
     
    
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
    	BasicDataSource dataSource = new BasicDataSource();
    	dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    	dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/sys");
    	dataSource.setUsername("root");
    	dataSource.setPassword("a1s2d3f4g5h6");
    	
    	return dataSource;
    }
    
    
    private Properties getHibernateProperties() {
    	Properties properties = new Properties();
    	properties.put("hibernate.show_sql", "true");
    	properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
    	return properties;
    }
    
    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
    	LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
    	sessionBuilder.addProperties(getHibernateProperties());
    	sessionBuilder.addAnnotatedClasses(User.class);
    	sessionBuilder.addAnnotatedClasses(GetServiceItem.class);
    	sessionBuilder.addAnnotatedClasses(SetServiceItem.class);
    	sessionBuilder.addAnnotatedClasses(MainSelection.class);
    	sessionBuilder.addAnnotatedClasses(ExtraSelection.class);
    	sessionBuilder.addAnnotatedClasses(PackageModel.class);
    	sessionBuilder.addAnnotatedClasses(SelectedItems.class);
    	sessionBuilder.addAnnotatedClasses(SelectedItemsAdd.class);
    	sessionBuilder.addAnnotatedClasses(SelectedItemsAddExtra.class);
    	sessionBuilder.addAnnotatedClasses(SetPackageModel.class);
    	sessionBuilder.addAnnotatedClasses(SetSelectedItems.class);
    	sessionBuilder.addAnnotatedClasses(SetSelectedItemsAdd.class);
    	sessionBuilder.addAnnotatedClasses(SetSelectedItemsAddExtra.class);
    	sessionBuilder.addAnnotatedClasses(ViewedPackage.class);
    	return sessionBuilder.buildSessionFactory();
    }
    
	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(
			SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(
				sessionFactory);

		return transactionManager;
	}
    
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
/*        httpSecurity
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()

                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

                // don't create session
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // allow anonymous resource requests
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/** /*.html",
                        "/** /*.css",
                        "/** /*.js"
                ).permitAll()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated();

        // Custom JWT based security filter
        //httpSecurity
               // .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        httpSecurity.headers().cacheControl();*/
    }
}
