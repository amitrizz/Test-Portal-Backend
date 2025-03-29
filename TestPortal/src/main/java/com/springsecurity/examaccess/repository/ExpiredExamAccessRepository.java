package com.springsecurity.examaccess.repository;

import com.springsecurity.examaccess.model.ExpiredExamAccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpiredExamAccessRepository extends JpaRepository<ExpiredExamAccess,Long> {
}
