package org.kea.nicolainielsen.fire;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FireServiceImplTest {

    @Mock
    private FireRepository fireRepository;

    @InjectMocks
    private FireServiceImpl fireServiceImpl;

    @Test
    void save_fireModelIsSavedAndReturned() {
        // Arrange
        FireModel fire = new FireModel();
        fire.setName("Test Fire");
        fire.setLatitude(55.0);
        fire.setLongitude(12.0);
        fire.setActive(true);

        // Act
        FireModel result = fireServiceImpl.save(fire);

        // Assert
        assertEquals(fire, result);
        verify(fireRepository, times(1)).save(fire);
    }
}