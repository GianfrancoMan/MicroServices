package com.eazybytes.accounts.dto;
//DTO relativo alla messagistica verso l'utente finale tramite eventi verso broker di messaggi
public record AccountsMsgDto(Long accountNumber, String name, String email, String mobileNumber) {
}
