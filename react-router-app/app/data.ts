import React from 'react';

const url = "http://localhost:8080";

const initialInit: { [key: string]: any } = {
    method: "GET",
    headers: {
        "Content-Type": "application/json",
    }
}

async function fetchData(path: string, init = initialInit) {
    const response = await fetch(url + path, init);
    console.log(response)
    return await response.json();
}

export async function getCategories(id: number) {
    return await fetchData(id === null ? "/category/all" : `/category/${id}`)
}

export async function getCategory(id: number) {
    return await fetchData(`/category/${id}`);
}

export const CREATE = "create";
export const READ = "get";
export const UPDATE = "update";
export const DELETE = "delete";
export const CONFIRM = "confirm-registration";


export enum Entity {
    CATEGORY = "category",
    PRODUCT = "product",
    USER = "user",
}

export async function create(body: { [key: string]: any }, entity: Entity) {
    return await fetchData(`/${entity}/${CREATE}`,
        {
            ...initialInit,
            body: JSON.stringify(body),
            method: "POST"
        });
}

export async function confirm(entity: Entity, token: string) {
    return await fetchData(`/${entity}/${CONFIRM}?token=${token}`);
}