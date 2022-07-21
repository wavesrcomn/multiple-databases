package org.test.multiple.databases.first;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@First
@Repository
public interface FirstDBEntityRepository extends PagingAndSortingRepository<FirstDBEntity, String> {
}
