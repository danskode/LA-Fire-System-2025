package org.kea.nicolainielsen.alarm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class AlarmControllerTest {

    @Mock
    private AlarmServiceImpl alarmServiceImpl;

    @InjectMocks
    private AlarmController alarmController;

    @Test
    void deleteAlarm_returnsOkWhenAlarmExists() {
        // Arrange
        int alarmId = 1;
        AlarmModel alarm = new AlarmModel();
        when(alarmServiceImpl.findById(alarmId)).thenReturn(alarm);

        // Act
        ResponseEntity<Void> response = alarmController.deleteAlarm(alarmId);

        // Assert
        assertEquals(OK, response.getStatusCode());
        verify(alarmServiceImpl).delete(alarm);
    }

    @Test
    void deleteAlarm_returnsNotFoundWhenAlarmDoesNotExist() {
        // Arrange
        int alarmId = 2;
        when(alarmServiceImpl.findById(alarmId)).thenReturn(null);

        // Act
        ResponseEntity<Void> response = alarmController.deleteAlarm(alarmId);

        // Assert
        assertEquals(NOT_FOUND, response.getStatusCode());
        verify(alarmServiceImpl, never()).delete(any());
    }
}
