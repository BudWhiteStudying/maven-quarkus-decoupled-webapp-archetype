#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.quarkus.ui.model.dto;

import ${package}.quarkus.ui.data.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Greeting implements Serializable {
    private Person greeter;

    @Override
    public String toString() {
        String greetingTemplate = "%s, aged %d, says hi!";
        return String.format(greetingTemplate, this.greeter.getName(), this.greeter.getAge());
    }
}
