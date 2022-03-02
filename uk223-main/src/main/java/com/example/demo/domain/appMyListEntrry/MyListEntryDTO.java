package com.example.demo.domain.appMyListEntrry;

import com.example.demo.domain.appUser.UserDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter @Setter
public class MyListEntryDTO {

    private String titel;

    private String text;

    private Date erstellungsdatum;

    private int wichtigkeit;

    private UserDTO userDTO;
}
