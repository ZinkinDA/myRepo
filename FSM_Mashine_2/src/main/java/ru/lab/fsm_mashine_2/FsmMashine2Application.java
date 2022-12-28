package ru.lab.fsm_mashine_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.lab.fsm_mashine_2.FSM.CashUnit;
import ru.lab.fsm_mashine_2.FSM.Product.Product;
import ru.lab.fsm_mashine_2.FSM.enums.Ivents;

@SpringBootApplication
public class FsmMashine2Application {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(FsmMashine2Application.class, args);
    }

}
