package com.logaa.service.live;

import java.util.List;

import com.logaa.domain.mongo.Lives;

public interface LivesService {

	List<Lives> find(int page, int size);

}
