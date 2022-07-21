package org.test.multiple.databases.first;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jdbc.core.convert.*;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@EnableJdbcRepositories(jdbcOperationsRef = "firstNamedParameterJdbcOperations",
        transactionManagerRef = "firstTransactionManager",
        basePackages = "org.test.multiple.databases.first")
public class FirstDbConfig {
    @Bean
    @First
    DataSource firstDataSource() {
        PGSimpleDataSource source = new PGSimpleDataSource();
        source.setServerNames(new String[] { "192.168.7.1" });
        source.setPortNumbers(new int[] { 5434 });
        source.setDatabaseName("accounting");
        source.setUser("postgres");
        source.setPassword("postgres");
        return source;
    }

    @Bean
    @First
    NamedParameterJdbcOperations firstNamedParameterJdbcOperations(@First DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @First
    TransactionManager firstTransactionManager(@First DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
//    @Bean
//    public JdbcCustomConversions jdbcCustomConversions() {
//        return new JdbcCustomConversions();
//    }
//
//    @Bean
//    public Dialect jdbcDialect() {
//        return PostgresDialect.INSTANCE;
//    }
//
//    @Bean
//    public JdbcMappingContext jdbcMappingContext(Optional<NamingStrategy> namingStrategy, JdbcCustomConversions customConversions) {
//        JdbcMappingContext mappingContext = new JdbcMappingContext((NamingStrategy)namingStrategy.orElse(NamingStrategy.INSTANCE));
//        mappingContext.setSimpleTypeHolder(customConversions.getSimpleTypeHolder());
//        return mappingContext;
//    }

    //
    @Bean
    @First
    public JdbcConverter jdbcConverter(JdbcMappingContext mappingContext,
                                       @First NamedParameterJdbcOperations operations,
                                       @Lazy RelationResolver relationResolver,
                                       JdbcCustomConversions conversions,
                                       Dialect dialect) {

        DefaultJdbcTypeFactory jdbcTypeFactory = new DefaultJdbcTypeFactory(operations.getJdbcOperations());
        return new BasicJdbcConverter(mappingContext, relationResolver, conversions, jdbcTypeFactory,
                dialect.getIdentifierProcessing());
    }

    @Bean
    @First
    public PlatformTransactionManager transactionManager(@First final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
