package com.pony.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pony.models.ChargeRequest;

public interface ChargeRequestRepository extends JpaRepository<ChargeRequest, Long> {

	List<ChargeRequest> findAllByOrderByAmountAsc();

}
