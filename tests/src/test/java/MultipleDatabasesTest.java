import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.test.multiple.databases.first.FirstDBEntityRepository;
import org.test.multiple.databases.second.SecondDBEntityRepository;
import org.test.multiple.databases.tests.TestConfig;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class MultipleDatabasesTest {
    @Autowired
    FirstDBEntityRepository firstDBEntityRepository;
    @Autowired
    SecondDBEntityRepository secondDBEntityRepository;


    @Test
    void test() {
        firstDBEntityRepository.findAll();
        secondDBEntityRepository.findAll();
    }
}
