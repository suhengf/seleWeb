package test;

import com.auto.common.exception.BizException;
import com.auto.controller.OrderController;
import com.auto.entity.UserInfo;
import com.auto.mapper.UserMapper;
import com.auto.service.abstr.ErrorEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


public class EmployeeControllerTest {


    public static void main(String[] args) {

        Test1 test1 = new Test1();
        if (test1==null) {
            System.out.println("null");
        }
        System.out.println("not null");

    }

}
