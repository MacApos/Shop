import React from 'react';
import {type ActionFunctionArgs, redirect} from "react-router";
import type {Route} from "./+types/login";
import ValidatedForm from "~/common/ValidatedForm";
import ValidatedInput from "~/common/ValidatedInput";
import {login} from "~/data";
import {jwtToken} from "~/cookie.server";
import {emailValidation} from "~/routes/registration";

export async function action({request}: ActionFunctionArgs) {
    const formData = await request.formData();
    const data = Object.fromEntries(formData);
    const response = await login(data);

    if (response.ok) {
        const jwtCookie = await jwtToken.serialize(response.body.jwt);
        return redirect("/", {
            headers: {
                "Set-Cookie": jwtCookie
            }
        });
    }

    return {
        errors: {
            email: [],
            password: ["Invalid email and/or password"]
        }
    };
}

export default function Login({actionData}: Route.ComponentProps) {
    const errors = actionData?.errors;

    return (
        <div className={"w-75 m-auto"}>
            <ValidatedForm errors={errors}
                           shouldRevokeValidation={true}>
                <ValidatedInput validationFunction={emailValidation}>
                    <input name={"email"} type={"email"} defaultValue={"admin@gmail.com"}/>
                </ValidatedInput>
                <ValidatedInput defaultError={["Invalid password"]}>
                    <input name={"password"} type={"password"} defaultValue={"admin"}/>
                </ValidatedInput>
            </ValidatedForm>
        </div>
    );
}

