package com.project.tanaka.web.rest;

import static com.project.tanaka.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.project.tanaka.IntegrationTest;
import com.project.tanaka.domain.Orders;
import com.project.tanaka.repository.OrdersRepository;
import com.project.tanaka.service.dto.OrdersDTO;
import com.project.tanaka.service.mapper.OrdersMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link OrdersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdersResourceIT {

    private static final LocalDate DEFAULT_DELIVERED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELIVERED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ORDER_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_STATUS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE = new BigDecimal(2);

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;

    private static final String ENTITY_API_URL = "/api/orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdersMockMvc;

    private Orders orders;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orders createEntity(EntityManager em) {
        Orders orders = new Orders()
            .deliveredDate(DEFAULT_DELIVERED_DATE)
            .note(DEFAULT_NOTE)
            .orderDate(DEFAULT_ORDER_DATE)
            .orderStatus(DEFAULT_ORDER_STATUS)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .customerId(DEFAULT_CUSTOMER_ID);
        return orders;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orders createUpdatedEntity(EntityManager em) {
        Orders orders = new Orders()
            .deliveredDate(UPDATED_DELIVERED_DATE)
            .note(UPDATED_NOTE)
            .orderDate(UPDATED_ORDER_DATE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .customerId(UPDATED_CUSTOMER_ID);
        return orders;
    }

    @BeforeEach
    public void initTest() {
        orders = createEntity(em);
    }

    @Test
    @Transactional
    void createOrders() throws Exception {
        int databaseSizeBeforeCreate = ordersRepository.findAll().size();
        // Create the Orders
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);
        restOrdersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isCreated());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeCreate + 1);
        Orders testOrders = ordersList.get(ordersList.size() - 1);
        assertThat(testOrders.getDeliveredDate()).isEqualTo(DEFAULT_DELIVERED_DATE);
        assertThat(testOrders.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testOrders.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testOrders.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);
        assertThat(testOrders.getTotalPrice()).isEqualByComparingTo(DEFAULT_TOTAL_PRICE);
        assertThat(testOrders.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createOrdersWithExistingId() throws Exception {
        // Create the Orders with an existing ID
        orders.setId(1L);
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        int databaseSizeBeforeCreate = ordersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList
        restOrdersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orders.getId().intValue())))
            .andExpect(jsonPath("$.[*].deliveredDate").value(hasItem(DEFAULT_DELIVERED_DATE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS)))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(sameNumber(DEFAULT_TOTAL_PRICE))))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));
    }

    @Test
    @Transactional
    void getOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get the orders
        restOrdersMockMvc
            .perform(get(ENTITY_API_URL_ID, orders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orders.getId().intValue()))
            .andExpect(jsonPath("$.deliveredDate").value(DEFAULT_DELIVERED_DATE.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS))
            .andExpect(jsonPath("$.totalPrice").value(sameNumber(DEFAULT_TOTAL_PRICE)))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOrders() throws Exception {
        // Get the orders
        restOrdersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();

        // Update the orders
        Orders updatedOrders = ordersRepository.findById(orders.getId()).get();
        // Disconnect from session so that the updates on updatedOrders are not directly saved in db
        em.detach(updatedOrders);
        updatedOrders
            .deliveredDate(UPDATED_DELIVERED_DATE)
            .note(UPDATED_NOTE)
            .orderDate(UPDATED_ORDER_DATE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .customerId(UPDATED_CUSTOMER_ID);
        OrdersDTO ordersDTO = ordersMapper.toDto(updatedOrders);

        restOrdersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordersDTO))
            )
            .andExpect(status().isOk());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);
        Orders testOrders = ordersList.get(ordersList.size() - 1);
        assertThat(testOrders.getDeliveredDate()).isEqualTo(UPDATED_DELIVERED_DATE);
        assertThat(testOrders.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testOrders.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testOrders.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testOrders.getTotalPrice()).isEqualByComparingTo(UPDATED_TOTAL_PRICE);
        assertThat(testOrders.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingOrders() throws Exception {
        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();
        orders.setId(count.incrementAndGet());

        // Create the Orders
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrders() throws Exception {
        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();
        orders.setId(count.incrementAndGet());

        // Create the Orders
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ordersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrders() throws Exception {
        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();
        orders.setId(count.incrementAndGet());

        // Create the Orders
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdersWithPatch() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();

        // Update the orders using partial update
        Orders partialUpdatedOrders = new Orders();
        partialUpdatedOrders.setId(orders.getId());

        partialUpdatedOrders.deliveredDate(UPDATED_DELIVERED_DATE).orderDate(UPDATED_ORDER_DATE).totalPrice(UPDATED_TOTAL_PRICE);

        restOrdersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrders.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrders))
            )
            .andExpect(status().isOk());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);
        Orders testOrders = ordersList.get(ordersList.size() - 1);
        assertThat(testOrders.getDeliveredDate()).isEqualTo(UPDATED_DELIVERED_DATE);
        assertThat(testOrders.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testOrders.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testOrders.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);
        assertThat(testOrders.getTotalPrice()).isEqualByComparingTo(UPDATED_TOTAL_PRICE);
        assertThat(testOrders.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateOrdersWithPatch() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();

        // Update the orders using partial update
        Orders partialUpdatedOrders = new Orders();
        partialUpdatedOrders.setId(orders.getId());

        partialUpdatedOrders
            .deliveredDate(UPDATED_DELIVERED_DATE)
            .note(UPDATED_NOTE)
            .orderDate(UPDATED_ORDER_DATE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .customerId(UPDATED_CUSTOMER_ID);

        restOrdersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrders.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrders))
            )
            .andExpect(status().isOk());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);
        Orders testOrders = ordersList.get(ordersList.size() - 1);
        assertThat(testOrders.getDeliveredDate()).isEqualTo(UPDATED_DELIVERED_DATE);
        assertThat(testOrders.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testOrders.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testOrders.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testOrders.getTotalPrice()).isEqualByComparingTo(UPDATED_TOTAL_PRICE);
        assertThat(testOrders.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingOrders() throws Exception {
        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();
        orders.setId(count.incrementAndGet());

        // Create the Orders
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrders() throws Exception {
        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();
        orders.setId(count.incrementAndGet());

        // Create the Orders
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ordersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrders() throws Exception {
        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();
        orders.setId(count.incrementAndGet());

        // Create the Orders
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ordersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        int databaseSizeBeforeDelete = ordersRepository.findAll().size();

        // Delete the orders
        restOrdersMockMvc
            .perform(delete(ENTITY_API_URL_ID, orders.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
