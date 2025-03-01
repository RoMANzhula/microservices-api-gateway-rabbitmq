package org.romanzhula.wallet_service.configurations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class RabbitmqConfig {

    @Value("${rabbitmq.queue.name.user-created}")
    private String queueUserCreated;

    @Value("${rabbitmq.queue.name.wallet-updated}")
    private String queueWalletReplenished;

}
