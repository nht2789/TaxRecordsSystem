package com.trs.repo;

import com.trs.entity.TaxPayer;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface is for interacting with database as searching and updating
 */
@Repository
public interface TaxPayerRepository extends CrudRepository<TaxPayer, Long> {

	List<TaxPayer> findByLastName(String lastName);

	TaxPayer findByTaxFileNumber(long tfn);
        
        @Query("select max(t.taxFileNumber) from TaxPayer t")
        Long findMaxTFN();
}
