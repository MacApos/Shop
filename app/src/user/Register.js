import React from 'react';
import {useInput} from "../hook/useInput";
import {fetchWithParams} from "./Login";

export const Register = () => {
    const [state, input] = useInput({
        username: "BigZbig",
        firstname: "Zbigniew",
        lastname: "Nowak",
        email: "zbigIsBig@gmail.com",
        password: "zbig"
    });


    const handleSubmit = async (e) => {
        e.preventDefault();
        const response = await fetchWithParams("user/create", "POST", state);
        if (response.ok) {
            const json = await response.json();
            console.log(json);
        }
        console.log("Something went wrong");
    };

    // const map = Object.entries(input).map(([key, value]) => {
    //     let type = "text";
    //
    //     if (key === "password" || key === "email") {
    //         type = key;
    //     }
    //
    //     return (
    //         <div key={key}>
    //             <label>{key} </label>
    //             <input type={type} {...value} required minLength={3}/>
    //         </div>
    //     );
    // });

    let {username, firstname, lastname, email, password} = input;
    return (
        <>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Username: </label>
                    <input type={"text"} {...username}
                           // required minLength={3}
                    />
                </div>
                <div>
                    <label>Firstname: </label>
                    <input type={"text"} {...firstname}
                           // required minLength={3}
                    />
                </div>
                <div>
                    <label>Lastname: </label>
                    <input type={"text"} {...lastname}
                           // required minLength={3}
                    />
                </div>
                <div>
                    <label>Email: </label>
                    <input type={"email"} {...email}
                           required minLength={3}
                    />
                </div>
                <div>
                    <label>Password: </label>
                    <input type={"password"} {...password} required />
                </div>
                <input type={"submit"} value={"Register"}/>
            </form>
        </>
    );
};