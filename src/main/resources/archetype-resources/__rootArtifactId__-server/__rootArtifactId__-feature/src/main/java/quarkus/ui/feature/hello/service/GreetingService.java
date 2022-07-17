#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.quarkus.ui.feature.hello.service;

import ${package}.quarkus.ui.data.entity.Person;
import ${package}.quarkus.ui.model.dto.Greeting;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class GreetingService {

    public String getGreeting() {
        final List<Person> people = Person.listAll();
        return new Greeting(people.isEmpty() ? new Person() : people.get(0)).toString();
    }
}
