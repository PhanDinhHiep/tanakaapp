package com.project.tanaka.repository;

import com.project.tanaka.domain.Orderdetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Orderdetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderdetailsRepository extends JpaRepository<Orderdetails, Long> {}
