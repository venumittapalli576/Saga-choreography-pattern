package com.developmentapps.saga.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developmentapps.saga.payment.entity.UserBlance;

@Repository
public interface UserBlanceRepository extends JpaRepository<UserBlance, Integer>{

}
