package org.romanzhula.wallet_service.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.romanzhula.wallet_service.repositories.WalletRepository;
import org.romanzhula.wallet_service.responses.CommonWalletResponse;
import org.romanzhula.wallet_service.responses.WalletBalanceResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;


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

}
