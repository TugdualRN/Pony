package com.pony.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pony.models.ChargeRequest;

public interface ChargeRequestRepository extends JpaRepository<ChargeRequest, Long> {

}
