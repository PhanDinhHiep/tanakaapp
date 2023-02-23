package com.project.tanaka.service.impl;

import com.project.tanaka.domain.Orderdetails;
import com.project.tanaka.repository.OrderdetailsRepository;
import com.project.tanaka.service.OrderdetailsService;
import com.project.tanaka.service.dto.OrderdetailsDTO;
import com.project.tanaka.service.mapper.OrderdetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Orderdetails}.
 */
@Service
@Transactional
public class OrderdetailsServiceImpl implements OrderdetailsService {

    private final Logger log = LoggerFactory.getLogger(OrderdetailsServiceImpl.class);

    private final OrderdetailsRepository orderdetailsRepository;

    private final OrderdetailsMapper orderdetailsMapper;

    public OrderdetailsServiceImpl(OrderdetailsRepository orderdetailsRepository, OrderdetailsMapper orderdetailsMapper) {
        this.orderdetailsRepository = orderdetailsRepository;
        this.orderdetailsMapper = orderdetailsMapper;
    }

    @Override
    public OrderdetailsDTO save(OrderdetailsDTO orderdetailsDTO) {
        log.debug("Request to save Orderdetails : {}", orderdetailsDTO);
        Orderdetails orderdetails = orderdetailsMapper.toEntity(orderdetailsDTO);
        orderdetails = orderdetailsRepository.save(orderdetails);
        return orderdetailsMapper.toDto(orderdetails);
    }

    @Override
    public OrderdetailsDTO update(OrderdetailsDTO orderdetailsDTO) {
        log.debug("Request to update Orderdetails : {}", orderdetailsDTO);
        Orderdetails orderdetails = orderdetailsMapper.toEntity(orderdetailsDTO);
        orderdetails = orderdetailsRepository.save(orderdetails);
        return orderdetailsMapper.toDto(orderdetails);
    }

    @Override
    public Optional<OrderdetailsDTO> partialUpdate(OrderdetailsDTO orderdetailsDTO) {
        log.debug("Request to partially update Orderdetails : {}", orderdetailsDTO);

        return orderdetailsRepository
            .findById(orderdetailsDTO.getId())
            .map(existingOrderdetails -> {
                orderdetailsMapper.partialUpdate(existingOrderdetails, orderdetailsDTO);

                return existingOrderdetails;
            })
            .map(orderdetailsRepository::save)
            .map(orderdetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderdetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Orderdetails");
        return orderdetailsRepository.findAll(pageable).map(orderdetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderdetailsDTO> findOne(Long id) {
        log.debug("Request to get Orderdetails : {}", id);
        return orderdetailsRepository.findById(id).map(orderdetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Orderdetails : {}", id);
        orderdetailsRepository.deleteById(id);
    }
}
