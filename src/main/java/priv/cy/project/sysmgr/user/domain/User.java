package priv.cy.project.sysmgr.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.util.IOUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringReader;
import java.util.Optional;
import java.util.function.Consumer;

@Data
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private Car car;

    private void driveCar(){
        System.out.println("Drive："+car.getName());
    }

    public static void main(String[] args) throws JAXBException {
        User user = User.builder().id(12L).name("liming").build();
//        user = null;
//        Optional<User> optionalUser = Optional.ofNullable(user);
//
//        Optional<String> insName = optionalUser.map(u -> u.getCar())
//                .map(c -> c.getInsurance())
//                .map(ins -> ins.getName(
//                ));
//
//        Optional<Car> optCar = optionalUser.map(u -> u.getCar());
//        optCar.ifPresent(car->{
//            System.out.println("Car exist");
//        });
//
//        Runnable driveCar = user::driveCar;
//        driveCar.run();
//
//        System.out.println(insName.orElse("Unkonwn"));

        JAXBContext context = JAXBContext.newInstance(User.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(user, System.out);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.unmarshal(new StringReader(""));

    }
}
