package com.logaa.repository.rdb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.logaa.domain.rdb.crypto.CoinList;

@Repository
public interface CoinListRepository extends JpaRepository<CoinList, Long>{

	CoinList findByName(String name);

}
