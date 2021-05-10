package com.hashedIn.service;

import com.hashedIn.exception.InvalidDateFormatException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class NetflixServiceImplTest
{

    @Test
    void validate() throws InvalidDateFormatException, ParseException {
        NetflixServiceImpl netflixServiceImpl = new NetflixServiceImpl();
        assertEquals(true,netflixServiceImpl.validate("01-01-2010","31-12-2019"));
    }
}