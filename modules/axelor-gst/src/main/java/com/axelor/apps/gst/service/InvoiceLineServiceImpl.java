package com.axelor.apps.gst.service;

import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.base.db.Address;
import com.axelor.apps.base.db.Company;
import java.math.BigDecimal;

public class InvoiceLineServiceImpl implements InvoiceLineService {

  @Override
  public InvoiceLine calculateInvoiceLine(
      InvoiceLine invoiceLine, Company invoiceCompany, Address invoiceAddress) {

    BigDecimal netAmount = invoiceLine.getExTaxTotal();
    BigDecimal gstRate = invoiceLine.getGstRate();
    BigDecimal grossAmount = invoiceLine.getInTaxTotal();

    BigDecimal taxRate = invoiceLine.getTaxLine().getValue();

    if (invoiceCompany.getAddress().getState() != invoiceAddress.getState()) {
      invoiceLine.setSgst(BigDecimal.ZERO);
      invoiceLine.setCgst(BigDecimal.ZERO);
      BigDecimal igst = netAmount.multiply(invoiceLine.getGstRate()).divide(new BigDecimal(100));
      invoiceLine.setIgst(igst);

      grossAmount = netAmount.add(netAmount.multiply(taxRate).add(igst));
      invoiceLine.setInTaxTotal(grossAmount);

    } else {
      invoiceLine.setIgst(BigDecimal.ZERO);
      BigDecimal sgstAndcgst =
          (netAmount.multiply(invoiceLine.getGstRate()).divide(new BigDecimal(2)))
              .divide(new BigDecimal(100));
      invoiceLine.setSgst(sgstAndcgst);
      invoiceLine.setCgst(sgstAndcgst);

      grossAmount = netAmount.add(netAmount.multiply(taxRate).add(sgstAndcgst));
      invoiceLine.setInTaxTotal(grossAmount);
    }

    return invoiceLine;
  }
}
