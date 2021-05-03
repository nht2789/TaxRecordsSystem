package com.trs;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.trs.entity.TaxPayer;
import com.trs.presenter.TaxManagementPresenter;
import com.trs.repo.TaxPayerRepository;
import com.trs.ui.TaxManagementMainView;
import java.sql.Connection;
import javax.sql.DataSource;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import sun.awt.AppContext;

@SpringBootApplication
public class TaxRecordSystemApplication implements CommandLineRunner {
    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private DataSource datasource;

	private static final Logger log = LoggerFactory.getLogger(TaxRecordSystemApplication.class);

	public static void main(String[] args) {
            try{
                new SpringApplicationBuilder(TaxRecordSystemApplication.class).headless(false).run(args);
            } catch (Exception e){
                if(e instanceof BeanCreationException){
                    if(((BeanCreationException) e).getBeanName().equals("entityManagerFactory")){
                        System.out.println("Could not connect MySQL database");
                        
                    }
                }
            }
                
	}

        
        
	@Bean
	public CommandLineRunner demo(TaxPayerRepository repository) {
		return (args) -> {
			// save a few customers
			repository.save(new TaxPayer(577123L, "john", "smith", "15 Airport road Brisbane", "0455111999", 80000L, 3000L, 17547L, 975L));

			// fetch all customers
			log.info("TaxPayers found with findAll():");
			log.info("-------------------------------");
			for (TaxPayer taxPayer : repository.findAll()) {
				log.info(taxPayer.toString());
			}
			log.info("");

			// fetch an individual customer by TFN
			TaxPayer taxPayer = repository.findByTaxFileNumber(577123L);
			log.info("TaxPayer found with findById(577123L):");
			log.info("--------------------------------");
			log.info(taxPayer.toString());
			log.info("");

			// fetch customers by last name
			log.info("TaxPayer found with findByLastName('smith'):");
			log.info("--------------------------------------------");
			repository.findByLastName("smith").forEach(smith -> {
				log.info(smith.toString());
			});
			// for (TaxPayer bauer : repository.findByLastName("Bauer")) {
			// 	log.info(bauer.toString());
			// }
			log.info("");
		};
	}

    @Override
    public void run(String... args) throws Exception {
        
        TaxManagementMainView mainView = new TaxManagementMainView();
        TaxPayer taxPayer = new TaxPayer();
        TaxManagementPresenter presenter = appContext.getBean(TaxManagementPresenter.class);
        presenter.setModel(taxPayer);
        presenter.setView(mainView);
        mainView.setVisible(true);
               
    }

}
