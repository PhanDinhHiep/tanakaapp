package com.project.tanaka.service;

import com.project.tanaka.service.dto.OrderdetailsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.project.tanaka.domain.Orderdetails}.
 */
public interface OrderdetailsService {
    /**
     * Save a orderdetails.
     *
     * @param orderdetailsDTO the entity to save.
     * @return the persisted entity.
     */
    OrderdetailsDTO save(OrderdetailsDTO orderdetailsDTO);

    /**
     * Updates a orderdetails.
     *
     * @param orderdetailsDTO the entity to update.
     * @return the persisted entity.
     */
    OrderdetailsDTO update(OrderdetailsDTO orderdetailsDTO);

    /**
     * Partially updates a orderdetails.
     *
     * @param orderdetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrderdetailsDTO> partialUpdate(OrderdetailsDTO orderdetailsDTO);

    /**
     * Get all the orderdetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrderdetailsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" orderdetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderdetailsDTO> findOne(Long id);

    /**
     * Delete the "id" orderdetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
