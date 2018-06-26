package it.eng.tim.profilo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.eng.tim.profilo.ProfileApplication;

/**
 * Created by alongo on 13/04/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProfileApplication.class)
public class ProfileApplicationTest {

    @Test
    public void contextLoads() {
    }

    /*@Test
    public void applicationMainTest() {
        ProfileApplication.main(new String[] {});
    }*/

}