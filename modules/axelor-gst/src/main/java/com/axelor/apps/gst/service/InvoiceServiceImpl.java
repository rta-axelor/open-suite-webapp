package com.axelor.apps.gst.service;

import com.axelor.apps.account.db.Invoice;
import com.axelor.apps.account.db.InvoiceLine;
import java.math.BigDecimal;
import java.util.List;

public class InvoiceServiceImpl implements InvoiceService {

  @Override
  public Invoice calculateNet(Invoice invoice) {

    List<InvoiceLine> invoiceLineList = invoice.getInvoiceLineList();

    BigDecimal netIgst = BigDecimal.ZERO;
    BigDecimal netSgst = BigDecimal.ZERO;
    BigDecimal netCgst = BigDecimal.ZERO;

    for (InvoiceLine invoiceLine : invoiceLineList) {
      netIgst = netIgst.add(invoiceLine.getIgst());
      netSgst = netSgst.add(invoiceLine.getSgst());
      netCgst = netCgst.add(invoiceLine.getCgst());
    }
    invoice.setNetIgst(netIgst);
    invoice.setNetSgst(netSgst);
    invoice.setNetCgst(netCgst);

    return invoice;
  }
}
