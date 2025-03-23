import React, {useEffect} from 'react';
import ValidatedForm from "~/common/ValidatedForm";
import ValidatedInput from "~/common/ValidatedInput";
import {type ActionFunctionArgs, useNavigate} from "react-router";
import {createEntity, defaultInit, EntityEnum, login} from "~/data";
import type {Route} from "../../.react-router/types/app/routes/+types/registration";
import {useAppDispatch} from "~/hooks";
import {setUser} from "~/features/userSlice";
import {emailValidation} from "~/routes/registration";

export async function action({request}: ActionFunctionArgs) {
    const formData = await request.formData();
    const data = Object.fromEntries(formData);

    // const login = await fetch("http://localhost:8080/login",{
    //     method: "POST",
    //     credentials: "include",
    //     headers: {
    //         "Accept": "application/json",
    //         "Content-Type": "application/json",
    //     },
    //     body: JSON.stringify(data),
    // });

    // let response = await login.json();
    // response = {
    //     ...response,
    //     ok: login.ok
    // };

    // return {data, response};
}

export default function Login({actionData}: Route.ComponentProps) {
    // const response = actionData?.response;
    // const body = response?.body;

    const handleSubmit = async () => {
        const login = await fetch("http://localhost:8080/login", {
            method: "POST",
            credentials: "include",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json",
            },
            body: JSON.stringify({username: "admin", password: "admin"}),
        });
        const newVar = await login.json();
    };

    return (
        <>
            <button onClick={handleSubmit}>Login</button>
            {/*<ValidatedForm actionData={response && !response.ok ? {*/}
            {/*    data: {username: actionData.data.username},*/}
            {/*    errors: body*/}
            {/*} : undefined}>*/}
            {/*    <ValidatedInput validationFunction={emailValidation}>*/}
            {/*        <input name={"email"} defaultValue={"admin@gmail.com"}/>*/}
            {/*    </ValidatedInput>*/}
            {/*    <ValidatedInput>*/}
            {/*        <input name={"password"} type={"password"} defaultValue={"admin"}/>*/}
            {/*    </ValidatedInput>*/}
            {/*</ValidatedForm>*/}
        </>
    );
}

