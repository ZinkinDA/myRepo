package ru.lab.fsm_mashine_2.FSM;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.lab.fsm_mashine_2.FSM.Product.Product;
import ru.lab.fsm_mashine_2.FSM.enums.Ivents;
import ru.lab.fsm_mashine_2.FSM.enums.States;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Logger;

@Component
public class CashUnit {


    private final String filename = "CashUnit.txt";
    private final String orderFileName = "OrderCashUnit.txt";
    private static CashUnit cashUnit;

    private CashUnit() {
    }

    public CashUnit(int value, States statement, List<Product> productList) {
        this.value = value;
        this.statement = statement;
        this.productList = productList;
    }

    public static CashUnit getInstance(){
        if(cashUnit == null){
            return new CashUnit(0,States.READY,new ArrayList<>());
        }
        return cashUnit;
    }

    private int value;

    private States statement;
    @JsonIgnore
    private List<Product> productList;


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public States getStatement() {
        return statement;
    }

    public void step(Ivents ivent) throws InterruptedException {
        /** 0 - READY        *
         *  1 - PAYMENT
         *  2 - PRINT
         *  3 - COLLECTION
         */
        Scanner scanner = new Scanner(System.in );
        Optional<Product> product = Optional.empty();
        switch (ivent){
            case GO_PAY :
                if(statement.equals(States.READY)){
                    statement = States.PAYMENT;
                    System.out.println("???????????????? ID ?????????????????????? ???????????? : " + value);
                    int scan = scanner.nextInt();
                    product = productList.stream().filter(x -> x.getId() == scan).findFirst();
                    if(product.isEmpty()){
                        System.out.println("???????????? ???????????? ???? ????????????????????.");
                        statement = States.READY;
                    }
                    System.out.println("???????????????? ????????????...");
                    Thread.sleep(2000);
                    System.out.println("????????????????...");
                    value += product.get().getPrice();
                    statement = States.PRINT;
                }
            case PRINT_INFO:
                if(statement.equals(States.PRINT)){
                    StringBuilder stringBuilder = new StringBuilder();
                    try(Writer writer = new FileWriter(orderFileName)) {
                        stringBuilder.append("Order : \n\t").append("?????? ???????????? : ").append(product.get().getName()).
                                append("\n\tId ???????????? : ").append(product.get().getId()).
                                append("\n\t???????? ???????????? : ").append(product.get().getPrice());
                        System.out.println("???????????? ????????...");
                        Thread.sleep(750);
                        System.out.println("????????????????...");
                        Thread.sleep(750);
                        writer.write(stringBuilder.toString());
                        System.out.println(stringBuilder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    statement = States.READY;
                    break;
                }

            case GO_COLLECTION:
                if(statement.equals(States.READY)){
                    statement = States.COLLECTION;
                    System.out.println("?????????????? ?????????? ?????????????????? : ");
                    int scan = scanner.nextInt();
                    if(scan > value){
                        System.out.println("???????????????????????? ?????????????? ?????? ??????????????????!");
                        statement = States.READY;
                        break;
                    }
                    System.out.println("???????????????? ???????????? ??????????????.");
                    Thread.sleep(2000);
                    System.out.println("????????????????");
                    value -= scan;
                    statement = States.READY;
                }
                break;
        }

    }

    public String stepString(Ivents ivent,Integer value) throws InterruptedException {
        /** 0 - READY        *
         *  1 - PAYMENT
         *  2 - COLLECTION
         *  3 - PRINT
         */
        Optional<Product> product = Optional.empty();
        switch (ivent){
            case GO_PAY :
                if(statement.equals(States.READY)){
                    statement = States.PAYMENT;
                    final Integer finalValue = value;
                    System.out.println("?????????????? ID ?????????????????????? ???????????? : " + value);
                    product = productList.stream().filter(x -> finalValue.equals(x.getId())).findFirst();
                    if(product.isEmpty()){
                        System.out.println("???????????? ???????????? ???? ????????????????????.");
                        statement = States.READY;
                        break;
                    }
                    System.out.println("???????????????? ????????????");
                    Thread.sleep(2000);
                    System.out.println("????????????????");
                    this.value += product.get().getPrice();
                    try(Writer writer = new FileWriter(filename)){
                        writer.write(String.valueOf(this.value));
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    statement = States.PRINT;
                }
            case PRINT_INFO:
                if(statement.equals(States.PRINT)){
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Order : ").
                            append("\n\t?????? ???????????? : ").append(product.get().getName()).
                            append("\n\tId ???????????? : ").append(product.get().getId()).
                            append("\n\t???????? ???????????? : ").append(product.get().getPrice());
                    System.out.println("???????????? ????????...");
                    Thread.sleep(750);
                    System.out.println("????????????????...");
                    Thread.sleep(750);
                    try(Writer writer = new FileWriter(orderFileName,true)) {
                        writer.append(stringBuilder.toString()).append("\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(stringBuilder);
                    statement = States.READY;
                    return stringBuilder.toString();
                }

            case GO_COLLECTION:
                StringBuilder stringBuilder = new StringBuilder();
                if(statement.equals(States.READY)){
                    statement = States.COLLECTION;
                    System.out.println("?????????????? ?????????? ?????????????????? : ");
                    if(this.value < value){
                        System.out.println("???????????????????????? ?????????????? ?????? ??????????????????!");
                        statement = States.READY;
                        return stringBuilder.append("???????????????????????? ?????????????? ?????? ??????????????????!").toString();
                    }
                    System.out.println("???????????????? ???????????? ??????????????.");
                    Thread.sleep(2000);
                    System.out.println("????????????????");
                    this.value -= value;
                    try(Writer writer = new FileWriter(filename)){
                        writer.append(String.valueOf(this.value));
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    statement = States.READY;
                    return stringBuilder.append("???????????????? ?????????????? : ").append(value).toString();
                }
                break;
        }
        return "ERROR!";
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void addProduct(Product productList) {
        this.productList.add(productList);
    }
}