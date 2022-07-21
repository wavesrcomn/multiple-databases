package org.test.multiple.databases.second;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Second
@Repository
public interface SecondDBEntityRepository extends PagingAndSortingRepository<SecondDbEntity, String> {
}
