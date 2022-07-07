package org.test.multiple.databases.tests;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.test.multiple.databases.first.FirstDbConfig;
import org.test.multiple.databases.second.SecondDbConfig;

@Configuration
@Import({
        FirstDbConfig.class,
        SecondDbConfig.class
})
public class TestConfig {
}
