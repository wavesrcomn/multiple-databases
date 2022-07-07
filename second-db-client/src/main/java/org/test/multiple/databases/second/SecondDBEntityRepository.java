package org.test.multiple.databases.second;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@Second
@Transactional(value = "secondTransactionManager")
public interface SecondDBEntityRepository extends PagingAndSortingRepository<SecondDbEntity, String> {
}
