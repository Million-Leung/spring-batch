package com.example.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Leung
 * @create 2020-04-27 17:49
 */
@Configuration
@EnableBatchProcessing
public class NestedDemo{

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    Job childJob1Job;

    @Autowired
    Job childJob2Job;

    @Autowired
    JobLauncher launcher;


    @Bean
    public Job NestedDemoJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager){
        return jobBuilderFactory.get("NestedDemoJob")
                .start(nestedChildJob1(jobRepository,platformTransactionManager))
                .next(nestedChildJob2(jobRepository,platformTransactionManager))
                .build();
    }

    private Step nestedChildJob2(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobStepBuilder(new StepBuilder("nestedChildJob2"))
                .job(childJob1Job)
                .launcher(launcher)
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .build();
    }

    private Step nestedChildJob1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobStepBuilder(new StepBuilder("nestedChildJob1"))
                .job(childJob2Job)
                .launcher(launcher)
                .repository(jobRepository)
                .transactionManager(platformTransactionManager)
                .build();
    }

}
