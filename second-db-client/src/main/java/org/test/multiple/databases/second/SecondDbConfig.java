package org.test.multiple.databases.second;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jdbc.core.convert.*;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.dialect.PostgresDialect;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration(proxyBeanMethods = false)
@EnableJdbcRepositories(jdbcOperationsRef = "secondNamedParameterJdbcOperations",
        basePackages = "org.test.multiple.databases.second")
public class SecondDbConfig {
    @Bean
    @Second
    DataSource secondDataSource() {
        PGSimpleDataSource source = new PGSimpleDataSource();
        source.setServerNames(new String[] { "192.168.7.1" });
        source.setPortNumbers(new int[] { 5433 });
        source.setDatabaseName("refill");
        source.setUser("postgres");
        source.setPassword("postgres");
        return source;
    }

    @Bean
    @Second
    NamedParameterJdbcOperations secondNamedParameterJdbcOperations(@Second DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Second
    public PlatformTransactionManager secondTransactionManager(@Second final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions();
    }

    @Bean
    public Dialect jdbcDialect() {
        return PostgresDialect.INSTANCE;
    }

    @Bean
    public JdbcMappingContext jdbcMappingContext(Optional<NamingStrategy> namingStrategy, JdbcCustomConversions customConversions) {
        JdbcMappingContext mappingContext = new JdbcMappingContext((NamingStrategy)namingStrategy.orElse(NamingStrategy.INSTANCE));
        mappingContext.setSimpleTypeHolder(customConversions.getSimpleTypeHolder());
        return mappingContext;
    }

    //
    @Bean
    @Second
    public JdbcConverter jdbcConverter(JdbcMappingContext mappingContext,
                                       @Second NamedParameterJdbcOperations operations,
                                       @Lazy RelationResolver relationResolver,
                                       JdbcCustomConversions conversions,
                                       Dialect dialect) {

        DefaultJdbcTypeFactory jdbcTypeFactory = new DefaultJdbcTypeFactory(operations.getJdbcOperations());
        return new BasicJdbcConverter(mappingContext, relationResolver, conversions, jdbcTypeFactory,
                dialect.getIdentifierProcessing());
    }

    @Bean
    @Second
    public PlatformTransactionManager transactionManager(@Second final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
