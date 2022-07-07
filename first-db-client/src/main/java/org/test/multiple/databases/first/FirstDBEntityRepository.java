package org.test.multiple.databases.first;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@First
@Transactional(value = "firstTransactionManager")
public interface FirstDBEntityRepository extends PagingAndSortingRepository<FirstDBEntity, String> {
}
