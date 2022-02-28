package com.developmentapps.saga.payment.service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developmentapps.saga.dto.OrderRequestDto;
import com.developmentapps.saga.dto.PaymentRequestDto;
import com.developmentapps.saga.event.OrderEvent;
import com.developmentapps.saga.event.PaymentEvent;
import com.developmentapps.saga.event.PaymentStatus;
import com.developmentapps.saga.payment.entity.UserBlance;
import com.developmentapps.saga.payment.entity.UserTransaction;
import com.developmentapps.saga.payment.repository.UserBlanceRepository;
import com.developmentapps.saga.payment.repository.UserTransactionRepository;

@Service
public class PaymentService {

	@Autowired
	private UserBlanceRepository userBalanceRepository;
	
	@Autowired
	private UserTransactionRepository userTransactionRepository;

	@PostConstruct
	public void initUserBalanceInDB() {
		userBalanceRepository
				.saveAll(Stream.of(new UserBlance(101, 5000), new UserBlance(102, 3000), new UserBlance(103, 4200),
						new UserBlance(104, 20000), new UserBlance(105, 999)).collect(Collectors.toList()));
	}

	@Transactional
	public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
		OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();

		PaymentRequestDto paymentRequestDto = new PaymentRequestDto(orderRequestDto.getOrderId(),
				orderRequestDto.getUserId(), orderRequestDto.getAmount());

		return userBalanceRepository.findById(orderRequestDto.getUserId())
				.filter(ub -> ub.getPrice() > orderRequestDto.getAmount()).map(ub -> {
					ub.setPrice(ub.getPrice() - orderRequestDto.getAmount());
					userTransactionRepository.save(new UserTransaction(orderRequestDto.getOrderId(),
							orderRequestDto.getUserId(), orderRequestDto.getAmount()));
					return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
				}).orElse(new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED));

	}

	@Transactional
	public void cancelOrderEvent(OrderEvent orderEvent) {

		userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId()).ifPresent(ut -> {
			userTransactionRepository.delete(ut);
			userTransactionRepository.findById(ut.getUserId())
					.ifPresent(ub -> ub.setAmount(ub.getAmount() + ut.getAmount()));
		});
	}
}