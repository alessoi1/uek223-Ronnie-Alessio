package com.example.demo.domain.appMyListEntrry;

import com.example.demo.domain.appUser.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Entity(name="mylistentry")
//from lombok
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MyListEntry {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "Titel cannot be null")
    private String titel;
    
    @NotNull(message = "Text cannot be null")
    private String text;

    @DateTimeFormat
    @NotNull(message = "Erstellungsdatum cannot be null")
    private Date erstellungsdatum;

    @Size(min = 1, max = 10, message = "Wichtigkeit has to be between 1 and 10")
    @NotNull(message = "Wichtigkeit cannot be null")
    private int wichtigkeit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "mylistentry_user",
            joinColumns = @JoinColumn(
                    name = "mylistentry_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"))
    private User user;

}
