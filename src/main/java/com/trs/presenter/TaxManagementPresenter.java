package com.trs.presenter;

import com.trs.entity.TaxPayer;
import com.trs.repo.TaxPayerRepository;
import com.trs.ui.TaxManagementMainView;
import com.trs.util.TaxUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaxManagementPresenter {
    private TaxPayer model;
    private TaxManagementMainView view;
    private TaxPayerRepository taxPayerRepo;

    @Autowired
    public TaxManagementPresenter(TaxPayerRepository taxPayerRepo) {
        this.taxPayerRepo = taxPayerRepo;
    }

    
    public TaxManagementPresenter(TaxPayer model, TaxManagementMainView view) {
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
//        taxPayerRepo = new TaxPayerRepository();
    }
    
    public void findButtonPressed(){
        String inputFindValue = view.txtFind.getText();
        if(inputFindValue == null || inputFindValue.isEmpty() || inputFindValue.isBlank()){
            view.showFindWarningMsg("Input TFN/Last Name/ * to search");
        }
        if(TaxUtilities.isStringOfNumber(inputFindValue)){
            TaxPayer found = taxPayerRepo.findByTaxFileNumber(Long.valueOf(inputFindValue));
            System.out.println(found);
        }
    }

    public TaxPayer getModel() {
        return model;
    }

    public void setModel(TaxPayer model) {
        this.model = model;
    }

    public TaxManagementMainView getView() {
        return view;
    }

    public void setView(TaxManagementMainView view) {
        this.view = view;
        this.view.setPresenter(this);
    }
    
    
}
