package com.common.jmark.domain.repository;

import com.common.jmark.domain.entity.JobOpening;
import com.common.jmark.dto.JobOpeningSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobOpeningRepository extends JpaRepository<JobOpening, Long>{
//        Page<JobOpening> pageJobOpeningList(JobOpeningSearchDto jobOpeningSearchDto, Pageable pageable);
}
