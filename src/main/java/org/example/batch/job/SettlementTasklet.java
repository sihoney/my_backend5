package org.example.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Component
public class SettlementTasklet implements Tasklet {

    private static final Logger log = LoggerFactory.getLogger(SettlementTasklet.class);

    @Override
    public RepeatStatus execute(
            StepContribution contribution,
            ChunkContext chunkContext
    ) {
        String rawDate = Objects.toString(
                chunkContext.getStepContext().getJobParameters().get("settlementDate"),
                LocalDate.now().toString()
        );

        LocalDate settlementDate;
        try {
            settlementDate = LocalDate.parse(rawDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("settlementDate must be yyyy-MM-dd: " + rawDate, e);
        }

        // TODO: 실제 정산 집계/원장 반영 로직으로 교체
        log.info("Settlement batch step started. settlementDate={}", settlementDate);
        log.info("Settlement batch step finished. settlementDate={}", settlementDate);

        // Job: [SimpleJob: [name=settlementJob]] launched with the following parameters:
        // Executing step: [settlementStep]
        //
        // Settlement batch step started. settlementDate=2026-03-15
        // Settlement batch step finished. settlementDate=2026-03-15
        //
        // Step: [settlementStep] executed in 12ms
        // Job: [SimpleJob: [name=settlementJob]] completed with the following parameters:

        return RepeatStatus.FINISHED;
    }
}