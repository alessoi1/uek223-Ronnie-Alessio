package com.example.demo.domain.appMyListEntrry;

import com.example.demo.domain.appUser.User;
import com.example.demo.domain.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "mylistentry_user",
            joinColumns = @JoinColumn(
                    name = "mylistentry_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"))
    private User user;

}
