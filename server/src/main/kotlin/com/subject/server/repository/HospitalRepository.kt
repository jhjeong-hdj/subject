package com.subject.server.repository;

import com.subject.server.domain.Hospital
import org.springframework.data.jpa.repository.JpaRepository

interface HospitalRepository : JpaRepository<Hospital, Long> {
}