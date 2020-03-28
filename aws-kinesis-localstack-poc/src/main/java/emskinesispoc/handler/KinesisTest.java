package emskinesispoc.handler;

import emskinesispoc.bean.Order;
import emskinesispoc.controller.OrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class KinesisTest implements Function<Order,String> {

    @Autowired
    OrderController orderController;

    @Override
    public String apply(Order order) {
        return null;
    }
}
