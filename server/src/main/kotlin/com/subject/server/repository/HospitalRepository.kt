package com.subject.server.repository;

import com.subject.server.domain.Hospital
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface HospitalRepository : JpaRepository<Hospital, Long>{
}