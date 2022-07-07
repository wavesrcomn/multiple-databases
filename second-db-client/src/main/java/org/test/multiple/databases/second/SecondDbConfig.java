package org.test.multiple.databases.second;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jdbc.core.convert.*;
import org.springframework.data.jdbc.core.dialect.JdbcArrayColumns;
import org.springframework.data.jdbc.core.dialect.JdbcDialect;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.DialectResolver;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJdbcRepositories(jdbcOperationsRef = "firstNamedParameterJdbcOperations",
        transactionManagerRef = "secondTransactionManager",
        basePackages = "org.test.multiple.databases.second")
public class SecondDbConfig extends AbstractJdbcConfiguration {
    @Bean
    @Second
    DataSource secondDataSource() {
        PGSimpleDataSource source = new PGSimpleDataSource();
        source.setServerNames(new String[] { "xxx.xxx.x.x" });
        source.setPortNumbers(new int[] { 5433 });
        source.setDatabaseName("second");
        source.setUser("user");
        source.setPassword("password");
        return source;
    }

    @Bean
    @Second
    NamedParameterJdbcOperations secondNamedParameterJdbcOperations(@Second DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    @Bean
    @Second
    public DataAccessStrategy dataAccessStrategyBean(@Second NamedParameterJdbcOperations operations, JdbcConverter jdbcConverter,
                                                     JdbcMappingContext context, Dialect dialect) {
        return new DefaultDataAccessStrategy(new SqlGeneratorSource(context, jdbcConverter, dialect), context,
                jdbcConverter, operations, new SqlParametersFactory(context, jdbcConverter, dialect),
                new InsertStrategyFactory(operations, new BatchJdbcOperations(operations.getJdbcOperations()), dialect));
    }

    @Override
    @Bean
    @Second
    public JdbcConverter jdbcConverter(JdbcMappingContext mappingContext, @Second NamedParameterJdbcOperations operations,
                                       @Lazy RelationResolver relationResolver, JdbcCustomConversions conversions, Dialect dialect) {

        JdbcArrayColumns arrayColumns = dialect instanceof JdbcDialect ? ((JdbcDialect) dialect).getArraySupport()
                : JdbcArrayColumns.DefaultSupport.INSTANCE;
        DefaultJdbcTypeFactory jdbcTypeFactory = new DefaultJdbcTypeFactory(operations.getJdbcOperations(), arrayColumns);

        return new BasicJdbcConverter(mappingContext, relationResolver, conversions, jdbcTypeFactory,
                dialect.getIdentifierProcessing());
    }

    @Override
    @Bean
    @Second
    public Dialect jdbcDialect(@Second NamedParameterJdbcOperations operations) {
        return DialectResolver.getDialect(operations.getJdbcOperations());
    }

    @Bean
    @Second
    TransactionManager secondTransactionManager(@Second DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
