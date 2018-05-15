package com.logaa.repository.rdb.love2io;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logaa.domain.rdb.love2io.Posts;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long>{

}
