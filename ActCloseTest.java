package com.example.junit;
import com.example.junit.model.LeprechaunBody;
import com.example.junit.model.Response;
import com.example.junit.service.ActClose;
import com.example.junit.service.Parser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;


public class ActCloseTest {
    private MockedStatic<Parser> mockedParser;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockedParser = Mockito.mockStatic(Parser.class);
    }
    @Test
    public void testBuildLepResp() {
        ActClose actClose = new ActClose();

        Response resp = new Response();
        LeprechaunBody body = new LeprechaunBody();
        body.addField("one","one");

        resp.setBody(body);

        // Mocking the Parser.parseMsg to do nothing
        mockedParser.when(() -> Parser.parseMsg(any(Response.class))).thenAnswer(invocation -> null);


        Response newResp =  actClose.buildLepResp(resp);
        System.out.println(body);

        mockedParser.close(); // Close the mocked static block


    }
}
