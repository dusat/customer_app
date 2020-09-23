package cz.tkacikd.consumerapp.repository;

import cz.tkacikd.consumerapp.domain.CustomerCard;
import org.springframework.data.repository.CrudRepository;

public interface CustomerCardRepository extends CrudRepository<CustomerCard, Long> {
}
