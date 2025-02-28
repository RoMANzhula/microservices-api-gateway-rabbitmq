package org.romanzhula.wallet_service.controllers;

import lombok.RequiredArgsConstructor;
import org.romanzhula.wallet_service.responses.CommonWalletResponse;
import org.romanzhula.wallet_service.services.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletService walletService;


    @GetMapping("/all")
    public ResponseEntity<List<CommonWalletResponse>> getAll() {
        return ResponseEntity.ok(walletService.getAllWallets());
    }

}
