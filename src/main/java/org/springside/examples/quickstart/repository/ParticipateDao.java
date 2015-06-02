package org.springside.examples.quickstart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springside.examples.quickstart.entity.Participate;


@Repository
public interface ParticipateDao extends 
	PagingAndSortingRepository<Participate, Long>,
	JpaSpecificationExecutor<Participate>{
	
	@Modifying
	@Query("from Participate where uuid=?1 and actuuid=?2")
	public List<Participate> findpart(String uuid, String activityId);
}
