package com.example.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Leung
 * @create 2020-04-24 11:34
 */
@Configuration
@EnableBatchProcessing
public class JobDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobDemoJob(){

        return jobBuilderFactory.get("jobDemoJob")
//                .start(JobDemoStep1())
//                .next(JobDemoStep2())
//                .next(JobDemoStep3())
//                .build();
                .start(JobDemoStep1())
                .on("COMPLETED").to(JobDemoStep2())
                .from(JobDemoStep2()).on("COMPLETED").to(JobDemoStep3())
                .from(JobDemoStep3()).end()
                .build();
    }

    @Bean
    public Step JobDemoStep1() {

        return stepBuilderFactory.get("JobDemoStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("step1*************************");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step JobDemoStep2() {

        return stepBuilderFactory.get("JobDemoStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("step2*************************");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }
    @Bean
    public Step JobDemoStep3() {

        return stepBuilderFactory.get("JobDemoStep3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("step3*************************");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }


}
