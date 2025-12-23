package org.miit.adm.beatsshopadm.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue beatsQueue() {
        return new Queue("beats.queue", true); // durable
    }
}
