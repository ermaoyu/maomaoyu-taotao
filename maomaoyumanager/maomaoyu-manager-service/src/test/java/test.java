import com.maomaoyu.zhihu.serivce.impl.HostHandler;
import com.maomaoyu.zhihu.mapper.CommentMapper;
import com.maomaoyu.zhihu.mapper.UserMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * maomaoyu    2019/3/9_10:34
 **/
public class test {
    private ApplicationContext context ;

    @Before
    public void setup(){
        context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
    }

//    @Test
//    public void userMapper(){
//        TTestMapper testMapper = (TTestMapper) context.getBean(TTestMapper.class);
//        System.out.println(testMapper);
//    }

    @Test
    public void test(){
        HostHandler hostHandler = (HostHandler) context.getBean("hostHandler");
        System.out.println(hostHandler);
    }

    @Test
    public void teMapper(){
        CommentMapper testMapper = (CommentMapper) context.getBean(CommentMapper.class);
        System.out.println(testMapper);
    }

    @Test
    public void tMapper(){
        UserMapper testMapper = (UserMapper) context.getBean(UserMapper.class);
        System.out.println(testMapper.findAll());
    }



}
