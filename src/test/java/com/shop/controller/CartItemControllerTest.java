package com.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.common.AbstractControllerTest;
import com.shop.model.CartItem;
import com.shop.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CartItemControllerTest extends AbstractControllerTest {
    @Autowired
    public MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenValidCartItem_whenPostRequestToCreate_thenStatusIsOk() throws Exception {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(new Product());
        cartItem.setQuantity(1);
        String json = objectMapper.writeValueAsString(cartItem);

        mockMvc.perform(postRequestBuilder("/cart-item/create", json))
                .andExpect(status().isOk());
    }

    @Test
    void givenValidCartItem_whenPutRequestToUpdate_thenStatusIsOk() throws Exception {
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setQuantity(5);
        String json = objectMapper.writeValueAsString(cartItem);

        mockMvc.perform(putRequestBuilder("/cart-item/update/1", json))
                .andExpect(status().isOk());
    }

    @Test
    void givenValidCartItem_whenDeleteRequestToDelete_thenStatusIsOk() throws Exception {
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        String json = objectMapper.writeValueAsString(cartItem);

        mockMvc.perform(deleteRequestBuilder("/cart-item/delete/1", json))
                .andExpect(status().isOk());
    }

    @Test
    void givenInvalidCartItem_whenPostRequestToCreate_thenBadRequest() throws Exception {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(null);
        cartItem.setQuantity(0);
        String json = objectMapper.writeValueAsString(cartItem);

        mockMvc.perform(postRequestBuilder("/cart-item/create", json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenNonExistentCartItem_whenPutRequestToUpdate_thenNotFound() throws Exception {
        CartItem cartItem = new CartItem();
        cartItem.setId(999L);
        cartItem.setQuantity(5);
        String json = objectMapper.writeValueAsString(cartItem);

        mockMvc.perform(put("/cart-item/update/999")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenNonExistentCartItem_whenDeleteRequestToDelete_thenNotFound() throws Exception {
        CartItem cartItem = new CartItem();
        cartItem.setId(999L);
        String json = objectMapper.writeValueAsString(cartItem);

        mockMvc.perform(delete("/cart-item/delete/999")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}