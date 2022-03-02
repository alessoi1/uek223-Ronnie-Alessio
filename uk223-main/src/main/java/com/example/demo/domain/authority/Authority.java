package com.example.demo.domain.authority;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "authority")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Authority  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

}
