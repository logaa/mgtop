package com.logaa.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.logaa.domain.es.RequestLog;
import com.logaa.domain.po.RequestLogView;
import com.logaa.repository.es.RequestLogRepository;
import com.logaa.view.BaseResponse;


@RestController
public class IndexRest {

	
}
