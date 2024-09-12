package com.smp.currency_rate.service;

import com.smp.currency_rate.config.ExchangeConfig;
import com.smp.currency_rate.wsdl.GetCursOnDateXML;
import com.smp.currency_rate.wsdl.GetCursOnDateXMLResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

@Service
public class CurrencyService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @Autowired
    private ExchangeConfig exchangeConfig;

    public BigDecimal getYuanRate() throws Exception {
        logger.info("Fetching Yuan rate from URL: {}", exchangeConfig.getUrl());

        GetCursOnDateXML request = new GetCursOnDateXML();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        XMLGregorianCalendar xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        request.setOnDate(xmlCalendar);

        GetCursOnDateXMLResponse response = (GetCursOnDateXMLResponse) webServiceTemplate.marshalSendAndReceive(request);

        return response.getGetCursOnDateXMLResult().getValuteData().getValuteCursOnDate().stream()
                .filter(v -> "CNY".equals(v.getVchCode()))
                .findFirst()
                .map(v -> v.getVcurs())
                .orElseThrow(() -> new RuntimeException("Yuan rate not found"));
    }
}
