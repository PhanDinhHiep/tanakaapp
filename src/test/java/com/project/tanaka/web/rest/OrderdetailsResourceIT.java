package com.project.tanaka.web.rest;

import static com.project.tanaka.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.project.tanaka.IntegrationTest;
import com.project.tanaka.domain.Orderdetails;
import com.project.tanaka.repository.OrderdetailsRepository;
import com.project.tanaka.service.dto.OrderdetailsDTO;
import com.project.tanaka.service.mapper.OrderdetailsMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrderdetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderdetailsResourceIT {

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String DEFAULT_SIZES = "AAAAAAAAAA";
    private static final String UPDATED_SIZES = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(2);

    private static final Long DEFAULT_ORDER_ID = 1L;
    private static final Long UPDATED_ORDER_ID = 2L;

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final String ENTITY_API_URL = "/api/orderdetails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrderdetailsRepository orderdetailsRepository;

    @Autowired
    private OrderdetailsMapper orderdetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderdetailsMockMvc;

    private Orderdetails orderdetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orderdetails createEntity(EntityManager em) {
        Orderdetails orderdetails = new Orderdetails()
            .color(DEFAULT_COLOR)
            .price(DEFAULT_PRICE)
            .quantity(DEFAULT_QUANTITY)
            .sizes(DEFAULT_SIZES)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .orderId(DEFAULT_ORDER_ID)
            .productId(DEFAULT_PRODUCT_ID);
        return orderdetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orderdetails createUpdatedEntity(EntityManager em) {
        Orderdetails orderdetails = new Orderdetails()
            .color(UPDATED_COLOR)
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY)
            .sizes(UPDATED_SIZES)
            .unitPrice(UPDATED_UNIT_PRICE)
            .orderId(UPDATED_ORDER_ID)
            .productId(UPDATED_PRODUCT_ID);
        return orderdetails;
    }

    @BeforeEach
    public void initTest() {
        orderdetails = createEntity(em);
    }

    @Test
    @Transactional
    void createOrderdetails() throws Exception {
        int databaseSizeBeforeCreate = orderdetailsRepository.findAll().size();
        // Create the Orderdetails
        OrderdetailsDTO orderdetailsDTO = orderdetailsMapper.toDto(orderdetails);
        restOrderdetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderdetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Orderdetails in the database
        List<Orderdetails> orderdetailsList = orderdetailsRepository.findAll();
        assertThat(orderdetailsList).hasSize(databaseSizeBeforeCreate + 1);
        Orderdetails testOrderdetails = orderdetailsList.get(orderdetailsList.size() - 1);
        assertThat(testOrderdetails.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testOrderdetails.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testOrderdetails.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrderdetails.getSizes()).isEqualTo(DEFAULT_SIZES);
        assertThat(testOrderdetails.getUnitPrice()).isEqualByComparingTo(DEFAULT_UNIT_PRICE);
        assertThat(testOrderdetails.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testOrderdetails.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
    }

    @Test
    @Transactional
    void createOrderdetailsWithExistingId() throws Exception {
        // Create the Orderdetails with an existing ID
        orderdetails.setId(1L);
        OrderdetailsDTO orderdetailsDTO = orderdetailsMapper.toDto(orderdetails);

        int databaseSizeBeforeCreate = orderdetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderdetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderdetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orderdetails in the database
        List<Orderdetails> orderdetailsList = orderdetailsRepository.findAll();
        assertThat(orderdetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrderdetails() throws Exception {
        // Initialize the database
        orderdetailsRepository.saveAndFlush(orderdetails);

        // Get all the orderdetailsList
        restOrderdetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderdetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].sizes").value(hasItem(DEFAULT_SIZES)))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(sameNumber(DEFAULT_UNIT_PRICE))))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())));
    }

    @Test
    @Transactional
    void getOrderdetails() throws Exception {
        // Initialize the database
        orderdetailsRepository.saveAndFlush(orderdetails);

        // Get the orderdetails
        restOrderdetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, orderdetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderdetails.getId().intValue()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.sizes").value(DEFAULT_SIZES))
            .andExpect(jsonPath("$.unitPrice").value(sameNumber(DEFAULT_UNIT_PRICE)))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID.intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOrderdetails() throws Exception {
        // Get the orderdetails
        restOrderdetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrderdetails() throws Exception {
        // Initialize the database
        orderdetailsRepository.saveAndFlush(orderdetails);

        int databaseSizeBeforeUpdate = orderdetailsRepository.findAll().size();

        // Update the orderdetails
        Orderdetails updatedOrderdetails = orderdetailsRepository.findById(orderdetails.getId()).get();
        // Disconnect from session so that the updates on updatedOrderdetails are not directly saved in db
        em.detach(updatedOrderdetails);
        updatedOrderdetails
            .color(UPDATED_COLOR)
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY)
            .sizes(UPDATED_SIZES)
            .unitPrice(UPDATED_UNIT_PRICE)
            .orderId(UPDATED_ORDER_ID)
            .productId(UPDATED_PRODUCT_ID);
        OrderdetailsDTO orderdetailsDTO = orderdetailsMapper.toDto(updatedOrderdetails);

        restOrderdetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderdetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderdetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Orderdetails in the database
        List<Orderdetails> orderdetailsList = orderdetailsRepository.findAll();
        assertThat(orderdetailsList).hasSize(databaseSizeBeforeUpdate);
        Orderdetails testOrderdetails = orderdetailsList.get(orderdetailsList.size() - 1);
        assertThat(testOrderdetails.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testOrderdetails.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testOrderdetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderdetails.getSizes()).isEqualTo(UPDATED_SIZES);
        assertThat(testOrderdetails.getUnitPrice()).isEqualByComparingTo(UPDATED_UNIT_PRICE);
        assertThat(testOrderdetails.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testOrderdetails.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void putNonExistingOrderdetails() throws Exception {
        int databaseSizeBeforeUpdate = orderdetailsRepository.findAll().size();
        orderdetails.setId(count.incrementAndGet());

        // Create the Orderdetails
        OrderdetailsDTO orderdetailsDTO = orderdetailsMapper.toDto(orderdetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderdetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderdetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderdetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orderdetails in the database
        List<Orderdetails> orderdetailsList = orderdetailsRepository.findAll();
        assertThat(orderdetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderdetails() throws Exception {
        int databaseSizeBeforeUpdate = orderdetailsRepository.findAll().size();
        orderdetails.setId(count.incrementAndGet());

        // Create the Orderdetails
        OrderdetailsDTO orderdetailsDTO = orderdetailsMapper.toDto(orderdetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderdetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderdetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orderdetails in the database
        List<Orderdetails> orderdetailsList = orderdetailsRepository.findAll();
        assertThat(orderdetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderdetails() throws Exception {
        int databaseSizeBeforeUpdate = orderdetailsRepository.findAll().size();
        orderdetails.setId(count.incrementAndGet());

        // Create the Orderdetails
        OrderdetailsDTO orderdetailsDTO = orderdetailsMapper.toDto(orderdetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderdetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderdetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Orderdetails in the database
        List<Orderdetails> orderdetailsList = orderdetailsRepository.findAll();
        assertThat(orderdetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderdetailsWithPatch() throws Exception {
        // Initialize the database
        orderdetailsRepository.saveAndFlush(orderdetails);

        int databaseSizeBeforeUpdate = orderdetailsRepository.findAll().size();

        // Update the orderdetails using partial update
        Orderdetails partialUpdatedOrderdetails = new Orderdetails();
        partialUpdatedOrderdetails.setId(orderdetails.getId());

        partialUpdatedOrderdetails.price(UPDATED_PRICE).sizes(UPDATED_SIZES).unitPrice(UPDATED_UNIT_PRICE).productId(UPDATED_PRODUCT_ID);

        restOrderdetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderdetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderdetails))
            )
            .andExpect(status().isOk());

        // Validate the Orderdetails in the database
        List<Orderdetails> orderdetailsList = orderdetailsRepository.findAll();
        assertThat(orderdetailsList).hasSize(databaseSizeBeforeUpdate);
        Orderdetails testOrderdetails = orderdetailsList.get(orderdetailsList.size() - 1);
        assertThat(testOrderdetails.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testOrderdetails.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testOrderdetails.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrderdetails.getSizes()).isEqualTo(UPDATED_SIZES);
        assertThat(testOrderdetails.getUnitPrice()).isEqualByComparingTo(UPDATED_UNIT_PRICE);
        assertThat(testOrderdetails.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testOrderdetails.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void fullUpdateOrderdetailsWithPatch() throws Exception {
        // Initialize the database
        orderdetailsRepository.saveAndFlush(orderdetails);

        int databaseSizeBeforeUpdate = orderdetailsRepository.findAll().size();

        // Update the orderdetails using partial update
        Orderdetails partialUpdatedOrderdetails = new Orderdetails();
        partialUpdatedOrderdetails.setId(orderdetails.getId());

        partialUpdatedOrderdetails
            .color(UPDATED_COLOR)
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY)
            .sizes(UPDATED_SIZES)
            .unitPrice(UPDATED_UNIT_PRICE)
            .orderId(UPDATED_ORDER_ID)
            .productId(UPDATED_PRODUCT_ID);

        restOrderdetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderdetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderdetails))
            )
            .andExpect(status().isOk());

        // Validate the Orderdetails in the database
        List<Orderdetails> orderdetailsList = orderdetailsRepository.findAll();
        assertThat(orderdetailsList).hasSize(databaseSizeBeforeUpdate);
        Orderdetails testOrderdetails = orderdetailsList.get(orderdetailsList.size() - 1);
        assertThat(testOrderdetails.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testOrderdetails.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testOrderdetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderdetails.getSizes()).isEqualTo(UPDATED_SIZES);
        assertThat(testOrderdetails.getUnitPrice()).isEqualByComparingTo(UPDATED_UNIT_PRICE);
        assertThat(testOrderdetails.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testOrderdetails.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingOrderdetails() throws Exception {
        int databaseSizeBeforeUpdate = orderdetailsRepository.findAll().size();
        orderdetails.setId(count.incrementAndGet());

        // Create the Orderdetails
        OrderdetailsDTO orderdetailsDTO = orderdetailsMapper.toDto(orderdetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderdetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderdetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderdetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orderdetails in the database
        List<Orderdetails> orderdetailsList = orderdetailsRepository.findAll();
        assertThat(orderdetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderdetails() throws Exception {
        int databaseSizeBeforeUpdate = orderdetailsRepository.findAll().size();
        orderdetails.setId(count.incrementAndGet());

        // Create the Orderdetails
        OrderdetailsDTO orderdetailsDTO = orderdetailsMapper.toDto(orderdetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderdetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderdetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orderdetails in the database
        List<Orderdetails> orderdetailsList = orderdetailsRepository.findAll();
        assertThat(orderdetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderdetails() throws Exception {
        int databaseSizeBeforeUpdate = orderdetailsRepository.findAll().size();
        orderdetails.setId(count.incrementAndGet());

        // Create the Orderdetails
        OrderdetailsDTO orderdetailsDTO = orderdetailsMapper.toDto(orderdetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderdetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderdetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Orderdetails in the database
        List<Orderdetails> orderdetailsList = orderdetailsRepository.findAll();
        assertThat(orderdetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderdetails() throws Exception {
        // Initialize the database
        orderdetailsRepository.saveAndFlush(orderdetails);

        int databaseSizeBeforeDelete = orderdetailsRepository.findAll().size();

        // Delete the orderdetails
        restOrderdetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderdetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Orderdetails> orderdetailsList = orderdetailsRepository.findAll();
        assertThat(orderdetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
