package com.logaa.repository.rdb.love2io;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logaa.domain.rdb.love2io.Summary;

@Repository
public interface SummaryRepository extends JpaRepository<Summary, Long>{

	List<Summary> findByTitle(String title);

	List<Summary> findByPostsId(Long postsId);

	List<Summary> findByPostsIdAndName(long postsId, String name);

}
