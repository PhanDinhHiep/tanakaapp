package com.project.tanaka.web.rest;

import com.project.tanaka.repository.CustomersRepository;
import com.project.tanaka.service.CustomersService;
import com.project.tanaka.service.dto.CustomersDTO;
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
 * REST controller for managing {@link com.project.tanaka.domain.Customers}.
 */
@RestController
@RequestMapping("/api")
public class CustomersResource {

    private final Logger log = LoggerFactory.getLogger(CustomersResource.class);

    private static final String ENTITY_NAME = "customers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomersService customersService;

    private final CustomersRepository customersRepository;

    public CustomersResource(CustomersService customersService, CustomersRepository customersRepository) {
        this.customersService = customersService;
        this.customersRepository = customersRepository;
    }

    /**
     * {@code POST  /customers} : Create a new customers.
     *
     * @param customersDTO the customersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customersDTO, or with status {@code 400 (Bad Request)} if the customers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customers")
    public ResponseEntity<CustomersDTO> createCustomers(@RequestBody CustomersDTO customersDTO) throws URISyntaxException {
        log.debug("REST request to save Customers : {}", customersDTO);
        if (customersDTO.getId() != null) {
            throw new BadRequestAlertException("A new customers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomersDTO result = customersService.save(customersDTO);
        return ResponseEntity
            .created(new URI("/api/customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customers/:id} : Updates an existing customers.
     *
     * @param id the id of the customersDTO to save.
     * @param customersDTO the customersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customersDTO,
     * or with status {@code 400 (Bad Request)} if the customersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomersDTO> updateCustomers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustomersDTO customersDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Customers : {}, {}", id, customersDTO);
        if (customersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomersDTO result = customersService.update(customersDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /customers/:id} : Partial updates given fields of an existing customers, field will ignore if it is null
     *
     * @param id the id of the customersDTO to save.
     * @param customersDTO the customersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customersDTO,
     * or with status {@code 400 (Bad Request)} if the customersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the customersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the customersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CustomersDTO> partialUpdateCustomers(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CustomersDTO customersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Customers partially : {}, {}", id, customersDTO);
        if (customersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomersDTO> result = customersService.partialUpdate(customersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customersDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /customers} : get all the customers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customers in body.
     */
    @GetMapping("/customers")
    public ResponseEntity<List<CustomersDTO>> getAllCustomers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Customers");
        Page<CustomersDTO> page = customersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /customers/:id} : get the "id" customers.
     *
     * @param id the id of the customersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomersDTO> getCustomers(@PathVariable Long id) {
        log.debug("REST request to get Customers : {}", id);
        Optional<CustomersDTO> customersDTO = customersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customersDTO);
    }

    /**
     * {@code DELETE  /customers/:id} : delete the "id" customers.
     *
     * @param id the id of the customersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomers(@PathVariable Long id) {
        log.debug("REST request to delete Customers : {}", id);
        customersService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
