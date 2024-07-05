package test.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    @Id
    private String id;

    @Field
    private String name;

    @Field
    private String description;

    @Field
    private String properties;

    @Field
    private double price;
}
