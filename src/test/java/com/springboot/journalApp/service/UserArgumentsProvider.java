package com.springboot.journalApp.service;

import com.springboot.journalApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Collections;
import java.util.stream.Stream;

public class UserArgumentsProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
              Arguments.of(User.builder().id(8L).username("aman").password("aman").roles(Collections.emptyList()).build()),
                Arguments.of(User.builder().id(9L).username("kash").password("kash").roles(Collections.emptyList()).build())
                );
    }
}
