package com.iskhak.padie.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.iskhak.padie.dao.UserDAO;
import com.iskhak.padie.dao.UserDAOImpl;
import com.iskhak.padie.model.User;
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


@Configuration
@ComponentScan("com.iskhak.padie")
@EnableTransactionManagement
@EnableWebMvc
public class ApplicationContextConfig {
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
    

}
