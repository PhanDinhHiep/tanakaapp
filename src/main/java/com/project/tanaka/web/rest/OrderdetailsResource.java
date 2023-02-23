package com.project.tanaka.web.rest;

import com.project.tanaka.repository.OrderdetailsRepository;
import com.project.tanaka.service.OrderdetailsService;
import com.project.tanaka.service.dto.OrderdetailsDTO;
import com.project.tanaka.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.project.tanaka.domain.Orderdetails}.
 */
@RestController
@RequestMapping("/api")
public class OrderdetailsResource {

    private final Logger log = LoggerFactory.getLogger(OrderdetailsResource.class);

    private static final String ENTITY_NAME = "orderdetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderdetailsService orderdetailsService;

    private final OrderdetailsRepository orderdetailsRepository;

    public OrderdetailsResource(OrderdetailsService orderdetailsService, OrderdetailsRepository orderdetailsRepository) {
        this.orderdetailsService = orderdetailsService;
        this.orderdetailsRepository = orderdetailsRepository;
    }

    /**
     * {@code POST  /orderdetails} : Create a new orderdetails.
     *
     * @param orderdetailsDTO the orderdetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderdetailsDTO, or with status {@code 400 (Bad Request)} if the orderdetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/orderdetails")
    public ResponseEntity<OrderdetailsDTO> createOrderdetails(@RequestBody OrderdetailsDTO orderdetailsDTO) throws URISyntaxException {
        log.debug("REST request to save Orderdetails : {}", orderdetailsDTO);
        if (orderdetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderdetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderdetailsDTO result = orderdetailsService.save(orderdetailsDTO);
        return ResponseEntity
            .created(new URI("/api/orderdetails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /orderdetails/:id} : Updates an existing orderdetails.
     *
     * @param id the id of the orderdetailsDTO to save.
     * @param orderdetailsDTO the orderdetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderdetailsDTO,
     * or with status {@code 400 (Bad Request)} if the orderdetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderdetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/orderdetails/{id}")
    public ResponseEntity<OrderdetailsDTO> updateOrderdetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderdetailsDTO orderdetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Orderdetails : {}, {}", id, orderdetailsDTO);
        if (orderdetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderdetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderdetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderdetailsDTO result = orderdetailsService.update(orderdetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderdetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /orderdetails/:id} : Partial updates given fields of an existing orderdetails, field will ignore if it is null
     *
     * @param id the id of the orderdetailsDTO to save.
     * @param orderdetailsDTO the orderdetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderdetailsDTO,
     * or with status {@code 400 (Bad Request)} if the orderdetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orderdetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderdetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/orderdetails/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderdetailsDTO> partialUpdateOrderdetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderdetailsDTO orderdetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Orderdetails partially : {}, {}", id, orderdetailsDTO);
        if (orderdetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderdetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderdetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderdetailsDTO> result = orderdetailsService.partialUpdate(orderdetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderdetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /orderdetails} : get all the orderdetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderdetails in body.
     */
    @GetMapping("/orderdetails")
    public ResponseEntity<List<OrderdetailsDTO>> getAllOrderdetails(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Orderdetails");
        Page<OrderdetailsDTO> page = orderdetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /orderdetails/:id} : get the "id" orderdetails.
     *
     * @param id the id of the orderdetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderdetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/orderdetails/{id}")
    public ResponseEntity<OrderdetailsDTO> getOrderdetails(@PathVariable Long id) {
        log.debug("REST request to get Orderdetails : {}", id);
        Optional<OrderdetailsDTO> orderdetailsDTO = orderdetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderdetailsDTO);
    }

    /**
     * {@code DELETE  /orderdetails/:id} : delete the "id" orderdetails.
     *
     * @param id the id of the orderdetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/orderdetails/{id}")
    public ResponseEntity<Void> deleteOrderdetails(@PathVariable Long id) {
        log.debug("REST request to delete Orderdetails : {}", id);
        orderdetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
