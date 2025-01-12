import React, {useState} from 'react';
import {useInput} from "../hook/useInput";

export const Login = () => {
    const [state, input] = useInput({email: "", password: ""});

    const fetching = async (body) => {
        const response = await fetch("http://localhost:8080/login", {
            method: "POST",
            // credentials: true,
            body: JSON.stringify(body),
            headers: {
                "Accept": "*/*",
                "Content-Type": "application/json",
            }
        });
        console.log(response);
        if (response.status === 401) {
            console.log("Bad credentials");
            return;
        }


        const newVar = await response.json();
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        fetching(state);
    };

    return (
        <>
            <form onSubmit={handleSubmit}>
                <input type={"text"} {...input.email}/>
                <input type={"text"} {...input.password}/>
                <input type={"submit"} value={"Log in"}/>
            </form>
        </>
    );
};