package me.pagarme;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.util.Scanner;

import me.pagar.model.PagarMe;
import me.pagar.model.Postback;
import me.pagar.util.JSONUtils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

public class PostbackTest extends BaseTest {

    @Before
    public void setUp() {
        PagarMe.init("ak_test_21eI4THNDJx7vkJRuAmXYlFACIaxnS");
    }

    @Test
    public void testNullOrEmptyParams() {
        Assert.assertFalse( PagarMe.validateRequestSignature("", "") );
        Assert.assertFalse( PagarMe.validateRequestSignature(null, null) );        
    }

    @Test
    public void testPostbackValidation() throws Throwable {
        Scanner postbackFile = new Scanner(new FileReader("src/test/resources/__files/1/postback.json"));
        JsonParser parser = new JsonParser();
        JsonObject jo = parser.parse(postbackFile.useDelimiter("\\Z").next()).getAsJsonObject();
        Postback postback = JSONUtils.getAsObject(jo, Postback.class);
        postbackFile.close();

        Assert.assertTrue(PagarMe.validateRequestSignature(postback.getPayload(), postback.getSignature()));
    }
}
