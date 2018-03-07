package io.transwarp.tdc.gn.common;

import io.transwarp.tdc.gn.common.seder.*;
import io.transwarp.tdc.gn.schema.concrete.Promotion;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * 18-2-11 created by zado
 */
public class TestSeder {

    private Zado zado;

    private Promotion promotion;
    private String promotionStr;

    @Before
    public void setUp() throws Exception {
        zado = new Zado();
        zado.setName("zado");
        zado.setAge(233);

        promotion = new Promotion();
        promotion.setId(1L);
        promotion.setContent("greed is good");
        promotion.setCreateTime(new Date(1519456018996L));

        promotionStr = "{\"id\":1,\"type\":\"ADVERTISEMENT\",\"description\":\"Promotion\",\"content\":\"greed is good\",\"level\":\"LOW\",\"createTime\":1519456018996}";
    }

    @Test
    public void testSer() {
        PayloadSerializer<Promotion> serializer = new JacksonPayloadSerializer<>();
        Assert.assertEquals(promotionStr, serializer.serialize(promotion));
    }

    @Test
    public void testSerRegistry() {
        PayloadSerializer<Promotion> serializer = SerializerRegistry.get(Promotion.class);
        Assert.assertEquals(promotionStr, serializer.serialize(promotion));
    }

    @Test
    public void testCustomSer() {
        PayloadSerializer<Promotion> serializer = new CustomSerializer();
        Assert.assertEquals(promotionStr, serializer.serialize(promotion));
    }

    @Test
    public void testDer() {
        PayloadDeserializer<Promotion> deserializer = new JacksonPayloadDeserializer<>(Promotion.class);
        Promotion dePromotion = deserializer.deserialize(promotionStr);
        assertPromotion(promotion, dePromotion);
    }

    @Test
    public void testDerRegistry() {
        PayloadDeserializer<Promotion> deserializer = DeserializerRegistry.get(Promotion.class);
        Promotion dePromotion = deserializer.deserialize(promotionStr);
        assertPromotion(promotion, dePromotion);
    }

    @Test
    public void testCustomDer() {
        PayloadDeserializer<Promotion> deserializer = new CustomDeserializer();
        Promotion dePromotion = deserializer.deserialize(promotionStr);
        assertPromotion(promotion, dePromotion);
    }

    private void assertPromotion(Promotion expect, Promotion actual) {
        Assert.assertEquals(expect.getId(), actual.getId());
        Assert.assertEquals(expect.getType(), actual.getType());
        Assert.assertEquals(expect.getDescription(), actual.getDescription());
        Assert.assertEquals(expect.getContent(), actual.getContent());
        Assert.assertEquals(expect.getLevel(), actual.getLevel());
        Assert.assertEquals(expect.getCreateTime(), actual.getCreateTime());
    }

    private static class CustomSerializer extends JacksonPayloadSerializer<Promotion> {
    }

    private static class CustomDeserializer extends JacksonPayloadDeserializer<Promotion> {
    }
}
