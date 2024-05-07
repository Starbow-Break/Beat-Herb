package store.beatherb.restapi.infrastructure.kafka.service;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import store.beatherb.restapi.infrastructure.kafka.KafkaTopic;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;


    public void sendMessage(final KafkaTopic topic, String message){
        kafkaTemplate.send(topic.getTopic(),message);
    }

    public void sendDirectMessage(String message){
        this.sendMessage(KafkaTopic.DIRECT_MESSAGE,message);
    }

    public void sendProcessingHls(String message){
        this.sendMessage(KafkaTopic.PROCESSING_HLS,message);
    }
}
