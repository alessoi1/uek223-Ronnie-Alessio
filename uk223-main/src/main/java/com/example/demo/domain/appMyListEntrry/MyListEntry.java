package com.example.demo.domain.appMyListEntrry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity(name="mylistentry")
//from lombok
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MyListEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String titel;
    private String text;
    private Date erstellungsdatum;
    private int wichtigkeit;

}
