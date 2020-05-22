package model.entity;

import com.boob.annotation.Column;
import com.boob.annotation.Entity;
import com.boob.annotation.Id;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @Column
    private Integer id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "age")
    private Integer age;
}
