package com.example.springbatch.config;

import com.example.springbatch.listener.MyChunkListener;
import com.example.springbatch.listener.MyJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @author Leung
 * @create 2020-04-29 14:45
 */
@Configuration
@EnableBatchProcessing
public class ListenerDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job listenerDemoJob(){

        return jobBuilderFactory.get("listenerDemoJob")
                .start(listenerStep())
                .listener(new MyJobListener())
                .build();
    }

    @Bean
    public Step listenerStep() {
        return stepBuilderFactory.get("listenerStep")
                .<String,String>chunk(2)
                .faultTolerant()
                .listener(new MyChunkListener())
                .reader(read())
                .writer(write())
                .build();

    }

    private ItemWriter<String> write() {

        return new ItemWriter<String>() {
            @Override
            public void write(List<? extends String> list) throws Exception {
                for (String s : list) {
                    System.out.println("s = " + s);
                }
            }
        };
    }

    private ItemReader<String> read() {
        return new ListItemReader<>(Arrays.asList("a","b","c"));
    }

}
