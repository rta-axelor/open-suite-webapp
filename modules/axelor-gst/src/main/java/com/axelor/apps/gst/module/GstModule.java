package com.axelor.apps.gst.module;

import com.axelor.app.AxelorModule;
import com.axelor.apps.gst.service.InvoiceLineService;
import com.axelor.apps.gst.service.InvoiceLineServiceImpl;
import com.axelor.apps.gst.service.InvoiceService;
import com.axelor.apps.gst.service.InvoiceServiceImpl;

public class GstModule extends AxelorModule {

  protected void configure() {

    bind(InvoiceLineService.class).to(InvoiceLineServiceImpl.class);

    bind(InvoiceService.class).to(InvoiceServiceImpl.class);
  }
}
