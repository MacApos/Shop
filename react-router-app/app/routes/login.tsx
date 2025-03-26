import React, {useEffect} from 'react';
import {type ActionFunctionArgs, data, redirect, useNavigate} from "react-router";
import type {Route} from "../../.react-router/types/app/routes/+types/registration";
import {useAppDispatch} from "~/hooks";
import ValidatedForm from "~/common/ValidatedForm";
import ValidatedInput from "~/common/ValidatedInput";
import {secretToken} from "~/data";
import {jwtToken, userCookie} from "~/cookie.server";
import {setUser} from "~/features/userSlice";
import {emailValidation} from "~/routes/registration";

export async function loader({request,}: Route.LoaderArgs) {
    const cookie = request.headers.get("Cookie");
    if (cookie) {
        const cookieHeader = cookie.split("; ").find(cookie => cookie.startsWith("user"));
        if (cookieHeader) {
            const cookie = (await userCookie.parse(cookieHeader)) || {};
            return data(
                {user: cookie.user},
                {
                    headers: {
                        "Set-Cookie": await userCookie.serialize({}, {maxAge: 0}),
                    }
                }
            );
        }
    }
    return {user: undefined};
}

export async function action({request}: ActionFunctionArgs) {
    const formData = await request.formData();
    const data = Object.fromEntries(formData);
    const response = await secretToken(data);

    if (response.ok) {
        let token = await jwtToken.serialize("");
        token = token.replace("jwt=;", `jwt=${response.body.token};`);
        const user = await userCookie.serialize({user: response.body.user});

        const headers = new Headers();
        headers.append("Set-Cookie", user);
        headers.append("Set-Cookie", token);

        return redirect("/login", {
            headers
        });
    }
    if (response.status === 401) {
        return {
            data, response: {
                ...response,
                body: {
                    email:[],
                    password: ["Invalid email or password"]
                }
            }
        };
    }
    return {data, response};
}

export default function Login({loaderData, actionData}: Route.ComponentProps) {
    const user = loaderData?.user;
    const response = actionData?.response;
    const body = response?.body;
    const navigate = useNavigate();
    const dispatch = useAppDispatch();

    useEffect(() => {
        if (user) {
            dispatch(setUser(user));
            navigate("/");
        }
    }, [user]);

    return (
        <>
            <ValidatedForm actionData={response && !response.ok ? {data: actionData.data, errors: body} : undefined}
                           shouldRevokeValidation={true}>
                <ValidatedInput validationFunction={emailValidation}>
                    <input name={"email"} type={"email"} defaultValue={"admn@gmail.com"}/>
                </ValidatedInput>
                <ValidatedInput defaultError={["Invalid password"]}>
                    <input name={"password"} type={"password"} defaultValue={"admin"}/>
                </ValidatedInput>
            </ValidatedForm>
        </>
    );
}

