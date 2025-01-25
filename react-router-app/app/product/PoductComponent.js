import React from 'react';

export const ProductComponent = () => {
    const url = "http://localhost:8080";
    const fetchCategories = async () => {
        const response = await fetch(url + "/category/all", {
            method: "GET",
        });
        const json = await response.json();
        console.log(json);
    };

    return (
        <>
            <button onClick={fetchCategories}>Fetch</button>
        </>
    );
};