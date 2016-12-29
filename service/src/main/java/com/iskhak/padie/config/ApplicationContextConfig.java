package com.iskhak.padie.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.iskhak.padie.dao.UserDAO;
import com.iskhak.padie.dao.UserDAOImpl;

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


@Configuration
@ComponentScan("com.iskhak.padie")
@EnableTransactionManagement
@EnableWebMvc
@EnableJpaRepositories(basePackages = "com.iskhak.padie", considerNestedRepositories = true)

public class ApplicationContextConfig {
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "a1s2d3f4g5h6";
    private static final String PROPERTY_NAME_DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/sys";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "root";
    
    @Bean(name = "viewResolver")
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
     
    @Bean
    public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
        return new DeviceResolverHandlerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(deviceResolverHandlerInterceptor());
    }
          
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
    	BasicDataSource dataSource = new BasicDataSource();
    	dataSource.setDriverClassName(PROPERTY_NAME_DATABASE_DRIVER );
    	dataSource.setUrl(PROPERTY_NAME_DATABASE_URL );
    	dataSource.setUsername(PROPERTY_NAME_DATABASE_USERNAME );
    	dataSource.setPassword(PROPERTY_NAME_DATABASE_PASSWORD );
    	
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
    
    
	@Bean
	public Map<String, Object> jpaProperties() {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("hibernate.dialect", MySQL5Dialect.class.getName());
		props.put("hibernate.hbm2ddl.auto", "update");
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.format_sql", "true");
		props.put("hibernate.connection.charSet", "UTF-8");
		return props;
	}
	
	@Bean 
	public JpaDialect jpaDialect() {
		return new HibernateJpaDialect();
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(true);
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
		return hibernateJpaVendorAdapter;
	}
	
	private LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
		lef.setPersistenceUnitName("persistenceUnit");
		lef.setDataSource(this.getDataSource());
		lef.setJpaPropertyMap(this.jpaProperties());
		lef.setJpaVendorAdapter(this.jpaVendorAdapter());
		lef.setPackagesToScan("com.iskhak.padie");
		return lef;
	}
	
	@Bean 
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = this.localContainerEntityManagerFactoryBean();
		factory.afterPropertiesSet();
		return factory.getObject();
	}

}