package org.example.batch.service;

import org.example.batch.config.SettlementBatchConfig;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SettlementJobLauncher {

    private final JobOperator jobOperator;
    private final Job settlementJob;
    private final Job settlementChunkJob;

    public SettlementJobLauncher(
            JobOperator jobOperator,
            @Qualifier(SettlementBatchConfig.SETTLEMENT_JOB_NAME) Job settlementJob,
            @Qualifier(SettlementBatchConfig.SETTLEMENT_CHUNK_JOB_NAME) Job settlementChunkJob
    ) {
        this.jobOperator = jobOperator;
        this.settlementJob = settlementJob;
        this.settlementChunkJob = settlementChunkJob;
    }

    public JobExecution launch(
            LocalDate settlementDate
    ) throws Exception {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("settlementDate", settlementDate.toString())
//                // 동일 날짜 재실행을 위한 유니크 파라미터
//                .addLong("requestedAt", System.currentTimeMillis())
//                .toJobParameters();

        return jobOperator.start(
                settlementJob,
                buildJobParameters(settlementDate, "tasklet")
        );
    }

    public JobExecution launchChunk(
            LocalDate settlementDate
    ) throws Exception {
        return jobOperator.start(
                settlementChunkJob,
                buildJobParameters(settlementDate, "chunk")
        );
    }

    private JobParameters buildJobParameters(
            LocalDate settlementDate,
            String mode
    ) {
        return new JobParametersBuilder()
                .addString("settlementDate", settlementDate.toString())
                .addString("mode", mode)
                // 동일 날짜 재실행을 위한 유니크 파라미터
                .addLong("requestedAt", System.currentTimeMillis())
                .toJobParameters();
    }

//    Job: [SimpleJob: [name=settlementChunkJob]] launched with the following parameters
//    : [{JobParameter{
//          name='mode',
//          value=chunk,
//          type=class java.lang.String,
//          identifying=true
//     },JobParameter{
//          name='requestedAt',
//          value=1773584338854,
//          type=class java.lang.Long,
//          identifying=true
//     },JobParameter{
//          name='settlementDate',
//          value=2026-03-15,
//          type=class java.lang.String,
//          identifying=true
//     }}]

}
