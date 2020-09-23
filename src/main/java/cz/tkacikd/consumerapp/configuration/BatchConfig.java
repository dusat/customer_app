package cz.tkacikd.consumerapp.configuration;

import cz.tkacikd.consumerapp.service.EmailService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EmailService emailService;

    @Bean
    public Step mailStep() {
        return stepBuilderFactory.get("mailStep").tasklet((stepContribution, chunkContext) -> {
            emailService.sendMailToCustomer();
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Job mailJob() {
        return jobBuilderFactory
                .get("mailJob")
                .incrementer(new RunIdIncrementer())
                .start(mailStep())
                .build();
    }

}
