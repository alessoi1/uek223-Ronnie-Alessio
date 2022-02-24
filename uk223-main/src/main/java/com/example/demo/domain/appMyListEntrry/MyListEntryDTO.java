package com.example.demo.domain.appMyListEntrry;

import com.example.demo.domain.appUser.User;
import com.example.demo.domain.appUser.UserDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Getter @Setter
public class MyListEntryDTO {

    private String titel;

    private String text;

    private Date erstellungsdatum;

    private int wichtigkeit;

    private UserDTO userDTO;
}
