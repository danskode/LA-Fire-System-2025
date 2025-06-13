package org.kea.nicolainielsen.siren;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SirenServiceImplTest {

    @Mock
    private SirenRepository sirenRepository;

    @InjectMocks
    private SirenServiceImpl sirenServiceImpl;

    @Test
    void getSireneModelbyID_whenIdIsNull_returnsNull() {
        // Act
        SirenModel result = sirenServiceImpl.getSireneModelbyID(null);

        // Assert
        assertNull(result);
        verify(sirenRepository, never()).findById(any());
    }

    @Test
    void getSireneModelbyID_whenSirenFound_returnsModel() {
        // Arrange
        SirenModel siren = new SirenModel();
        siren.setId(1);
        when(sirenRepository.findById(1)).thenReturn(Optional.of(siren));

        // Act
        SirenModel result = sirenServiceImpl.getSireneModelbyID(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(sirenRepository).findById(1);
    }

    @Test
    void getSireneModelbyID_whenSirenNotFound_returnsNull() {
        // Arrange
        when(sirenRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        SirenModel result = sirenServiceImpl.getSireneModelbyID(1);

        // Assert
        assertNull(result);
        verify(sirenRepository).findById(1);
    }
}