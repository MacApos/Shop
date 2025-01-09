import React, {useState} from 'react';
import {useInput} from "../hook/useInput";

export const Login = () => {
    const [state, input]= useInput({email: "", password: ""});

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(state.email);
        console.log(state.password);
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