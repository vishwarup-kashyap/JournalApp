package com.springboot.journalApp.service;

import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.repository.UserRepo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@SpringBootTest
public class UserServiseTests {

    @Autowired
    public UserRepo userRepo;

    @Autowired
    public UserService userService;

    @Disabled //this test will not run
    @Test
    public void testFindByUsername(){

        User user=userRepo.findByUsername("Ram");
        assertTrue(!user.getJournalEntries().isEmpty());
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void SaveUser(User user){
        User savedUser=userService.saveUser(user);
        assertNotNull(savedUser);
    }


    @ParameterizedTest
    @CsvSource({
            "1,2,3","2,3,5","3,4,7"
            //1->a 2->b 3->expected
    })
    public void test(int a,int b,int expected){
        assertEquals(expected,a+b);
    }
}
