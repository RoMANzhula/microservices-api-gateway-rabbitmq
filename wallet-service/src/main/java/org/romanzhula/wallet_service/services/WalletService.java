package org.romanzhula.wallet_service.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.romanzhula.wallet_service.configurations.RabbitmqConfig;
import org.romanzhula.wallet_service.models.Wallet;
import org.romanzhula.wallet_service.models.events.BalanceOperationEvent;
import org.romanzhula.wallet_service.repositories.WalletRepository;
import org.romanzhula.wallet_service.responses.CommonWalletResponse;
import org.romanzhula.wallet_service.responses.WalletBalanceResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitmqConfig rabbitmqConfig;


    @Transactional(readOnly = true)
    public List<CommonWalletResponse> getAllWallets() {
        return walletRepository.findAll()
                .stream()
                .map(wallet -> new CommonWalletResponse(wallet.getUserId(), wallet.getBalance()))
                .collect(Collectors.toList())
        ;
    }

    @Transactional(readOnly = true)
    public CommonWalletResponse getWalletById(UUID walletId) {
        return walletRepository.findById(walletId)
                .map(wallet -> new CommonWalletResponse(wallet.getUserId(), wallet.getBalance()))
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found with id: " + walletId))
        ;
    }

    @Transactional(readOnly = true)
    public WalletBalanceResponse getBalanceByWalletId(UUID walletId) {
        return walletRepository.findById(walletId)
                .map(wallet -> new WalletBalanceResponse(wallet.getBalance()))
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found with id: " + walletId))
        ;
    }

    @Transactional
    public String replenishBalance(BalanceOperationEvent balanceOperationEvent) {
        Wallet wallet = walletRepository
                .findById(balanceOperationEvent.getUserId())
                .orElseThrow(() -> new RuntimeException("Wallet not found!"))
        ;

        wallet.setBalance(wallet.getBalance().add(balanceOperationEvent.getAmount()));

        walletRepository.save(wallet);

        rabbitTemplate.convertAndSend(
                rabbitmqConfig.getQueueWalletReplenished(),
                new BalanceOperationEvent(balanceOperationEvent.getUserId(), balanceOperationEvent.getAmount())
        );

        return "Balance replenished successfully.";
    }

}
