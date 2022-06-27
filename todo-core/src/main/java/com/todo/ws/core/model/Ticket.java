package com.todo.ws.core.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ticket {
    private String title;
    private String description;
    private String priority;
    private Boolean completed;
}
