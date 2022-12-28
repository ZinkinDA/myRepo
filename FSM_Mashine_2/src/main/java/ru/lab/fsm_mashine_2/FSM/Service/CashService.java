package ru.lab.fsm_mashine_2.FSM.Service;

import org.springframework.stereotype.Service;
import ru.lab.fsm_mashine_2.FSM.CashUnit;
import ru.lab.fsm_mashine_2.FSM.Product.Product;
import ru.lab.fsm_mashine_2.FSM.enums.Ivents;

import java.util.List;

@Service
public class CashService {

    private Integer id = 5;
    private final CashUnit cashUnit;

    public CashService() {
        cashUnit = CashUnit.getInstance();
        Product product1 = new Product(1,"Jeans",2000,"Blue jeans");
        Product product2 = new Product(2,"Jeans",2200,"Black jeans");
        Product product3 = new Product(3,"Jeans",1900,"White jeans");
        Product product4 = new Product(4,"Banana",170,"Yellow");
        Product product5 = new Product(5,"Orange",230,"Orange");
        cashUnit.addProduct(product1);
        cashUnit.addProduct(product2);
        cashUnit.addProduct(product3);
        cashUnit.addProduct(product4);
        cashUnit.addProduct(product5);
    }

    public CashUnit getCashUnit(){
        return cashUnit;
    }

    public String getPay(Integer id) throws InterruptedException {
        return cashUnit.stepString(Ivents.GO_PAY,id);
    }

    public String getCollection(Integer value) throws InterruptedException {
        return cashUnit.stepString(Ivents.GO_COLLECTION,value);
    }

    public void addProduct(Product product){
        product.setId(++id);
        cashUnit.addProduct(product);
    }

    public List<Product> getProductList(){
        return cashUnit.getProductList();
    }
}
