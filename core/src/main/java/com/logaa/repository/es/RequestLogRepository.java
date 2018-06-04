package com.logaa.repository.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import com.logaa.domain.es.RequestLog;

@Component
public interface RequestLogRepository extends ElasticsearchRepository<RequestLog, Long>{

}
