package com.searchmetrics.n3jobservice.dao;

import com.searchmetrics.n3jobservice.dto.Job;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

/**
 * Created by arobinson on 3/25/17.
 */
@Repository
public interface JobsRepository extends CassandraRepository<Job> {
    Stream<Job> findById(Long crawlId);
}
