package com.logaa.repository.rdb.crypto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.logaa.domain.rdb.crypto.CoinChangeRank;

@Repository
public interface CoinChangeRankRepository extends JpaRepository<CoinChangeRank, Long>{
	
	@Query(value = "SELECT * FROM t_coin_change_rank WHERE date = :date ORDER BY rank LIMIT :top", nativeQuery = true)
	List<CoinChangeRank> findTopByDateOrderByRankAsc(@Param(value = "date")Long date, @Param(value = "top")int top);

	@Query(value = "SELECT * FROM t_coin_change_rank WHERE date = :date ORDER BY rank DESC LIMIT :top", nativeQuery = true)
	List<CoinChangeRank> findTopByDateOrderByRankDesc(@Param(value = "date")Long date, @Param(value = "top")int top);

	@Query(value = "SELECT date FROM t_coin_change_rank ORDER BY date DESC LIMIT 1", nativeQuery = true)
	Long findTopDate();

	List<CoinChangeRank> findByDate(Long date);

}
