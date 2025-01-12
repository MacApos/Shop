import React, {useState} from 'react';
import {useInput} from "../hook/useInput";

export const getFetchParams = (path, method = "GET", body,
                               credentials = "include") => {
    const input = `http://localhost:8080/${path}`;
    const init = {
        method: method,
        credentials: "include",
        headers: {
            "Accept": "*/*",
            "Content-Type": "application/json",
        }
    };
    if (body !== null) {
        init.body = JSON.stringify(body);
    }

    return {input, init};

};

export const fetchWithParams = (path, method = "GET", body = null, credentials = "include") => {
    const input = `http://localhost:8080/${path}`;
    const init = {
        method,
        credentials,
        headers: {
            "Accept": "*/*",
            "Content-Type": "application/json",
        }
    };
    if (body !== null) {
        init.body = JSON.stringify(body);
    }


    return fetch(input, init);
};

export const Login = () => {
    const [state, input] = useInput({email: "", password: ""});


    const handleSubmit = async (e) => {
        e.preventDefault();
        let response = await fetchWithParams("login", "POST", state, "omit");
        if (response.ok) {
            const json = await response.json();
            console.log(json);
        }
        console.log("Bad credentials");
    };

    return (
        <>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Email: </label>
                    <input type={"text"} {...input.email}/>
                </div>
                <div>
                    <label>Password: </label>
                    <input type={"password"} {...input.password}/>
                </div>
                <div>
                    <input type={"submit"} value={"Log in"}/>
                </div>
            </form>
        </>
    );
};
;