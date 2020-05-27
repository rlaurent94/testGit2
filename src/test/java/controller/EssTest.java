//package controller;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;
//import testSpringBoot2.MainClass;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = MainClass.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//public class EssTest {
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
////    @Test
////    public void test1() {
////        String url = String.format("http://localhost:%s/home", Integer.toString(port));
////        String body = this.restTemplate.getForObject(url, String.class);
////        Assertions.assertEquals(body, "Home");
////    }
//}
