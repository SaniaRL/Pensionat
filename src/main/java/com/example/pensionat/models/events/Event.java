package com.example.pensionat.models.events;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //Justera ev. vid behov
@DiscriminatorColumn(name = "typeOfEvent") //Justera ev. vid behov.
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
    @JsonProperty("TimeStamp")
    private LocalDateTime TimeStamp;
    @JsonProperty("RoomNo")
    private String RoomNo;
    //Bort kommenterat då vi redan får en type. //Justera ev. vid behov
    // @JsonProperty("type")
    // private String type;
}