package com.trs;

import com.trs.entity.TaxPayer;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import com.trs.repo.TaxPayerRepository;

@DataJpaTest
public class TaxPayerRepositoryTests {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private TaxPayerRepository taxPayers;

	@Test
	public void testFindByLastName() {
		TaxPayer taxPayer = new TaxPayer(123456L, "Tom", "Hawnk", "15 Airport road Brisbane", "0455111999", 80000L, 3000L, 17547L, 975L);
		entityManager.persist(taxPayer);

		List<TaxPayer> findByLastName = taxPayers.findByLastName(taxPayer.getLastName());

		assertThat(findByLastName).extracting(TaxPayer::getLastName).containsOnly(taxPayer.getLastName());
	}
}
