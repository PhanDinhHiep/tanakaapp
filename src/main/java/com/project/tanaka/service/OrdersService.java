package com.project.tanaka.service;

import com.project.tanaka.service.dto.OrdersDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.project.tanaka.domain.Orders}.
 */
public interface OrdersService {
    /**
     * Save a orders.
     *
     * @param ordersDTO the entity to save.
     * @return the persisted entity.
     */
    OrdersDTO save(OrdersDTO ordersDTO);

    /**
     * Updates a orders.
     *
     * @param ordersDTO the entity to update.
     * @return the persisted entity.
     */
    OrdersDTO update(OrdersDTO ordersDTO);

    /**
     * Partially updates a orders.
     *
     * @param ordersDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdersDTO> partialUpdate(OrdersDTO ordersDTO);

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrdersDTO> findAll(Pageable pageable);

    /**
     * Get the "id" orders.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdersDTO> findOne(Long id);

    /**
     * Delete the "id" orders.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
