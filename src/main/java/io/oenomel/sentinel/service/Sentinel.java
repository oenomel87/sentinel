package io.oenomel.sentinel.service;

import io.oenomel.sentinel.service.analyzer.Analyzer;
import io.oenomel.sentinel.service.linker.Linker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class Sentinel {

    private Logger logger = LoggerFactory.getLogger(Sentinel.class);

    @Autowired
    private Analyzer analyzer;

    @Autowired
    private Linker linker;

    @Scheduled(cron = "0/10 * * * * *")
    public void task() {
        logger.info("Sentinel starts task! at " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
}
