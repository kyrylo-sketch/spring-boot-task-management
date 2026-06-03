package com.karol.tastManagement.service;
import com.karol.tastManagement.model.RefreshToken;
import com.karol.tastManagement.model.User;
import com.karol.tastManagement.repository.RefreshTokenRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceTest {
    @Mock
    RefreshTokenRepo refreshTokenRepo;

    @InjectMocks
    RefreshTokenService refreshTokenService;

    @Test
    void createRefreshToken_whenIsNotExists(){
        User user = new User();
        user.set_id("userId");
        user.setEmail("email");
        when(refreshTokenRepo.findByUser(user)).thenReturn(null);
        when(refreshTokenRepo.save(any(RefreshToken.class))).thenAnswer(i -> i.getArgument(0));

        RefreshToken result = refreshTokenService.createRefreshToken(user);

        assertNotNull(result.getToken());           // token istnieje
        assertNotNull(result.getExpiryAt());        // data wygaśnięcia istnieje
        assertEquals(user, result.getUser());       // przypisany do właściwego usera
        assertTrue(result.getExpiryAt().isAfter(LocalDateTime.now())); // wygasa w przyszłości
        verify(refreshTokenRepo, never()).delete(any()); // stary token NIE był usuwany
        verify(refreshTokenRepo).save(any(RefreshToken.class));
    }

    @Test
    void createRefreshToken_whenIsExists(){
        User user = new User();
        user.set_id("userId");
        user.setEmail("email");
        RefreshToken existing = new RefreshToken();
        existing.setToken("existingToken");

        when(refreshTokenRepo.findByUser(user)).thenReturn(existing);
        when(refreshTokenRepo.save(any(RefreshToken.class))).thenAnswer(i -> i.getArgument(0));

        RefreshToken result = refreshTokenService.createRefreshToken(user);

        verify(refreshTokenRepo).delete(existing);  // stary był usunięty
        verify(refreshTokenRepo).save(any());
        assertNotEquals("stary-token", result.getToken());
    }

    @Test
    void isValid_shouldReturnTrue_whenTokenNotExpired(){
        RefreshToken existing = new RefreshToken();
        existing.setUser(new User());
        existing.setExpiryAt(LocalDateTime.now().plusDays(7));

        assertTrue(refreshTokenService.isValid(existing));
    }

    @Test
    void isValid_shouldReturnTrue_whenTokenExpired(){
        RefreshToken existing = new RefreshToken();
        existing.setUser(new User());
        existing.setExpiryAt(LocalDateTime.now().minusDays(1));

        assertFalse(refreshTokenService.isValid(existing));
    }
}



