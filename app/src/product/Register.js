import React from 'react';
import {useInput} from "../hook/useInput";

export const Register = () => {
    const [state, input]= useInput({
        username:"",
        firstname:"",
        lastname:"",
        email: "",
        password: ""});

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(state.email);
        console.log(state.password);
    };


    let {username, firstname, lastname, email, password} = input;
    return (
        <>
            <form onSubmit={handleSubmit}>
                <label>First name</label>
                <input type={"text"} {...firstname} required minLength={3}/>
                <label>Last name</label>
                <input type={"text"} {...lastname} required minLength={3}/>
                <label>Username</label>
                <input type={"text"} {...username} required minLength={3}/>
                <label>Email</label>
                <input type={"email"} {...email}/>
                <label>Password</label>
                <input type={"password"} {...password}/>
                <input type={"submit"} value={"Register"}/>
            </form>
        </>
    );
};