package it.eng.tim.profilo.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import it.eng.tim.profilo.integration.proxy.DCAProxy;
import it.eng.tim.profilo.integration.proxy.SDPProxy;
import it.eng.tim.profilo.model.exception.BadRequestException;
import it.eng.tim.profilo.model.exception.ServiceException;
import it.eng.tim.profilo.model.exception.GenericException;
import it.eng.tim.profilo.model.exception.NotAuthorizedException;
import it.eng.tim.profilo.service.ProfileService;

import static org.junit.Assert.*;

/**
 * Created by alongo on 30/04/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest {

    @Mock
    SDPProxy proxy;

    @Mock
    DCAProxy dcaProxy;

    private ProfileService service;

    @Before
    public void init(){
        service = new ProfileService(proxy, dcaProxy);
    }

    @After
    public void cleanup(){
        Mockito.reset(proxy);
    }

    @Test(expected = ServiceException.class)
    public void authorizeFailsOnNUllRifCliente() throws Exception {
    	service.getUserProfileInfo(null, "");
        //service.getUserProfileInfo(null);
    }

    @Test(expected = ServiceException.class)
    public void authorizeFailsOnNUllDcaCookie() throws Exception {
    	service.getUserProfileInfo("", null);
        //service.getUserProfileInfo(null);
    }

    
    /*
    
    @Test(expected = NotAuthorizedException.class)
    public void authorizeFailsOnNotOkResponse() throws Exception {
        Mockito.when(proxy.authorizeLineForRecharge(Mockito.anyString())).thenReturn(new RechargeAuthorizationResponse("A","a_line","1"));
        service.authorize("a_line");
    }

    @Test
    public void authorizeOk() throws Exception {
        Mockito.when(proxy.authorizeLineForRecharge(Mockito.anyString()))
                .thenReturn(new RechargeAuthorizationResponse("A","a_line","0"));
        service.authorize("a_line");
    }

    @Test(expected = ServiceException.class)
    public void doRechargeFailsOnNotOkResponse() throws Exception {
        Mockito.when(proxy.rechargeWithScratchCard(Mockito.anyObject()))
                .thenReturn(new ScratchCardResponse("A","a_line","1",null));
        service.doRecharge("11111", "a_line", "a_line");
    }

    @Test(expected = GenericException.class)
    public void doRechargeFailsOnWrongFormatAmount() throws Exception {
        Mockito.when(proxy.rechargeWithScratchCard(Mockito.anyObject()))
                .thenReturn(new ScratchCardResponse("A","a_line","0","AAA"));
        service.doRecharge("11111", "a_line", "a_line");
    }

    @Test
    public void doRechargeOk() throws Exception {
        Mockito.when(proxy.rechargeWithScratchCard(Mockito.anyObject()))
                .thenReturn(new ScratchCardResponse("A","a_line","0","25.00"));
        Double amount = service.doRecharge("11111", "a_line", "a_line");
        assertEquals(new Double(25d), amount);
    }

    @Test
    public void rechargeWithScratchCardOk() throws Exception {
        Mockito.when(proxy.authorizeLineForRecharge(Mockito.anyString()))
                .thenReturn(new RechargeAuthorizationResponse("A","a_line","0"));
        Mockito.when(proxy.rechargeWithScratchCard(Mockito.anyObject()))
                .thenReturn(new ScratchCardResponse("A","a_line","0","25.00"));
        Double amount = service.rechargeWithScratchCard("11111", "a_line", "a_line");
        assertEquals(new Double(25d), amount);
    }

*/

}