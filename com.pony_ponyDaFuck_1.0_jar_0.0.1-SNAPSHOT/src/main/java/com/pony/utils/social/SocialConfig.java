// package com.pony.utils.social;

// import javax.inject.Inject;
// import javax.sql.DataSource;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Scope;
// import org.springframework.context.annotation.ScopedProxyMode;
// import org.springframework.core.env.Environment;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.crypto.encrypt.TextEncryptor;
// import org.springframework.social.connect.ConnectionFactoryLocator;
// import org.springframework.social.connect.ConnectionRepository;
// import org.springframework.social.connect.UsersConnectionRepository;
// import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
// import org.springframework.social.connect.support.ConnectionFactoryRegistry;
// import org.springframework.social.connect.web.ConnectController;
// import org.springframework.social.facebook.connect.FacebookConnectionFactory;
// import org.springframework.social.twitter.connect.TwitterConnectionFactory;

// @Configuration
// public class SocialConfig {

//     @Inject
//     private Environment environment;

//     @Inject
//     private DataSource dataSource;

//     @Inject
//     private TextEncryptor textEncryptor;

//     @Bean
//     public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {

//         ConnectionFactoryLocator factory = connectionFactoryLocator();
//         ConnectionRepository repository = connectionRepository();
        
//         ConnectController controller =  new ConnectController(connectionFactoryLocator, connectionRepository);
//         controller.setApplicationUrl("/");

//         return controller;
//     }

//     @Bean
//     public ConnectionFactoryLocator connectionFactoryLocator()
//     {
//         ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        
//         registry.addConnectionFactory(new FacebookConnectionFactory(
//             environment.getProperty("facebook.clientId"),
//             environment.getProperty("facebook.clientSecret")));
        
//         registry.addConnectionFactory(new TwitterConnectionFactory(
//             environment.getProperty("twitter.consumerKey"),
//             environment.getProperty("twitter.consumerSecret")));
            
//         return registry;
//     }

//     @Bean
//     @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
//     public ConnectionRepository connectionRepository()
//     {
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//         if (authentication == null) {
//             throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
//         }
//         return usersConnectionRepository().createConnectionRepository(authentication.getName());
//     }

//     @Bean
//     public UsersConnectionRepository usersConnectionRepository() {
//         return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator(), 
//             textEncryptor);
//     }
// }