package com.example.demo.domain.appMyListEntrry;

import com.example.demo.domain.appUser.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MyListEntryDTO {

    private String titel;

    private String text;

    private Date erstellungsdatum;

    private int wichtigkeit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "mylistentry_user",
            joinColumns = @JoinColumn(
                    name = "mylistentry_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"))

    private User user;

}
