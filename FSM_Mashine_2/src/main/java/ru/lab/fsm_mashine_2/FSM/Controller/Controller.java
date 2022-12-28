package ru.lab.fsm_mashine_2.FSM.Controller;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.JSONParserTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.lab.fsm_mashine_2.FSM.CashUnit;
import ru.lab.fsm_mashine_2.FSM.Product.Product;
import ru.lab.fsm_mashine_2.FSM.Service.CashService;
import ru.lab.fsm_mashine_2.FSM.enums.Ivents;

@RestController
@RequestMapping("/api/fsm")
public class Controller {
    private final CashService cashService;

    public Controller(@Autowired CashService cashService) {
        this.cashService = cashService;
    }
    @GetMapping
    public ResponseEntity<?> getCashUnit(){
        return ResponseEntity.ok(cashService.getCashUnit());
    }
    @PostMapping("pay")
    public ResponseEntity<?> getPay(@RequestBody int id) throws InterruptedException {
        return ResponseEntity.ok(cashService.getPay(id));
    }
    @PostMapping("deduction")
    public ResponseEntity<?> deduction(@RequestBody int value) throws InterruptedException {
        return ResponseEntity.ok(cashService.getCollection(value));
    }

    @GetMapping("product")
    public ResponseEntity<?> getProductList(){
        return ResponseEntity.ok(cashService.getProductList());
    }

    @PostMapping("/product/add")
    public void addProductList(@RequestBody Product product){
        if(product == null || product.getName() == null || product.getPrice() == 0 || product.getDescription() == null){
            throw new RuntimeException("Не верный ввод");
        }
        cashService.addProduct(product);
    }
}
