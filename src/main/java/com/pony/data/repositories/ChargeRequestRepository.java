package com.pony.data.repositories;

import com.pony.entities.models.ChargeRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChargeRequestRepository extends JpaRepository<ChargeRequest, Long> {

	List<ChargeRequest> findAllByOrderByAmountAsc();

}
