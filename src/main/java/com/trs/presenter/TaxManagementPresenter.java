package com.trs.presenter;

import com.trs.entity.TaxPayer;
import com.trs.repo.TaxPayerRepository;
import com.trs.ui.TaxManagementMainView;
import com.trs.ui.TaxPayerListView;
import com.trs.util.TaxUtilities;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
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
    }
    
    public void find(){
        String inputFindValue = view.txtFind.getText();
        if(inputFindValue == null || inputFindValue.isEmpty() || inputFindValue.isBlank()){
            view.showFindWarningMsg("Input TFN/Last Name/ * to search");
        } 
        //searching by TFN
        else if (TaxUtilities.isStringOfNumber(inputFindValue)){
            System.out.println("Searching by TFN");
            model = taxPayerRepo.findByTaxFileNumber(Long.valueOf(inputFindValue));
            System.out.println(model);
            if(null == model){
                showInfoDialog("No tax payer(s) found");
            } else {
                loadTaxRecord(model);
            }
        }
        //searching by Last name
        else if (!inputFindValue.equals("*")){
            System.out.println("Searching by last name");
            List<TaxPayer> taxPayers = taxPayerRepo.findByLastName(inputFindValue);
            if (taxPayers == null || taxPayers.isEmpty()){
                showInfoDialog("No tax payer(s) found");
            } else if(taxPayers.size() == 1){
                model = taxPayers.get(0);
                loadTaxRecord(model);
            } else if ( taxPayers.size() > 1) {
                showTableOfResults(taxPayers);
            }
        } else {
            //browse all Tax payers
            System.out.println("Browsing all records");
            Iterable<TaxPayer> results = taxPayerRepo.findAll();
            List<TaxPayer> taxPayers = new ArrayList<>();
            results.forEach(taxPayers::add);
            showTableOfResults(taxPayers);
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
    
    public void loadTaxRecord(TaxPayer model){
        if(null != model){
            view.loadTaxRecord(
                    //personal info
                    model.getTaxFileNumber(), model.getFirstName(), model.getLastName(), 
                    model.getAddress(), model.getPhone(),
                    //tax info
                    model.getIncome(), model.getDeductibleAmount(),
                    model.getTaxHeld(), model.getTaxToBeReturned());
        }
    }

    private void showTableOfResults(List<TaxPayer> taxPayers) {
        TaxPayerListView taxPayerListView = new TaxPayerListView();
        TaxPayerListPresenter taxPayerListPresenter = new TaxPayerListPresenter(taxPayers, taxPayerListView, this);
        taxPayerListPresenter.showView();
    }

    public void resetTaxRecord() {
        view.resetTaxRecord();
    }

    public void saveOrUpdateTaxRecord() {
        if(view.areRequiredFieldsFilled()){
            model = updateModelFromView();
            taxPayerRepo.save(model);
            showInfoDialog("Saved successfully");
        } else {
            showInfoDialog("Please fill all required fields");
        }    
    }
    
    private TaxPayer updateModelFromView(){
        Long tfn = Long.valueOf(view.getTxtTFN().getText());
        String firstName = view.getTxtFirstName().getText();
        String lastName = view.getTxtLastName().getText();
        String address = view.getTxtAddress().getText();
        String phone = view.getTxtPhone().getText();
        Long income = Long.valueOf(view.getTxtIncome().getText());
        Long deductAmt = Long.valueOf(view.getTxtDeduct().getText());
        Long taxHels = Long.valueOf(view.getTxtTaxHeld().getText());
        Long taxToBeReturned = Long.valueOf(view.getTxtTaxToBeReturn().getText());
        model = new TaxPayer(tfn, firstName, lastName, address, phone, 
                income, deductAmt, taxHels, taxToBeReturned);
        
        return model;
    }
    
    public void generateTaxFileNumber(){
        Long maxTFN = taxPayerRepo.findMaxTFN();
        System.out.println(maxTFN);
        view.setGeneratedTFN(String.valueOf(maxTFN+1));
    }

    private void showInfoDialog(String message) {
        JOptionPane.showMessageDialog(this.view, message);
    }

    public void calculateTaxs() {
        //validate empty income and deductible amount
        if(view.getTxtIncome().getText().trim().isEmpty()){
            showInfoDialog("Please input income");
            return;
        }
        if(view.getTxtDeduct().getText().trim().isEmpty()){
            showInfoDialog("Please input the deductiable amount, input 0 if not having");
            return;
        }
        //caculate
        model.setIncome(Long.valueOf(view.getTxtIncome().getText()));
        model.setDeductibleAmount(Long.valueOf(view.getTxtDeduct().getText()));
        Long taxHeld = model.getTaxHeld();
        Long taxToBeReturned = model.getTaxToBeReturned();
        //set values
        view.getTxtTaxHeld().setText(String.valueOf(taxHeld));
        view.getTxtTaxToBeReturn().setText(String.valueOf(taxToBeReturned));
    }
}
