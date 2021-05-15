package test;

import com.auto.controller.OrderController;
import com.auto.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootConfiguration
@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class EmployeeControllerTest {

    // 这个是专门用来测试mvc的类，可以模拟发起http请求
    @Autowired
    private MockMvc mvc;
    // 这里是模拟以一个service组件
    @MockBean
    private UserMapper userMapper;

    @Test
    public void testMvc() throws Exception {

        mvc.perform(get("/order/selectAll/",100));
}

}
