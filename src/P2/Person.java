package P2;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xinyu
 * Date: 2023-03-06
 * Time: 20:25
 */
public class Person {
    // Abstraction function:
    //   AF(name)=a person's name
    // Representation invariant:
    //  name is string
    // Safety from rep exposure:
    //    All fields are private;
    public Person(String name) {
        this.name = name;
    }
    private final String name;
    public String getName(){
        return this.name;
    }
}
