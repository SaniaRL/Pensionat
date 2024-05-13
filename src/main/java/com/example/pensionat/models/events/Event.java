package com.example.pensionat.models.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //ÄNDRA EV I FRAMTID XD
//@DiscriminatorColumn(name = "type")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RoomClosed.class, name = "RoomClosed"),
        @JsonSubTypes.Type(value = RoomCleaningFinished.class, name = "RoomCleaningFinished"),
        @JsonSubTypes.Type(value = RoomCleaningStarted.class, name = "RoomCleaningStarted"),
        @JsonSubTypes.Type(value = RoomOpened.class, name = "RoomOpened")
})
public class Event {
    @Id
    @GeneratedValue
    private Long id;
    public LocalDateTime TimeStamp;
    //@Column(insertable=false, updatable=false)
    private String type;
}