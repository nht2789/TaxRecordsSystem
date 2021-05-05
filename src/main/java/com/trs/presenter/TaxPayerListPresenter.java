package com.trs.presenter;

import com.trs.entity.TaxPayer;
import com.trs.repo.TaxPayerRepository;
import com.trs.ui.TaxPayerListView;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaxPayerListPresenter {

    private List<TaxPayer> taxPayers = new ArrayList<>();
    private TaxPayerListView view;
    private TaxManagementPresenter taxManagementPresenter;

    TaxPayerListPresenter(List<TaxPayer> taxPayers, TaxPayerListView taxPayerListView, TaxManagementPresenter taxManagementPresenter) {
        this.taxPayers = taxPayers;
        this.view = taxPayerListView;
        this.view.setPresenter(this);
        this.taxManagementPresenter = taxManagementPresenter;
    }

    void showView() {
        Object[][] taxPayerTableData = new Object[taxPayers.size()][8];
        for (int i = 0; i < taxPayers.size(); i++) {
            taxPayerTableData[i][0] = taxPayers.get(i).getTaxFileNumber();
            taxPayerTableData[i][1] = taxPayers.get(i).getFullName();
            taxPayerTableData[i][2] = taxPayers.get(i).getAddress();
            taxPayerTableData[i][3] = taxPayers.get(i).getPhone();
            taxPayerTableData[i][4] = taxPayers.get(i).getIncome();
            taxPayerTableData[i][5] = taxPayers.get(i).getDeductibleAmount();
            taxPayerTableData[i][6] = taxPayers.get(i).getTaxHeld();
            taxPayerTableData[i][7] = taxPayers.get(i).getTaxToBeReturned();
        }
        this.view.loadTaxPayersTable(taxPayerTableData);
        this.view.setVisible(true);
    }

    public void loadSelectedTaxPayer() {
        int selectedRow = this.view.getTblTaxPayerList().getSelectedRow();
        String tfn = String.valueOf(this.view.getTblTaxPayerList().getValueAt(selectedRow, 0));
        taxManagementPresenter.getView().getTxtFind().setText(String.valueOf(tfn));
        taxManagementPresenter.find();
        this.view.dispose();
    }
    
}
