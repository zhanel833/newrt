package com.smp.currency_rate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "file:${user.dir}/exchange_conf.txt", ignoreResourceNotFound = true)
public class ExchangeConfig {

    @Value("${url:https://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx}")
    private String url;

    public String getUrl() {
        return url;
    }
}