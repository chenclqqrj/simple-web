package my.guili.simple;

import com.alibaba.druid.pool.DruidDataSource;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.json.Json;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/json")
public class HelloServiceController {

    private Dao nutzDao;

    @PostConstruct
    private void init() {
        DruidDataSource ddd = new DruidDataSource();
        ddd.setDriverClassName("com.mysql.jdbc.Driver");
        ddd.setUrl("jdbc:mysql://" + System.getProperty("MYSQL_SERVICE_HOST") + ":" + System.getProperty("MYSQL_SERVICE_PORT") + "/" + System.getProperty("MYSQL_DATABASE"));
        ddd.setUsername(System.getProperty("MYSQL_USER"));
        ddd.setPassword(System.getProperty("MYSQL_PASSWORD"));
        ddd.setMaxActive(10);
        ddd.setMinIdle(3);
        nutzDao = new NutDao(ddd);
        if (!nutzDao.exists(Person.class)) {
            nutzDao.create(Person.class, false);
            System.out.println("----> 创建表");
            Person p = new Person();
            p.setAge(30);
            p.setUsername("zhangsan");
            nutzDao.insert(p);
            System.out.println("----> 插入用户");
        }

    }

    @RequestMapping(value = "/sayhello/{username}", method = RequestMethod.GET)
    public String sayHello(@PathVariable String username) {
        return "Hello " + username;
    }

    @RequestMapping(value = "/person/{username}", method = RequestMethod.GET)
    public String getPerson(@PathVariable String username) {
        if(null != username && username.trim().equals("zhangsan")) {
            List<Person> ps = nutzDao.query(Person.class, Cnd.where("username", "=", username));
            if (ps.size() > 0) {
                return Json.toJson(ps.get(0));
            }
            return "数据库中没有此人";
        }
        return "参数为空";
    }

}
