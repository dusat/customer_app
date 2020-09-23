package cz.tkacikd.consumerapp.service;

import cz.tkacikd.consumerapp.domain.CustomerCard;
import cz.tkacikd.consumerapp.exception.NoCustomerCardException;
import cz.tkacikd.consumerapp.repository.CustomerCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CardDataConsumerService {

    private static Logger logger = LoggerFactory.getLogger(CardDataConsumerService.class);

    private JobLauncher jobLauncher;

    private CustomerCardRepository customerCardRepository;

    private Job job;

    @Autowired
    CardDataConsumerService(CustomerCardRepository customerCardRepository, JobLauncher jobLauncher, Job job) {
        this.customerCardRepository = customerCardRepository;
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @KafkaListener(topics = "csttopic", groupId = "test-consumer-group", containerFactory = "kafkaFactory")
    public void consume(CustomerCard customerCard) {
        System.out.println("Consuming the message" + customerCard);
        Optional<CustomerCard> optionalCustomerCard = customerCardRepository.findById(customerCard.getId());
        if (optionalCustomerCard.isPresent()) {
            CustomerCard cst = new CustomerCard();
            cst = optionalCustomerCard.get();
            Long sumFromKafka = customerCard.getSum();
            Long sumFromDatabase = cst.getSum();
            Long newSum = sumFromKafka + sumFromDatabase;
            Long discount = cst.getDiscount();
            if(newSum > 10000 & discount == 1) {
                cst.setDiscount(2L);
                cst.setSum(newSum);
                customerCardRepository.save(cst);
                try {
                    jobLauncher.run(job, new JobParameters());
                } catch (JobExecutionException e) {
                    logger.error("Job has not been activated", e);
                }
            }
            else {
                cst.setSum(newSum);
                customerCardRepository.save(cst);
            }
        }
        else {
            throw new NoCustomerCardException("Customer card number" + " " + customerCard.getId() + " " + "doesn't exist");
        }
    }

}
