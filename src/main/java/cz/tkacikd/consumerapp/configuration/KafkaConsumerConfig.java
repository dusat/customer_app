package cz.tkacikd.consumerapp.configuration;

import cz.tkacikd.consumerapp.domain.CustomerCard;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrap_server;

    @Value("${spring.kafka.consumer.group-id}")
    private String group_id;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String offset_reset;

    @Bean
    public ConsumerFactory<String, CustomerCard> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_server);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, group_id);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offset_reset);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(CustomerCard.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CustomerCard> kafkaFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CustomerCard> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
