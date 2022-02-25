package com.example.demo.domain.appMyListEntrry;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter @Setter
public class UpdateMyListEntryDTO {

    private String titel;

    private String text;

    private int wichtigkeit;
}
