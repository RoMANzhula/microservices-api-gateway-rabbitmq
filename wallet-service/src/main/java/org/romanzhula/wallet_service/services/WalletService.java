package org.romanzhula.wallet_service.services;

import lombok.RequiredArgsConstructor;
import org.romanzhula.wallet_service.repositories.WalletRepository;
import org.romanzhula.wallet_service.responses.CommonWalletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

}
