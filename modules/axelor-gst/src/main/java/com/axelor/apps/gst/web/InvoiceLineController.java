package com.axelor.apps.gst.web;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import com.axelor.apps.base.db.Address;
import com.axelor.apps.base.db.Company;
import com.axelor.apps.gst.service.InvoiceLineService;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;

public class InvoiceLineController {

  @Inject InvoiceLineService invoiceLineService;

  public void calculateGst(ActionRequest request, ActionResponse response) {
    Invoice invoice = request.getContext().getParent().asType(Invoice.class);
    InvoiceLine invoiceLine = request.getContext().asType(InvoiceLine.class);
    Company invoiceCompany = invoice.getCompany();
    Address invoiceAddress = invoice.getAddress();

    invoiceLine =
        invoiceLineService.calculateInvoiceLine(invoiceLine, invoiceCompany, invoiceAddress);

    response.setValue("igst", invoiceLine.getIgst());
    response.setValue("sgst", invoiceLine.getSgst());
    response.setValue("cgst", invoiceLine.getCgst());
    response.setValue("grossAmount", invoiceLine.getInTaxTotal());
  }
}
