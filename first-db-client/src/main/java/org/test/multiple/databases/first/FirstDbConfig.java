package org.test.multiple.databases.first;

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
        transactionManagerRef = "firstTransactionManager",
        basePackages = "org.test.multiple.databases.first")
public class FirstDbConfig extends AbstractJdbcConfiguration {
    @Bean
    @First
    DataSource firstDataSource() {
        PGSimpleDataSource source = new PGSimpleDataSource();
        source.setServerNames(new String[] { "xxx.xxx.x.x" });
        source.setPortNumbers(new int[] { 5434 });
        source.setDatabaseName("fist");
        source.setUser("user");
        source.setPassword("password");
        return source;
    }

    @Bean
    @First
    NamedParameterJdbcOperations firstNamedParameterJdbcOperations(@First DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    @Bean
    @First
    public DataAccessStrategy dataAccessStrategyBean(@First NamedParameterJdbcOperations operations, JdbcConverter jdbcConverter,
                                                     JdbcMappingContext context, Dialect dialect) {
        return new DefaultDataAccessStrategy(new SqlGeneratorSource(context, jdbcConverter, dialect), context,
                jdbcConverter, operations, new SqlParametersFactory(context, jdbcConverter, dialect),
                new InsertStrategyFactory(operations, new BatchJdbcOperations(operations.getJdbcOperations()), dialect));
    }

    @Override
    @Bean
    @First
    public JdbcConverter jdbcConverter(JdbcMappingContext mappingContext, @First NamedParameterJdbcOperations operations,
                                       @Lazy RelationResolver relationResolver, JdbcCustomConversions conversions, Dialect dialect) {

        JdbcArrayColumns arrayColumns = dialect instanceof JdbcDialect ? ((JdbcDialect) dialect).getArraySupport()
                : JdbcArrayColumns.DefaultSupport.INSTANCE;
        DefaultJdbcTypeFactory jdbcTypeFactory = new DefaultJdbcTypeFactory(operations.getJdbcOperations(), arrayColumns);

        return new BasicJdbcConverter(mappingContext, relationResolver, conversions, jdbcTypeFactory,
                dialect.getIdentifierProcessing());
    }

    @Override
    @Bean
    @First
    public Dialect jdbcDialect(@First NamedParameterJdbcOperations operations) {
        return DialectResolver.getDialect(operations.getJdbcOperations());
    }

    @Bean
    @First
    TransactionManager firstTransactionManager(@First DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
