package com.trs.repo;

import com.trs.entity.TaxPayer;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxPayerRepository extends CrudRepository<TaxPayer, Long> {

	List<TaxPayer> findByLastName(String lastName);

	TaxPayer findByTaxFileNumber(long tfn);
}
